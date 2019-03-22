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

public class C_SetMode extends Command {
  private Mode mode;
  private boolean toggle;
  
  public C_SetMode(Mode mode) {
    requires(Robot.getHatch());
    this.mode = mode;
    toggle = false;
  }

  public C_SetMode() {
    requires(Robot.getHatch());
    requires(Robot.getBall());
    toggle = true;
  }

  @Override
  protected void execute() {
    if(toggle){
      if(!Robot.getBall().isPresent() && !Robot.getHatch().isPresent()){
        if(Robot.getHatch().getMode() == Mode.kBall){
          mode = Mode.kHatch;
        }
        else{
          mode = Mode.kBall;
        }
        Robot.getHatch().setMode(mode);
      }
    }

    else{
      if(!Robot.getHatch().isPresent() && !Robot.getBall().isPresent()){
        Robot.getHatch().setMode(mode);
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
