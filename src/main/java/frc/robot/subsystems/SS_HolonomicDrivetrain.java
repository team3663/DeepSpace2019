package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.C_HolonomicDrive;

public class SS_HolonomicDrivetrain extends Subsystem {

	private double mAdjustmentAngle = 0;
	private boolean mFieldOriented = true;

	private final double width;
	private final double length;
	private double speedMultiplier = 1;

	public SS_HolonomicDrivetrain(double width, double length) {
		this.width = width;
		this.length = length;
	}



	public final double getWidth() {
		return width;
	}

	public final double getLength() {
		return length;
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

    public double getMaxAcceleration() {
        return 5.5;
    }

    public double getMaxVelocity() {
        return 10;
    }


	public double getAdjustmentAngle() {
		return mAdjustmentAngle;
	}

	public double getGyroAngle(){
		return Robot.getSwerve().getGyroAngle();
	}

	public double getRawGyroAngle(){
		return Robot.getSwerve().getRawGyroAngle();
	}

	public void holonomicDrive(double forward, double strafe, double rotation) {
		Robot.getSwerve().holonomicDrive(forward, strafe, rotation, isFieldOriented());
	}

	@Override
	protected void initDefaultCommand() {
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

	public void stopDriveMotors(){

	}

	public void zeroGyro() {
		setAdjustmentAngle(getRawGyroAngle());
	}
}
