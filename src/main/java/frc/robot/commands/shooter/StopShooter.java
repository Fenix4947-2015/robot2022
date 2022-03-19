package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class StopShooter extends InstantCommand {

    private final Shooter m_shooter;

    public StopShooter(Shooter shooter) {
        m_shooter = shooter;

        addRequirements(shooter);
    }

    @Override
    public void execute() {
        m_shooter.stop();
    }
}