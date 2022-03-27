package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;

public class ExitTarmacTimer extends SequentialCommandGroup {

    public ExitTarmacTimer(DriveTrain driveTrain) {
        addCommands(
            new InstantCommand(() -> driveTrain.arcadeDrive(-0.5, 0.0), driveTrain),    // Drive in reverse.
            new WaitCommand(2.0),                                                       // Wait a moment.
            new InstantCommand(driveTrain::stop, driveTrain));                          // Stop.
    }
}
