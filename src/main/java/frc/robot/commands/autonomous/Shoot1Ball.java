package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class Shoot1Ball extends SequentialCommandGroup {

    public Shoot1Ball(Shooter shooter, DriveTrain driveTrain) {
        addCommands(
                new Shoot(shooter),                 // Shoot ball.
                new ExitTarmacTimer(driveTrain));   // Exit tarmac.
    }
}
