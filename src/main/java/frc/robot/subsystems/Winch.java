package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WinchConstants;

public class Winch extends SubsystemBase {

    private final CANSparkMax m_leader = new CANSparkMax(WinchConstants.kLeaderDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_follower = new CANSparkMax(WinchConstants.kFollowerDeviceId, MotorType.kBrushless);

    private final RelativeEncoder m_leaderEncoder = m_leader.getEncoder();
    private final RelativeEncoder m_followerEncoder = m_follower.getEncoder();

    public Winch() {
        m_leader.restoreFactoryDefaults();
        m_follower.restoreFactoryDefaults();

        m_leader.setIdleMode(IdleMode.kBrake);
        m_follower.setIdleMode(IdleMode.kBrake);

        m_follower.follow(m_leader, true);
    }

    public void pull(double speed) {
        m_leader.set(speed);
    }

    public void stop() {
        m_leader.set(0.0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Winch Leader Speed (RPM)", m_leaderEncoder.getVelocity());
        SmartDashboard.putNumber("Winch Leader Bus Voltage", m_leader.getBusVoltage());
        SmartDashboard.putNumber("Winch Leader Current", m_leader.getOutputCurrent());
        SmartDashboard.putNumber("Winch Leader Applied Output", m_leader.getAppliedOutput());
        SmartDashboard.putNumber("Winch Leader Temperature (C)", m_leader.getMotorTemperature());

        SmartDashboard.putNumber("Winch Follower Speed (RPM)", m_followerEncoder.getVelocity());
        SmartDashboard.putNumber("Winch Follower Bus Voltage", m_follower.getBusVoltage());
        SmartDashboard.putNumber("Winch Follower Current", m_follower.getOutputCurrent());
        SmartDashboard.putNumber("Winch Follower Applied Output", m_follower.getAppliedOutput());
        SmartDashboard.putNumber("Winch Follower Temperature (C)", m_follower.getMotorTemperature());        
    }
}
