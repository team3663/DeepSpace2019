/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Mode;

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
        if(Robot.getHatch().getMode() == Mode.kBall){
          Robot.getHatch().setMode(Mode.kHatch);
        }
        else{
          Robot.getHatch().setMode(Mode.kBall);
        }
      }
    }

    //TODO go over this again, dont know why this here
    else{
      if(!Robot.getHatch().isPresent()){
        Robot.getHatch().setMode(Mode.kHatch);
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
