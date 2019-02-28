/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_EndEffectorHold;
import frc.robot.commands.C_ExtendHatch;
import frc.robot.commands.C_Flip;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_SetHatchClosed;
import frc.robot.commands.test_commands.C_ElevatorToInch;

public class CG_HatchReady extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_HatchReady() {
    addSequential(new C_Flip(false));
   // addParallel(new C_EndEffectorHold());
    addParallel(new C_ElevatorToInch(4.5));
    addSequential(new C_ExtendHatch(true));
    addSequential(new C_SetHatchClosed(false));
    addSequential(new C_FrontClimber(0));
  }
}
