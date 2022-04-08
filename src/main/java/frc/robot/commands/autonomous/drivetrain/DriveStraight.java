package frc.robot.commands.autonomous.drivetrain;

import java.time.Duration;
import java.time.Instant;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveTrainConstants;
import frc.robot.subsystems.DriveTrain;

public class DriveStraight extends CommandBase {


    private final double m_targetPositionMeters;
    private final DriveTrain m_driveTrain;
    private final PIDController m_pidController;
    
    private Instant lastTime = Instant.now();
    private double lastPos;

    public DriveStraight(double targetPositionMeters, DriveTrain driveTrain) {
        m_targetPositionMeters = targetPositionMeters;
        m_driveTrain = driveTrain;

        SmartDashboard.putNumber("DT/PID_kp", 1.5);
        SmartDashboard.putNumber("DT/PID_ki", 0.5);

        final double kp = SmartDashboard.getNumber("DT/PID_kp", 1.5);
        final double ki = SmartDashboard.getNumber("DT/PID_ki", 0.5);

        m_pidController = new PIDController(kp, ki, 0.0);
        m_pidController.setTolerance(0.25);

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        m_driveTrain.reset();
        m_pidController.reset();
    }

    @Override
    public void execute() {
        final Instant now = Instant.now();
        final double dtSeconds = ((double) Duration.between(lastTime, now).toMillis()) / 1000.0;
        final double posFudgeFactor = 0.6;
        final double currPos = m_driveTrain.getPosition() / posFudgeFactor;
        final double velocity = (currPos - lastPos) / dtSeconds;
        lastPos = currPos;

        double feedforward = DriveTrainConstants.m_feedFwdLow.calculate(velocity);

        double ffSpeed = feedforward / RobotController.getBatteryVoltage();
        double nextVal = MathUtil.clamp(m_pidController.calculate(currPos, m_targetPositionMeters), -0.6, 0.6);
        double rotation = 0;//-m_driveTrain.getHeading();

        double speed = MathUtil.clamp(nextVal + ffSpeed, -1.0, 1.0);
        System.out.println(String.format("Pos: %f, Speed: %f ; Rotation: %f ; Feedforward: %f", currPos, speed, rotation, feedforward));

        m_driveTrain.arcadeDrive(speed, rotation);
    }

    @Override
    public boolean isFinished() {
        return m_pidController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrain.stop();
    }
}
