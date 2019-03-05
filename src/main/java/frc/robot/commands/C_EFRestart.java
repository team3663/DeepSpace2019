
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_EFRestart extends Command {
  /**
   * initilizes (restarts the initilization) the end effector after placing a hatch, if started with a hatch
   */
  public C_EFRestart() {

    requires(Robot.getEndEffectorAngle());
    requires(Robot.getElevator());
    requires(Robot.getHatch());
  }

  @Override
  protected void execute() {
    if(!Robot.getHatch().isPresent()){
      if(!Robot.getEndEffectorAngle().isInitialized()){

        if(Robot.getElevator().getAverageInch() < Robot.getElevator().getSafeFlipTop() && Robot.getFrontClimber().safeToFlip()){
          if(!Robot.getEndEffectorAngle().isReset()){
            Robot.getEndEffectorAngle().setAngleSpeed(-.6);
          }
          else{
            Robot.getEndEffectorAngle().resetEncoder();
            Robot.getEndEffectorAngle().setAngleSpeed(0);
            Robot.getEndEffectorAngle().setInitialized(true);
          }
        }
        else{
          Robot.getElevator().goToInch(1);
          Robot.getFrontClimber().goToDegree(Robot.getFrontClimber().getSafeTop());
        }
      }
    }

  }

  @Override
  protected boolean isFinished() {
    return Robot.getEndEffectorAngle().isReset() || Robot.getHatch().isPresent();
  }

}
