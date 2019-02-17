/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_ElevatorHold;
import frc.robot.commands.C_EndEffectorAngle;
import frc.robot.commands.C_EndEffectorHold;
import frc.robot.commands.C_Flip;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_GoToSelectedLevel;
import frc.robot.commands.C_SetEndEffectorIntakeSpeed;
import frc.robot.commands.C_SetFrontClimberIntake;
import frc.robot.commands.C_Wait;
import frc.robot.commands.test_commands.C_ElevatorToInch;

public class CG_GoToSelectedLevel extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_GoToSelectedLevel() {
    addSequential(new C_SetFrontClimberIntake(0));
    addSequential(new C_SetEndEffectorIntakeSpeed(0));
    addSequential(new C_FrontClimber(90));
    addSequential(new C_Flip(false));
    addParallel(new C_FrontClimber(0));
    addParallel(new C_EndEffectorAngle(-85));
    addSequential(new C_GoToSelectedLevel());
    addParallel(new C_ElevatorHold());
    addParallel(new C_EndEffectorHold());
  }
}
