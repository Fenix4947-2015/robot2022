package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.InstakeConstants;

public class Intake extends SubsystemBase {

    private final CANSparkMax m_motor = new CANSparkMax(InstakeConstants.kMotorDeviceId, MotorType.kBrushless);

    private final RelativeEncoder m_encoder = m_motor.getEncoder();

    public Intake() {
        m_motor.restoreFactoryDefaults();
        m_motor.setIdleMode(IdleMode.kBrake);
        m_motor.setInverted(true);

        m_motor.burnFlash();
    }

    public void roll(double speed) {
        m_motor.set(speed);
    }

    public void stop() {
        m_motor.set(0.0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Speed (RPM)", m_encoder.getVelocity());
        SmartDashboard.putNumber("Bus Voltage", m_motor.getBusVoltage());
        SmartDashboard.putNumber("Current", m_motor.getOutputCurrent());
        SmartDashboard.putNumber("Applied Output", m_motor.getAppliedOutput());
        SmartDashboard.putNumber("Temperature (C)", m_motor.getMotorTemperature());
    }
}
