package frc.robot.joysticks;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.JoystickConstants;

/**
 * For sticks
 *   - neutral: ~0 (see deadband)
 *   - horizontal range: -1 (left) to 1 (right)
 *   - vertical range: -1 (down) to 1 (up)
 *
 * For triggers
 *   - neutral: 0
 *   - fully pressed: 1
 *
 * For D-PAD
 *   - supported buttons: up, right, down, left
 *   - intermediate buttons are NOT supported (e.g. upper-left)
 *
 * Usage example:
 *   XboxJoystick.Driver.X.whenPressed(new PrintCommand("Driver pressed X"));
 */
public enum XboxJoystick {
    Driver(JoystickConstants.DRIVER_PORT),
    Helper(JoystickConstants.HELPER_PORT);

    private static final double DEADBAND = 0.15;

    public final JoystickButton A;
    public final JoystickButton B;
    public final JoystickButton X;
    public final JoystickButton Y;
    public final JoystickButton leftBumper;
    public final JoystickButton leftStick;
    public final JoystickButton rightBumper;
    public final JoystickButton rightStick;
    public final JoystickButton back;
    public final JoystickButton start;

    public final Button dPadUp;
    public final Button dPadRight;
    public final Button dPadDown;
    public final Button dPadLeft;

    private final XboxController controller;

    private XboxJoystick(int port) {
        controller = new XboxController(port);

        A = new JoystickButton(controller, XboxController.Button.kA.value);
        B = new JoystickButton(controller, XboxController.Button.kB.value);
        X = new JoystickButton(controller, XboxController.Button.kX.value);
        Y = new JoystickButton(controller, XboxController.Button.kY.value);

        leftBumper = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
        leftStick = new JoystickButton(controller, XboxController.Button.kLeftStick.value);

        rightBumper = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
        rightStick = new JoystickButton(controller, XboxController.Button.kRightStick.value);

        back = new JoystickButton(controller, XboxController.Button.kBack.value);
        start = new JoystickButton(controller, XboxController.Button.kStart.value);

        dPadUp = new XboxDPadButton(controller, 0);
        dPadRight = new XboxDPadButton(controller, 90);
        dPadDown = new XboxDPadButton(controller, 180);
        dPadLeft = new XboxDPadButton(controller, 270);
    }

    public double getLeftX() {
        return MathUtil.applyDeadband(controller.getLeftX(), DEADBAND);
    }

    public double getLeftY() {
        return -MathUtil.applyDeadband(controller.getLeftY(), DEADBAND);
    }

    public double getLeftTriggerAxis() {
        return controller.getLeftTriggerAxis();
    }

    public double getRightX() {
        return MathUtil.applyDeadband(controller.getRightX(), DEADBAND);
    }

    public double getRightY() {
        return -MathUtil.applyDeadband(controller.getRightY(), DEADBAND);
    }

    public double getRightTriggerAxis() {
        return controller.getRightTriggerAxis();
    }
}
