package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import static frc.robot.RobotMap.*;

public class S_Swerve extends SS_HolonomicDrivetrain {
    public static final double WHEELBASE = 14.5;  // Swerve bot: 14.5 Comp bot: 20.5
    public static final double TRACKWIDTH = 14.5; // Swerve bot: 13.5 Comp bot: 25.5

    public static final double WIDTH = 21;  // Swerve bot: 20 Comp bot: 37
    public static final double LENGTH = 21; // Swerve bot: 19 Comp bot: 32

    
	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
	private SwerveModule[] mSwerveModules;

    private AHRS mNavX = new AHRS(Port.kUSB);

    public S_Swerve() {
        super(WIDTH, LENGTH);
        zeroGyro();

        double FR = 92;
        double FL = 80;
        double BR = 148;
        double BL = 105;
        SmartDashboard.putNumber("Front Right", FR);
        SmartDashboard.putNumber("Front Left", FL);
        SmartDashboard.putNumber("Back Right", BR);
        SmartDashboard.putNumber("Back Left", BL);

        if (Robot.PRACTICE_BOT) {
            mSwerveModules = new SwerveModule[]  {
                    new SwerveModule(0, new TalonSRX(6), new TalonSRX(5), FL),
                    new SwerveModule(1, new TalonSRX(2), new TalonSRX(1), FR),
                    new SwerveModule(2, new TalonSRX(3), new TalonSRX(4), BL),
                    new SwerveModule(3, new TalonSRX(7), new TalonSRX(8), BR),
            };

            mSwerveModules[0].setDriveInverted(true);
            mSwerveModules[3].setDriveInverted(true);
        } else {
            mSwerveModules = new SwerveModule[] {
                    new SwerveModule(0,
                            new TalonSRX(DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR),
                            new TalonSRX(DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR),
                            87.890),
                    new SwerveModule(1,
                            new TalonSRX(DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR),
                            new TalonSRX(DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR),
                            235.195),
                    new SwerveModule(2,
                            new TalonSRX(DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR),
                            new TalonSRX(DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR),
                            320.976),
                    new SwerveModule(3,
                            new TalonSRX(DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR),
                            new TalonSRX(DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR),
                            245.742),
            };

            mSwerveModules[0].setDriveInverted(true);
            mSwerveModules[3].setDriveInverted(true);
        }

        for (SwerveModule module : mSwerveModules) {
            module.setTargetAngle(0);
            module.setDriveGearRatio(5.7777);
            module.setDriveWheelRadius(module.getDriveWheelRadius() * 1.05);
            module.setMotionConstraints(getMaxAcceleration(), getMaxVelocity());
        }
    }

    public double[] calculateSwerveModuleAngles(double forward, double strafe, double rotation) {
        if (isFieldOriented()) {
            double angleRad = Math.toRadians(getGyroAngle());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
        }

        double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
        double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
        double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
        double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

        return new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };
    }

    public AHRS getNavX() {
        return mNavX;
    }

    public double getGyroAngle() {
        double angle = mNavX.getAngle() - getAdjustmentAngle();
        angle %= 360;
        if (angle < 0) angle += 360;

        if (Robot.PRACTICE_BOT) {
            return angle;
        } else {
            return 360 - angle;
        }
    }

    public double getGyroRate() {
        return mNavX.getRate();
    }

    public double getRawGyroAngle() {
        double angle = mNavX.getAngle();
        angle %= 360;
        if (angle < 0) angle += 360;

        return angle;
    }

    public SwerveModule getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    @Override
    public void holonomicDrive(double forward, double strafe, double rotation, boolean fieldOriented) {
        forward *= getSpeedMultiplier();
        strafe *= getSpeedMultiplier();

        if (fieldOriented) {
            double angleRad = Math.toRadians(getGyroAngle());
            double temp = forward * Math.cos(angleRad) +
                    strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
        }

        double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
        double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
        double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
        double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

        double[] angles = new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };

        double[] speeds = new double[]{
                Math.sqrt(b * b + c * c),
                Math.sqrt(b * b + d * d),
                Math.sqrt(a * a + d * d),
                Math.sqrt(a * a + c * c)
        };

        double max = speeds[0];

        for (double speed : speeds) {
            if (speed > max) {
                max = speed;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (Math.abs(forward) > 0.05 ||
                    Math.abs(strafe) > 0.05 ||
                    Math.abs(rotation) > 0.05) {
                mSwerveModules[i].setTargetAngle(angles[i] + 180);
            } else {
                mSwerveModules[i].setTargetAngle(mSwerveModules[i].getTargetAngle());
            }
            mSwerveModules[i].setTargetSpeed(speeds[i]);
        }
    }

    @Override
    public void stopDriveMotors() {
        for (SwerveModule module : mSwerveModules) {
            module.setTargetSpeed(0);
        }
    }
    
    public void resetMotors() {
    	for(int i = 0; i < mSwerveModules.length; i++) {
    		mSwerveModules[i].resetMotor();
    	}
    }

    public SwerveModule[] getSwerveModules() {
        return mSwerveModules;
    }

    @Override
    public double getMaxAcceleration() {
        return 5.5;
    }

    @Override
    public double getMaxVelocity() {
        return 10;
    }
}
