package frc.robot.controllers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
 *   Frc4947Controller driverController = new Frc4947Controller(ControllerConstants.kDriverPort);
 *   driverController.X.whenPressed(new PrintCommand("Driver pressed X"));
 */
public class Frc4947Controller {

    public final JoystickButton kA;
    public final JoystickButton kB;
    public final JoystickButton kX;
    public final JoystickButton kY;
    public final JoystickButton kLeftBumper;
    public final JoystickButton kLeftStick;
    public final JoystickButton kRightBumper;
    public final JoystickButton kRightStick;
    public final JoystickButton kBack;
    public final JoystickButton kStart;

    public final Button kDPadUp;
    public final Button kDPadRight;
    public final Button kDPadDown;
    public final Button kDPadLeft;

    private final XboxController m_controller;

    public Frc4947Controller(int port) {
        m_controller = new XboxController(port);

        kA = new JoystickButton(m_controller, XboxController.Button.kA.value);
        kB = new JoystickButton(m_controller, XboxController.Button.kB.value);
        kX = new JoystickButton(m_controller, XboxController.Button.kX.value);
        kY = new JoystickButton(m_controller, XboxController.Button.kY.value);

        kLeftBumper = new JoystickButton(m_controller, XboxController.Button.kLeftBumper.value);
        kLeftStick = new JoystickButton(m_controller, XboxController.Button.kLeftStick.value);

        kRightBumper = new JoystickButton(m_controller, XboxController.Button.kRightBumper.value);
        kRightStick = new JoystickButton(m_controller, XboxController.Button.kRightStick.value);

        kBack = new JoystickButton(m_controller, XboxController.Button.kBack.value);
        kStart = new JoystickButton(m_controller, XboxController.Button.kStart.value);

        kDPadUp = new Frc4947DPadButton(m_controller, 0);
        kDPadRight = new Frc4947DPadButton(m_controller, 90);
        kDPadDown = new Frc4947DPadButton(m_controller, 180);
        kDPadLeft = new Frc4947DPadButton(m_controller, 270);
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

    public void setLeftRumble(double value) {
        m_controller.setRumble(RumbleType.kLeftRumble, value);
    }

    public void setRightRumble(double value) {
        m_controller.setRumble(RumbleType.kRightRumble, value);
    }    
}