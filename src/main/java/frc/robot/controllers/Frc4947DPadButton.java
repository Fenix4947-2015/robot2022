package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class Frc4947DPadButton extends Button {

    private final XboxController m_controller;
    private final int m_povAngle;

    public Frc4947DPadButton(XboxController controller, int povAngle) {
        m_controller = controller;
        m_povAngle = povAngle;
    }

    @Override
    public boolean get() {
        return (m_controller.getPOV() == m_povAngle);
    }
}
