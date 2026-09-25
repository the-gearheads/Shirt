package frc.robot.controllers;

import edu.wpi.first.wpilibj.DriverStation;

public class Controllers {
    public Controllers(){}

    private static final int MAX_DRIVERSTATION_PORTS = DriverStation.kJoystickPorts;

    public static DriveController cDriveController;

    public static void updateActiveControllers(){
        boolean foundDriveController = false;

        cDriveController = new DriveController(-1);

        for (int port = 0; port < MAX_DRIVERSTATION_PORTS; port++) {
            if (DriverStation.isJoystickConnected(port)) {
                if (!foundDriveController && DriverStation.getJoystickIsXbox(port)) {
                    foundDriveController = true;
                    cDriveController = new DriveController(port);
                }
            }
        }
    }
}
