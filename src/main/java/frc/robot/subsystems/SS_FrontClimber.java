/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class SS_FrontClimber extends Subsystem {

  private CANSparkMax frontNeoOne;
  private CANSparkMax frontNeoTwo;

  private double speedMultiplier = 1;

  public SS_FrontClimber(){
    frontNeoOne = new CANSparkMax(RobotMap.CLIMBER_FRONT_MOTOR_ONE, MotorType.kBrushless);
    frontNeoTwo = new CANSparkMax(RobotMap.CLIMBER_FRONT_MOTOR_TWO, MotorType.kBrushless);
    frontNeoTwo.follow(frontNeoOne);
  }

  public void setClimberFrontMotorsSpeed(double speed) {
    frontNeoOne.set(speed * speedMultiplier);
  }

  public void setClimberFrontMotorsSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  @Override
  public void initDefaultCommand() {
  }
}
