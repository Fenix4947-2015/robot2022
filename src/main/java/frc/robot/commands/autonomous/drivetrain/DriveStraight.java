package frc.robot.commands.autonomous.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveStraight extends CommandBase {

    private final double m_targetPositionMeters;
    private final DriveTrain m_driveTrain;
    private final PIDController m_pidController;

    public DriveStraight(double targetPositionMeters, DriveTrain driveTrain) {
        m_targetPositionMeters = targetPositionMeters;
        m_driveTrain = driveTrain;

        m_pidController = new PIDController(4, 0, 0);
        m_pidController.setTolerance(0.01);

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        m_driveTrain.reset();

        m_pidController.reset();
    }

    @Override
    public void execute() {
        double speed = m_pidController.calculate(m_driveTrain.getPosition(), m_targetPositionMeters);
        double rotation = -m_driveTrain.getHeading();

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
