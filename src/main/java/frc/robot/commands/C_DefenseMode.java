/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_RearClimber;

public class C_DefenseMode extends Command {
  private SS_RearClimber climber;
  private boolean toggle = false;
  private boolean isDefense;

  public C_DefenseMode() {
    requires(Robot.getRearClimber());
    climber = Robot.getRearClimber();
    toggle = true;
    this.isDefense = climber.getDefense();
  }

  public C_DefenseMode(boolean isDefense) {
    requires(Robot.getRearClimber());
    climber = Robot.getRearClimber();
    this.isDefense = isDefense;
  }

  @Override
  protected void execute() {
    if(toggle){

      if(isDefense){
        climber.goToDegree(0);
        climber.setDefense(!isDefense);
        System.out.println("is defence");

      }
      else{
        climber.setDefense(isDefense);
        climber.goToDegree(climber.getSafeAngle());
      }
      System.out.println("toggled");
    }
    else{

      climber.setDefense(isDefense);
      if(isDefense){
        climber.goToDegree(0);
      }
      else{
        climber.goToDegree(climber.getSafeAngle());
      }

    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    double targetDegree = climber.getSafeAngle();
    if(isDefense) targetDegree = 0;
    return climber.atTarget(targetDegree);
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
