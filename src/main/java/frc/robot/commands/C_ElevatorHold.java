/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_ElevatorHold extends Command {
  private double holdPos;

  /**
   * grabs the current encoder position and sets the PID in an infinate loop until interrupted
   */
  public C_ElevatorHold() {
    requires(Robot.getElevator());
  }

  @Override
  protected void initialize() {
    holdPos = Robot.getElevator().getAverageEncoder();
  }

  @Override
  protected void execute() {
    Robot.getElevator().goToPos(holdPos);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
