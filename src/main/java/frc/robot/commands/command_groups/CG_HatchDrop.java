/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_SetHatchClosed;
import frc.robot.commands.test_commands.C_ElevatorToInch;

public class CG_HatchDrop extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_HatchDrop() {
    addSequential(new C_SetHatchClosed(false));
    addSequential(new C_ElevatorToInch(4));
  }
}