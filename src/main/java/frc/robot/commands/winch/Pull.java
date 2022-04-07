package frc.robot.commands.winch;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.Winch;

public class Pull extends CommandBase {

    private final Frc4947Controller m_controller;
    private final Winch m_winch;

    private static final double kAutoRewindDistance = 10.0;
    private static final double kAutoRewindSpeed = -0.9;
    private static final double kJoystickDeadband = 0.1;
    private boolean m_isAutoOpening = false;
    private boolean m_isClosing = false;
    private double m_autoRewindStartPos;

    public Pull(Frc4947Controller controller, Winch winch) {
        m_controller = controller;
        m_winch = winch;

        addRequirements(winch);
    }

    public void reset() {
        m_isAutoOpening = false;
        m_isClosing = false;
    }

    @Override
    public void execute() {
        final double joystickSpeed = m_controller.getRightY();
    
        final double speed = joystickSpeed; //semiAutoUnwinchStateMachine(joystickSpeed);

        //System.out.println("Winch speed: " + speed);
        m_winch.pull(speed);
    }

    private double semiAutoUnwinchStateMachine(final double joystickSpeed) {
        final boolean autoUnwindEnabled = false; // m_controller.kY.getAsBoolean()

        final double speed;
        if (m_isAutoOpening) {
            final double distanceTraveled = m_autoRewindStartPos - m_winch.getEncoderDistance();
            final boolean finishedOpening = m_winch.isOpenLimitSwitchPressed(); // distanceTraveled > kAutoRewindDistance
            if (finishedOpening) {
                m_isAutoOpening = false;
                speed = 0.0;
                System.out.println("Winch has finished auto-opening");
            } else {
                System.out.println("Winch is auto-opening...");
                speed = kAutoRewindSpeed;
            }
        } else {
            if (m_isClosing) {
                if (autoUnwindEnabled && joystickSpeed > 0.0 && m_winch.isClosedLimitSwitchPressed()) {
                    m_isAutoOpening = true;
                    m_isClosing = false;
                    m_autoRewindStartPos = m_winch.getEncoderDistance();
                    speed = 0.0;
                    System.out.println("Winch has reached closed position");
                } else if (joystickSpeed <= kJoystickDeadband) {
                    m_isClosing = false;
                    speed = 0.0;
                    System.out.println("Winch is stopped");
                } else {
                    speed = joystickSpeed;
                }
            } else if (joystickSpeed > kJoystickDeadband) {
                m_isClosing = true;
                speed = joystickSpeed;
                System.out.println("Winch is closing");
            } else if (joystickSpeed < (0 - kJoystickDeadband)) {
                speed = joystickSpeed;
            } else {
                speed = 0.0;
            }
        }

        return speed;
    }
}
