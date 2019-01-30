/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_Elevator extends Command {
  private static final double DEAD_BAND = 0.05;

  public C_Elevator() {
    requires(Robot.getElevator());
  }

  private double ignoreDeadBand(double input){
    if(Math.abs(input) < DEAD_BAND){
      return 0;
    }
    return input;
  }
  @Override
  protected void execute() {
    double speed = Robot.getOI().getSecondaryController().getLeftYValue();
    Robot.getElevator().setElevatorSpeed(ignoreDeadBand(speed));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}
