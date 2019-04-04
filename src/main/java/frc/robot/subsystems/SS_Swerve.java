/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.C_HolonomicDrive;

public class SS_Swerve extends Subsystem {

	private double mAdjustmentAngle = 0;
	private boolean mFieldOriented = true;

	private final double width = 14.5;
	private final double length = 14.5;
    private double speedMultiplier = 1;
    PIDController displayPID;
    
    double[] pid = {0, 0, 0};


    
    public SS_Swerve() {
        
        zeroGyro();

        double FR = 9;
        double FL = 8;
        double BR = 41;
        double BL = 27;
        SmartDashboard.putNumber("0 Offset", FR);
        SmartDashboard.putNumber("1 Offset", FL);
        SmartDashboard.putNumber("2 Offset", BR);
        SmartDashboard.putNumber("3 Offset", BL);

        
        

    
        mSwerveModules = new SwerveModule[]  {
            new SwerveModule(0, new CANSparkMax(RobotMap.getDriveMotor(0), MotorType.kBrushless), new TalonSRX(RobotMap.getAngleMotor(0)), FL),
            new SwerveModule(1, new CANSparkMax(RobotMap.getDriveMotor(1), MotorType.kBrushless), new TalonSRX(RobotMap.getAngleMotor(1)), FR),
            new SwerveModule(2, new CANSparkMax(RobotMap.getDriveMotor(3), MotorType.kBrushless), new TalonSRX(RobotMap.getAngleMotor(3)), BR),
            new SwerveModule(3, new CANSparkMax(RobotMap.getDriveMotor(2), MotorType.kBrushless), new TalonSRX(RobotMap.getAngleMotor(2)), BL),
        };
        mSwerveModules[3].setDriveInverted(true);
        mSwerveModules[2].setDriveInverted(true);
        mSwerveModules[1].setDriveInverted(true);

        

        for (SwerveModule module : mSwerveModules) {
            //module.setTargetAngle(0);
            module.setDriveGearRatio(5.7777);
            module.setDriveWheelRadius(module.getDriveWheelRadius() * 1.05);
        }


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
    
    public void setSpeedMultiplier( double speed){
        speedMultiplier = speed;
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

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new C_HolonomicDrive());
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


	public void zeroGyro() {
		setAdjustmentAngle(getRawGyroAngle());
    }
  /////////////////////////////////////////////////////
  /////////////////////////////////////////////////////






  /////////////////////////////////////////////////////
  public static final double WHEELBASE = 21.56;  // Swerve bot: 14.5 Comp bot: 20.5
  public static final double TRACKWIDTH = 24.5; // Swerve bot: 13.5 Comp bot: 25.5

  public static final double WIDTH = 21;  // Swerve bot: 20 Comp bot: 37
  public static final double LENGTH = 21; // Swerve bot: 19 Comp bot: 32

    
	/*
	 *
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 *
     * 
     *   front
     * 1------0
     * |      |
     * |      |
     * 2------3
	 */
	private SwerveModule[] mSwerveModules;

    private AHRS mNavX = new AHRS(Port.kUSB);
    private double offset = 0;
    private boolean firstTime = true;
    /**
     * @deprecated
     * @param forward
     * @param strafe
     * @param rotation
     * @return
     */
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

    public double getPitch() {
        double angle = mNavX.getPitch();
        if(angle >= 360){
        angle %= 360;
        }
        return angle;
    }

    public double getRawAngle() {
        return mNavX.getAngle();
    }

    public void hardReset(){
        mNavX.reset();
    }

    public void softReset(){
        if(firstTime){
            offset = -getPitch();
            firstTime = false;
        }
    }

    public double getOffsetPitch(){
        SmartDashboard.putNumber("Pitch", getPitch() + offset);
        return getPitch() + offset;
    }
    public double getGyroAcelleration(){
        return mNavX.getAccelFullScaleRangeG();
    }

    public SwerveModule getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    public void holonomicDrive(double forward, double strafe, double rotation) {
        // slows everything down
        forward *= getSpeedMultiplier();
        strafe *= getSpeedMultiplier();
        rotation *= getSpeedMultiplier();

        boolean fieldOriented = isFieldOriented();

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

}
