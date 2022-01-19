package frc.robot.joysticks;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class XboxDPadButton extends Button {

    private final XboxController controller;
    private final int povAngle;

    public XboxDPadButton(XboxController controller, int povAngle) {
        this.controller = controller;
        this.povAngle = povAngle;
    }

    @Override
    public boolean get() {
        return (controller.getPOV() == povAngle);
    }
}
