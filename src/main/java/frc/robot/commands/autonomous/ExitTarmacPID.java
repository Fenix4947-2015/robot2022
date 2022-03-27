package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.drivetrain.DriveStraight;
import frc.robot.subsystems.DriveTrain;

public class ExitTarmacPID extends SequentialCommandGroup {

    public ExitTarmacPID(DriveTrain driveTrain) {
        addCommands(new DriveStraight(-2.0, driveTrain).withTimeout(5.0));
    }
}
