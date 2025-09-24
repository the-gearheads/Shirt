package frc.robot.controllers;

import edu.wpi.first.wpilibj.DriverStation;

import static edu.wpi.first.math.MathUtil.applyDeadband;

public class Controllers {

  private Controllers() {}

  private static final int MAX_DRIVER_STATION_PORTS = DriverStation.kJoystickPorts; 
  private static String[] lastControllerNames = new String[MAX_DRIVER_STATION_PORTS];

  public static DriverController driverController;

  /** Returns true if the connected controllers have changed since last called. */
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

  public static void updateActiveControllerInstance() {
    String joyName;

    driverController = new DriverController(-1);

    for (int port = 0; port < MAX_DRIVER_STATION_PORTS; port++) {
      if (DriverStation.isJoystickConnected(port)) {
        joyName = DriverStation.getJoystickName(port);
        driverController = new DriverController(port);
      }
    }
  }

  public static double deadband(double num) {
    return applyDeadband(num, 0.02);
  }
}
