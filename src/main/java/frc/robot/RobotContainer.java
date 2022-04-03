package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.StopAll;
import frc.robot.commands.autonomous.ExitTarmacPID;
import frc.robot.commands.autonomous.ExitTarmacTimer;
import frc.robot.commands.drivetrain.DriveArcade;
import frc.robot.commands.intake.Roll;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.SpinShooter;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.commands.winch.Pull;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.limelight.Limelight;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Winch;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // Cameras and SmartDashboard
    private final Limelight _limelight = new Limelight();

    // Contollers.
    public final Frc4947Controller m_driverController = new Frc4947Controller(ControllerConstants.kDriverPort);
    public final Frc4947Controller m_helperController = new Frc4947Controller(ControllerConstants.kHelperPort);

    // Subsystems.
    private final DriveTrain m_driveTrain = new DriveTrain();
    private final Intake m_intake = new Intake();
    private final Shooter m_shooter = new Shooter();
    private final Winch m_winch = new Winch();

    // Commands.
    private final CommandBase m_moveShooterUp = new InstantCommand(m_shooter::moveUp, m_shooter);
    private final CommandBase m_moveShooterDown = new InstantCommand(m_shooter::moveDown, m_shooter);
    private final CommandBase m_spinShooter = new SpinShooter(m_shooter);
    private final CommandBase m_stopShooter = new StopShooter(m_shooter);
    private final CommandBase m_shoot = new Shoot(m_shooter);
    private final CommandBase m_shiftHigh = new InstantCommand(m_driveTrain::shiftHigh);
    private final CommandBase m_shiftLow = new InstantCommand(m_driveTrain::shiftLow);
    private final CommandBase m_stopAll = new StopAll(m_driveTrain, m_intake, m_shooter);
    private final CommandBase m_latchIntake = new InstantCommand(m_intake::latch);
    private final CommandBase m_unlatchIntake = new InstantCommand(m_intake::unlatch);
    private final CommandBase m_unlatchWinch = new InstantCommand(m_winch::unlatch);

    // Default commands.
    private final DriveArcade m_driveArcade = new DriveArcade(m_driverController, m_driveTrain);
    private final CommandBase m_pull = new Pull(m_helperController, m_winch);
    private final CommandBase m_roll = new Roll(m_helperController, m_intake);

    // Autonomous commands.
    private final CommandBase m_autoNone = new PrintCommand("No autonomous command selected");
    private final CommandBase m_autoExitTarmacTimer = new ExitTarmacTimer(m_driveTrain);
    private final CommandBase m_autoExitTarmacPID = new ExitTarmacPID(m_driveTrain);

    private final SendableChooser<Integer> m_autonomousDelayChooser = new SendableChooser<>();
    private final SendableChooser<Command> m_autonomousCommandChooser = new SendableChooser<>();  

    public RobotContainer() {
        configureButtonBindings();
        configureDefaultCommands();
        configureAutonomousCommands();
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
        m_winch.setDefaultCommand(m_pull);
    }

    private void configureAutonomousCommands() {
        m_autonomousDelayChooser.setDefaultOption("0", 0);
        for (int i = 1; i <= 15; ++i) {
            m_autonomousDelayChooser.addOption(String.valueOf(i), i);
        }

        m_autonomousCommandChooser.setDefaultOption("None", m_autoNone);
        m_autonomousCommandChooser.addOption("Exit Tarmac (PID)", m_autoExitTarmacPID);
        m_autonomousCommandChooser.addOption("Exit Tarmac (timer)", m_autoExitTarmacTimer);
        //m_autonomousCommandChooser.addOption("Shoot 1 Ball", m_autoShoot1Ball);
        //m_autonomousCommandChooser.addOption("Shoot 2 Balls", m_autoShoot2Balls);

        SmartDashboard.putData("Autonomous Delay", m_autonomousDelayChooser);
        SmartDashboard.putData("Autonomous Command", m_autonomousCommandChooser);
    }

    private void configureSmartDashboard() {
        SmartDashboard.putData(CommandScheduler.getInstance());

        SmartDashboard.putData(m_driveTrain);
        SmartDashboard.putData(m_intake);
        SmartDashboard.putData(m_shooter);

        SmartDashboard.putData("Commands/Shift High", m_shiftHigh);
        SmartDashboard.putData("Commands/Shift Low", m_shiftLow);        

        SmartDashboard.putData("Commands/Move Shooter Up", m_moveShooterUp);
        SmartDashboard.putData("Commands/Move Shooter Down", m_moveShooterDown);
        SmartDashboard.putData("Commands/Shoot", m_shoot);
        SmartDashboard.putData("Commands/Spin Shooter", m_spinShooter);
        SmartDashboard.putData("Commands/Stop Shooter", m_stopShooter);

        SmartDashboard.putData("Commands/Latch Intake", m_latchIntake);
        SmartDashboard.putData("Commands/Unlatch Intake", m_unlatchIntake);

        SmartDashboard.putData("Commands/Unlatch Winch", m_unlatchWinch);

        SmartDashboard.putData("Commands/Stop All", m_stopAll);
    }

    public void teleopInit() {
        m_driveTrain.shiftLow();
        m_driveTrain.enableSafety();
        m_moveShooterUp.schedule();
    }

    public void autonomousInit() {
        m_driveTrain.shiftLow();
        m_driveTrain.disableSafety();
        //m_moveShooterUp.schedule();
    }

    public Command getAutonomousCommand() {
        int autonomousDelay = getAutonomousDelay();
        if (autonomousDelay > 0) {
            return m_autonomousCommandChooser.getSelected().beforeStarting(new WaitCommand(autonomousDelay));
        }

        return m_autonomousCommandChooser.getSelected();
    }

    private int getAutonomousDelay() {
        Integer autonomousDelay = m_autonomousDelayChooser.getSelected();
        return (autonomousDelay != null) ? autonomousDelay.intValue() : 0;
    }
}
