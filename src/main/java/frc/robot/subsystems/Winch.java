package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WinchConstants;

public class Winch extends SubsystemBase {

    private final CANSparkMax m_leader = new CANSparkMax(WinchConstants.kLeaderDeviceId, MotorType.kBrushed);
    private final CANSparkMax m_follower = new CANSparkMax(WinchConstants.kFollowerDeviceId, MotorType.kBrushed);
    private final CANSparkMax m_top = new CANSparkMax(WinchConstants.kTopDeviceId, MotorType.kBrushed); // ajouter par
    private final CANSparkMax m_top2 = new CANSparkMax(WinchConstants.kTop2DeviceId, MotorType.kBrushed); // ajouter par
                                                                                                      // arnav samedi
                                                                                                        // le 2 avril

    private final DigitalInput m_limitSwitchClosed = new DigitalInput(WinchConstants.kLimitSwitchClosedId);
    private final DigitalInput m_limitSwitchOpen = new DigitalInput(WinchConstants.kLimitSwitchOpenId);

    private final Solenoid m_latch = new Solenoid(PneumaticsModuleType.CTREPCM, WinchConstants.kLatchSolenoidChannelId);
    // private final RelativeEncoder m_leaderEncoder = m_leader.getEncoder();
    // private final RelativeEncoder m_followerEncoder = m_follower.getEncoder();

    private final Encoder m_encoder = new Encoder(WinchConstants.kEncoderDeviceId1, WinchConstants.kEncoderDeviceId2);

    public Winch() {
        m_leader.restoreFactoryDefaults();
        m_follower.restoreFactoryDefaults();
        m_top.restoreFactoryDefaults();
        m_top2.restoreFactoryDefaults();

        m_leader.setIdleMode(IdleMode.kBrake);
        m_follower.setIdleMode(IdleMode.kBrake);
        m_top.setIdleMode(IdleMode.kBrake);
        m_top2.setIdleMode(IdleMode.kBrake);

        m_follower.follow(m_leader, false);
        m_top.follow(m_leader, true);
        m_top2.follow(m_leader, true);

        m_top.burnFlash();
        m_top2.burnFlash();
        m_leader.burnFlash();
        m_follower.burnFlash();
    }

    public void pull(double speed) {
        final boolean disableSafety = false;

        final double realSpeed;
        if (disableSafety) {
            realSpeed = speed;
        } else {
            if (speed > 0) {
                if (isClosedLimitSwitchPressed()) {
                    realSpeed = 0.0;
                } else {
                    realSpeed = speed;
                }
            } else {
                if (isOpenLimitSwitchPressed()) {
                    realSpeed = 0.0;
                } else {
                    realSpeed = speed;
                }
            }
        }

        SmartDashboard.putNumber("Winch/Pull speed", realSpeed);
        m_leader.set(realSpeed);
    }

    public void stop() {
        m_leader.set(0.0);
    }

    public void unlatch() {
        m_latch.set(true);
    }

    public boolean isClosedLimitSwitchPressed() {
        return m_limitSwitchClosed.get();
    }

    public boolean isOpenLimitSwitchPressed() {
        return !m_limitSwitchOpen.get();
    }

    public boolean isStopped() {
        return m_encoder.getStopped();
    }

    public double getEncoderDistance() {
        return m_encoder.getDistance();
    }

    @Override
    public void periodic() {
        // SmartDashboard.putNumber("Winch Leader Bus Voltage",
        // m_leader.getBusVoltage());
        // SmartDashboard.putNumber("Winch Leader Current",
        // m_leader.getOutputCurrent());
        // SmartDashboard.putNumber("Winch Leader Applied Output",
        // m_leader.getAppliedOutput());
        // SmartDashboard.putNumber("Winch Leader Temperature (C)",
        // m_leader.getMotorTemperature());

        SmartDashboard.putBoolean("Winch/Limit switch Closed", isClosedLimitSwitchPressed());
        SmartDashboard.putBoolean("Winch/Limit switch Open", isOpenLimitSwitchPressed());

        SmartDashboard.putNumber("Winch/Encoder distance", getEncoderDistance());
    }
}
