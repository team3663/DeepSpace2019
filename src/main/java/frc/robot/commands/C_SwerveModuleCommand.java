package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SwerveModule;

public class C_SwerveModuleCommand extends Command {

	private SwerveModule mDriveModule;

	public C_SwerveModuleCommand(SwerveModule driveModule) {
		this.mDriveModule = driveModule;

		requires(driveModule);
	}

	@Override
	protected void execute() {
		// TODO: Handle smart swerve drive angling.
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
