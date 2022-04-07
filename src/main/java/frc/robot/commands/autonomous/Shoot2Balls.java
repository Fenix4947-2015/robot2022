package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.autonomous.drivetrain.DriveStraight;
import frc.robot.commands.autonomous.drivetrain.TurnAngle;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Shoot2Balls extends SequentialCommandGroup {

    public Shoot2Balls(Shooter shooter, Intake intake, DriveTrain driveTrain) {
        addCommands(
                new InstantCommand(() -> intake.unlatch(), intake),
                new WaitCommand(4.0),
                new Shoot(shooter),                                 // Shoot 1st ball.
                new TurnAngle(-180.0, driveTrain),                     // Turn around toward 2nd ball.
                new InstantCommand(() -> intake.roll(1.0), intake), // Start the intake.
                new DriveStraight(3.0, driveTrain).withTimeout(2),  // Drive toward 2nd ball until pickup.
                new TurnAngle(-180.0, driveTrain),                    // Turn around toward hub.
                new InstantCommand(intake::stop, intake),           // Stop the intake.
                new DriveStraight(3.0, driveTrain).withTimeout(2),                 // Drive toward hub.
                new Shoot(shooter),                                 // Shoot 2nd ball.
                new DriveStraight(-1.8, driveTrain)                 // Exit tarmac.
            );
    }
}
