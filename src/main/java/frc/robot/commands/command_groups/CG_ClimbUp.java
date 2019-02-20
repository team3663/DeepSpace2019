/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.C_Climb;
import frc.robot.commands.C_DriveDistance;
import frc.robot.commands.C_EndEffectorAngle;
import frc.robot.commands.C_Flip;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_RearClimber;
import frc.robot.commands.C_SetFrontClimberIntake;
import frc.robot.commands.test_commands.C_ElevatorToInch;

public class CG_ClimbUp extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_ClimbUp() {
    addSequential(new C_ElevatorToInch(0));
    addSequential(new C_Flip(false));
    //climb sequence
    addParallel(new C_FrontClimber(40));
    addParallel(new C_RearClimber(105));
    addSequential(new C_Climb(210));
    addSequential(new C_DriveDistance(25, 0.3));
    addSequential(new C_RearClimber(0));
  }
}
