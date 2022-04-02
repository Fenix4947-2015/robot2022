package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.InstakeConstants;

public class Intake extends SubsystemBase {

    private final Solenoid m_latch = new Solenoid(PneumaticsModuleType.CTREPCM, InstakeConstants.kLatchSolenoidChannelId);

    public void unlatch() {
        m_latch.set(true);
    }
}
