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
  private boolean isDeploying;
  public C_WaitForHatch(boolean isDeploying) {
    requires(Robot.getHatch());
    this.isDeploying = isDeploying;
  }

  @Override
  protected boolean isFinished() {
    if(isDeploying){
      return !Robot.getHatch().isPresent();
    }
    return Robot.getHatch().isPresent();
  }

}
