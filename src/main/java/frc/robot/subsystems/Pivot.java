// package frc.robot.subsystems;

// import static edu.wpi.first.units.Units.Rotation;
// import static frc.robot.Constants.PivotConstants.*;

// import com.revrobotics.AbsoluteEncoder;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.config.SparkMaxConfig;

// import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.units.measure.Voltage;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
// import frc.robot.Constants;

// public class Pivot extends SubsystemBase {
//   private final SparkMax pivotMotor = new SparkMax(PIVOT_ID, MotorType.kBrushless);
//   private final SparkMaxConfig pivotConfig = new SparkMaxConfig();
//   private final ProfiledPIDController pid = new ProfiledPIDController(PID_CONSTANTS[0], PID_CONSTANTS[1], PID_CONSTANTS[2], PID_CONSTRAINTS);
//   private final AbsoluteEncoder encoder = pivotMotor.getAbsoluteEncoder();

//   public Pivot() {
//     configure();
//   }

//   public void configure() {
//     pivotConfig.smartCurrentLimit(60);
//     pivotConfig.voltageCompensation(12);
//   }

//   public Rotation2d getAngle() {
//     return Rotation2d.fromRadians(encoder.getPosition());
//   }

//   public SysIdRoutine getSysidRoutine() {
//     return new SysIdRoutine(
//       new SysIdRoutine.Config(Volts.of(0.8).per(Seconds), Volts.of(3.2), null,
//           (state) -> Logger.recordOutput("Pivot/SysIdTestState", state.toString())),
//       new SysIdRoutine.Mechanism((Voltage v) -> {
//         setMode(RunMode.VOLTAGE);
//         setVoltage(v);
//       }, null, this)
//     );
//   }

// }
