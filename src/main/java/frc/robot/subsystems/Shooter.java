package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.controllers.Controllers;

public class Shooter extends SubsystemBase {
  TalonFX shooter = new TalonFX(10);
  TalonFX shooterFolllower = new TalonFX(11);
  double output = 0;
  public double getOutput() {
    return output;
  }

  public void setOutput(double output) {
    this.output = output;
  }

  DutyCycleOut shooterOut = new DutyCycleOut(0.0);
  public Shooter() {
    configure();
    shooterFolllower.setControl(new Follower(shooter.getDeviceID(), true));
  }

  @Override
  public void periodic() {
    // Controllers.driverController.getYBtn().onTrue(Commands.runEnd(()->{shooter.setControl(shooterOut.withOutput(1));}, ()->{shooter.setControl(shooterOut.withOutput(0));}, this));
    shooter.setControl(shooterOut.withOutput(-output));
  }

  public void configure() {
    TalonFXConfiguration mainConfig = new TalonFXConfiguration();
    TalonFXConfiguration followConfig = new TalonFXConfiguration();

    mainConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    followConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    mainConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    followConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
  }
}
