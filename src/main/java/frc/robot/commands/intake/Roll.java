package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Roll extends CommandBase {

    private final Frc4947Controller m_controller;
    private final Intake m_intake;
    private final Shooter m_shooter;

    public Roll(Frc4947Controller controller, Intake intake, Shooter shooter) {
        m_controller = controller;
        m_intake = intake;
        m_shooter = shooter;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        double speed = (m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis());
        m_intake.roll(speed);
        // if (Math.abs(speed) > 0.1) {
        //     m_shooter.spinFrontSlow();
        // }
    }
}