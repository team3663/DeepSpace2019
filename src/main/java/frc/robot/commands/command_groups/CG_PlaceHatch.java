/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class CG_PlaceHatch extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_PlaceHatch() {
    addSequential(new C_SetHatchClosed(false));
    addSequential(new C_WaitForHatch(false)); //Waits for the Hatch to stop being sensed by the optical limit switch
    addSequential(new C_Wait(1000));
    addParallel(new C_ExtendHatch(false));
    addSequential(new C_SetHatchClosed(true));
  }
}
