/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_RearClimberToAngle;

public class CG_ClimbMode extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_ClimbMode() {
    addParallel(new C_RearClimberToAngle(100));
    addParallel(new C_FrontClimber(60));
  }
}
