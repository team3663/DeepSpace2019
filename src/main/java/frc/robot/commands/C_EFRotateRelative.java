/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EFRotateRelative extends Command {
  private boolean isFront;
  private double absAdjustmentAngle;
  private double adjustment; 
  public C_EFRotateRelative(boolean isFront, double absAdjustmentAngle) {
   requires(Robot.getEndEffectorAngle());
   this.isFront = isFront;
   this.absAdjustmentAngle = Math.abs(absAdjustmentAngle);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(isFront){
      this.adjustment = Robot.getEndEffectorAngle().getSafeFlipAngle(isFront) - absAdjustmentAngle;

    }
    else{
      this.adjustment = Robot.getEndEffectorAngle().getSafeFlipAngle(isFront) + absAdjustmentAngle;

    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(Robot.getEndEffectorAngle().isFlipped(isFront)){
      Robot.getEndEffectorAngle().goToDegree(adjustment);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println(" C EF ROTATE RELTAVITE IS RUNNING");
    return Robot.getEndEffectorAngle().atTarget(adjustment) || !Robot.getEndEffectorAngle().isFlipped(isFront);
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
