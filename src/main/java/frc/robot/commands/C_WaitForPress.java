/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.RumbleSide;
import frc.robot.util.RumbleType;

public class C_WaitForPress extends Command {
  boolean rumble = false;
  public C_WaitForPress() {
    requires(Robot.getHatch());
  }

  @Override
  protected boolean isFinished() {
    if(Robot.getOI().getSecondaryController().getLeftTriggerButton().get()){
      rumble = true;
      return Robot.getHatch().isPressed();
    }
    return true;
  }

  @Override
  protected void end() {
    if(rumble){
      new C_Rumble(1, 200, 500, RumbleType.kPing, RumbleSide.kBoth, 1).start();

    }

  }

}
