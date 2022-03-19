package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class StopAll extends InstantCommand {

    private final DriveTrain m_driveTrain;
    private final Intake m_intake;
    private final Shooter m_shooter;

    public StopAll(DriveTrain driveTrain, Intake intake, Shooter shooter) {
        m_driveTrain = driveTrain;
        m_intake = intake;
        m_shooter = shooter;

        addRequirements(driveTrain);
        addRequirements(intake);
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        m_driveTrain.stop();
        m_intake.stop();
        m_shooter.stop();

        CommandScheduler.getInstance().cancelAll();
    }
}