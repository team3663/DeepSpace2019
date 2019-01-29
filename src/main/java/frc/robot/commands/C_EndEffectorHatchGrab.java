/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_EndEffector;

public class C_EndEffectorHatchGrab extends Command {

  boolean grab;

  /**
   * Makes the end effector grab or release the hatch
   * @param grab if true, grab the hatch. If false, release it.
   */
  public C_EndEffectorHatchGrab(boolean grab) {
    requires(Robot.getEndEffector());
    this.grab = grab;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.getEndEffector().setHatchOpen(grab);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
