/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.C_EFToAngle;
import frc.robot.commands.C_ExtendHatch;
import frc.robot.commands.C_Flip;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_SetHatchClosed;
import frc.robot.commands.test_commands.C_ElevatorToInch;
import frc.robot.util.Mode;
import frc.robot.util.Side;

public class CG_HatchMode extends CommandGroup {
  /**
   * goes to hatch default position
   */
  public CG_HatchMode() {
    addSequential(new C_Flip(Side.kBack));
    addParallel(new C_EFToAngle(Robot.getEndEffectorAngle().getSafeFlipAngle(Side.kBack) + 10, false));
    addParallel(new C_ElevatorToInch(Robot.getElevator().getLevelInch(1, Mode.kHatch)));
    addSequential(new C_SetHatchClosed(false));
    addSequential(new C_FrontClimber(0));
  }
}
