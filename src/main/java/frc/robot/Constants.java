package frc.robot;

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

    public static final class ControllerConstants {
        public static final int kDriverPort = 0;
        public static final int kHelperPort = 1;

        public static final double kDeadband = 0.15;
    }

    public static final class DriveTrainConstants {
        public static final int kLeftLeaderDeviceId = 28;
        public static final int kLeftFollowerDeviceId = 27;

        public static final int kRightLeaderDeviceId = 24;
        public static final int kRightFollowerDeviceId = 23;

        public static final int kShifterSolenoidChannelId = 2;

        private static final double kGearRatio = 10.86;
        private static final double kWheelDiameterMeters = Units.inchesToMeters(6.0);

        public static final double kEncoderPositionConversionFactor = (Math.PI * kWheelDiameterMeters) / kGearRatio;          // in meters
        public static final double kEncoderVelocityConversionFactor = (Math.PI * kWheelDiameterMeters) / (kGearRatio * 60.0); // in meters per second

        public static final int kSpareTalonDeviceNumber = 9;
    }

    public static final class InstakeConstants {
        public static final int kMotorDeviceId = 36;

        public static final int kLatchSolenoidChannelId = 3;
    }

    public static final class ShooterConstants {
        public static final int kShooterBackDeviceId = 35;
        public static final int kShooterFrontDeviceId = 32;

        public static final int kCylindersSolenoidChannelId = 1;

        public static final double kSpinSpeed = 0.5;
        public static final double kBeforeSpinDelaySec = 0.5;
        public static final double kSpinDelaySec = 1.0;
        public static final double kAfterShotDelaySec = 1.0;
    }   

    public static final class WinchConstants {
        public static final int kLeaderDeviceId = 21;
        public static final int kFollowerDeviceId = 31;

        public static final int kLimitSwitchClosedId = 1;
        public static final int kLimitSwitchOpenId = 0;

        public static final int kLatchSolenoidChannelId = 4;
    }     
}
