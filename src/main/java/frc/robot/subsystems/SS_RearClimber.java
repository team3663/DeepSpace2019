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
import frc.robot.commands.test_commands.C_RearClimberDirect;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_RearClimber extends Subsystem {

  private CANSparkMax rearClimberMotor;
  private CANPIDController PID;

  private double fakeEncoder = 0;
  private double speedMultiplier = 0.3;

  private double GEAR_RATIO = 1/210;
  private double TICKS_PER_DEGREE = GEAR_RATIO/360; 

  private double ANGLE_LIMIT = 180;


  public SS_RearClimber() {
    rearClimberMotor = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR, MotorType.kBrushless);

    rearClimberMotor.getEncoder().setPosition(0);

    PID = new CANPIDController(rearClimberMotor);
    PID.setP(1);
    PID.setI(.01);
    PID.setD(3);
    PID.setOutputRange(-1, 1);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_RearClimberDirect());
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

  public double getAngleLimit() {
    return ANGLE_LIMIT;
  }

  public double getAngle(){
    double position = getRawEncoder() * GEAR_RATIO * 360;
    if(position > 360 || position < -360) {
      position %= 360;
      position *= 360;
    }
    return position;
  }
  
  public void goToDegree(double degrees){
    //rearClimberMotor.getPIDController().setReference(degrees * REAR_CLIMBER_MOTOR_GEAR_RATIO * TICKS_PER_DEGREE, 
      //ControlType.kPosition);
  }


}
