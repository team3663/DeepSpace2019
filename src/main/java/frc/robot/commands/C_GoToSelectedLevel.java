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

public class C_GoToSelectedLevel extends Command {
  SS_Elevator elevator;

  public C_GoToSelectedLevel() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getElevator());

    elevator = Robot.getElevator();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    elevator.goToSelectedLevel();

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("GO TO LEVEL RUNNING");
    return elevator.atTarget(elevator.getSelectedLevelInch());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  
  }

}
