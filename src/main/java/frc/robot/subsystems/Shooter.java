package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

    private final CANSparkMax m_feeder = new CANSparkMax(ShooterConstants.kFeederDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_shooter = new CANSparkMax(ShooterConstants.kShooterDeviceId, MotorType.kBrushless);

    private final Solenoid m_cylinders = new Solenoid(PneumaticsModuleType.CTREPCM, ShooterConstants.kCylindersSolenoidChannelId);

    private final RelativeEncoder m_feederEncoder = m_feeder.getEncoder();
    private final RelativeEncoder m_shooterEncoder = m_shooter.getEncoder();

    public Shooter() {
        m_feeder.restoreFactoryDefaults();
        m_shooter.restoreFactoryDefaults();

        m_feeder.setIdleMode(IdleMode.kCoast);
        m_shooter.setIdleMode(IdleMode.kCoast);

        addChild("Cylinders", m_cylinders);
    }

    public void close() {
        m_cylinders.set(true);
    }

    public void open() {
        m_cylinders.set(false);
    }

    public void spin(double speed) {
        m_feeder.set(speed);
        m_shooter.set(speed);
    }

    public void stop() {
        m_feeder.set(0.0);
        m_shooter.set(0.0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Feeder Speed (RPM)", m_feederEncoder.getVelocity());
        SmartDashboard.putNumber("Feeder Bus Voltage", m_feeder.getBusVoltage());
        SmartDashboard.putNumber("Feeder Current", m_feeder.getOutputCurrent());
        SmartDashboard.putNumber("Feeder Applied Output", m_feeder.getAppliedOutput());
        SmartDashboard.putNumber("Feeder Temperature (C)", m_feeder.getMotorTemperature());

        SmartDashboard.putNumber("Shooter Speed (RPM)", m_shooterEncoder.getVelocity());
        SmartDashboard.putNumber("Shooter Bus Voltage", m_shooter.getBusVoltage());
        SmartDashboard.putNumber("Shooter Current", m_shooter.getOutputCurrent());
        SmartDashboard.putNumber("Shooter Applied Output", m_shooter.getAppliedOutput());
        SmartDashboard.putNumber("Shooter Temperature (C)", m_shooter.getMotorTemperature());        
    }
}