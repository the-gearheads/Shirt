// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Shoot;
import frc.robot.subsystems.Angler;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Controllers.DriverController;
//import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  @SuppressWarnings("unused")
  private final Drivebase Drivebase = new Drivebase();
  private final Angler Angler = new Angler();
  private final Shoot Shoot = new Shoot();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  public final DriverController driverController =
      new DriverController(OperatorConstants.kDriverControllerPort);
    
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    DriverController.createDriveController();
    
    CommandScheduler.getInstance().getActiveButtonLoop().clear();

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    driverController.povUp().onTrue(Angler.upCommand());
    driverController.povDown().onTrue(Angler.downCommand());

    driverController.povUp().onFalse(Angler.stopCommand());
    driverController.povDown().onFalse(Angler.stopCommand());
    //YBXA
    driverController.getYBtn().onTrue(Shoot.shootCommand(1.0));
    driverController.getYBtn().onFalse(Shoot.shootCommand(0.0));

    driverController.getBBtn().onTrue(Shoot.shootCommand(0.75));
    driverController.getBBtn().onFalse(Shoot.shootCommand(0.0));

    driverController.getXBtn().onTrue(Shoot.shootCommand(0.5));
    driverController.getXBtn().onFalse(Shoot.shootCommand(0.0));

    driverController.getABtn().onTrue(Shoot.shootCommand(0.25));
    driverController.getABtn().onFalse(Shoot.shootCommand(0.0));
    
  }

}
