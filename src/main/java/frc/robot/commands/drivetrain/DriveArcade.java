package frc.robot.commands.drivetrain;

//import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.DriveTrain;

public class DriveArcade extends CommandBase {

  private final DriveTrain _driveTrain;

  public DriveArcade(DriveTrain driveTrain) {
    _driveTrain = driveTrain;
    addRequirements(_driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    
    double movePosValue = Frc4947Controller.DRIVER.getLeftTriggerAxis();
    double moveNegValue = Frc4947Controller.DRIVER.getRightTriggerAxis();
    double moveValue = movePosValue - moveNegValue;
    double rotateValue = Frc4947Controller.DRIVER.getLeftX();

    // System.out.println("Move value: " + moveValue);
    // System.out.println("Rotate value: " + rotateValue);

    _driveTrain.driveArcadeMethod(-moveValue, rotateValue);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
  }
}
