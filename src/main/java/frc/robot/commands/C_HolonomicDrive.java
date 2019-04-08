package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.PIDCont;
import frc.robot.subsystems.SS_Swerve;

public class C_HolonomicDrive extends Command {

	private final SS_Swerve mDrivetrain;

	//variables for snap to rotate
	private static final double ANGLE_ERROR = 2.5;

	private PIDCont PIDCont;
  	private double kP = 1;
	private double kI = 0;
	private double kD = 0;
	private double maxPIDSpeed = 0.4;  

	private double targetAngle = 0;

	public C_HolonomicDrive() {
		requires(Robot.getDrivetrain());
		mDrivetrain = Robot.getDrivetrain();
		PIDCont = new PIDCont(maxPIDSpeed, kP, kI, kD); //TODO kP, kI, and kD need tuning
	}

	private double deadband(double input) {
		if (Math.abs(input) < 0.05) return 0;
		return input;
	}

	@Override
	protected void execute() {
		double forward = Robot.getOI().getPrimaryController().getLeftYValue();
		double strafe = Robot.getOI().getPrimaryController().getLeftXValue();
		double rotation = Robot.getOI().getPrimaryController().getRightXValue();

		forward *= Math.abs(forward);		//[FY] Squared Input yet signum preserved
		strafe *= Math.abs(strafe);			//[FY] Squared Input yet signum preserved
		rotation *= Math.abs(rotation);		//[FY] Squared Input yet signum preserved

		forward = deadband(forward);		// to exclude incidental bump on the joystick
		strafe = deadband(strafe);			// to exclude incidental bump on the joystick
		rotation = deadband(rotation);		// to excluse incidental bump on the joystick

		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Strafe", strafe);
		SmartDashboard.putNumber("Rotation", rotation);

		// Driver's right bumper toggles between field-oriented and robot-orientated driving
		if(Robot.getOI().getPrimaryController().getRightBumperButton().get()){
			mDrivetrain.setFieldOriented(false);
			strafe = -strafe;

		}
		else{
			mDrivetrain.setFieldOriented(true);
			strafe = -strafe;
		}
		
		// Driver's left trigger to snap to cargoShip or rocket deploy angle
		if(Robot.getOI().getPrimaryController().getLeftTriggerButton().get()) {
			// If the controller is out of the deadband, update the snapped rotation
			if(Math.abs(deadband(Robot.getOI().getPrimaryController().getRightXValue())) > 0 || Math.abs(deadband(Robot.getOI().getPrimaryController().getRightYValue())) > 0) {
				targetAngle = getShortestPath(getSnappedRotation());
			}

			//if robot has not reached the target angle, set the power for that rotation that is being inputed to holonomic drive
			if(!reachedTargetAngle(targetAngle)) {
				rotation = PIDCont.get(getAngleError(targetAngle));
			}
			
		} else {
			// set target angle to current angle so robot doesn't spin unexpectedly when left bumper is pressed and right joystick
			// is not moved
			targetAngle = mDrivetrain.getGyroAngle();
		}

		SmartDashboard.putNumber("snap power", rotation);
		SmartDashboard.putNumber("snap angle ", targetAngle);
		SmartDashboard.putNumber("current angle", mDrivetrain.getGyroAngle());

		mDrivetrain.holonomicDrive(forward, strafe, rotation);
	}

	private boolean reachedTargetAngle(double targetAngle) {
		return Math.abs(getAngleError(targetAngle)) < ANGLE_ERROR;
	}

	private double getAngleError(double targetAngle) {
		return targetAngle - mDrivetrain.getGyroAngle();
	}
	@Override
	protected void end() {
		mDrivetrain.stopDriveMotors();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	/**
	 * Updated the target angle to a rotation in 45 degree intervals based on the right joystick
	 * @return the closest 45 degree angle increment to the joystick's angle
	 */
	private int getSnappedRotation() {
		double x = Robot.getOI().getPrimaryController().getRightXValue();
		double y = Robot.getOI().getPrimaryController().getRightYValue();

		// find the angle of the joystick (x and y are flipped to make forwards be 0 degrees)
		double rotation = Math.atan2(x, y);
		// [FY] convert rotation to degrees
		rotation = rotation / Math.PI * 180;	// int degree = rotation / Math.PI * 180;
		// snap to closest 45 degree interval
		rotation = (int)(rotation + 22.5 * Math.signum(rotation)) / 45 * 45;  
		// [FY] degree = (int)(degree + 22.5 * Math.signum(degree)) / 45 * 45; 
		//convert to correct angle for rockets
        if(Math.abs(rotation) == 45) {				// [FY] if(Math.abs(degree) == 45) {
            rotation = 30 * Math.signum(rotation);	// [FY] 	degree = 30 * Math.signum(degree);
        } else if(Math.abs(rotation) == 135) {		// [FY]	} else if(Math.abs(degree) == 135) {
            rotation = 150 * Math.signum(rotation);	// [FY]     degree = 150 * Math.signum(degree);
        }											// [FY] }
		return (int)rotation;						// [FY] return (int)degree;
	}

	/**
	 * Returns the angle to rotate to in order to rotate the least distance
	 */
	private int getShortestPath(int targetAngle) {
		int path = targetAngle - (int)mDrivetrain.getGyroAngle();
		if(Math.abs(path) > 180) {
			return (int) -Math.signum(targetAngle) * (360 - Math.abs(targetAngle));
		}
		return targetAngle;
	}
}
