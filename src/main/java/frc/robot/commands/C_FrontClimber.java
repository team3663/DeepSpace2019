/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.*;

public class C_FrontClimber extends Command {
  private double angle;

  private double topAngleLimit;
  private double bottomAngleLimit;

  public C_FrontClimber(double angle) {
    requires(Robot.getFrontClimber());
    requires(Robot.getEndEffector());
    this.angle = angle;

    topAngleLimit = Robot.getFrontClimber().getTopAngleLimit();
    bottomAngleLimit = Robot.getFrontClimber().getBottomAngleLimit();
  }

  /**
   * TopAngleLimit = -25
   * BottomAngleLimit = 200
   */
  @Override
  protected boolean isFinished() {
    if(angle < topAngleLimit) {
      Robot.getFrontClimber().goToDegree(topAngleLimit);
      return false;
    } else if (angle > bottomAngleLimit) {
      Robot.getFrontClimber().goToDegree(bottomAngleLimit);
      return false;
    } else {
      Robot.getFrontClimber().goToDegree(angle);
      return false;
    }
  }

}
