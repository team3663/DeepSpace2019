/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EFToAngle extends Command {
  private double angle;

  private double forwardAngleLimit;
  private double backwardAngleLimit;
  private boolean SafetyOverride;

  /**
   * 
   * goes to end effector angle
   * 
   * @param angle target angle
   */

  public  C_EFToAngle(double angle, boolean SafetyOverride) {
    requires(Robot.getEndEffectorAngle());
    this.angle = angle;
    this.SafetyOverride = SafetyOverride;

    forwardAngleLimit = Robot.getEndEffectorAngle().getFrontAngleLimit();
    backwardAngleLimit = Robot.getEndEffectorAngle().getBackAngleLimit();


  }

  @Override
  protected void initialize() {
    if(!Robot.getEndEffectorAngle().isInitialized() && !Robot.getHatch().isPresent()){
      new C_EFRestart().start();
    }

    if(angle > forwardAngleLimit){
      angle = forwardAngleLimit;
    }
    if(angle < backwardAngleLimit){
      angle = backwardAngleLimit;
    }

    setTimeout(3);
  }

  protected void execute() {
    if(Robot.getEndEffectorAngle().isInitialized()){
      if(Robot.getElevator().getAverageInch() < Robot.getElevator().getSafeFlipTop() || SafetyOverride){
        Robot.getEndEffectorAngle().goToDegree(angle);
      }
    }
  }
  @Override
  protected boolean isFinished() {
    System.out.println("C END EFFECOT ANGLE");
    return Robot.getEndEffectorAngle().atTarget(angle) || !Robot.getEndEffectorAngle().isInitialized() || isTimedOut() ||
    //this is a temp fix for the PID controller
    (angle + 1 > Robot.getEndEffectorAngle().getAngle() && angle - 1 < Robot.getEndEffectorAngle().getAngle()); 
  }


}
