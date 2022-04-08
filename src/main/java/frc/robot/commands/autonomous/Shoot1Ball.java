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

public class Shoot1Ball extends SequentialCommandGroup {

    public Shoot1Ball(Shooter shooter, Intake intake, DriveTrain driveTrain) {
        addCommands(
                new InstantCommand(() -> intake.unlatch(), intake),
                new WaitCommand(2.0),
                new Shoot(shooter, false),                                    // Shoot 1st ball.
                new DriveStraight(-1.8, driveTrain).withTimeout(5.0)   // Exit tarmac.
            );
    }
}
