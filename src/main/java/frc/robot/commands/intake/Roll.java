package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.Intake;

public class Roll extends CommandBase {

    private final Frc4947Controller m_controller;
    private final Intake m_intake;

    public Roll(Frc4947Controller controller, Intake intake) {
        m_controller = controller;
        m_intake = intake;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        double speed = (m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis());
        m_intake.roll(speed);
    }
}