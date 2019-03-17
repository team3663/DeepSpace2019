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
  private boolean end;

  private double direction;
  private double targetAngle = 0;
  private static final double THRESHOLD_MIN = 100;
  private static final double THRESHOLD_MAX = 160;
  private double ANGLE_ERROR_AMOUNT = 3;
  private static final double FRONT_CLIMBER_RADIUS = 16.5;
  private static final double REAR_LONG_SIDE_LENGTH = 27;
  private static final double REAR_SHORT_SIDE_LENGTH = 4;
  private static final double REAR_CLIMBER_RADIUS = Math.sqrt(Math.pow(REAR_LONG_SIDE_LENGTH, 2) + Math.pow(REAR_SHORT_SIDE_LENGTH, 2));
  private static final double FRONT_REAR_RATIO = FRONT_CLIMBER_RADIUS/REAR_CLIMBER_RADIUS;
  private static final double ABSOLUTE_PITCH_LIMIT = 15;
  private static final double WOAH_THERE = .45;

  private final double FRONT_CLIMBER_SCALAR = (Robot.getRearClimber().getGearRatio() / Robot.getFrontClimber().getGearRatio())
    * (REAR_CLIMBER_RADIUS / FRONT_CLIMBER_RADIUS);

  public C_Climb(boolean end) {
    this.end = end;
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getDrivetrain());
    //this.targetAngle = targetAngle;


    direction = Math.signum(targetAngle - Robot.getFrontClimber().getAngle());
    Robot.getFrontClimber().setBrakeMode();
    Robot.getRearClimber().setBrakeMode();
    Robot.getBall().setBrakeMode();
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    

    double joystickInput = Robot.getOI().getSecondaryController().getLeftYValue();
    double pitch = Robot.getDrivetrain().getPitch();
    
    if(joystickInput > .8){
      Robot.getFrontClimber().goToDegree(5);
      Robot.getRearClimber().goToDegree(0);
    }
    else if (joystickInput < .1){
      if(Robot.getFrontClimber().getAngle() > 155){
        Robot.getFrontClimber().goToDegree(170);
      }
      else{
        Robot.getFrontClimber().setSpeed(-joystickInput);
      }


      if(pitch > 0){
        if(Robot.getRearClimber().getAngle() < 175){
          Robot.getRearClimber().setSpeed(-1);
        }

      }
      else{
        Robot.getRearClimber().setSpeed(0);
      }
    

    }



/*
    //balanceed climb
    if (Math.abs(pitch) <= ABSOLUTE_PITCH_LIMIT) {
      Robot.getRearClimber().setSpeed(-joystickInput * WOAH_THERE);
      Robot.getFrontClimber().setSpeed((joystickInput + calcAdjustmentSpeed(pitch)) * FRONT_CLIMBER_SCALAR * WOAH_THERE);
    }
    //compensated climb
    else {
      //tilted back
      if(pitch > 0) {
        Robot.getRearClimber().setSpeed(-joystickInput * WOAH_THERE);
        Robot.getFrontClimber().goToDegree(Robot.getFrontClimber().getAngle());
      } 
      //tilted forward
      else {
        Robot.getRearClimber().goToDegree(Robot.getRearClimber().getAngle());
        Robot.getFrontClimber().setSpeed(joystickInput * WOAH_THERE);
      }
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

  private double calcAdjustmentSpeed(double pickles) {
    double adjSpeed = pickles / ABSOLUTE_PITCH_LIMIT;
    return -adjSpeed;

  }

  @Override
  protected boolean isFinished() {
    //return (targetAngle - Robot.getFrontClimber().getAngle()) * direction < 0;
    return end;
  }

  @Override
  protected void end() {
    Robot.getFrontClimber().setSpeed(0);
    Robot.getRearClimber().setSpeed(0);
  }
}