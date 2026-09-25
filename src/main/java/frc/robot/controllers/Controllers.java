package frc.robot.controllers;

import edu.wpi.first.wpilibj.DriverStation;

public class Controllers {
    public Controllers(){}

    private static final int MAX_DRIVERSTATION_PORTS = DriverStation.kJoystickPorts;

    public static DriveController driveController;

    public static void updateActiveControllers(){
        boolean foundDriveController = false;

        driveController = new DriveController(-1);

        for (int port = 0; port < MAX_DRIVERSTATION_PORTS; port++) {
            if (DriverStation.isJoystickConnected(port)) {
                if (!foundDriveController && DriverStation.getJoystickIsXbox(port)) {
                    foundDriveController = true;
                    driveController = new DriveController(port);
                }
            }
        }
    }
}
