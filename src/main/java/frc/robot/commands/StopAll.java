package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

public class StopAll extends InstantCommand {

    private final DriveTrain m_driveTrain;
    private final Intake m_intake;

    public StopAll(DriveTrain driveTrain, Intake intake) {
        m_driveTrain = driveTrain;
        m_intake = intake;

        addRequirements(driveTrain);
        addRequirements(intake);
    }

    @Override
    public void execute() {
        m_driveTrain.stop();
        m_intake.stop();

        CommandScheduler.getInstance().cancelAll();
    }
}