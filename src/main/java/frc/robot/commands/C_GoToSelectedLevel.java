/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_Elevator;
import frc.robot.subsystems.SS_EndEffectorAngle;
import frc.robot.subsystems.SS_FrontClimber;

public class C_GoToSelectedLevel extends Command {
  SS_FrontClimber frontClimber;
  SS_Elevator elevator;
  SS_EndEffectorAngle efAngle;
  public C_GoToSelectedLevel() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getElevator());
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getFrontClimber());

    elevator = Robot.getElevator();
    frontClimber = Robot.getFrontClimber();
    efAngle = Robot.getEndEffectorAngle();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(frontClimber.getAngle() + 5 > frontClimber.getAngle() || frontClimber.getAngle() - 5 < frontClimber.getAngle()){
      frontClimber.goToDegree(90);
    }
    
    else if(frontClimber.getAngle() + 5 < frontClimber.getAngle() && frontClimber.getAngle() - 5 > frontClimber.getAngle()){
      if(efAngle.getAngle() + 5 > efAngle.getAngle() || efAngle.getAngle() - 2 < efAngle.getAngle()){
        elevator.goToInch(elevator.getSafeFlipHeight());
        efAngle.goToDegree(-30);
      }
      
    }
    
    

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
