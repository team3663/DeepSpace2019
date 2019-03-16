/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SmartHatchClose extends Command {
  private boolean close;
  private boolean wait;
  public C_SmartHatchClose() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getHatch());
    wait = true;
  }
  public C_SmartHatchClose(boolean close) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getHatch());
    this.wait = false;
    this.close = close;
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getHatch().extendHatchPickup(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(wait){
      if(Robot.getHatch().isPresent()){
        Robot.getHatch().setHatchClosed(true);
      }
    }
    else{
      Robot.getHatch().setHatchClosed(close);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !wait;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.getHatch().extendHatchPickup(false);

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
