// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.controllers.Controllers;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;


public class RobotContainer {

  Drive drive = new Drive();
  Pivot pivot = new Pivot();
  Shooter shooter = new Shooter();
  SendableChooser<Command> sysidChooser;

  public RobotContainer() {
    // Configure the trigger bindings
    sysidChooser = new SendableChooser<>();
    sysidChooser.addOption("Pivot Quasi Forward", pivot.getSysidRoutine().quasistatic(Direction.kForward));
    sysidChooser.addOption("Pivot Quasi Reverse", pivot.getSysidRoutine().quasistatic(Direction.kReverse));
    sysidChooser.addOption("Pivot Dynamic Forward", pivot.getSysidRoutine().dynamic(Direction.kForward));
    sysidChooser.addOption("Pivot Dynamic Reverse", pivot.getSysidRoutine().dynamic(Direction.kReverse));
    SmartDashboard.putData(sysidChooser);
    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    if (!Controllers.didControllersChange())
      return;
    
    // Clear buttons
    CommandScheduler.getInstance().getActiveButtonLoop().clear();

    // Find new controllers
    Controllers.updateActiveControllerInstance();

    Controllers.driverController.getXBtn().onTrue(Commands.runOnce(() -> {shooter.setOutput(1);}, shooter));
    Controllers.driverController.getXBtn().onFalse(Commands.runOnce(() -> {shooter.setOutput(0);}, shooter));


    Controllers.driverController.getYBtn().onTrue(Commands.runOnce(() -> {pivot.setVoltage(4);}, pivot));
    Controllers.driverController.getBBtn().onTrue(Commands.runOnce(() -> {pivot.setVoltage(-4);}, pivot));

    Controllers.driverController.getYBtn().onFalse(Commands.runOnce(() -> {pivot.setVoltage(0);}, pivot));
    Controllers.driverController.getBBtn().onFalse(Commands.runOnce(() -> {pivot.setVoltage(0);}, pivot));



  }


  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Commands.runOnce(() -> {System.out.println("bruh");});
    return sysidChooser.getSelected();
    // return shooter.run(() -> {shooter.setOutput(1);});
  }
}
