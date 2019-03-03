/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_FrontClimber extends Command {
  private double angle;

  private boolean hatchAware;

  private double topAngleLimit;
  private double bottomAngleLimit;

  public C_FrontClimber(double angle) {
    requires(Robot.getFrontClimber());
    this.angle = angle;
    this.hatchAware = false;

    topAngleLimit = Robot.getFrontClimber().getTopAngleLimit();
    bottomAngleLimit = Robot.getFrontClimber().getBottomAngleLimit();
  }

  public C_FrontClimber(double angle, boolean hatchAware) {
    requires(Robot.getFrontClimber());
    requires(Robot.getHatch());
    this.angle = angle;
    this.hatchAware = hatchAware;

    topAngleLimit = Robot.getFrontClimber().getTopAngleLimit();
    bottomAngleLimit = Robot.getFrontClimber().getBottomAngleLimit();
  }

  protected void execute() {
    if(hatchAware){
      if(!Robot.getHatch().isPresent()){
        if(angle < topAngleLimit) {
          Robot.getFrontClimber().goToDegree(topAngleLimit);
        } else if (angle > bottomAngleLimit) {
          Robot.getFrontClimber().goToDegree(bottomAngleLimit);
        } else {
          Robot.getFrontClimber().goToDegree(angle);
        }
      }
    }
    else{
      if(angle < topAngleLimit) {
        Robot.getFrontClimber().goToDegree(topAngleLimit);
      } else if (angle > bottomAngleLimit) {
        Robot.getFrontClimber().goToDegree(bottomAngleLimit);
      } else {
        Robot.getFrontClimber().goToDegree(angle);
      }
    }

  }

  /**
   * TopAngleLimit = -25
   * BottomAngleLimit = 200
   */
  @Override
  protected boolean isFinished() {
    if(hatchAware){
      return Robot.getFrontClimber().atTarget(angle) || Robot.getHatch().isPresent();
    }
    else{
      return Robot.getFrontClimber().atTarget(angle);
    }
  }
  

}
