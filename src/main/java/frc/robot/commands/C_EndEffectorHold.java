/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EndEffectorHold extends Command {
  private double holdAngle;
  /**
   * grabs the current encoder position and sets the PID in an infinate loop until interrupted
   */
  public C_EndEffectorHold() {
    requires(Robot.getEndEffectorAngle());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isInterruptible();
    holdAngle = Robot.getEndEffectorAngle().getAngle();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.getEndEffectorAngle().goToDegree(holdAngle);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  public synchronized boolean isInterruptible() {
    return true;
  }
}
