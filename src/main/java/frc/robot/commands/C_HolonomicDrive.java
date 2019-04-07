package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.PIDCont;
import frc.robot.subsystems.SS_Swerve;

public class C_HolonomicDrive extends Command {

	private final SS_Swerve mDrivetrain;

	//variables for snap to rotate
	private static final double ANGLE_ERROR = 3;

	private PIDCont PIDCont;
  	private double kP = .0005;
	private double kI = .000;//15
	private double kD = .0000;//5
	private double maxPIDSpeed = 0.6;  

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

		forward *= Math.abs(forward);
		strafe *= Math.abs(strafe);
		rotation *= Math.abs(rotation);

		forward = deadband(forward);
		strafe = deadband(strafe);
		rotation = deadband(rotation);

		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Strafe", strafe);
		SmartDashboard.putNumber("Rotation", rotation);

		if(Robot.getOI().getPrimaryController().getRightBumperButton().get()){
			mDrivetrain.setFieldOriented(false);
			strafe = -strafe;

		}
		else{
			mDrivetrain.setFieldOriented(true);
			strafe = -strafe;
		}
		
		if(Robot.getOI().getPrimaryController().getLeftTriggerButton().get()) {
			// If the controller is out of the deadband, update the snapped rotation
			if(Math.abs(deadband(Robot.getOI().getPrimaryController().getRightXValue())) > 0 || Math.abs(deadband(Robot.getOI().getPrimaryController().getRightYValue())) > 0) {
				targetAngle = getShortestPathTwo(getSnappedJoystick());
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
		// convert rotation to degrees
		rotation = rotation / Math.PI * 180;
		// snap to closest 45 degree interval
		rotation = (int)(rotation + 22.5 * Math.signum(rotation)) / 45 * 45;
		//convert to correct angle for rockets
        if(Math.abs(rotation) == 45) {
            rotation = 30 * Math.signum(rotation);
        } else if(Math.abs(rotation) == 135) {
            rotation = 150 * Math.signum(rotation);
        }
		return (int)rotation;
	}

	/**
	 * Returns the angle to rotate to in order to rotate the least distance
	 */
	private int getShortestPath(int targetAngle) {
		int path = targetAngle - (int)mDrivetrain.getGyroAngle();
		if(Math.abs(path) > 180) {
			return (int)-Math.signum(targetAngle) * (360 - Math.abs(targetAngle));
		}
		return targetAngle;
	}

	private double getSnappedJoystick(){
		double x = Robot.getOI().getPrimaryController().getRightXValue();
		double y = Robot.getOI().getPrimaryController().getRightYValue();

		// find the angle of the joystick (x and y are flipped to make forwards be 0 degrees)
		double rotation = Math.atan2(-x, -y);

		rotation = -rotation / Math.PI * 180;

		rotation = (int)(rotation + 22.5) / 45 * 45;
		SmartDashboard.putNumber("joystick angle", rotation);

		// switch ((int)rotation){
		// 	case 45: rotation = 30;
		// 	case 135: rotation = 150;
		// 	case -180: rotation = 180;
		// 	case -45: rotation = 330;
		// 	case -135: rotation = 210;
		// 	case -90: rotation = 270;
		// 	default: rotation = 0;
		// }
		if(rotation == 45) {
            rotation = 30;
        } else if(rotation == 135) {
            rotation = 150;
		} else if (rotation == -180){
			rotation = Math.abs(rotation);
		} else if (rotation == -45){
			rotation = 330;
		} else if (rotation == -135){
			rotation = 210;
		}
		else if (rotation == -90){
			rotation = 270;
		} 	
		SmartDashboard.putNumber("joystick snap", rotation);

		return rotation;

	}

	private double getShortestPathTwo(double targetAngle) {
		double currentAngle = mDrivetrain.getGyroAngle();
		double currentAngleMod = currentAngle % 360;
        if (currentAngleMod < 0) currentAngleMod += 360;

        double delta = currentAngleMod - targetAngle;

        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }
		targetAngle += currentAngle - currentAngleMod;
		SmartDashboard.putNumber("shortest path", targetAngle);
		return targetAngle;
	}
}
