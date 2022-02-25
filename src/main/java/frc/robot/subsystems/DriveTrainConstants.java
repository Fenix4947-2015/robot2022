package frc.robot.subsystems;

public class DriveTrainConstants {

	// Which PID slot to pull gains from. Starting 2018, you can choose from 0, 1, 2 or 3.
	// Only the first two (0, 1) are visible in web-based configuration.
	public static final int SLOTIDX = 0;

	// Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops.
	// For now we just want the primary one.
	public static final int PID_LOOP_INDEX = 0;

	// Set to zero to skip waiting for confirmation, set to nonzero to wait and report to DS if action fails.
	public static final int TIMEOUT_MS = 10;

	// Choose so that Talon does not report sensor out of phase.
	public static final boolean SENSOR_PHASE = true;

	// Cchoose based on what direction you want to be positive, this does not affect motor invert.
	//public static boolean kMotorInvert = false;

	public static final double GO_STRAIGHT_COMPENSATION_DYNAMIC = 0;
	public static final double GO_STRAIGHT_COMPENSATION_STATIC = 0;
	
	public static final boolean GYRO_REVERSED = false;
	
	public static final double FEET_PER_METER = 0.3048;
	public static final double LEFT_ENCODER_DISTANCE_FEET_PER_TURN = 0.536;
	public static final double LEFT_ENCODER_DISTANCE_M_PER_TURN = LEFT_ENCODER_DISTANCE_FEET_PER_TURN * FEET_PER_METER;
	public static final double LEFT_ENCODER_DIRECTION = 1.0;
	public static final double RIGHT_ENCODER_DISTANCE_FEET_PER_TURN = 0.534;
	public static final double RIGHT_ENCODER_DISTANCE_M_PER_TURN = RIGHT_ENCODER_DISTANCE_FEET_PER_TURN * FEET_PER_METER;
	public static final double RIGHT_ENCODER_DIRECTION = -1.0;

}
