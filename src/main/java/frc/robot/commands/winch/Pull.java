package frc.robot.commands.winch;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.Winch;

public class Pull extends CommandBase {

    private final Frc4947Controller m_controller;
    private final Winch m_winch;

    private static final double kAutoRewindDistance = 10.0;
    private static final double kAutoRewindSpeed = -0.9;
    private boolean m_isAutoRewinding = false;
    private double m_autoRewindStartPos;

    public Pull(Frc4947Controller controller, Winch winch) {
        m_controller = controller;
        m_winch = winch;

        addRequirements(winch);
    }

    @Override
    public void execute() {
        final double speed = m_controller.getRightY();
        final boolean autoUnwindEnabled = true; // m_controller.kY.getAsBoolean()

        if (m_isAutoRewinding) {
            final double distanceTraveled = m_autoRewindStartPos - m_winch.getEncoderDistance();
            if (distanceTraveled > kAutoRewindDistance || m_winch.isOpenLimitSwitchPressed()) {
                m_isAutoRewinding = false;
                m_winch.pull(0.0);
            } else {
                m_winch.pull(kAutoRewindSpeed);
            }
        } else {
            if (autoUnwindEnabled && speed > 0.0 && (m_winch.isStopped() || m_winch.isClosedLimitSwitchPressed())) {
                m_isAutoRewinding = true;
                m_autoRewindStartPos = m_winch.getEncoderDistance();
                m_winch.pull(0.0);
            } else {
                m_winch.pull(speed);
            }
        }
    }
}
