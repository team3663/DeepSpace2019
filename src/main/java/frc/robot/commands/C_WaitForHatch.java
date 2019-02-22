/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_WaitForHatch extends Command {

  private boolean waitForHatchDeploy;

  /**
   * 
   */
  public C_WaitForHatch() {
    requires(Robot.getHatch());
    this.waitForHatchDeploy = false;
  }

  public C_WaitForHatch(boolean waitForHatchDeploy) {
    requires(Robot.getHatch());
    this.waitForHatchDeploy = waitForHatchDeploy;
  }

  @Override
  protected boolean isFinished() {
    if (waitForHatchDeploy) {
      return !Robot.getHatch().hatchIsPresent();
    }
    return Robot.getHatch().hatchIsPresent();
  }

}
