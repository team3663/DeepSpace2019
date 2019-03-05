/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Mode;

public class C_Flip extends Command {
  private boolean isFront;
  private double elevatorEnd;
  private double defaultElevatorEndTop = Robot.getElevator().getSafeFlipTop() - 1;
  private double defaultElevatorEndBot = Robot.getElevator().getSafeFlipBot() + .5;

/**
 * cooridinates the end effector with the elevator to make sure it flips saefly
 * 
 * @param isFront which side the end effector is being flipped to 
 */
  public C_Flip(boolean isFront) {
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getElevator());
    requires(Robot.getFrontClimber());
    requires(Robot.getHatch());

    if(!Robot.getEndEffectorAngle().isInitialized() && !Robot.getHatch().isPresent()){
      new C_EFRestart().start();
    }

    this.isFront = isFront;
    this.elevatorEnd = defaultElevatorEndTop;
  }

  /**
   * cooridinates the end effector with the elevator to make sure it flips saefly
   * 
   * @param isFront which side the end effector is being flipped to 
   * @param elevatorEnd the height the elevator will end at
   */
  C_Flip(boolean isFront, double elevatorEnd) {
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getElevator());
    requires(Robot.getFrontClimber());
    requires(Robot.getHatch());

    if(!Robot.getEndEffectorAngle().isInitialized() && !Robot.getHatch().isPresent()){
      new C_EFRestart().start();
    }


    if(elevatorEnd > Robot.getElevator().getSafeFlipTop()){
      elevatorEnd = defaultElevatorEndTop;
    }
    else if(elevatorEnd < Robot.getElevator().getSafeFlipBot()){
      elevatorEnd = defaultElevatorEndBot;
    }

    this.isFront = isFront;
    this.elevatorEnd = elevatorEnd;
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(4);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.getEndEffectorAngle().isFlipped(isFront) && !Robot.getHatch().isPresent()){

      if(Robot.getFrontClimber().safeToFlip()){
        Robot.getElevator().goToInch(elevatorEnd);

        if(Robot.getElevator().getAverageInch() > Robot.getElevator().getSafeFlipBot() && Robot.getElevator().getAverageInch() < Robot.getElevator().getSafeFlipTop()){
          Robot.getEndEffectorAngle().goToDegree(Robot.getEndEffectorAngle().getSafeFlipAngle(isFront));
        }
        
      }
      else{
        Robot.getFrontClimber().goToDegree(Robot.getFrontClimber().getSafeTop()); 
      }
    }
    else if(Robot.getHatch().isPresent()){
      Robot.getHatch().setMode(Mode.kHatch);
    }
    //stops the motor if not initilized
    if(!Robot.getEndEffectorAngle().isInitialized() && !Robot.getHatch().isPresent()){
      Robot.getEndEffectorAngle().goToDegree(Robot.getEndEffectorAngle().getAngle());
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("C FLIP IS GOING");
    return Robot.getEndEffectorAngle().isFlipped(isFront) || Robot.getHatch().isPresent()|| isTimedOut() || (!Robot.getEndEffectorAngle().isInitialized() && !Robot.getHatch().isPresent()) || 
    //this is a temp fix for the PID controller
    (Robot.getEndEffectorAngle().getSafeFlipAngle(isFront) + 3 > Robot.getEndEffectorAngle().getAngle() && Robot.getEndEffectorAngle().getSafeFlipAngle(isFront) - 3 < Robot.getEndEffectorAngle().getAngle());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
