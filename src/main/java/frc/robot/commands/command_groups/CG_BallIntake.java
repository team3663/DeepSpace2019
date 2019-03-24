/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.C_EFToAngle;
import frc.robot.commands.C_ExtendHatch;
import frc.robot.commands.C_Flip;
import frc.robot.commands.C_FrontClimber;
import frc.robot.commands.C_HoldBall;
import frc.robot.commands.C_SetEFIntakeSpeed;
import frc.robot.commands.C_SetFrontClimberIntake;
import frc.robot.commands.C_SetHatchClosed;
import frc.robot.commands.C_SetMode;
import frc.robot.commands.C_WaitForBall;
import frc.robot.commands.test_commands.C_ElevatorToInch;
import frc.robot.util.Mode;
import frc.robot.util.Side;

public class CG_BallIntake extends CommandGroup {
  /**
   * goes to ball mode and waits for ball , then goes resets
   */
  public CG_BallIntake() {
    addSequential(new C_FrontClimber(95));

    addSequential(new C_SetMode(Mode.kBall));
    addSequential(new C_ExtendHatch(false));
    addSequential(new C_SetHatchClosed(true));
    addSequential(new C_Flip(Side.kFront));
    addParallel(new C_ElevatorToInch(.5));

    addSequential(new C_SetEFIntakeSpeed(-.4));  
    addSequential(new C_SetFrontClimberIntake(-1));

    addSequential(new C_WaitForBall());

    addSequential(new C_SetFrontClimberIntake(0));
    // addSequential(new C_SetEFIntakeSpeed(0));
    addParallel(new C_HoldBall());
    
    addSequential(new C_FrontClimber(0, true));
  }
}
