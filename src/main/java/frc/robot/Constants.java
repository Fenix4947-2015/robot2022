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

        private static final double kGearRatioLow = 10.86;
        private static final double kGearRatioHigh = 6.0;
        private static final double kWheelDiameterMeters = Units.inchesToMeters(6.0);

        public static final double kEncoderPositionConversionFactorLow = (Math.PI * kWheelDiameterMeters) / kGearRatioLow;          // in meters
        public static final double kEncoderVelocityConversionFactorLow = (Math.PI * kWheelDiameterMeters) / (kGearRatioLow * 60.0); // in meters per second
        public static final double kEncoderPositionConversionFactorHigh = (Math.PI * kWheelDiameterMeters) / kGearRatioHigh;          // in meters
        public static final double kEncoderVelocityConversionFactorHigh = (Math.PI * kWheelDiameterMeters) / (kGearRatioHigh * 60.0); // in meters per second

        public static final int kSpareTalonDeviceNumber = 9;

        public static final double kTurnP = 0.8;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0;
        public static final double kTurnToleranceDegrees = 5;
        public static final double kTurnRateToleranceDegreesPerSec = 10; // degrees per second
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
        public static final int kTopDeviceId = 41;
        public static final int kLimitSwitchClosedId = 1;
        public static final int kLimitSwitchOpenId = 0;

        public static final int kLatchSolenoidChannelId = 4;
        public static final int kEncoderDeviceId1 = 6;
        public static final int kEncoderDeviceId2 = 7;

        public static final boolean kUnlatchWinchOnlyAtEndOfMatch = false;
    }     
}
