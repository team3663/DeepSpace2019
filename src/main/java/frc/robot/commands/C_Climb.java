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

public class C_Climb extends Command {
  private PIDCont frontPID;
  private PIDCont rearPID;

  private final double frontkP = 0;
  private final double frontkI = 0;
  private final double frontkD = 0;

  private final double rearkP = 0;
  private final double rearkI = 0;
  private final double rearkD = 0;

  private double originalFrontClimberAngle;
  private double originalRearClimberAngle;

  private final double ANGLE_ERROR = 2.5;

  private double targetAngle;
  private double frontTargetAngle;
  private double rearTargetAngle;

  public C_Climb(double maxSpeed, double targetAngle) {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());

    this.targetAngle = targetAngle;
    frontPID = new PIDCont(maxSpeed, frontkP, frontkI, frontkD);
    rearPID = new PIDCont(maxSpeed, rearkP, rearkI, rearkD);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    originalFrontClimberAngle = Robot.getFrontClimber().getAngle();
    originalRearClimberAngle = Robot.getRearClimber().getAngle();

    frontTargetAngle = targetAngle;
    rearTargetAngle = targetAngle;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(getFrontAngleInc() > getRearAngleInc()){
      frontTargetAngle = Robot.getRearClimber().getAngle();
      rearTargetAngle = targetAngle;
    }else{
      rearTargetAngle = Robot.getFrontClimber().getAngle();
      frontTargetAngle = targetAngle;
    }
    Robot.getFrontClimber().setClimberMotorSpeed(frontPID.get(getFrontAngleError()));
    Robot.getRearClimber().setCimberMotorSpeed(rearPID.get(getRearAngleError()));
  }

  private double getFrontAngleError(){
    return frontTargetAngle - Robot.getFrontClimber().getAngle();
  }

  private double getRearAngleError(){
    return rearTargetAngle - Robot.getRearClimber().getAngle();
  }

  private double getFrontAngleInc(){
    return Robot.getFrontClimber().getAngle() - originalFrontClimberAngle;
  }

  private double getRearAngleInc(){
    return Robot.getRearClimber().getAngle() - originalRearClimberAngle;
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return getFrontAngleError() < ANGLE_ERROR && getRearAngleError() < ANGLE_ERROR;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.getFrontClimber().setClimberMotorSpeed(0);
    Robot.getRearClimber().setCimberMotorSpeed(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
