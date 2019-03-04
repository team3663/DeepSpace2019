/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_AdjustFrontClimberAngle extends Command {
  private static final double DEADBAND = 0.1;

  /**
   * makes small adjustments to the front climber angle
   */
  public C_AdjustFrontClimberAngle() {
    requires(Robot.getFrontClimber());
  }

  @Override
  protected void initialize() {
  }

  //TODO make while its held then revert back to whatever it was
  private double ignoreDeadband(double input){
    if(Math.abs(input) < DEADBAND){
      return 0;
    }
    return input;
  }

  @Override
  protected void execute() {
    double joystickInput = ignoreDeadband(Robot.getOI().getSecondaryController().getRightYValue());
    Robot.getFrontClimber().goToDegree(Robot.getFrontClimber().getAngle() + joystickInput);    
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
