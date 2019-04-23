/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_ExtendKickstand extends Command {
  private boolean extendKickstand;
  public C_ExtendKickstand(boolean extendKickstand) {
    this.extendKickstand = extendKickstand;
    // Use requires() here to declare subsystem dependencies
    requires(Robot.getRearClimber());
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.getRearClimber().extendKickstand(extendKickstand);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

}
