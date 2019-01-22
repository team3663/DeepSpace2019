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

/**
 * Add your docs here.
 */
public class SS_RearClimber extends Subsystem {

  private CANSparkMax rearClimberMotorOne;
  private CANSparkMax rearClimberMotorTwo;

  private double speedMultiplier = 1;

  public SS_RearClimber() {
    rearClimberMotorOne = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR_ONE, MotorType.kBrushless);
    rearClimberMotorTwo = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR_TWO, MotorType.kBrushless);
    rearClimberMotorTwo.follow(rearClimberMotorOne);
  }

  public void setClimberRearMotorsSpeed(double speed) {
    rearClimberMotorOne.set(speed * speedMultiplier);
  }

  public void setClimberRearMotorsSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  @Override
  public void initDefaultCommand() {
  }
}
