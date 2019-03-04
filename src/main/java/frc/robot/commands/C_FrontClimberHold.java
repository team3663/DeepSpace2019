/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_FrontClimberHold extends Command {

  private double holdPos;
  public C_FrontClimberHold() {
    requires(Robot.getFrontClimber());
  }

  @Override
  protected void initialize() {
    isInterruptible();
    holdPos = Robot.getFrontClimber().getAngle();
  }

  @Override
  protected void execute() {
    Robot.getFrontClimber().goToDegree(holdPos);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

 @Override
 public synchronized boolean isInterruptible() {
   return true;
 }
}
