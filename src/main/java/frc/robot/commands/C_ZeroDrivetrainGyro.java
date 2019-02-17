package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SS_Swerve;

public class C_ZeroDrivetrainGyro extends Command {
	private SS_Swerve mDrivetrain;

	public C_ZeroDrivetrainGyro(SS_Swerve drivetrain) {
		mDrivetrain = drivetrain;
	}

	@Override
	public void execute() {
		mDrivetrain.zeroGyro();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
