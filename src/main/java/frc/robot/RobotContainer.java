package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.drivetrain.DriveArcade;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.DriveTrain;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // Contollers.
    public final Frc4947Controller m_driverController = new Frc4947Controller(ControllerConstants.kDriverPort);

    // Subsystems.
    private final DriveTrain m_driveTrain = new DriveTrain();

    // Default commands.
    private final DriveArcade m_driveArcade = new DriveArcade(m_driverController, m_driveTrain);

    public RobotContainer() {
        configureButtonBindings();
        configureDefaultCommands();
    }

    private void configureButtonBindings() {
        m_driverController.X.whenPressed(new InstantCommand(m_driveTrain::shiftHigh));
        m_driverController.A.whenPressed(new InstantCommand(m_driveTrain::shiftLow));
    }

    private void configureDefaultCommands() {
        m_driveTrain.setDefaultCommand(m_driveArcade);
    }

    public Command getAutonomousCommand() {
        return null;
    }
}