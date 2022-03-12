package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveTrainConstants;

public class DriveTrain extends SubsystemBase {

    private final CANSparkMax m_leftLeader = new CANSparkMax(DriveTrainConstants.kLeftLeaderDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_leftFollower = new CANSparkMax(DriveTrainConstants.kLeftFollowerDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_rightLeader = new CANSparkMax(DriveTrainConstants.kRightLeaderDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_rightFollower = new CANSparkMax(DriveTrainConstants.kRightFollowerDeviceId, MotorType.kBrushless);

    private final Solenoid m_shifter = new Solenoid(PneumaticsModuleType.CTREPCM, DriveTrainConstants.kShifterSolenoidChannelId);

    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader, m_rightLeader);
    private final RelativeEncoder m_leftLeaderEncoder = m_leftLeader.getEncoder();
    private final RelativeEncoder m_rightLeaderEncoder = m_rightLeader.getEncoder();

    public DriveTrain() {
        m_leftLeader.restoreFactoryDefaults();
        m_leftFollower.restoreFactoryDefaults();
        m_rightLeader.restoreFactoryDefaults();
        m_rightFollower.restoreFactoryDefaults();

        m_leftLeader.setIdleMode(IdleMode.kBrake);
        m_leftFollower.setIdleMode(IdleMode.kBrake);
        m_rightLeader.setIdleMode(IdleMode.kBrake);
        m_rightFollower.setIdleMode(IdleMode.kBrake);

        m_leftLeader.setInverted(true);
        m_leftFollower.setInverted(true);

        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);

        m_leftLeaderEncoder.setPositionConversionFactor(DriveTrainConstants.kEncoderPositionConversionFactor);
        m_rightLeaderEncoder.setPositionConversionFactor(DriveTrainConstants.kEncoderPositionConversionFactor);

        m_leftLeaderEncoder.setVelocityConversionFactor(DriveTrainConstants.kEncoderVelocityConversionFactor);
        m_rightLeaderEncoder.setVelocityConversionFactor(DriveTrainConstants.kEncoderVelocityConversionFactor);

        shiftLow();
        reset();

        addChild("Drive", m_drive);
        addChild("Shifter", m_shifter);
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        m_drive.arcadeDrive(xSpeed, zRotation);
    }

    public double getPosition() {
        return (m_leftLeaderEncoder.getPosition() + m_rightLeaderEncoder.getPosition()) / 2.0;
    }

    public void reset() {
        m_leftLeaderEncoder.setPosition(0.0);
        m_rightLeaderEncoder.setPosition(0.0);
    }

    public void shiftHigh() {
        m_shifter.set(true);
    }

    public void shiftLow() {
        m_shifter.set(false);
    }

    public void stop() {
        m_drive.arcadeDrive(0.0, 0.0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Distance (m)", m_leftLeaderEncoder.getPosition());
        SmartDashboard.putNumber("Left Speed (m/s)", m_leftLeaderEncoder.getVelocity());
        SmartDashboard.putNumber("Left Bus Voltage", m_leftLeader.getBusVoltage());
        SmartDashboard.putNumber("Left Current", m_leftLeader.getOutputCurrent());
        SmartDashboard.putNumber("Left Applied Output", m_leftLeader.getAppliedOutput());
        SmartDashboard.putNumber("Left Temperature (C)", m_leftLeader.getMotorTemperature());

        SmartDashboard.putNumber("Right Distance (m)", m_rightLeaderEncoder.getPosition());
        SmartDashboard.putNumber("Right Speed (m/s)", m_rightLeaderEncoder.getVelocity());
        SmartDashboard.putNumber("Right Bus Voltage", m_rightLeader.getBusVoltage());
        SmartDashboard.putNumber("Right Current", m_rightLeader.getOutputCurrent());
        SmartDashboard.putNumber("Right Applied Output", m_rightLeader.getAppliedOutput());
        SmartDashboard.putNumber("Right Temperature (C)", m_rightLeader.getMotorTemperature());
    }
}
