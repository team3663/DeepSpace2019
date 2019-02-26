/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_EndEffectorHold;
import frc.robot.commands.C_SetHatchClose;

public class CG_HatchHold extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_HatchHold() {
    addSequential(new C_SetHatchClose(true));
    addSequential(new C_EndEffectorHold());
  }
}
