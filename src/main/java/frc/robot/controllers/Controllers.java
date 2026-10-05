package frc.robot.controllers;

import edu.wpi.first.wpilibj.DriverStation;

public class Controllers {
    public Controllers(){}

    private static final int MAX_DRIVER_STATION_PORTS = DriverStation.kJoystickPorts;
    private static String[] lastControllerNames = new String[MAX_DRIVER_STATION_PORTS];

    public static DriveController cDriveController;

     public static boolean didControllersChange() {
    boolean hasChanged = false;
    String name;

    for (int i = 0; i < MAX_DRIVER_STATION_PORTS ; i++) {
      name = DriverStation.getJoystickName(i);
      if (!name.equals(lastControllerNames[i])) {
        hasChanged = true;
        lastControllerNames[i] = name;
      }
    }

    return hasChanged;
  }

    public static void updateActiveControllers(){
        boolean foundDriveController = false;

        cDriveController = new DriveController(-1);

        for (int port = 0; port < MAX_DRIVER_STATION_PORTS; port++) {
            if (DriverStation.isJoystickConnected(port)) {
                if (!foundDriveController && DriverStation.getJoystickIsXbox(port)) {
                    foundDriveController = true;
                    cDriveController = new DriveController(port);
                }
            }
        }
    }
}
