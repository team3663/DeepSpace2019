/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SetEFIntakeSpeed extends Command {
  // speed value between -1 and 1
  private double speed;

  public C_SetEFIntakeSpeed(double speed) {
    requires(Robot.getBall());
    this.speed = speed;
  }

  @Override
  protected void execute() {
    Robot.getBall().setEFIntakeSpeed(speed);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
