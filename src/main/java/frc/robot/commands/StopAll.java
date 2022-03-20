package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveTrain;

public class StopAll extends InstantCommand {

    private final DriveTrain m_driveTrain;

    public StopAll(DriveTrain driveTrain) {
        m_driveTrain = driveTrain;

        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        m_driveTrain.stop();

        CommandScheduler.getInstance().cancelAll();
    }
}