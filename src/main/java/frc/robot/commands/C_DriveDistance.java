/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.PIDCont;

public class C_DriveDistance extends Command {

  private PIDCont PIDController;

  private double maxSpeed;
  private double distance;

  public C_DriveDistance(double distance, double maxSpeed) {

    this.maxSpeed = maxSpeed;
    this.distance = distance;
    requires(Robot.getDrivetrain());

  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.getDrivetrain().holonomicDrive(0,0,0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
