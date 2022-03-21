package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class Shoot extends SequentialCommandGroup {

    public Shoot(Shooter shooter) {
        addCommands(
                new InstantCommand(shooter::open, shooter),
                new SpinShooter(shooter),
                new WaitCommand(ShooterConstants.kSpinDelaySec),
                new InstantCommand(shooter::close, shooter),
                new WaitCommand(ShooterConstants.kAfterShotDelaySec),
                new StopShooter(shooter),
                new InstantCommand(shooter::open, shooter));
    }
}