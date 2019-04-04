/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_WaitForPress extends Command {
  public C_WaitForPress() {
    requires(Robot.getHatch());
  }

  @Override
  protected boolean isFinished() {
    if(Robot.getOI().getSecondaryController().getLeftTriggerButton().get()){
      return Robot.getHatch().isPressed();
    }
    return true;
  }

}
