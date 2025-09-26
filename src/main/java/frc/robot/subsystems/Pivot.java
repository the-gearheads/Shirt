package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;
import static frc.robot.Constants.PivotConstants.*;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants;
import frc.robot.controllers.Controllers;

public class Pivot extends SubsystemBase {
  private final SparkMax pivotMotor = new SparkMax(PIVOT_ID, MotorType.kBrushless);
  private final SparkMaxConfig pivotConfig = new SparkMaxConfig();
  private final ProfiledPIDController pid = new ProfiledPIDController(PID_CONSTANTS[0], PID_CONSTANTS[1], PID_CONSTANTS[2], PID_CONSTRAINTS);
  private final AbsoluteEncoder absEncoder = pivotMotor.getAbsoluteEncoder();
  private final RelativeEncoder encoder = pivotMotor.getEncoder();
  private final ArmFeedforward pivotFeedforward = new ArmFeedforward(PIVOT_KS, PIVOT_KG, PIVOT_KV, PIVOT_KA);

  protected double manualVoltage = 0;

  public Pivot() {
    configure();
    pid.reset(getRelAngle().getRadians());
    pid.setGoal(getRelAngle().getRadians());
    pid.setTolerance(ANGLE_TOLERANCE.getRadians());
  }

  @Override
  public void periodic() {
    // SmartDashboard.putData(pid);
    if(DriverStation.isDisabled()) {
      syncIntegratedEncoder();
    }
   
    // double pidOutput = 0, ff = 0;
    // // https://gist.github.com/person4268/46710dca9a128a0eb5fbd93029627a6b
    // if (Math.abs(Units.radiansToDegrees(
    //     getRelAngle().getRadians() - pid.getSetpoint().position)) > PIVOT_ANGLE_LIVE_FF_THRESHOLD) {
    //   ff = pivotFeedforward.calculate(getRelAngle().getRadians(), pid.getSetpoint().velocity);
    // } else {
    //   ff = pivotFeedforward.calculate(pid.getSetpoint().position, pid.getSetpoint().velocity);
    // }

    // pidOutput = pid.calculate(getRelAngle().getRadians());
    
    // double output = pidOutput + ff;

    // if (output > 0 && getAbsAngle().getRadians() > MAX_ANGLE.getRadians()) {
    //   output = 0;
    // }

    // if (output < 0 && getAbsAngle().getRadians() < MIN_ANGLE.getRadians()) {
    //   output = 0;
    // }

    // if (pid.getGoal().position < MIN_ANGLE.getRadians() || pid.getGoal().position > MAX_ANGLE.getRadians()) {
    //   pid.setGoal(MathUtil.clamp(pid.getGoal().position, MIN_ANGLE.getRadians(), MAX_ANGLE.getRadians()));
    // }

    // setVoltage(output);

    SmartDashboard.putNumber("Pivot/absAngle", getAbsAngle().getRadians());
    SmartDashboard.putNumber("Pivot/relAngle", getRelAngle().getRadians());

    if (manualVoltage > 0 && getAbsAngle().getRadians() > MAX_ANGLE.getRadians()) {
      manualVoltage = 0;
    }

    if (manualVoltage < 0 && getAbsAngle().getRadians() < MIN_ANGLE.getRadians()) {
      manualVoltage = 0;
    }

    setMotorVoltage(manualVoltage);
    // SmartDashboard.putNumber("Pivot/getGoal", getGoal().getRadians());
  }

  public void configure() {
    pivotConfig.smartCurrentLimit(60);
    pivotConfig.voltageCompensation(12);
    pivotConfig.idleMode(IdleMode.kBrake);
    pivotConfig.encoder.positionConversionFactor(POS_FACTOR);
    pivotConfig.encoder.velocityConversionFactor(VEL_FACTOR);

    pivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public Rotation2d getAbsAngle() {
    return new Rotation2d((PIVOT_ABS_ENCODER_OFFSET + Units.rotationsToRadians(absEncoder.getPosition())) % (2 * Math.PI));
  }

  public Rotation2d getRelAngle() {
    return Rotation2d.fromRadians(encoder.getPosition());
  }

  public void setAngle(Rotation2d angle) {
    pid.setGoal(angle.getRadians());
  }

  public Rotation2d getGoal() {
    return Rotation2d.fromRadians(pid.getGoal().position);
  }

  protected void setMotorVoltage(Voltage v) {
    pivotMotor.setVoltage(v);
  }

  public void setVoltage(double v) {
    manualVoltage = v;
  }

  protected void setMotorVoltage(double v) {
    pivotMotor.setVoltage(v);
  } 

  public boolean atPoint(Rotation2d angle) {
    return atPoint(angle, ANGLE_TOLERANCE);
  }

  public boolean atPoint(Rotation2d angle, Rotation2d tolerance) {
    return MathUtil.isNear(getAbsAngle().getRadians(), angle.getRadians(), tolerance.getRadians());
  }

  public SysIdRoutine getSysidRoutine() {
    return new SysIdRoutine(
      new SysIdRoutine.Config(Volts.of(0.8).per(Seconds), Volts.of(3.2), null,
          (state) -> SmartDashboard.putString("Pivot/SysIdTestState", state.toString())),
      new SysIdRoutine.Mechanism((Voltage v) -> {
        setMotorVoltage(v);
      }, null, this)
    );
  }

  public void syncIntegratedEncoder() {
    encoder.setPosition(getAbsAngle().getRadians());
  }
}
