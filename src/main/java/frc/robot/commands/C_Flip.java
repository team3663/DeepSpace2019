/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_Flip extends Command {
  private boolean isFront;
  public C_Flip(boolean isFront) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getElevator());
    this.isFront = isFront;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.getElevator().goToInch(Robot.getElevator().getSafeFlipHeight());
    if(Robot.getElevator().atSafeFlipHeight()){
      Robot.getEndEffectorAngle().goToDegree(Robot.getEndEffectorAngle().getSafeFlipAngle(isFront));
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.getEndEffectorAngle().isFliped(isFront);
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
