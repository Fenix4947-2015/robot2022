package frc.robot.controllers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ControllerConstants;

/**
 * For sticks
 *   - neutral: ~0 (see ControllerConstants.kDeadband)
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
 *   Frc4947Controller driverController = new Frc4947Controller(ControllerConstants.DRIVER_PORT);
 *   driverController.X.whenPressed(new PrintCommand("Driver pressed X"));
 */
public class Frc4947Controller {

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

    private final XboxController m_controller;

    public Frc4947Controller(int port) {
        m_controller = new XboxController(port);

        A = new JoystickButton(m_controller, XboxController.Button.kA.value);
        B = new JoystickButton(m_controller, XboxController.Button.kB.value);
        X = new JoystickButton(m_controller, XboxController.Button.kX.value);
        Y = new JoystickButton(m_controller, XboxController.Button.kY.value);

        leftBumper = new JoystickButton(m_controller, XboxController.Button.kLeftBumper.value);
        leftStick = new JoystickButton(m_controller, XboxController.Button.kLeftStick.value);

        rightBumper = new JoystickButton(m_controller, XboxController.Button.kRightBumper.value);
        rightStick = new JoystickButton(m_controller, XboxController.Button.kRightStick.value);

        back = new JoystickButton(m_controller, XboxController.Button.kBack.value);
        start = new JoystickButton(m_controller, XboxController.Button.kStart.value);

        dPadUp = new Frc4947DPadButton(m_controller, 0);
        dPadRight = new Frc4947DPadButton(m_controller, 90);
        dPadDown = new Frc4947DPadButton(m_controller, 180);
        dPadLeft = new Frc4947DPadButton(m_controller, 270);
    }

    public double getLeftX() {
        return MathUtil.applyDeadband(m_controller.getLeftX(), ControllerConstants.kDeadband);
    }

    public double getLeftXSquared() {
        double leftX = getLeftX();
        return Math.copySign(leftX * leftX, leftX);
    }

    public double getLeftY() {
        return -MathUtil.applyDeadband(m_controller.getLeftY(), ControllerConstants.kDeadband);
    }

    public double getLeftYSquared() {
        double leftY = getLeftY();
        return Math.copySign(leftY * leftY, leftY);
    }

    public double getLeftTriggerAxis() {
        return m_controller.getLeftTriggerAxis();
    }

    public double getRightX() {
        return MathUtil.applyDeadband(m_controller.getRightX(), ControllerConstants.kDeadband);
    }

    public double getRightXSquared() {
        double rightX = getRightX();
        return Math.copySign(rightX * rightX, rightX);
    }

    public double getRightY() {
        return -MathUtil.applyDeadband(m_controller.getRightY(), ControllerConstants.kDeadband);
    }

    public double getRightYSquared() {
        double rightY = getRightY();
        return Math.copySign(rightY * rightY, rightY);
    }

    public double getRightTriggerAxis() {
        return m_controller.getRightTriggerAxis();
    }

    public double getTriggerAxes() {
        return getRightTriggerAxis() - getLeftTriggerAxis();
    }
}
