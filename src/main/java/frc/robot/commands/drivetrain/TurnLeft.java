package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TurnLeft extends CommandBase {

    private final DriveTrain m_driveTrain;

    public TurnLeft(DriveTrain driveTrain) {
        m_driveTrain = driveTrain;

        addRequirements(driveTrain);

        withTimeout(10.0);
    }

    @Override
    public void execute() {
        double rotation = SmartDashboard.getNumber("Turn Left Output", 0.0);

        m_driveTrain.arcadeDrive(0, -rotation);
    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrain.stop();
    }
}