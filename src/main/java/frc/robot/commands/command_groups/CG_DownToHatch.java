/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.C_EndEffectorAngle;
import frc.robot.commands.C_Flip;
import frc.robot.commands.test_commands.C_ElevatorToInch;

public class CG_DownToHatch extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CG_DownToHatch() {
    setInterruptible(false);
    
    addSequential(new C_Flip(false));
    addParallel(new C_EndEffectorAngle(-75));
    addParallel(new C_ElevatorToInch(Robot.getElevator().getLevelInch(1, true)));
  
  }
  @Override
  protected synchronized void setInterruptible(boolean interruptible) {
    super.setInterruptible(interruptible);
  }
}
