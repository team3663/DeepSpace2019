/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_Climb extends Command {
  private double angleMultiplier = 10;
  private static final double FRONT_CLIMBER_RADIUS = 16;
  private static final double REAR_CLIMBER_RADIUS = Math.sqrt(Math.pow(27, 2) + Math.pow(4, 2));
  private static final double FRONT_REAR_RATIO = FRONT_CLIMBER_RADIUS/REAR_CLIMBER_RADIUS;

  public C_Climb(double angleMultiplier) {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getDrivetrain());
    this.angleMultiplier = angleMultiplier;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getDrivetrain().softReset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    /*
    TESTING(back = pos)
    1.) figure out which direction the robot is tilted for a (-) output value from gyro
    2.) figure out if the balancing works without controller
    3.) figure out if the balancing works with the controller
    */

    double speedScale =  Robot.getOI().getTestController().getLeftYValue();
    Robot.getFrontClimber().setClimberMotorSpeed(0.5 * speedScale);
    if(getAngleError() != 0){
      Robot.getRearClimber().setCimberMotorSpeed(-1 * speedScale);
    }else{
      Robot.getRearClimber().setCimberMotorSpeed(0);
    }
    // double currentFrontAngle = Robot.getFrontClimber().getAngle();
    // double currentRearAngle = Robot.getRearClimber().getAngle();
    // Robot.getFrontClimber().goToDegree(currentFrontAngle + targetAngle + -
    // getAngleError());
    // Robot.getRearClimber().goToDegree(FRONT_REAR_RATIO * (currentRearAngle + targetAngle + getAngleError()));
  }

  private double getAngleError(){
    return Robot.getDrivetrain().getOffsetPitch();
  }

  @Override
  protected boolean isFinished() {
    return Robot.getFrontClimber().getAngle() > 160;
  }

  @Override
  protected void end() {
    Robot.getFrontClimber().setClimberMotorSpeed(0);
    Robot.getRearClimber().setCimberMotorSpeed(0);
  }

  @Override
  protected void interrupted() {
    Robot.getFrontClimber().setClimberMotorSpeed(0);
    Robot.getRearClimber().setCimberMotorSpeed(0);    
  }
}