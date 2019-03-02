
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EFRestart extends Command {
  
  public C_EFRestart() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getElevator());
    requires(Robot.getHatch());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.getEndEffectorAngle().isInitialized()){

      if(Robot.getElevator().getAverageInch() < Robot.getElevator().getSafeFlipTop() && Robot.getFrontClimber().safeToFlip()){
        if(!Robot.getEndEffectorAngle().isReset()){
          Robot.getEndEffectorAngle().setAngleSpeed(-.6);
        }
        else{
          Robot.getEndEffectorAngle().resetEncoder();
          Robot.getEndEffectorAngle().setAngleSpeed(0);
          Robot.getEndEffectorAngle().setInitialized(true);
        }
      }
      else{
        Robot.getElevator().goToInch(1);
        Robot.getFrontClimber().goToDegree(Robot.getFrontClimber().getSafeTop());
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.getEndEffectorAngle().isReset() || Robot.getHatch().isPresent();
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
