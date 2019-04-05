/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.SS_Swerve;
import frc.robot.subsystems.SS_Vision;
import frc.robot.util.PIDCont;

public class C_VisionAlign extends Command {
  private SS_Vision vision;
  private SS_Swerve drivetrain;

	private PIDCont PIDCont;
  private double kP = .005; //.002
	private double kI = .00;
	private double kD = .000;
  private double maxPIDSpeed = 0.3;  
  

  private PIDCont PIDVision;
  private double kPv = .015;
	private double kIv = 0;
	private double kDv = 0;
	private double maxPIDSpeedv = 0.5;  

  private double angleToSnap = 0;

  private double maxTargetArea = 15;
  private double arbitraryPValue = 40;

  public C_VisionAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getVision());
    requires(Robot.getDrivetrain());
    vision = Robot.getVision();
    drivetrain = Robot.getDrivetrain();

    PIDCont = new PIDCont(maxPIDSpeed, kP, kI, kD); //TODO kP, kI, and kD need tuning

    PIDVision = new PIDCont(maxPIDSpeedv, kPv, kIv, kDv);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    vision.setLightMode(3);
    drivetrain.setFieldOriented(false);
    
    double angle = drivetrain.getGyroAngle();
    angleToSnap = (int)(angle / 45) * 45;
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
    SmartDashboard.putNumber("Vangle Snap", angleToSnap);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    double angleError = PIDCont.get(vision.getXOffset());
    SmartDashboard.putNumber("Vangle power", angleError);
    
    drivetrain.holonomicDrive((maxTargetArea - vision.getTargetArea()) / arbitraryPValue, 
          - PIDVision.get(vision.getXOffset()), angleError); //TODO: make Drive forward proportional to target area
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !vision.validTarget() || !Robot.getOI().getPrimaryController().getLeftBumperButton().get();
    //TODO find the proper area for it to be hitting
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    vision.setLightMode(3);
    drivetrain.setFieldOriented(true);
    drivetrain.holonomicDrive(0, 0, 0);
  }
  private double getAngleError(double targetAngle) {
		return targetAngle - Robot.getDrivetrain().getGyroAngle();
	}

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
