/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_RearClimber extends Subsystem {

  private CANSparkMax rearClimberMotor;

  private double speedMultiplier = 1;

  private PIDCont PID;

  public SS_RearClimber() {
    rearClimberMotor = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR_ONE, MotorType.kBrushless);
    PID = new PIDCont(1, 10, .1, 10);//TODO pid contoller tweaks
  }

  public void setClimberRearMotorsSpeed(double speed) {
    rearClimberMotorOne.set(speed * speedMultiplier);
  }

  public void setClimberRearMotorsSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  public double 

  @Override
  public void initDefaultCommand() {
  }
}
