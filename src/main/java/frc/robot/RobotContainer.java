package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.StopAll;
import frc.robot.commands.drivetrain.DriveArcade;
import frc.robot.commands.drivetrain.MoveForward;
import frc.robot.commands.drivetrain.MoveReverse;
import frc.robot.commands.drivetrain.TurnLeft;
import frc.robot.commands.drivetrain.TurnRight;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

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

    // Commands.
    private final CommandBase m_shiftHigh = new InstantCommand(m_driveTrain::shiftHigh);
    private final CommandBase m_shiftLow = new InstantCommand(m_driveTrain::shiftLow);
    private final CommandBase m_stopAll = new StopAll(m_driveTrain);
    private final CommandBase m_unlatchIntake = new InstantCommand(m_intake::unlatch);


    private final CommandBase m_moveForward = new MoveForward(m_driveTrain);
    private final CommandBase m_moveReverse = new MoveReverse(m_driveTrain);
    private final CommandBase m_turnLeft = new TurnLeft(m_driveTrain);
    private final CommandBase m_turnRight = new TurnRight(m_driveTrain);

    // Default commands.
    private final DriveArcade m_driveArcade = new DriveArcade(m_driverController, m_driveTrain);

    public RobotContainer() {
        configureButtonBindings();
        configureDefaultCommands();
        configureSmartDashboard();
    }

    private void configureButtonBindings() {
        m_driverController.kA.whenPressed(m_shiftHigh);
        m_driverController.kX.whenPressed(m_shiftLow);
        m_driverController.kBack.whenPressed(m_stopAll);

        m_helperController.kY.whenPressed(m_moveForward);
        m_helperController.kA.whenPressed(m_moveReverse);
        m_helperController.kX.whenPressed(m_turnLeft);
        m_helperController.kB.whenPressed(m_turnRight);
        m_helperController.kBack.whenPressed(m_stopAll);
    }

    private void configureDefaultCommands() {
        m_driveTrain.setDefaultCommand(m_driveArcade);
    }

    private void configureSmartDashboard() {
        SmartDashboard.putData(CommandScheduler.getInstance());

        SmartDashboard.putData(m_driveTrain);
        SmartDashboard.putData(m_intake);

        SmartDashboard.putData("Shift High", m_shiftHigh);
        SmartDashboard.putData("Shift Low", m_shiftLow);        
        SmartDashboard.putData("Unlatch Intake", m_unlatchIntake);
        SmartDashboard.putData("Stop All", m_stopAll);

        SmartDashboard.putData("Move Forward", m_moveForward);
        SmartDashboard.putData("Move Reverse", m_moveReverse);        
        SmartDashboard.putData("Turn Left", m_turnLeft);
        SmartDashboard.putData("Turn Right", m_turnRight);        

        SmartDashboard.putNumber("Forward Output", 0.0);
        SmartDashboard.putNumber("Reverse Output", 0.0);
        SmartDashboard.putNumber("Turn Left Output", 0.0);
        SmartDashboard.putNumber("Turn Right Output", 0.0);
    }

    public void teleopInit() {
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
