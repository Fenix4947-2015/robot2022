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

    private final CANSparkMax m_shooterBack = new CANSparkMax(ShooterConstants.kShooterBackDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_shooterFront = new CANSparkMax(ShooterConstants.kShooterFrontDeviceId, MotorType.kBrushless);

    private final Solenoid m_cylinders = new Solenoid(PneumaticsModuleType.CTREPCM, ShooterConstants.kCylindersSolenoidChannelId);

    private final RelativeEncoder m_shooterBackEncoder = m_shooterBack.getEncoder();
    private final RelativeEncoder m_shooterFrontEncoder = m_shooterFront.getEncoder();

    public Shooter() {
        m_shooterBack.restoreFactoryDefaults();
        m_shooterFront.restoreFactoryDefaults();

        m_shooterBack.setIdleMode(IdleMode.kCoast);
        m_shooterFront.setIdleMode(IdleMode.kCoast);

        addChild("Cylinders", m_cylinders);
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
        SmartDashboard.putNumber("Shooter Back Speed (RPM)", m_shooterBackEncoder.getVelocity());
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
