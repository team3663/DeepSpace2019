/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EndEffectorAngle extends Command {
  private double angle;

  private double forwardAngleLimit;
  private double backwardAngleLimit;

  public C_EndEffectorAngle(double angle) {
    requires(Robot.getEndEffectorAngle());
    this.angle = angle;

    forwardAngleLimit = Robot.getEndEffectorAngle().getFrontAngleLimit();
    backwardAngleLimit = Robot.getEndEffectorAngle().getBackAngleLimit();
    if(angle > forwardAngleLimit){
      angle = forwardAngleLimit;
    }
    if(angle < backwardAngleLimit){
      angle = backwardAngleLimit;
    }
  }

  protected void execute() {
    Robot.getEndEffectorAngle().goToDegree(angle);
    
  }
  @Override
  protected boolean isFinished() {
    return Robot.getElevator().getAverageInch() > Robot.getElevator().getSafeFlipHeight();
  }
  @Override
  public synchronized boolean isInterruptible() {
    return true;
  }

}
