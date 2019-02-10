/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test_commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * ONLY FOR TESTING, LIMITS NOT SET
 */
public class C_FrontClimberDirect extends Command {
  public final double DEAD_BAND = 0.1;
  public C_FrontClimberDirect() {
    requires(Robot.getFrontClimber());
  }

  public double ignoreDeadBand(double input){
    if(Math.abs(input) < DEAD_BAND){
      return 0;
    }
    return input;
  }
  @Override
  protected void execute() {
    double speed = Robot.getOI().getSecondaryController().getLeftYValue();
    Robot.getFrontClimber().setClimberMotorSpeed(ignoreDeadBand(speed));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }


}
