/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_EFRotateRelative;
import frc.robot.commands.C_Flip;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_GoToSelectedLevel;
import frc.robot.commands.C_SetEFIntakeSpeed;
import frc.robot.commands.C_SetFrontClimberIntake;

public class CG_GoToSelectedLevelFront extends CommandGroup {
  /**
   * goes to selected level on the front side
   */
  public CG_GoToSelectedLevelFront() {
    setInterruptible(false);

    addSequential(new C_SetFrontClimberIntake(0));
    addSequential(new C_SetEFIntakeSpeed(0));
    addSequential(new C_Flip(true));
    //addParallel(new C_EndEffectorHold());
    addParallel(new C_FrontClimber(20));
    addSequential(new C_GoToSelectedLevel());
    addSequential(new C_EFRotateRelative(true, 5));
    //addParallel(new C_ElevatorHold());
    
  }
}
