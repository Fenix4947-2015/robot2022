package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class DriveTrain extends SubsystemBase {

  // main motion system
  private final CANSparkMax leftMotor1 = new CANSparkMax(RobotMap.LEFT_MOTOR1_CAN_ID, MotorType.kBrushless);
  private final CANSparkMax leftMotor2 = new CANSparkMax(RobotMap.LEFT_MOTOR2_CAN_ID, MotorType.kBrushless);
  private final CANSparkMax rightMotor1 = new CANSparkMax(RobotMap.RIGHT_MOTOR1_CAN_ID, MotorType.kBrushless);
  private final CANSparkMax rightMotor2 = new CANSparkMax(RobotMap.RIGHT_MOTOR2_CAN_ID, MotorType.kBrushless);
  private final DifferentialDrive robotDrive = new DifferentialDrive(leftMotor1, rightMotor1);

  private final RelativeEncoder m_leftEncoder = leftMotor1.getEncoder();
  private final RelativeEncoder m_rightEncoder = rightMotor1.getEncoder();

  private final Solenoid shifterSolenoid = RobotMap.SHIFTER_SOLENOID_CHANNEL_ID != null
      ? new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHIFTER_SOLENOID_CHANNEL_ID)
      : null;

  //private final Pigeon _pigeon;

  public DriveTrain() {
    //_pigeon = pigeon;
    // Initialize drivetrain motors
    // setMotorsAllowablePower(leftMotor1);
    // setMotorsAllowablePower(leftMotor2);

    // setMotorsAllowablePower(rightMotor1);
    // setMotorsAllowablePower(rightMotor2);

    // leftMotor2.setInverted(false);
    // leftMotor2.set(ControlMode.Follower, leftMotor1.getDeviceID());

    // rightMotor2.setInverted(false);
    // rightMotor2.set(ControlMode.Follower, rightMotor1.getDeviceID());

    // robotDrive.setSafetyEnabled(false);
    leftMotor1.restoreFactoryDefaults();
    leftMotor2.restoreFactoryDefaults();

    rightMotor1.restoreFactoryDefaults();
    rightMotor2.restoreFactoryDefaults();

    leftMotor2.follow(leftMotor1);
    leftMotor1.setInverted(true);
    rightMotor2.follow(rightMotor1);

    leftMotor1.setIdleMode(IdleMode.kBrake);
    leftMotor2.setIdleMode(IdleMode.kBrake);
    rightMotor1.setIdleMode(IdleMode.kBrake);
    rightMotor2.setIdleMode(IdleMode.kBrake);

    m_leftEncoder.setPositionConversionFactor(DriveTrainConstants.LEFT_ENCODER_DISTANCE_M_PER_TURN);
    // m_leftEncoder.setVelocityConversionFactor(DriveTrainConstants.ENCODER_VELOCITY_METER_PER_SECONDS);
    m_rightEncoder.setPositionConversionFactor(DriveTrainConstants.RIGHT_ENCODER_DISTANCE_M_PER_TURN);
    // m_rightEncoder.setVelocityConversionFactor(DriveTrainConstants.ENCODER_VELOCITY_METER_PER_SECONDS);

    resetEncoderAndHeading();
  }

  @Override
  public void periodic() {
    //_pigeon.refresh();

    SmartDashboard.putNumber("Drivetrain encoder left pos", m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Drivetrain encoder right pos", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Drivetrain encoder left velocity", m_leftEncoder.getVelocity());
    SmartDashboard.putNumber("Drivetrain encoder right velocity", m_rightEncoder.getVelocity());
    SmartDashboard.putNumber("Drivetrain encoder avg", getAverageEncoderDistance());
    //SmartDashboard.putNumber("Heading", getHeading());
  }
  
  public void driveArcadeMethod(double speed, double rotation) {
    robotDrive.arcadeDrive(speed, rotation);
  }

  public void stop() {
    robotDrive.arcadeDrive(0.0, 0.0);
  }

  public void shiftHigh() {
    if (shifterSolenoid != null) {
      shifterSolenoid.set(true);
    }
  }

  public void shiftLow() {
    if (shifterSolenoid != null) {
      shifterSolenoid.set(false);
    }
  }

  public void resetEncoders() {
    m_leftEncoder.setPosition(0.0);
    m_rightEncoder.setPosition(0.0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return ((m_leftEncoder.getPosition() * DriveTrainConstants.LEFT_ENCODER_DIRECTION)
        + (m_rightEncoder.getPosition() * DriveTrainConstants.RIGHT_ENCODER_DIRECTION)) / 2.0;
  }

  public void zeroHeading() {
    //_pigeon.resetHeading();
  }
/*
  public double getHeadingAbsolute() {
    // return Math.IEEEremainder(m_gyro.getAngle(), 360) *
    // (DriveTrainConstants.GYRO_REVERSED ? -1.0 : 1.0);
    return Math.IEEEremainder(_pigeon.getHeading(), 360) * (DriveTrainConstants.GYRO_REVERSED ? -1.0 : 1.0);
  }

  public double getHeading() {
    return _pigeon.getHeading();
  }
  */
  public void resetEncoderAndHeading() {
    resetEncoders();
    zeroHeading();
  }
}
