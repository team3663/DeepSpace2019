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

  private final double GEAR_RATIO = 1.0/300.0;

  private double ANGLE_LIMIT = 180;


  public SS_RearClimber() {
    rearClimberMotor = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR, MotorType.kBrushless);

    rearClimberMotor.getEncoder().setPosition(0);

    PID = new CANPIDController(rearClimberMotor);
    PID.setP(1);
    PID.setI(0);
    PID.setD(0);
    PID.setOutputRange(-.3, .3);
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

  public double degreeToRotation(double degree){
    return degree/360;
  }

  public double rotationToDegrees(double rotations){
    return rotations*360;
  }

  public double getRawEncoder(){
    return rearClimberMotor.getEncoder().getPosition();
  }

  public double getEncoder(){
    return gearMultiply(getRawEncoder());
  }

  public double gearMultiply(double rotation){
    return rotation * GEAR_RATIO;
  }

  public double getAngleLimit() {
    return ANGLE_LIMIT;
  }

  public double getAngle(){
    double position = rotationToDegrees(getEncoder());
    return position;
  }
  
  public void goToDegree(double degrees){
    rearClimberMotor.getPIDController().setReference(gearMultiply(degrees), 
      ControlType.kPosition);
  }

  public void resetEncoder(){
    rearClimberMotor.getEncoder().setPosition(0);
  }


}
