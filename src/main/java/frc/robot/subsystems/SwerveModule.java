package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SwerveModule extends Subsystem {
    private static final long STALL_TIMEOUT = 2000;
    private final double ANALOG_TICKS_PER_DEGREE = 360/4096;

    private long mStallTimeBegin = Long.MAX_VALUE;

    private double mLastError = 0, lastTargetAngle = 0;

    private final int moduleNumber;

    private final double mZeroOffset;

    private final TalonSRX mAngleMotor;
    private final AnalogInput mAngleEncoder;
    private final CANSparkMax mDriveMotor;

    private boolean driveInverted = false;
    private double driveGearRatio = 1;
    private double driveWheelRadius = 2;
    private boolean angleMotorJam = false;
    
    /*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */

    public SwerveModule(int moduleNumber, CANSparkMax driveMotor, TalonSRX angleMotor, double zeroOffset) {
        this.moduleNumber = moduleNumber;

        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mAngleEncoder = new AnalogInput(moduleNumber);
        

        mZeroOffset = zeroOffset;


        //ANGLE MOTORS
        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        angleMotor.setSensorPhase(true);

        angleMotor.config_kP(0, 30, 50);     //30
        angleMotor.config_kI(0, .0006, 50);  //.001
        angleMotor.config_kD(0, 200, 50);    //200
        angleMotor.config_kF(0, .2);
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);

        //DRIVE MOTORS
        driveMotor.setControlFramePeriod(10);
        driveMotor.setIdleMode(IdleMode.kBrake);

        driveMotor.getPIDController().setP(0);     //15
        driveMotor.getPIDController().setI(.00);    //.01
        driveMotor.getPIDController().setD(0);     //.1
        driveMotor.getPIDController().setFF(0);    //.2

        
        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(30, 0);
        angleMotor.configPeakCurrentLimit(30, 0);
        angleMotor.configPeakCurrentDuration(100, 0);
        angleMotor.enableCurrentLimit(true);

        driveMotor.setSmartCurrentLimit(25);
        
    	SmartDashboard.putBoolean("Motor Jammed" + moduleNumber, angleMotorJam);
    }
    
    @Override
    protected void initDefaultCommand() {
    }

    private double encoderTicksToInches(double ticks) {
        if (Robot.PRACTICE_BOT) {
            return ticks / 36.65;
        } else {
            return ticks / 35.6;
        }
    }

    private int inchesToEncoderTicks(double inches) {
        if (Robot.PRACTICE_BOT) {
            return (int) Math.round(inches * 36.65);
        } else {
            return (int) Math.round(inches * 35.6);
        }
    }



    public TalonSRX getAngleMotor() {
        return mAngleMotor;
    }

    public AnalogInput getAngleEncoder(){
        return mAngleEncoder;
    }

    /**
     * Get the current angle of the swerve module
     *
     * @return An angle in the range [0, 360)
     */
    public double getCurrentAngle() {
        mAngleMotor.setSelectedSensorPosition(mAngleEncoder.getValue());
        double angle = mAngleMotor.getSelectedSensorPosition()* (360.0 / 4096.0);
        angle -= mZeroOffset;
        angle %= 360;
        if (angle < 0) angle += 360;

        return angle;
    }


    //distance stuff needs to be changed
    public double getDriveDistance() {
        double ticks = mDriveMotor.getEncoder().getPosition();
        if (driveInverted)
            ticks = -ticks;

        return encoderTicksToInches(ticks);
    }

    public CANSparkMax getDriveMotor() {
        return mDriveMotor;
        
    }

    public double getTargetAngle() {
        return lastTargetAngle;
    }

    public void robotDisabledInit() {
        mStallTimeBegin = Long.MAX_VALUE;
    }

    public void setDriveGearRatio(double ratio) {
        driveGearRatio = ratio;
    }

    public void setDriveInverted(boolean inverted) {
        driveInverted = inverted;
    }

    public double getDriveWheelRadius() {
        return driveWheelRadius;
    }

    public void setDriveWheelRadius(double radius) {
        driveWheelRadius = radius;
    }

    public void setTargetAngle(double targetAngle) {
//    	if(angleMotorJam) {
//    		mAngleMotor.set(ControlMode.Disabled, 0);
//    		return;
//    	}
    	
        lastTargetAngle = targetAngle;

        targetAngle %= 360;

        SmartDashboard.putNumber("Module Target Angle " + moduleNumber, targetAngle % 360);

        targetAngle += mZeroOffset;

        mAngleMotor.setSelectedSensorPosition(mAngleEncoder.getValue());
        double currentAngle = mAngleMotor.getSelectedSensorPosition() * (360.0 / 4096.0);
        double currentAngleMod = currentAngle % 360;
        if (currentAngleMod < 0) currentAngleMod += 360;

        double delta = currentAngleMod - targetAngle;

        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }

        delta = currentAngleMod - targetAngle;
        if (delta > 90 || delta < -90) {
            if (delta > 90)
                targetAngle += 180;
            else if (delta < -90)
                targetAngle -= 180;
            mDriveMotor.setInverted(false);
        } else {
            mDriveMotor.setInverted(true);
        }

        targetAngle += currentAngle - currentAngleMod;

        double currentError = mAngleMotor.getClosedLoopError(0);
//        if (Math.abs(currentError - mLastError) < 7.5 &&
//                Math.abs(currentAngle - targetAngle) > 5) {
//            if (mStallTimeBegin == Long.MAX_VALUE) {
//            	mStallTimeBegin = System.currentTimeMillis();
//            }
//            if (System.currentTimeMillis() - mStallTimeBegin > STALL_TIMEOUT) {
//            	angleMotorJam = true;
//            	mAngleMotor.set(ControlMode.Disabled, 0);
//            	mDriveMotor.set(ControlMode.Disabled, 0);
//            	SmartDashboard.putBoolean("Motor Jammed" + moduleNumber, angleMotorJam);
//            	return;
//            }
//        } else {
//            mStallTimeBegin = Long.MAX_VALUE;
//        }
        mLastError = currentError;
        targetAngle *= 4096.0 / 360.0;
        mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetDistance(double distance) {
//    	if(angleMotorJam) {
//    		mDriveMotor.set(ControlMode.Disabled, 0);
//    		return;
//    	}
        if (driveInverted) distance = -distance;

//        distance /= 2 * Math.PI * driveWheelRadius; // to wheel rotations
//        distance *= driveGearRatio; // to encoder rotations
//        distance *= 80; // to encoder ticks

        distance = inchesToEncoderTicks(distance);

        SmartDashboard.putNumber("Module Ticks " + moduleNumber, distance);

        mDriveMotor.set( distance);
    }

    public void setTargetSpeed(double speed) {
//    	if(angleMotorJam) {
//    		mDriveMotor.set(ControlMode.Disabled, 0);
//    		return;
//    	}
        if (driveInverted) speed = -speed;

        mDriveMotor.set( speed);
    }

    public void zeroDistance() {
        
        mDriveMotor.getEncoder().setPosition(0);
    }

    public void resetMotor() {
    	angleMotorJam = false;
    	mStallTimeBegin = Long.MAX_VALUE;
    	SmartDashboard.putBoolean("Motor Jammed" + moduleNumber, angleMotorJam);
    }

}
