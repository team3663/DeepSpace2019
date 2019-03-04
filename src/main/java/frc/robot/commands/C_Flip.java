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
  private double elevatorEnd;

  public 
  C_Flip(boolean isFront) {
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getElevator());
    requires(Robot.getFrontClimber());
    requires(Robot.getHatch());
    this.isFront = isFront;
    this.elevatorEnd = Robot.getElevator().getSafeFlipTop() - 1;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(4);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.getEndEffectorAngle().isFlipped(isFront) && !Robot.getHatch().isPresent()){

      if(Robot.getFrontClimber().safeToFlip()){
        Robot.getElevator().goToInch(elevatorEnd);

        if(Robot.getElevator().getAverageInch() > Robot.getElevator().getSafeFlipBot() && Robot.getElevator().getAverageInch() < Robot.getElevator().getSafeFlipTop()){
          Robot.getEndEffectorAngle().goToDegree(Robot.getEndEffectorAngle().getSafeFlipAngle(isFront));
        }
        
      }
      else{
        Robot.getFrontClimber().goToDegree(Robot.getFrontClimber().getSafeTop()); 
      }
    }
  
    else if(Robot.getHatch().isPresent()){
      Robot.getHatch().setHatchMode(true);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("C FLIP IS GOING");
    return Robot.getEndEffectorAngle().isFlipped(isFront) || Robot.getHatch().isPresent()|| isTimedOut() || 
    //this is a temp fix for the PID controller
    (Robot.getEndEffectorAngle().getSafeFlipAngle(isFront) + 3 > Robot.getEndEffectorAngle().getAngle() && Robot.getEndEffectorAngle().getSafeFlipAngle(isFront) - 3 < Robot.getEndEffectorAngle().getAngle());
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
