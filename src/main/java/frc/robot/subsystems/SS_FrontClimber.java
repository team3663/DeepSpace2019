/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.commands.test_commands.C_FrontClimberDirect;

/**
 * Add your docs here.
 */
public class SS_FrontClimber extends Subsystem {

  private CANSparkMax frontClimberMotor;
  private CANPIDController PID;
  
  private final double TOP_ANGLE_LIMIT = -25;
  private final double BOTTOM_ANGLE_LIMIT = 200;

  private final double SAFE_FLIP_TOP = 85;
  private final double SAFE_FLIP_BOT = 115;

  private final double MAX_RPM = 5900;
  private final double GEAR_RATIO = 1.0/252.0;
  private final double TICKS_PER_DEGREE = 1.0/360.0;
  private double fakeEncoder = 0;

  private boolean initialized = false;
  //TODO: need to find actual lengths
  private final double FRONT_CLIMBER_RADIUS = 16.5;
  private final double ARM_BASE_DISTANCE = 4;
  private final double LEVEL_THREE_INCH = 18;
  private final double LEVEL_TWO_INCH = 6;
  private double selectedLevel = 3;
  private double frontClimberSpeedMultiplier = 0.5;//askInitDefault
  private double cargoIntakeSpeedMultiplier = 1;
  public SS_FrontClimber() {
    frontClimberMotor = new CANSparkMax(RobotMap.CLIMBER_FRONT_MOTOR, MotorType.kBrushless);

    frontClimberMotor.setIdleMode(IdleMode.kBrake);
    frontClimberMotor.setInverted(true);



    PID = new CANPIDController(frontClimberMotor);
    PID.setP(.6);    //.4  for music use commented pid values
    PID.setI(.00001);  //.0001
    PID.setD(0);     //10
    PID.setOutputRange(-1, 1);
    
    
    // //max RPM 5900

    // frontClimberMotor.getPIDController().setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);

    // frontClimberMotor.getPIDController().setSmartMotionMaxVelocity((MAX_RPM*GEAR_RATIO)*.75, 0);

    // //lower the value the slower it will ramp

    // frontClimberMotor.getPIDController().setSmartMotionMaxAccel(1500, 0);
    // //2 degree error
    // frontClimberMotor.getPIDController().setSmartMotionAllowedClosedLoopError(GEAR_RATIO*360*2, 0);
    // frontClimberMotor.getPIDController().setSmartMotionMinOutputVelocity(0, 0);
  }
  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new C_FrontClimberDirect());
  }

  public void setSpeed(double speed){
    frontClimberMotor.set(speed * frontClimberSpeedMultiplier);
  }

  public void setIntakeSpeedMultiplier(double speedMuliplier){
    cargoIntakeSpeedMultiplier = speedMuliplier;
  }

  public void setClimberSpeedMulitplier(double speedMuliplier){
    frontClimberSpeedMultiplier = speedMuliplier;
  }

  public void setBrakeMode(){
    frontClimberMotor.setIdleMode(IdleMode.kBrake);
  }

  public double degreeToRotation(double degree) {
    return degree/360;
  }

  public double ticksToDegrees(double ticks) {
    return ticks/TICKS_PER_DEGREE;
  }

  public double getRawEncoder() {
    return frontClimberMotor.getEncoder().getPosition();
  }

  public void resetEncoder(){
    frontClimberMotor.getEncoder().setPosition(0);
  }


  public double getAngle(){
    double position = getRawEncoder() * GEAR_RATIO * 360;
    if(position > 360 || position < -360) {
      position %= 360;
      position *= 360;
    }
    return position;
  }
  public double getHeight(double pitch){
    double inchFromLevel = FRONT_CLIMBER_RADIUS * Math.cos(Math.toRadians(getAngle()));
    double height = (getSelectedLevelInch() - ARM_BASE_DISTANCE) - Math.round(inchFromLevel * Math.pow(10, 7)) / Math.pow(10, 7);
    double tiltHeight =  height - (FRONT_CLIMBER_RADIUS*Math.sin(Math.toRadians(pitch)));
    return tiltHeight * Math.cos(Math.toRadians(10));

  }
  public boolean atTarget(double angle){
    return getAngle() < angle + 1 && getAngle() > angle - 1;
  }

  public void goToDegree(double degree) {
    frontClimberMotor.getPIDController().setReference(degreeToRotation(degree) * (1/GEAR_RATIO), ControlType.kPosition);
    // frontClimberMotor.getPIDController().setReference(degreeToRotation(degree) * (1/GEAR_RATIO), ControlType.kSmartMotion);

  }

  public void goToInch(double level, double inch){
    selectedLevel = level;
    double targetInchHeigtDiff = inch - getSelectedLevelInch();
    double targetInch = ARM_BASE_DISTANCE + targetInchHeigtDiff;
    
    double degreesToRotate = Math.toDegrees(Math.acos((double)(targetInch/FRONT_CLIMBER_RADIUS)));
    goToDegree(180 - degreesToRotate);
  }
  public double getSelectedLevelInch(){
    if(selectedLevel == 2){
      return LEVEL_TWO_INCH;
    }
    return LEVEL_THREE_INCH;
  }

  public double getTopAngleLimit() {
    return TOP_ANGLE_LIMIT;
  }

  public double getBottomAngleLimit() {
    return BOTTOM_ANGLE_LIMIT;
  }

  public boolean isInitialized(){
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

  public boolean safeToFlip(){
    return getAngle() > SAFE_FLIP_TOP && getAngle() < SAFE_FLIP_BOT;
  }
  
  public double getSafeTop(){
    return SAFE_FLIP_TOP;
  }

  public double getSafeBot(){
    return SAFE_FLIP_BOT;
  }

  public double getGearRatio() {
    return GEAR_RATIO;
  }

}
