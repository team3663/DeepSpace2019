/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EdClimb extends Command {
  private final double SPEED_MULTIPLIER = 1/3;
  private final double DEADBAND = 0.05;
  private final double ROBOT_LENGTH = 32;
  private double level = 3;
  public C_EdClimb() {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getDrivetrain());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
   // new C_CrabDrive().start();
    Robot.getFrontClimber().setBrakeMode();
    Robot.getRearClimber().setBrakeMode();
    Robot.getDrivetrain().softReset();
    
    if(Robot.getOI().getSecondaryController().getLeftBumperButton().get()){
      level = 2;
    }else{
      level = 3;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double pitch = Robot.getDrivetrain().getOffsetPitch();
    double joystickInput = deadband(Robot.getOI().getSecondaryController().getRightYValue());

    Robot.getFrontClimber().goToInch(level, Robot.getFrontClimber().getHeight(pitch) + (joystickInput*SPEED_MULTIPLIER));
    Robot.getRearClimber().goToInch(Robot.getRearClimber().getHeight(Robot.getFrontClimber().getHeight(pitch), pitch) +
     calAdjustmentInch(pitch) + (joystickInput*SPEED_MULTIPLIER));
  }

  private double deadband(double input){
    if(Math.abs(input) < DEADBAND){
      return 0;
    }
    return input;
  }
  private double calAdjustmentInch(double pitch){
    return ROBOT_LENGTH * Math.sin(Math.toRadians(pitch));
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}