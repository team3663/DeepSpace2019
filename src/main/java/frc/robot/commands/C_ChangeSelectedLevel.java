/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_ChangeSelectedLevel extends Command {
  private int level;
  public C_ChangeSelectedLevel(int level) {
    this.level = level;
    requires(Robot.getElevator());
  }
  @Override
  protected void execute() {
    int totalLevel = Robot.getElevator().getSelectedLevel() + level;
    if (totalLevel >= 1 && totalLevel <= 3)
    Robot.getElevator().setSelectedLevel( totalLevel );
  }
  @Override
  protected boolean isFinished() {
    return true;
  }

}
