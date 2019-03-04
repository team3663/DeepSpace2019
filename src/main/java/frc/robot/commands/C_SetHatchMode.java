/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SetHatchMode extends Command {
  private boolean hatchMode;
  private boolean toggle;
  public C_SetHatchMode(boolean hatchMode) {
    requires(Robot.getHatch());
    this.hatchMode = hatchMode;
    toggle = false;
  }

  public C_SetHatchMode() {
    requires(Robot.getHatch());
    requires(Robot.getBall());
    toggle = true;
  }

  @Override
  protected void execute() {
    if(toggle){
      if(!Robot.getBall().isPresent() && !Robot.getHatch().isPresent()){
        Robot.getHatch().setHatchMode(!Robot.getHatch().isHatchMode());
      }
    }
    else{
      if(!Robot.getHatch().isPresent()){
        Robot.getHatch().setHatchMode(hatchMode);
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
