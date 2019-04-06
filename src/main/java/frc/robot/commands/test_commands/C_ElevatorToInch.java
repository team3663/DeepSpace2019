/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test_commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_ElevatorToInch extends Command {
  double inch;
  public C_ElevatorToInch(double inch) {
 
    this.inch = inch;
    requires(Robot.getElevator());
  }

  @Override
  protected void execute() {
    Robot.getElevator().goToInch(inch);
  }
  @Override
  protected boolean isFinished() {
    return Robot.getElevator().atTarget(inch);
  }
  
}
