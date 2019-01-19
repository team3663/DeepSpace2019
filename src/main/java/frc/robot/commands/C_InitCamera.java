/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class C_InitCamera extends Command {
  public C_InitCamera() {
    requires(Robot.getVision());
  }
  @Override
  protected boolean isFinished() {
    return Robot.getVision().initCamera(RobotMap.CAMERA_MODE, RobotMap.CAMERA_LIGHT_MODE, RobotMap.CAMERA_PIPELINE);
  }

}
