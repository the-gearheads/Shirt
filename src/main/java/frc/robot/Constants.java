// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.opencv.core.RotatedRect;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class DriveConstants {
    public static final double TRACK_WIDTH = Units.inchesToMeters(24.25);
  }

  public static class PivotConstants {
    public static final int PIVOT_ID = 8;
    public static final double RATIO = (28.0/14.0) * 100;

    public static final double[] PID_CONSTANTS = {1, 0, 0}; // placeholder
    public static final Constraints PID_CONSTRAINTS = new Constraints(Units.rotationsToRadians(2), Units.rotationsToRadians(1));


    public static final double PIVOT_KS = 0;
    public static final double PIVOT_KG = 0;
    public static final double PIVOT_KV = 0;
    public static final double PIVOT_KA = 0;
  
    public static final double PIVOT_ABS_ENCODER_OFFSET = (Math.PI / 2);

    // min max angles; sysid
    public static final Rotation2d ANGLE_TOLERANCE = Rotation2d.fromDegrees(3);

    public static final double PIVOT_ANGLE_LIVE_FF_THRESHOLD = 10;
    public static final Rotation2d MAX_ANGLE = Rotation2d.fromRadians(1.662);
    public static final Rotation2d MIN_ANGLE = Rotation2d.fromRadians(0.28);

    public static final double POS_FACTOR = (1.0 / RATIO) * 2 * Math.PI; // motor rotations -> axle radians
    public static final double VEL_FACTOR = ((1.0 / RATIO) * 2 * Math.PI) / 60.0 ;
  }
}
