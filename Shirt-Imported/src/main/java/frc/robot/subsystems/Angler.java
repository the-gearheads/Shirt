// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

//import com.revrobotics.AbsoluteEncoder;
//import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.SparkBase.PersistMode;
//import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
//import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Angler extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private final SparkMax anglerMotor = new SparkMax(AnglerConstants.AnglerID, MotorType.kBrushless);
  @SuppressWarnings("unused")
  private final SparkMaxConfig anglerConfig = new SparkMaxConfig();


  public Angler() {
  }
  
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command upCommand() {
    return runOnce( () -> anglerMotor.setVoltage(4));
  }
  public Command downCommand() {
    return runOnce( () -> anglerMotor.setVoltage(-4));
  }
  public Command stopCommand() {
    return runOnce( () -> anglerMotor.setVoltage(0));
  }
  //Add PID to make the motion smoother

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

