/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_RearClimber extends Command {
  private double angle;

  private double limit;

  public C_RearClimber(double angle) {
    requires(Robot.getRearClimber());
    this.angle = angle;
    limit = Robot.getRearClimber().getAngleLimit();
  }

  protected void execute() {
    if(angle < 0) {
      Robot.getRearClimber().goToDegree(0);
    } else if (angle > limit) {
      Robot.getFrontClimber().goToDegree(limit);
    } else {
      Robot.getFrontClimber().goToDegree(angle);
    }
  }
  /**
   * limit = 180 degrees
   */
  @Override
  protected boolean isFinished() {
    return false;
  }

}
