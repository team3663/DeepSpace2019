package frc.robot.subsystems;

import frc.robot.commands.C_HolonomicDrive;

public abstract class SS_HolonomicDrivetrain extends SS_Drivetrain {

	private double mAdjustmentAngle = 0;
	private boolean mFieldOriented = true;

	public SS_HolonomicDrivetrain(double width, double length) {
		super(width, length);
	}


	public double getAdjustmentAngle() {
		return mAdjustmentAngle;
	}

	public abstract double getGyroAngle();

	public abstract double getRawGyroAngle();

	public void holonomicDrive(double forward, double strafe, double rotation) {
		holonomicDrive(forward, strafe, rotation, isFieldOriented());
	}

	public abstract void holonomicDrive(double forward, double strafe, double rotation, boolean fieldOriented);

	@Override
	protected void initDefaultCommand() {
		System.out.println("you got AT LEANT THIS FAR");
		setDefaultCommand(new C_HolonomicDrive(this));
	}

	public boolean isFieldOriented() {
		return mFieldOriented;
	}

	public void setAdjustmentAngle(double adjustmentAngle) {
		System.out.printf("New Adjustment Angle: % .3f\n", adjustmentAngle);
		mAdjustmentAngle = adjustmentAngle;
	}

	public void setFieldOriented(boolean fieldOriented) {
		mFieldOriented = fieldOriented;
	}

	public abstract void stopDriveMotors();

	public void zeroGyro() {
		setAdjustmentAngle(getRawGyroAngle());
	}
}
