/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.command_groups.CG_GoToSelectedLevelBack;
import frc.robot.commands.command_groups.CG_GoToSelectedLevelFront;
import frc.robot.util.Mode;
import frc.robot.util.Side;

public class C_SelectUp extends Command {

  /**
   * selectes the CG to go to selected level based on what is the selected side
   */
  
  public C_SelectUp() {

    requires(Robot.getElevator());
  }
  @Override
  protected void execute() {
    //selected level check could be removed for more levels, but was told that was a bad idea by mech 
    if(Robot.getElevator().getSelectedSide() == Side.kFront && Robot.getHatch().getMode() == Mode.kBall && Robot.getElevator().getSelectedLevel() == 15){
      new CG_GoToSelectedLevelFront().start();
    }else{
      new CG_GoToSelectedLevelBack().start();
    }
  }
  @Override
  protected boolean isFinished() {
    return true;
  }

}
