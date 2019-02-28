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
  private static final double THRESHOLD_MIN = 100;
  private static final double THRESHOLD_MAX = 160;
  private double ANGLE_ERROR_AMOUNT = 3;
  private static final double FRONT_CLIMBER_RADIUS = 16.5;
  private static final double REAR_LONG_SIDE_LENGTH = 27;
  private static final double REAR_SHORT_SIDE_LENGTH = 4;
  private static final double REAR_CLIMBER_RADIUS = Math.sqrt(Math.pow(REAR_LONG_SIDE_LENGTH, 2) + Math.pow(REAR_SHORT_SIDE_LENGTH, 2));
  private static final double FRONT_REAR_RATIO = FRONT_CLIMBER_RADIUS/REAR_CLIMBER_RADIUS;
  private static final double ABSOLUTE_PITCH_LIMIT = 30;
  private static final double WOAH_THERE = .45;

  private final double FRONT_CLIMBER_SCALAR = (Robot.getRearClimber().getGearRatio() / Robot.getFrontClimber().getGearRatio())
    * (REAR_CLIMBER_RADIUS / FRONT_CLIMBER_RADIUS);

  public C_Climb() {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getDrivetrain());
    requires(Robot.getBall());
    //this.targetAngle = targetAngle;
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getDrivetrain().softReset();
    direction = Math.signum(targetAngle - Robot.getFrontClimber().getAngle());
    Robot.getFrontClimber().setBrakeMode();
    Robot.getRearClimber().setBrakeMode();
    Robot.getBall().setBrakeMode();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {


    double joystickInput = Robot.getOI().getSecondaryController().getRightYValue();
    double pitch = Robot.getDrivetrain().getOffsetPitch();

    Robot.getRearClimber().setSpeed(joystickInput * WOAH_THERE);
    
    Robot.getFrontClimber().setSpeed((joystickInput + calcAdjustmentSpeed(pitch)) * FRONT_CLIMBER_SCALAR * WOAH_THERE);

    /*
      I have made 3 different ways to climb, im not sure which one works, wrote them just in case one doesn't work
    */

    //1.)Climbs up or down based on speed(front climber speed is always constant, back climber adjust)

    /*
    ANGLE_ERROR_AMOUNT = 0;
		double tilt = Math.signum(getAngleError());
	  double maxSpeed = 1;
    double controlSpeed = Math.abs(Math.signum(direction + tilt));
  
    Robot.getFrontClimber().setSpeed(0.5 * speedMultiplier * direction);
    Robot.getRearClimber().setSpeed(controlSpeed * speedMultiplier);
    */

    //2.) Climbs based on Angle(front climber target angle is constant, back must make up for the errors)
    // ANGLE_ERROR_AMOUNT = 3;
    // double currentFrontAngle = Robot.getFrontClimber().getAngle();
    // double currentRearAngle = Robot.getRearClimber().getAngle();
		// double direction = Math.signum(targetAngle - currentFrontAngle);
    // double tilt = Math.signum(getAngleError());    
    // double angleError = Math.abs(Math.signum(direction + tilt)) * Math.abs(getAngleError());

    // if(Math.signum(angleError) > 0){
    //   Robot.getFrontClimber().setClimberMotorSpeed(0);
    // }else{
    //   Robot.getFrontClimber().goToDegree(targetAngle);
    //   Robot.getRearClimber().goToDegree(FRONT_REAR_RATIO * (currentRearAngle + (angleError * direction)));
    // }
    

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
    

    //rotate the front climber intake wheels
    if(isInIntakeArea()){
      Robot.getBall().setCargoIntakeSpeed(maxSpeed * 0.5 * direction);
    }else{
      Robot.getBall().setCargoIntakeSpeed(0);
    } */
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

  private double calcAdjustmentSpeed(double pitch) {
    if(Math.abs(pitch) > ABSOLUTE_PITCH_LIMIT) pitch = Math.signum(pitch) * ABSOLUTE_PITCH_LIMIT;
    double adjSpeed = pitch / ABSOLUTE_PITCH_LIMIT;
    return -adjSpeed;

  }

  @Override
  protected boolean isFinished() {
    return (targetAngle - Robot.getFrontClimber().getAngle()) * direction < 0;
  }

  @Override
  protected void end() {
    Robot.getFrontClimber().setSpeed(0);
    Robot.getRearClimber().setSpeed(0);
  }

  @Override
  protected void interrupted() {
    Robot.getFrontClimber().setSpeed(0);
    Robot.getRearClimber().setSpeed(0);    
  }
}