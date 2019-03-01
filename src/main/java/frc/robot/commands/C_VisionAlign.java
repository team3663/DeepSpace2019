/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_Swerve;
import frc.robot.subsystems.SS_Vision;

public class C_VisionAlign extends Command {
  private SS_Vision vision;
  private SS_Swerve drivetrain;

  private double angleToSnap = 0;

  public C_VisionAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getVision());
    requires(Robot.getDrivetrain());
    vision = Robot.getVision();
    drivetrain = Robot.getDrivetrain();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    vision.setLightMode(3);
    drivetrain.setFieldOriented(false);
    
    double angle = drivetrain.getGyroAngle();
    angleToSnap = (int)(angle) / 45 * 45;
    if(angleToSnap == 45){
      angleToSnap = 30;
    }
    else if(angleToSnap == 135){
      angleToSnap = 150;
    }
    else if(angleToSnap == 225){
      angleToSnap = 240;
    }
    else if(angleToSnap == 315){
      angleToSnap = 330;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    drivetrain.holonomicDrive(.2, vision.getXOffset()/2, 0); //make Drive forward proportional to target area
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !vision.validTarget() || vision.getTargetArea() > 30 || Robot.getOI().getPrimaryController().getRightBumperButton().get(); //TODO find the proper area for it to be hitting
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    vision.setLightMode(0);
    drivetrain.setFieldOriented(true);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
