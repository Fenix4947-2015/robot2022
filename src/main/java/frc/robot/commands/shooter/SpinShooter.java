package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class SpinShooter extends InstantCommand {

    private final Shooter m_shooter;
    private final boolean m_far;

    public SpinShooter(Shooter shooter, boolean far) {
        m_shooter = shooter;
        m_far = far;

        addRequirements(shooter);

        SmartDashboard.putNumber("Shooter/speed near", ShooterConstants.kSpinSpeedNear);
        SmartDashboard.putNumber("Shooter/speed far", ShooterConstants.kSpinSpeedFar);
    }

    @Override
    public void execute() {
        final double speed = m_far ? SmartDashboard.getNumber("Shooter/speed near", ShooterConstants.kSpinSpeedNear)
                : SmartDashboard.getNumber("Shooter/speed near", ShooterConstants.kSpinSpeedNear);
        m_shooter.spin(speed);
    }
}
