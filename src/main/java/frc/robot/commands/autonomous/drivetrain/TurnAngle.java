package frc.robot.commands.autonomous.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveTrainConstants;
import frc.robot.subsystems.DriveTrain;

public class TurnAngle extends CommandBase {

    private final double m_targetAngleDegrees;
    private final DriveTrain m_driveTrain;
    private final PIDController m_pidController;

    public TurnAngle(double targetAngleDegrees, DriveTrain driveTrain) {
        m_targetAngleDegrees = targetAngleDegrees;
        m_driveTrain = driveTrain;

        m_pidController = new PIDController(DriveTrainConstants.kTurnP, DriveTrainConstants.kTurnI, DriveTrainConstants.kTurnD);
        m_pidController.setTolerance(DriveTrainConstants.kTurnToleranceDegrees, DriveTrainConstants.kTurnRateToleranceDegreesPerSec);

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        m_driveTrain.reset();

        m_pidController.reset();
    }

    @Override
    public void execute() {
        double speed = 0;
        double rotation = MathUtil.clamp(m_pidController.calculate(m_driveTrain.getHeading(), m_targetAngleDegrees) * 1.0, -0.5, 0.5);

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
