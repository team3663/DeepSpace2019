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
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_RearClimber extends Subsystem {

  private CANSparkMax rearClimberMotor;
  private CANPIDController PID;

  private double fakeEncoder = 0;
  private double speedMultiplier = 1;
  private double TICKS_PER_DEGREE = 0; 

  public SS_RearClimber() {
    rearClimberMotor = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR, MotorType.kBrushless);
    PID = new CANPIDController(rearClimberMotor);
    PID.setP(1);
    PID.setI(.01);
    PID.setD(3);
    PID.setOutputRange(-1, 1);
  }

  public double degreeToTicks(int degree){
    return degree*TICKS_PER_DEGREE;
  }

  public double ticksToDegrees(int ticks){
    return ticks/TICKS_PER_DEGREE;
  }

  public double getEncoder(){
    return rearClimberMotor.getEncoder().getPosition();
  }

  public double getAngle(){
    fakeEncoder = Math.round(Math.abs(getEncoder() - 0.5));

    return (Math.abs(getEncoder()) - fakeEncoder) * 360;
  }
  public void goToPos(int pos){
    rearClimberMotor.getPIDController().setReference(pos, ControlType.kPosition);
  }

  @Override
  public void initDefaultCommand() {
  }
}
