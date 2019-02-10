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
  private CANSparkMax angleMotor;
  private DigitalInput angleResetSwitch;
  private CANPIDController PID;


  private double GEAR_RATIO = 1.0/100.0;
  private double TICKS_PER_DEGREE = 1.0/360.0;
  private double speedMultiplier = .3;

  //TODO: double check these angles
  private double FRONT_ANGLE_LIMIT = 105; 
  private double VERTICAL_ANGLE = 15;
  private double BACK_ANGLE_LIMIT = -60;

  private double SAFE_FLIP_ANGLE = -40;

  public SS_EndEffectorAngle() {
    angleMotor = new CANSparkMax(RobotMap.ENDEFFECTOR_ANGLE_MOTOR, MotorType.kBrushless);
    angleMotor.setIdleMode(IdleMode.kBrake);
    angleMotor.setInverted(false);

    angleResetSwitch = new DigitalInput(RobotMap.ANGLE_RESET_SWITCH);

    PID = new CANPIDController(angleMotor);
    PID.setP(.5);    //.4  for music use commented pid values
    PID.setI(.00001);  //.0001
    PID.setD(0);     //10
    PID.setOutputRange(-.6, .6);
    
    
    PID.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);

  }
  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new C_EndEffectorAngle(0));
  }

  public double getFrontAngleLimit() {
    return FRONT_ANGLE_LIMIT;
  }

  public double getBackAngleLimit() {
    return BACK_ANGLE_LIMIT;
  }

  public void goToDegree(double degree) {
    angleMotor.getPIDController().setReference(degree * GEAR_RATIO * TICKS_PER_DEGREE, 
      ControlType.kPosition);
  }

  public double getRawEncoder(){
    return angleMotor.getEncoder().getPosition();
  }

  public double getAngle(){

    double angle = getRawEncoder() * GEAR_RATIO * 360.0;
    // if(angle < 0){
    //   angle += 360;
    // }
    return angle;
  }

  public void resetEncoder(){
    angleMotor.getEncoder().setPosition(30);
  }

  //TODO: this should not ever exist, as this will break a bunch of things, good for testing tho
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
  public boolean getIsReset(){
    return !angleResetSwitch.get();
  }  

  public boolean isInitialized(){
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }


}
