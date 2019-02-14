/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_Climb extends Command {
  private double frontAngleError = 0;
  private double rearAngleError = 0;
  private double angleMultiplier = 10;

  public C_Climb(double angleMultiplier) {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getGyro());
    this.angleMultiplier = angleMultiplier;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getGyro().softReset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double targetAngle =  Robot.getOI().getTestController().getLeftYValue() * angleMultiplier;
    if(getAngleError() > 0){
      rearAngleError = getAngleError();
      frontAngleError = 0;
    }else{
      frontAngleError = getAngleError();
      rearAngleError = 0;
    }

    Robot.getFrontClimber().goToDegree(targetAngle + frontAngleError);
    Robot.getRearClimber().goToDegree(-(targetAngle + rearAngleError));
  }

  private double getAngleError(){
    return Robot.getGyro().getOffsetAngle();
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
