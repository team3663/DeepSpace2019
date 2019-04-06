/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.test_commands.C_ElevatorToInch;
import frc.robot.subsystems.SS_Swerve;
import frc.robot.subsystems.SS_Vision;
import frc.robot.util.Mode;
import frc.robot.util.PIDCont;

public class C_VisionAlignStrafe extends Command {
  private SS_Vision vision;
  private SS_Swerve drivetrain;

	private PIDCont PIDCont;
  private double kP = .01; //.004 gyro .01 vision .004 new vision
	private double kI = .000;
	private double kD = .00; //.001 
  private double maxPIDSpeed = 0.6;  
  

  private PIDCont PIDVision;
  private double kPv = .015;//.009 new
	private double kIv = 0;
	private double kDv = 0;
	private double maxPIDSpeedv = 1; // 1 new

  private double angleToSnap = 0;

  private double maxTargetArea = 15;
  private double arbitraryPValue = 50; // 60 new

  public C_VisionAlignStrafe() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getVision());
    requires(Robot.getDrivetrain());
    requires(Robot.getElevator());
    vision = Robot.getVision();
    drivetrain = Robot.getDrivetrain();

    PIDCont = new PIDCont(maxPIDSpeed, kP, kI, kD); //TODO kP, kI, and kD need tuning

    PIDVision = new PIDCont(maxPIDSpeedv, kPv, kIv, kDv);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
 
    if(DriverStation.getInstance().isFMSAttached()){
      vision.setPipeline(0);
    }
    else{
      vision.setPipeline(1);
    }
    vision.setLightMode(3);
    drivetrain.setFieldOriented(false);

    
    double angle = drivetrain.getGyroAngle();
    angleToSnap = (int)(angle + 22.5 ) / 45 * 45;
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
    
    if(Robot.getHatch().getMode() == Mode.kHatch && Robot.getElevator().getSelectedLevel() == 1){
      if(vision.getTargetArea() < 6){
        Robot.getElevator().goToInch(0);
      }
      else{
        Robot.getElevator().goToInch(Robot.getElevator().getLevelInch(1, Mode.kHatch));
      }
    }

    double forwardOut = 0;
    double angleOut = 0;

    if(Math.abs(vision.getXOffset()) < 6){
      forwardOut = (maxTargetArea - vision.getTargetArea())/arbitraryPValue;
      angleOut = PIDCont.get(vision.getXOffset());
    }
      double strafeOut =  -PIDVision.get(vision.getXOffset());


    // if(drivetrain.getGyroAngle() > 350){
    //    angleOut = PIDCont.get(angleToSnap - 0);
    // }
    // else{
    //    angleOut = -PIDCont.get(angleToSnap - drivetrain.getGyroAngle());
    // }
    
    drivetrain.holonomicDrive(forwardOut, strafeOut, angleOut);
    // drivetrain.holonomicDrive((maxTargetArea - vision.getTargetArea())/arbitraryPValue, -PIDVision.get(vision.getXOffset()), angleError); //TODO: make Drive forward proportional to target area
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !vision.validTarget() || !Robot.getOI().getPrimaryController().getYButton().get();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    vision.setLightMode(3);
    drivetrain.setFieldOriented(true);
    drivetrain.holonomicDrive(0, 0, 0);

    if(Robot.getHatch().getMode() == Mode.kHatch && Robot.getElevator().getSelectedLevel() == 1){
      new C_ElevatorToInch(Robot.getElevator().getLevelInch(1, Mode.kHatch)).start();
    }

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
