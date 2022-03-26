package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class SpinShooter extends CommandBase {

    private final Shooter m_shooter;

    public SpinShooter(Shooter shooter) {
        m_shooter = shooter;

        addRequirements(shooter);

        withTimeout(ShooterConstants.kSpinTimeoutSec);
    }

    @Override
    public void initialize() {
        m_shooter.spin(ShooterConstants.kShooterBackSetpointRpm, ShooterConstants.kShooterFrontSetpointRpm);
    }

    @Override
    public boolean isFinished() {
        return m_shooter.isAtSetpoint();
    }
}
