package frc.robot;

public class RobotMap {

  public static final boolean COMPET = true;
  public static final boolean ALWAYS_WINCH = true;

  // Joysticks
  public static final int JOYSTICK_DRIVER_PORT = 0;
  public static final int JOYSTICK_HELPER_PORT = 1;

  public static final Integer LEFT_MOTOR1_CAN_ID;
  public static final Integer LEFT_MOTOR2_CAN_ID;
  public static final Integer RIGHT_MOTOR1_CAN_ID;
  public static final Integer RIGHT_MOTOR2_CAN_ID;
  public static final Integer SHIFTER_SOLENOID_CHANNEL_ID;

  static {
    if (COMPET) {
      LEFT_MOTOR1_CAN_ID = 40;
      LEFT_MOTOR2_CAN_ID = 41;
      RIGHT_MOTOR1_CAN_ID = 21;
      RIGHT_MOTOR2_CAN_ID = 31;
      SHIFTER_SOLENOID_CHANNEL_ID = 0;
    } else {
      LEFT_MOTOR1_CAN_ID = 40;
      LEFT_MOTOR2_CAN_ID = 41;
      RIGHT_MOTOR1_CAN_ID = 21;
      RIGHT_MOTOR2_CAN_ID = 31;
      SHIFTER_SOLENOID_CHANNEL_ID = 0;
    }
  }

}
