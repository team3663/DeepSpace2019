/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_ResetEFEncoder extends Command {
  public C_ResetEFEncoder() {
    requires(Robot.getEndEffectorAngle());
  }

  @Override
  protected void execute() {
    Robot.getEndEffectorAngle().setAngleSpeed(-.2);
  }

  @Override
  protected boolean isFinished() {

    return Robot.getEndEffectorAngle().isReset();
  }

  @Override
  protected void end() {
    Robot.getEndEffectorAngle().setAngleSpeed(0);
    Robot.getEndEffectorAngle().resetEncoder();
  }


}
