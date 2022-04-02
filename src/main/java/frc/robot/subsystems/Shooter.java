package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

    private final CANSparkMax m_shooterBack = new CANSparkMax(ShooterConstants.kShooterBackDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_shooterFront = new CANSparkMax(ShooterConstants.kShooterFrontDeviceId, MotorType.kBrushless);

    private final Solenoid m_cylinders = new Solenoid(PneumaticsModuleType.CTREPCM, ShooterConstants.kCylindersSolenoidChannelId);

    private final RelativeEncoder m_shooterBackEncoder = m_shooterBack.getEncoder();
    private final RelativeEncoder m_shooterFrontEncoder = m_shooterFront.getEncoder();

    private final Encoder m_shooterBackQuadratureEncoder = new Encoder(2, 3);
    private final Encoder m_shooterFrontQuadratureEncoder = new Encoder(4, 5);

    private final LinearFilter m_shooterBackRpmAverage = LinearFilter.movingAverage(4);
    private double m_shooterBackLastPosition = -1.0;
    private double m_shooterBackRpm = -1.0;

    public Shooter() {
        m_shooterBack.restoreFactoryDefaults();
        m_shooterFront.restoreFactoryDefaults();

        m_shooterBack.setIdleMode(IdleMode.kCoast);
        m_shooterFront.setIdleMode(IdleMode.kCoast);

        m_shooterBack.burnFlash();
        m_shooterFront.burnFlash();

        m_shooterBackQuadratureEncoder.setDistancePerPulse(1.0 / 8192.0); // 1 rotation per 8192 pulses
        m_shooterFrontQuadratureEncoder.setDistancePerPulse(1.0 / 8192.0); // 1 rotation per 8192 pulses

        addChild("Cylinders", m_cylinders);
    }

    public double getShooterBackRpm() {
        return m_shooterBackEncoder.getVelocity();
    }

    public double getShooterBackQuadratureRpm() {
        return m_shooterBackQuadratureEncoder.getRate() * 60.0;
    }

    public void moveUp() {
        m_cylinders.set(true);
    }

    public void moveDown() {
        m_cylinders.set(false);
    }

    public void spin(double speed) {
        m_shooterBack.set(speed);
        m_shooterFront.set(-speed);
    }

    public void stop() {
        m_shooterBack.set(0.0);
        m_shooterFront.set(0.0);
    }

    @Override
    public void periodic() {
        if (m_shooterBackLastPosition > -1.0) {
            m_shooterBackRpm = m_shooterBackRpmAverage.calculate(
                (m_shooterBackEncoder.getPosition() - m_shooterBackLastPosition) / 0.02 * 60.0);
        }

        m_shooterBackLastPosition = m_shooterBackEncoder.getPosition();

        SmartDashboard.putNumber("Shooter Back Quadrature Speed (RPM)", getShooterBackQuadratureRpm());
        SmartDashboard.putNumber("Shooter Back Speed (RPM)", m_shooterBackEncoder.getVelocity());
        SmartDashboard.putNumber("Shooter Back Speed Average (RPM)", m_shooterBackRpm);

        SmartDashboard.putNumber("Shooter Back Bus Voltage", m_shooterBack.getBusVoltage());
        SmartDashboard.putNumber("Shooter Back Current", m_shooterBack.getOutputCurrent());
        SmartDashboard.putNumber("Shooter Back Applied Output", m_shooterBack.getAppliedOutput());
        SmartDashboard.putNumber("Shooter Back Temperature (C)", m_shooterBack.getMotorTemperature());

        SmartDashboard.putNumber("Shooter Front Speed (RPM)", m_shooterFrontEncoder.getVelocity());
        SmartDashboard.putNumber("Shooter Front Bus Voltage", m_shooterFront.getBusVoltage());
        SmartDashboard.putNumber("Shooter Front Current", m_shooterFront.getOutputCurrent());
        SmartDashboard.putNumber("Shooter Front Applied Output", m_shooterFront.getAppliedOutput());
        SmartDashboard.putNumber("Shooter Front Temperature (C)", m_shooterFront.getMotorTemperature());        
    }
}
