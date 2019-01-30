/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SetRearClimberSpeed extends Command {
  private double speed;

  public C_SetRearClimberSpeed(double speed) {
    requires(Robot.getRearClimber());
    this.speed = speed;
  }

  @Override
  protected void execute() {
    Robot.getRearClimber().setCimberMotorSpeed(speed);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
