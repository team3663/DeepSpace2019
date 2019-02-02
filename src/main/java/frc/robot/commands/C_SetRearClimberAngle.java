/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SetRearClimberAngle extends Command {
  private double angle;
  private final double THE_MAGIC_NUMBER_OF_CHRISTIAN = 0.5;
  private double speed = 1;
  private double originalSpeed = 1;

  public C_SetRearClimberAngle(double angle) {
    requires(Robot.getRearClimber());
    this.angle = angle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.getRearClimber().getAngle() < angle){
      if(Robot.getRearClimber().getAngle() >= angle * THE_MAGIC_NUMBER_OF_CHRISTIAN){
        speed = originalSpeed - ((Robot.getRearClimber().getAngle() / angle) * 0.75);
      }
      Robot.getRearClimber().setCimberMotorSpeed(speed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
