// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.controllers.Controllers;

public class Drive extends SubsystemBase {
  /** Creates a new DriveSubsystem. */

  final TalonFX leftLeader = new TalonFX(3, "rio");
  final TalonFX rightLeader = new TalonFX(1, "rio");
  final TalonFX leftFollower = new TalonFX(4, "rio");
  final TalonFX rightFollower = new TalonFX(2, "rio");

  final DutyCycleOut leftRequest = new DutyCycleOut(0.0);
  final DutyCycleOut rightRequest = new DutyCycleOut(0.0);

  public Drive() {
    configureMotors();

    leftFollower.setControl(new Follower(leftLeader.getDeviceID(), false));
    rightFollower.setControl(new Follower(rightLeader.getDeviceID(), false));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double forward = Controllers.driverController.getTranslate();
    double turn = -Controllers.driverController.getRotateAxis();

    // Arcade drive logic
    double leftOut = forward + turn;
    double rightOut = forward - turn;

    // Set motor output
    leftLeader.setControl(leftRequest.withOutput(leftOut));
    rightLeader.setControl(rightRequest.withOutput(rightOut));
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /**
   * Configures the motor settings such as neutral mode and inversion.
   */
  public void configureMotors() {
    TalonFXConfiguration leftConfig = new TalonFXConfiguration();
    TalonFXConfiguration rightConfig = new TalonFXConfiguration();

    leftConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    rightConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    leftConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    rightConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    leftLeader.getConfigurator().apply(leftConfig);
    leftFollower.getConfigurator().apply(leftConfig); 
    rightLeader.getConfigurator().apply(rightConfig);
    rightFollower.getConfigurator().apply(rightConfig); 
  }
}