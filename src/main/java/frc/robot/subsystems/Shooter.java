package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

    private final CANSparkMax m_shooterBack = new CANSparkMax(ShooterConstants.kShooterBackDeviceId, MotorType.kBrushless);
    private final CANSparkMax m_shooterFront = new CANSparkMax(ShooterConstants.kShooterFrontDeviceId, MotorType.kBrushless);

    private final Solenoid m_cylinders = new Solenoid(PneumaticsModuleType.CTREPCM, ShooterConstants.kCylindersSolenoidChannelId);

    private final SparkMaxPIDController m_shooterBackController = m_shooterBack.getPIDController();
    private final SparkMaxPIDController m_shooterFrontController = m_shooterBack.getPIDController();

    private final RelativeEncoder m_shooterBackEncoder = m_shooterBack.getEncoder();
    private final RelativeEncoder m_shooterFrontEncoder = m_shooterFront.getEncoder();

    private final SimpleMotorFeedforward m_shooterBackFeedforward = new SimpleMotorFeedforward(
            ShooterConstants.kShooterBackKs,
            ShooterConstants.kShooterBackKv,
            ShooterConstants.kShooterBackKa);

    private final SimpleMotorFeedforward m_shooterFrontFeedForward = new SimpleMotorFeedforward(
            ShooterConstants.kShooterFrontKs,
            ShooterConstants.kShooterFrontKv,
            ShooterConstants.kShooterFrontKa);

    private double m_shooterBackSetpointRpm = -1.0;
    private double m_shooterBackFeedForwardValue = -1.0;

    private double m_shooterFrontSetpointRpm = -1.0;
    private double m_shooterFrontFeedForwardValue = -1.0;

    public Shooter() {
        m_shooterBack.restoreFactoryDefaults();
        m_shooterFront.restoreFactoryDefaults();

        m_shooterBack.setIdleMode(IdleMode.kCoast);
        m_shooterFront.setIdleMode(IdleMode.kCoast);

        m_shooterBackController.setP(ShooterConstants.kShooterBackP);
        m_shooterBackController.setI(ShooterConstants.kShooterBackI);
        m_shooterBackController.setD(ShooterConstants.kShooterBackD);
        m_shooterBackController.setOutputRange(ShooterConstants.kShooterBackMinOutput, ShooterConstants.kShooterBackMaxOutput);

        m_shooterFrontController.setP(ShooterConstants.kShooterFrontP);
        m_shooterFrontController.setI(ShooterConstants.kShooterFrontI);
        m_shooterFrontController.setD(ShooterConstants.kShooterFrontD);
        m_shooterFrontController.setOutputRange(ShooterConstants.kShooterFrontMinOutput, ShooterConstants.kShooterFrontMaxOutput);

        SmartDashboard.putNumber("Shooter Back P", ShooterConstants.kShooterBackP);
        SmartDashboard.putNumber("Shooter Back I", ShooterConstants.kShooterBackI);
        SmartDashboard.putNumber("Shooter Back D", ShooterConstants.kShooterBackD);
        SmartDashboard.putNumber("Shooter Back Max Output", ShooterConstants.kShooterBackMaxOutput);
        SmartDashboard.putNumber("Shooter Back Min Output", ShooterConstants.kShooterBackMinOutput);
        SmartDashboard.putNumber("Shooter Back Setpoint (RPM)", m_shooterBackSetpointRpm);
        SmartDashboard.putNumber("Shooter Back Feed Forward", m_shooterBackFeedForwardValue);

        SmartDashboard.putNumber("Shooter Front P", ShooterConstants.kShooterFrontP);
        SmartDashboard.putNumber("Shooter Front I", ShooterConstants.kShooterFrontI);
        SmartDashboard.putNumber("Shooter Front D", ShooterConstants.kShooterFrontD);
        SmartDashboard.putNumber("Shooter Front Max Output", ShooterConstants.kShooterFrontMaxOutput);
        SmartDashboard.putNumber("Shooter Front Min Output", ShooterConstants.kShooterFrontMinOutput);
        SmartDashboard.putNumber("Shooter Front Setpoint (RPM)", m_shooterFrontSetpointRpm);
        SmartDashboard.putNumber("Shooter Front Feed Forward", m_shooterFrontFeedForwardValue);

        addChild("Cylinders", m_cylinders);
    }

    public void moveUp() {
        m_cylinders.set(true);
    }

    public void moveDown() {
        m_cylinders.set(false);
    }

    public boolean isAtSetpoint() {
        return isShooterBackAtSetpoint() && isShooterFrontAtSetpoint();
    }

    private boolean isShooterBackAtSetpoint() {
        double positionError = (m_shooterBackSetpointRpm - m_shooterBackEncoder.getVelocity());
        return Math.abs(positionError) < ShooterConstants.kShooterBackToleranceRpm;
    }

    private boolean isShooterFrontAtSetpoint() {
        double positionError = (m_shooterFrontSetpointRpm - m_shooterFrontEncoder.getVelocity());
        return Math.abs(positionError) < ShooterConstants.kShooterFrontToleranceRpm;
    }

    public void spin(double shooterBackRpm, double shooterFrontRpm) {
        spinShooterBack(shooterBackRpm);
        spinShooterFront(shooterFrontRpm);
    }

    private void spinShooterBack(double rpm) {
        m_shooterBackSetpointRpm = rpm;
        m_shooterBackFeedForwardValue = m_shooterBackFeedforward.calculate(rpm);

        m_shooterBackController.setFF(m_shooterBackFeedForwardValue);

        SmartDashboard.putNumber("Shooter Back Setpoint (RPM)", m_shooterBackSetpointRpm);
        SmartDashboard.putNumber("Shooter Back Feed Forward", m_shooterBackFeedForwardValue);
    }

    private void spinShooterFront(double rpm) {
        m_shooterFrontSetpointRpm = rpm;
        m_shooterFrontFeedForwardValue = m_shooterFrontFeedForward.calculate(rpm);

        m_shooterFrontController.setFF(m_shooterFrontFeedForwardValue);

        SmartDashboard.putNumber("Shooter Front Setpoint (RPM)", m_shooterFrontSetpointRpm);
        SmartDashboard.putNumber("Shooter Front Feed Forward", m_shooterFrontFeedForwardValue);
    }

    public void stop() {
        stopShooterBack();
        stopShooterFront();
    }

    private void stopShooterBack() {
        m_shooterBackSetpointRpm = -1.0;
        m_shooterBackFeedForwardValue = -1.0;

        SmartDashboard.putNumber("Shooter Back Setpoint (RPM)", m_shooterBackSetpointRpm);
        SmartDashboard.putNumber("Shooter Back Feed Forward", m_shooterBackFeedForwardValue);
    }

    private void stopShooterFront() {
        m_shooterFrontSetpointRpm = -1.0;
        m_shooterFrontFeedForwardValue = -1.0;

        SmartDashboard.putNumber("Shooter Front Setpoint (RPM)", m_shooterFrontSetpointRpm);
        SmartDashboard.putNumber("Shooter Front Feed Forward", m_shooterFrontFeedForwardValue);
    }

    @Override
    public void periodic() {
        if (m_shooterBackSetpointRpm < 0.0) {
            m_shooterBackController.setReference(0.0, CANSparkMax.ControlType.kDutyCycle);
        } else {
            m_shooterBackController.setReference(m_shooterBackSetpointRpm, CANSparkMax.ControlType.kVelocity);
        }

        if (m_shooterFrontSetpointRpm < 0.0) {
            m_shooterFrontController.setReference(0.0, CANSparkMax.ControlType.kDutyCycle);
        } else {
            m_shooterFrontController.setReference(m_shooterFrontSetpointRpm, CANSparkMax.ControlType.kVelocity);
        }

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
