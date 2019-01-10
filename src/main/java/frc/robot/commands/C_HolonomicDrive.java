package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class C_HolonomicDrive extends Command {
	

	public C_HolonomicDrive() {
		requires(Robot.ss_holonomicdrivetrain);
	}

	private double deadband(double input) {
		if (Math.abs(input) < 0.05) return 0;
		return input;
	}

	@Override
	protected void initialize() {
		System.out.println("eggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggeggegg");

	}

	@Override
	protected void execute() {

		System.out.println("egg");
		// double forward = -Robot.getOI().getPrimaryController().getLeftYValue();
		// double strafe = Robot.getOI().getPrimaryController().getLeftXValue();
		// double rotation = Robot.getOI().getPrimaryController().getRightXValue();

		double rotation =1;
		double strafe = 1;
		double forward = 1;

		forward *= Math.abs(forward);
		strafe *= Math.abs(strafe);
		rotation *= Math.abs(rotation);

		forward = deadband(forward);
		strafe = deadband(strafe);
		rotation = deadband(rotation);

		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Strafe", strafe);
		SmartDashboard.putNumber("Rotation", rotation);

		Robot.ss_holonomicdrivetrain.holonomicDrive(forward, strafe, rotation);
	}

	@Override
	protected void end() {
		Robot.ss_holonomicdrivetrain.stopDriveMotors();
	}

	@Override
	protected void interrupted() {
		//end();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
