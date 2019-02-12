/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_Climb extends Command {
  private double frontTargetAngle;
  private double rearTargetAngle;

  public C_Climb() {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getGyro());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getGyro().reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.getFrontClimber().goToDegree(-(getAngleError() + Robot.getOI().getTestController().getLeftYValue()));
    Robot.getRearClimber().goToDegree(getAngleError() + Robot.getOI().getTestController().getLeftYValue());
  }

  private double getAngleError(){
    return Robot.getGyro().getAngle();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
