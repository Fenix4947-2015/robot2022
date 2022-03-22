package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.StopAll;
import frc.robot.commands.drivetrain.DriveArcade;
import frc.robot.commands.intake.Roll;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.SpinShooter;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // Contollers.
    public final Frc4947Controller m_driverController = new Frc4947Controller(ControllerConstants.kDriverPort);
    public final Frc4947Controller m_helperController = new Frc4947Controller(ControllerConstants.kHelperPort);

    // Subsystems.
    private final DriveTrain m_driveTrain = new DriveTrain();
    private final Intake m_intake = new Intake();
    private final Shooter m_shooter = new Shooter();

    // Commands.
    private final CommandBase m_moveShooterUp = new InstantCommand(m_shooter::moveUp, m_shooter);
    private final CommandBase m_moveShooterDown = new InstantCommand(m_shooter::moveDown, m_shooter);
    private final CommandBase m_spinShooter = new SpinShooter(m_shooter);
    private final CommandBase m_stopShooter = new StopShooter(m_shooter);
    private final CommandBase m_shoot = new Shoot(m_shooter);
    private final CommandBase m_shiftHigh = new InstantCommand(m_driveTrain::shiftHigh);
    private final CommandBase m_shiftLow = new InstantCommand(m_driveTrain::shiftLow);
    private final CommandBase m_stopAll = new StopAll(m_driveTrain, m_intake, m_shooter);

    // Default commands.
    private final DriveArcade m_driveArcade = new DriveArcade(m_driverController, m_driveTrain);
    private final CommandBase m_roll = new Roll(m_helperController, m_intake);

    public RobotContainer() {
        configureButtonBindings();
        configureDefaultCommands();
        configureSmartDashboard();
    }

    private void configureButtonBindings() {
        m_driverController.kA.whenPressed(m_shiftHigh);
        m_driverController.kX.whenPressed(m_shiftLow);
        m_driverController.kBack.whenPressed(m_stopAll);

        m_helperController.kA.whenPressed(m_spinShooter);
        m_helperController.kX.whenPressed(m_stopShooter);
        m_helperController.kLeftBumper.whenPressed(m_moveShooterDown);
        m_helperController.kRightBumper.whenPressed(m_moveShooterUp);
        m_helperController.kB.whenPressed(m_shoot);
        m_helperController.kBack.whenPressed(m_stopAll);
    }

    private void configureDefaultCommands() {
        m_driveTrain.setDefaultCommand(m_driveArcade);
        m_intake.setDefaultCommand(m_roll);
    }

    private void configureSmartDashboard() {
        SmartDashboard.putData(CommandScheduler.getInstance());

        SmartDashboard.putData(m_driveTrain);
        SmartDashboard.putData(m_intake);
        SmartDashboard.putData(m_shooter);

        SmartDashboard.putData("Move Shooter Up", m_moveShooterUp);
        SmartDashboard.putData("Move Shooter Down", m_moveShooterDown);
        SmartDashboard.putData("Shift High", m_shiftHigh);
        SmartDashboard.putData("Shift Low", m_shiftLow);
        SmartDashboard.putData("Shoot", m_shoot);
        SmartDashboard.putData("Spin Shooter", m_spinShooter);
        SmartDashboard.putData("Stop All", m_stopAll);
        SmartDashboard.putData("Stop Shooter", m_stopShooter);
    }

    public void teleopInit() {
        m_moveShooterUp.schedule();
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
