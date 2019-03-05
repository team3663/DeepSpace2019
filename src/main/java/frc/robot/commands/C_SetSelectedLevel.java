/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Side;

public class C_SetSelectedLevel extends Command {
  private int level;
  public C_SetSelectedLevel(int level) {
    this.level = level;
    requires(Robot.getElevator());
  }
  @Override
  protected void execute() {
    if(Robot.getOI().getSecondaryController().getLeftTriggerButton().get()){
      Robot.getElevator().setSelectedSide(Side.kFront);
    }
    else{
      Robot.getElevator().setSelectedSide(Side.kBack);
    }

    Robot.getElevator().setSelectedLevel(level);
  }
  @Override
  protected boolean isFinished() {
    return true;
  }

}
