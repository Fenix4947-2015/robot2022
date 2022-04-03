package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WinchConstants;

public class Winch extends SubsystemBase {

    private final CANSparkMax m_leader = new CANSparkMax(WinchConstants.kLeaderDeviceId, MotorType.kBrushed);
    private final CANSparkMax m_follower = new CANSparkMax(WinchConstants.kFollowerDeviceId, MotorType.kBrushed);
    private final CANSparkMax m_top = new CANSparkMax(WinchConstants.kTopDeviceId, MotorType.kBrushed); // ajouter par arnav samedi le 2 avril

    private final DigitalInput m_limitSwitchClosed = new DigitalInput(WinchConstants.kLimitSwitchClosedId);
    private final DigitalInput m_limitSwitchOpen = new DigitalInput(WinchConstants.kLimitSwitchOpenId);

    private final Solenoid m_latch = new Solenoid(PneumaticsModuleType.CTREPCM, WinchConstants.kLatchSolenoidChannelId);

    public Winch() {
        m_leader.restoreFactoryDefaults();
        m_follower.restoreFactoryDefaults();
        m_top.restoreFactoryDefaults();

        m_leader.setIdleMode(IdleMode.kBrake);
        m_follower.setIdleMode(IdleMode.kBrake);
        m_top.setIdleMode(IdleMode.kBrake);

        m_follower.follow(m_leader, false);
        m_top.follow(m_leader, true);

        m_leader.burnFlash();
        m_follower.burnFlash();
    }

    public void pull(double speed) {
        if (speed > 0) {
            if (m_limitSwitchClosed.get()) {
                m_leader.set(0);
            } else {
                m_leader.set(speed);
            }
        } else {
            if (!m_limitSwitchOpen.get()) {
                m_leader.set(0);
            } else {
                m_leader.set(speed);
            }
        }
    }

    public void stop() {
        m_leader.set(0.0);
    }

    public void unlatch() {
        m_latch.set(true);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Winch Leader Bus Voltage", m_leader.getBusVoltage());
        SmartDashboard.putNumber("Winch Leader Current", m_leader.getOutputCurrent());
        SmartDashboard.putNumber("Winch Leader Applied Output", m_leader.getAppliedOutput());
        SmartDashboard.putNumber("Winch Leader Temperature (C)", m_leader.getMotorTemperature());

        SmartDashboard.putBoolean("Limit switch Closed", m_limitSwitchClosed.get());
        SmartDashboard.putBoolean("Limit switch Open", m_limitSwitchOpen.get());
    }
}
