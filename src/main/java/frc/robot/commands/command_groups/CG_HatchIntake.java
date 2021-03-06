/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_ExtendHatch;
import frc.robot.commands.C_Rumble;
import frc.robot.commands.C_SetHatchClosed;
import frc.robot.commands.C_Wait;
import frc.robot.commands.C_WaitForHatch;
import frc.robot.util.RumbleSide;
import frc.robot.util.RumbleType;

public class CG_HatchIntake extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_HatchIntake() {
    addSequential(new C_ExtendHatch(true));
    addSequential(new C_SetHatchClosed(false));
    addSequential(new C_WaitForHatch(true));
    addParallel(new C_Rumble(1, 200, 500, RumbleType.kPing, RumbleSide.kBoth, 1));
    addSequential(new C_SetHatchClosed(true));
    addSequential(new C_Wait(200));
    addSequential(new C_ExtendHatch(false));
  }
}
