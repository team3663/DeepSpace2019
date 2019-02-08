/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.*;
import frc.robot.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;


public class SS_EndEffectorAngle extends Subsystem {

  private CANSparkMax angleMotor;

  private double GEAR_RATIO = 1/100;
  private double TICKS_PER_DEGREE = 1/360;
  private double speedMultiplier = .2;

  //TODO: double check these angles
  private double FRONT_ANGLE_LIMIT = 95; 
  private double BACK_ANGLE_LIMIT = 270;

  public SS_EndEffectorAngle() {
    angleMotor = new CANSparkMax(RobotMap.ENDEFFECTOR_ANGLE_MOTOR, MotorType.kBrushless);
    angleMotor.getEncoder().setPosition(30);
    angleMotor.setIdleMode(IdleMode.kBrake);
    angleMotor.setInverted(true);
  }

  public double getFrontAngleLimit() {
    return FRONT_ANGLE_LIMIT;
  }

  public double getBackAngleLimit() {
    return BACK_ANGLE_LIMIT;
  }

  public void goToDegree(double degree) {
    angleMotor.getPIDController().setReference(degree * TICKS_PER_DEGREE / GEAR_RATIO, 
      ControlType.kPosition);
  }

  public double getRawEncoder(){
    return angleMotor.getEncoder().getPosition();
  }

  public double getAngle(){

    double angle = -getRawEncoder() * GEAR_RATIO * 360;
    if(angle < 0){
      angle += 360;
    }
    return angle;
  }

  //TODO: this should not ever exist, as this will break a bunch of things, good for testing tho
  public void setAngleSpeed(double speed){
    angleMotor.set(speed * speedMultiplier);
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

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new C_EndEffectorAngle(0));
  }
}
