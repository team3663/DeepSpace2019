/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_Climb;
import frc.robot.commands.C_DriveDistance;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_RearClimber;

public class CG_ClimbDown extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_ClimbDown() {
    addParallel(new C_RearClimber(105));
    addParallel(new C_FrontClimber(180));
    addSequential(new C_DriveDistance(25, 0.3));
    //addSequential(new C_Climb(0));
    addSequential(new C_RearClimber(0));
  }
}
