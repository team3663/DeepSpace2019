/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SetEndEffectorIntakeSpeed extends Command {
  private double speed;

  public C_SetEndEffectorIntakeSpeed(double speed) {
    requires(Robot.getBall());
    this.speed = speed;
  }

  @Override
  protected void execute() {
    Robot.getBall().setIntakeSpeed(speed);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
