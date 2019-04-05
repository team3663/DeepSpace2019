/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_WaitForExtend extends Command {
  boolean isExtended;
  public C_WaitForExtend(boolean isExtended) {
    this.isExtended = isExtended;
    requires(Robot.getHatch());
  }

  @Override
  protected boolean isFinished() {
    if(!Robot.getOI().getSecondaryController().getLeftTriggerButton().get()){
      return true;
    }
    return !Robot.getHatch().isPressed() == isExtended;
  }
}