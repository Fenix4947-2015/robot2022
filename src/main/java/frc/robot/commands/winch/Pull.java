package frc.robot.commands.winch;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controllers.Frc4947Controller;
import frc.robot.subsystems.Winch;

public class Pull extends CommandBase {

    private final Frc4947Controller m_controller;
    private final Winch m_winch;

    public Pull(Frc4947Controller controller, Winch winch) {
        m_controller = controller;
        m_winch = winch;

        addRequirements(winch);
    }

    @Override
    public void execute() {
        double speed = m_controller.getRightY();
        m_winch.pull(speed);
    }
}
