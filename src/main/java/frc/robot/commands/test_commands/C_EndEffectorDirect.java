/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test_commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EndEffectorDirect extends Command {
  private static final double DEAD_BAND = 0.5;
  public C_EndEffectorDirect() {
    requires(Robot.getEndEffectorAngle());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  public double ignoreDeadBand(double input){
    if(Math.abs(input) < DEAD_BAND){
      return 0;
    }
    return input;
  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = Robot.getOI().getSecondaryController().getRightYValue();
    if(Robot.getEndEffectorAngle().isReset()){
      if(speed < 0){
        speed = 0;
      }
    }
    Robot.getEndEffectorAngle().setAngleSpeed(ignoreDeadBand(speed));
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
  // subsystems is scheduled to runp
  @Override
  protected void interrupted() {
    end();
  }
}
