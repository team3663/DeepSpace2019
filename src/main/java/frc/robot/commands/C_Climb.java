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
  private double direction;
  private double targetAngle;
  private static final double THRESHOLD_MIN = 145;
  private static final double THRESHOLD_MAX = 160;
  private double ANGLE_ERROR_AMOUNT = 3;
  private static final double FRONT_CLIMBER_RADIUS = 16;
  private static final double REAR_LONG_SIDE_LENGTH = 27;
  private static final double REAR_SHORT_SIDE_LENGTH = 4;
  private static final double REAR_CLIMBER_RADIUS = Math.sqrt(Math.pow(REAR_LONG_SIDE_LENGTH, 2) + Math.pow(REAR_SHORT_SIDE_LENGTH, 2));
  private static final double FRONT_REAR_RATIO = FRONT_CLIMBER_RADIUS/REAR_CLIMBER_RADIUS;

  public C_Climb(double targetAngle) {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getDrivetrain());
    requires(Robot.getBall());
    this.targetAngle = targetAngle;
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getDrivetrain().softReset();
    direction = Math.signum(targetAngle - Robot.getFrontClimber().getAngle());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    /*
      I have made 3 different ways to climb, im not sure which one works, wrote them just in case one doesn't work
    */

    //1.)Climbs up or down based on speed(front climber speed is always constant, back climber adjust)
    ANGLE_ERROR_AMOUNT = 0;
		double tilt = Math.signum(getAngleError());
	  double maxSpeed = 1;
    double controlSpeed = Math.abs(Math.signum(direction + tilt));

    Robot.getFrontClimber().setClimberMotorSpeed(0.5 * maxSpeed * direction);
    Robot.getRearClimber().setCimberMotorSpeed(controlSpeed * maxSpeed);
    

    // This statement is if we can use intake wheels to climb
    // rotate the front climber intake wheels
    // if(isInThresholdArea()){
    //   Robot.getBall().setCargoIntakeSpeed(speedLimit * 0.5);
    // }

    /*2.) Climbs based on Angle(front climber target angle is constant, back must make up for the errors)
    ANGLE_ERROR_AMOUNT = 3;
    double currentFrontAngle = Robot.getFrontClimber().getAngle();
		double direction = Math.signum(targetAngle - currentFrontAngle);
    double tilt = Math.signum(getAngleError());    
    double angleError = Math.abs(Math.signum(direction + tilt)) * Math.abs(getAngleError());
    if(direction == -1 && tilt == -1){
      Robot.getFrontClimber().goToDegree(currentFrontAngle);
    }else{ 
      Robot.getFrontClimber().goToDegree(targetAngle);
    }
    Robot.getRearClimber().goToDegree(FRONT_REAR_RATIO * (currentFrontAngle + angleError));
    */

    /*3.) Both climbers climb to target angle, if the robot is tilted, pause the climb and autoBalances
    ANGLE_ERROR_AMOUNT = 3;
    double currentFrontAngle = Robot.getFrontClimber().getAngle();
    double currentRearAngle = Robot.getRearClimber().getAngle();
    if(Math.abs(getAngleError()) > 0){
      Robot.getFrontClimber().goToDegree(currentFrontAngle + -getAngleError());
      Robot.getRearClimber().goToDegree(FRONT_REAR_RATIO  * (currentRearAngle + getAngleError()));
    }else{
      Robot.getFrontClimber().goToDegree(targetAngle + -getAngleError());
      Robot.getRearClimber().goToDegree(FRONT_REAR_RATIO * (targetAngle + getAngleError()));
    }
    */
  }

  private boolean isInIntakeArea(){
    return Robot.getFrontClimber().getAngle() > THRESHOLD_MIN && Robot.getFrontClimber().getAngle() < THRESHOLD_MAX;
  }

  private double getAngleError(){
    if(Math.abs(Robot.getDrivetrain().getOffsetPitch()) < ANGLE_ERROR_AMOUNT){
      return 0;
    }
    return Robot.getDrivetrain().getOffsetPitch();
  }

  @Override
  protected boolean isFinished() {
    return (targetAngle - Robot.getFrontClimber().getAngle()) * direction < 0;
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