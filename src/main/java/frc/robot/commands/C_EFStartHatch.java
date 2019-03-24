/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EFStartHatch extends Command {
  private double angle;

  /**
   * this command should ONLY be run ONCE under certian conditions, as it has NO SAFETYS
   * @param angle
   */
  public C_EFStartHatch(double angle) {
    requires(Robot.getEndEffectorAngle());
    this.angle = angle;
  }
  @Override
  protected void execute() {
    if(!Robot.getEndEffectorAngle().isHatchRestarted()){
      Robot.getEndEffectorAngle().goToDegree(angle);
    }

  }
  @Override
  protected boolean isFinished() {
    return Robot.getEndEffectorAngle().atTarget(angle) || Robot.getEndEffectorAngle().isHatchRestarted();
  }

  @Override
  protected void end() {
    Robot.getEndEffectorAngle().setHatchRestarted(true);
  }
  @Override
  protected void interrupted() {
    end();
  }
}
