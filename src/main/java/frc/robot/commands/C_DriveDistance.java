/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import javax.print.attribute.standard.Destination;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.PIDCont;

public class C_DriveDistance extends Command {

  private PIDCont PIDController;

  private final double INCHES_PER_REVOLUTION = 4 * Math.PI;
  private double destination;

  private double maxSpeed;
  private double kP = 0;
  private double kI = 0;
  private double kD = 0;

  public C_DriveDistance(double distanceInInches, double maxSpeed) {

    this.maxSpeed = maxSpeed;
    requires(Robot.getDrivetrain());
    PIDController = new PIDCont(maxSpeed, kP, kI, kD);

    destination = distanceInInches * INCHES_PER_REVOLUTION;
  }


  private double getDistanceDifference() {
    return destination;
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
