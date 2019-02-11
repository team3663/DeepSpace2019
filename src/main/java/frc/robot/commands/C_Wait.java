package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.util.ElapsedTime;

/**
 * Waits for a given period
 */
public class C_Wait extends Command {
	private final ElapsedTime time = new ElapsedTime();
	private final int durationMillis;

	public C_Wait(int durationMillis) {
		this.durationMillis = durationMillis;
	}
	
	public static C_Wait fromSeconds(int seconds) {
		return new C_Wait(seconds * 1000);
	}

	@Override
	protected void initialize() {
		time.reset();
	}

	@Override
	protected boolean isFinished() {
		return time.getElapsedMillis() >= durationMillis;
	}
}