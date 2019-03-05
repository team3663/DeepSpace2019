/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.command_groups.CG_DownToBall;
import frc.robot.commands.command_groups.CG_DownToHatch;
import frc.robot.util.Mode;

public class C_SelectDown extends Command {

  
  private CommandGroup down;
  /**
   * Selects elevator down based on hatch mode
   */
  public C_SelectDown() {

    requires(Robot.getHatch());
  }

  @Override
  protected void initialize() {
    if(Robot.getHatch().getMode() == Mode.kHatch){
      down = new CG_DownToHatch();
      
    }
    else{
      down = new CG_DownToBall();
    }
    
    down.start();
  }
  @Override
  protected void execute() {
    
  }

  @Override
  protected boolean isFinished() {
    return down.isCompleted();
  }

}
