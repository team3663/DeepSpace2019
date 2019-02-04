/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.commands.C_RearClimberDirect;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_RearClimber extends Subsystem {

  private CANSparkMax rearClimberMotor;
  private CANPIDController PID;

  private double fakeEncoder = 0;
  private double speedMultiplier = 0.3;
  private double TICKS_PER_DEGREE = 1/360; 
  private double GEAR_RATIO = 1/210;

  public SS_RearClimber() {
    rearClimberMotor = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR, MotorType.kBrushless);
    PID = new CANPIDController(rearClimberMotor);
    PID.setP(1);
    PID.setI(.01);
    PID.setD(3);
    PID.setOutputRange(-1, 1);
  }

  public void setCimberMotorSpeed(double speed){
    rearClimberMotor.set( -speed * speedMultiplier);
  }

  public void setSpeedMultiplier(double speedMultiplier){
    this.speedMultiplier = speedMultiplier;
  }

  public double degreeToTicks(int degree){
    return degree*TICKS_PER_DEGREE;
  }

  public double ticksToDegrees(int ticks){
    return ticks/TICKS_PER_DEGREE;
  }

  public double getRawEncoder(){
    return rearClimberMotor.getEncoder().getPosition();
  }

  //Returns a positive between 0 and 180 degrees if climber is forward(out)
  //Or a negative between 0 and -180 degrees if climber is backward(in the robot frame)
  public double getAngle(){
    //fakeEncoder = Math.round(Math.abs(getRawEncoder() - 0.5));
    //return (Math.abs(getRawEncoder()) - fakeEncoder) * 360;
    double position = getRawEncoder() * GEAR_RATIO;
    if(position > 1 || position < -1) {
      position %= 360;
    }
    if(position > .5 || position < -.5) {
      position = 1 - position;
    }
    return position * 360;
  }
  public void goToDegree(double degrees){
    //rearClimberMotor.getPIDController().setReference(degrees * REAR_CLIMBER_MOTOR_GEAR_RATIO * TICKS_PER_DEGREE, 
      //ControlType.kPosition);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_RearClimberDirect());
  }
}
