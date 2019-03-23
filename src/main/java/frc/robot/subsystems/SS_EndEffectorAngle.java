/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.*;
import frc.robot.*;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;



public class SS_EndEffectorAngle extends Subsystem {

  private boolean initialized = false;
  private boolean flipFailed = false;

  private CANSparkMax angleMotor;
  private DigitalInput angleResetSwitch;
  private CANPIDController PID;


  private double GEAR_RATIO = 1.0/147.0;
  private double TICKS_PER_DEGREE = 1.0/360.0;
  private double speedMultiplier = .5;

  //TODO: double check these angles
  private double FRONT_ANGLE_LIMIT = 95; 
  private double VERTICAL_ANGLE = 0;
  private double BACK_ANGLE_LIMIT = -80;

  private double SAFE_FLIP_BACK_ANGLE = -85;
  private double SAFEFLIP_FRONT_ANGLE = 90;

  public SS_EndEffectorAngle() {
    angleMotor = new CANSparkMax(RobotMap.ENDEFFECTOR_ANGLE_MOTOR, MotorType.kBrushless);
    angleMotor.setIdleMode(IdleMode.kBrake);
    angleMotor.setInverted(false);

    angleResetSwitch = new DigitalInput(RobotMap.ANGLE_RESET_SWITCH);

    PID = new CANPIDController(angleMotor);
    // PID.setP(.5);
    // PID.setI(.00001); 
    // PID.setD(0);     
    // PID.setOutputRange(-.85, .85);

    PID.setP(2);
    PID.setI(.00003); //.00003
    PID.setD(.0005);    //.04 
    PID.setOutputRange(-.6, .6);
    
    
    
    
    PID.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);

  }
  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new C_EndEffectorHold());
  }

  public double getSafeFlipAngle(boolean isFront){

    if(isFront){
      return SAFEFLIP_FRONT_ANGLE;
    }
    else{
      return SAFE_FLIP_BACK_ANGLE;
    }
  }

  public double getFrontAngleLimit() {
    return FRONT_ANGLE_LIMIT;
  }

  public double getBackAngleLimit() {
    return BACK_ANGLE_LIMIT;
  }

  public boolean getFlipFailed(){
    return flipFailed;
  }
  public void setFlipFailed(boolean flipFailed){
    this.flipFailed = flipFailed;
  }

  public void goToDegree(double degree) {
    angleMotor.getPIDController().setReference(degree / 360 * (1 / GEAR_RATIO), 
      ControlType.kPosition);
  }

  public void goToPos(double pos) {
    angleMotor.getPIDController().setReference(pos, 
      ControlType.kPosition);
  }

  public double getRawEncoder(){
    return angleMotor.getEncoder().getPosition();
  }

  public double getAngle(){

    double angle = getRawEncoder() * GEAR_RATIO * 360.0;

    return angle;
  }

  public boolean atTarget(double angle){
    return getAngle() < angle + 3 && getAngle() > angle - 3;
  }

  public boolean isFlipped(boolean toFront){
    if(toFront){
      return getAngle() > 85;
    }
    else{
      return getAngle() < -65;
    }
    
  }
  public void resetEncoder(){
    angleMotor.getEncoder().setPosition(38.3);
  }

  public void setAngleSpeed(double speed){
    angleMotor.set(-speed * speedMultiplier);
  }

  public double ticksToDegree(double ticks){
    return (ticks * GEAR_RATIO) / TICKS_PER_DEGREE;
  }
  public double degreesToTicks(double degrees){
    return degrees * TICKS_PER_DEGREE;
  }

  public void setSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  public DigitalInput getAngleSwitch(){
    return angleResetSwitch;
  }
  public boolean isReset(){
    return !angleResetSwitch.get();
  }  

  public boolean isInitialized(){
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }


}
