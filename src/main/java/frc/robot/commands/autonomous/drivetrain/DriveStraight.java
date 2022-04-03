package frc.robot.commands.autonomous.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveStraight extends CommandBase {

    private final double m_targetPositionMeters;
    private final DriveTrain m_driveTrain;
    private final PIDController m_pidController;

    public DriveStraight(double targetPositionMeters, DriveTrain driveTrain) {
        m_targetPositionMeters = targetPositionMeters;
        m_driveTrain = driveTrain;

        SmartDashboard.putNumber("DT/PID_kp", 2.0);
        SmartDashboard.putNumber("DT/PID_ki", 0.0);

        final double kp = SmartDashboard.getNumber("DT/PID_kp", 2.0);
        final double ki = SmartDashboard.getNumber("DT/PID_ki", 0.0);

        m_pidController = new PIDController(kp, ki, 0.0);
        m_pidController.setTolerance(0.1);

        addRequirements(driveTrain);

    }

    @Override
    public void initialize() {
        m_driveTrain.reset();

        m_pidController.reset();
    }

    @Override
    public void execute() {
        double speed = MathUtil.clamp(m_pidController.calculate(m_driveTrain.getPosition(), m_targetPositionMeters) * 0.2, -0.5, 0.5);
        double rotation = 0;//-m_driveTrain.getHeading();

        System.out.println(String.format("Speed: %f ; Rotation: %f", speed, rotation));

        m_driveTrain.arcadeDrive(speed, rotation);
    }

    @Override
    public boolean isFinished() {
        return m_pidController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrain.stop();
    }
}
