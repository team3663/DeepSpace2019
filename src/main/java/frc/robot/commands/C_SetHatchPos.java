/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_EndEffector;

public class C_SetHatchPos extends Command {

  private boolean isClosed;

  /**
   * Makes the end effector grab or release the hatch
   * @param grab if true, grab the hatch. If false, release it.
   */
  public C_SetHatchPos(boolean isClosed) {
    requires(Robot.getEndEffector());
    this.isClosed = isClosed;
  }

  @Override
  protected void execute() {
    Robot.getEndEffector().setHatchOpen(isClosed);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
