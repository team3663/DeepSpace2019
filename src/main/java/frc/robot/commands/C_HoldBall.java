/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_HoldBall extends Command {
  public C_HoldBall() {
    requires(Robot.getBall());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getBall().setEFIntakeSpeed(-.05);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !Robot.getBall().isPresent();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.getBall().setEFIntakeSpeed(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
