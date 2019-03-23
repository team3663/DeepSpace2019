
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.test_commands.C_ElevatorToInch;

public class C_EFRestart extends Command {
  /**
   * initilizes (restarts the initilization) the end effector after placing a hatch, if started with a hatch
   */
  public C_EFRestart() {

    requires(Robot.getEndEffectorAngle());
    requires(Robot.getElevator());
    requires(Robot.getFrontClimber());
    requires(Robot.getHatch());
  }

  @Override
  protected void initialize() {
    Robot.getFrontClimber().goToDegree(Robot.getFrontClimber().getSafeTop());
  }

  @Override
  protected void execute() {
    if(!Robot.getHatch().isPresent()){

      if(!Robot.getElevator().isInitialized()){
        if(!Robot.getElevator().getAtBottom()){
          Robot.getElevator().setElevatorSpeed(-.4);
        }
        else{
          Robot.getElevator().setElevatorSpeed(0);
          Robot.getElevator().resetEncoders();
          Robot.getElevator().setInitialized(true);
        }
      }
      else if(!Robot.getEndEffectorAngle().isInitialized() && Robot.getFrontClimber().safeToFlip()){
        
        if(!Robot.getEndEffectorAngle().isReset()){
          Robot.getEndEffectorAngle().setAngleSpeed(-.6);
        }
        else{
          Robot.getEndEffectorAngle().resetEncoder();
          Robot.getEndEffectorAngle().setAngleSpeed(0);
          Robot.getEndEffectorAngle().setInitialized(true);
        }

        }
      }
    }

  

  @Override
  protected boolean isFinished() {
    return Robot.getEndEffectorAngle().isInitialized() || Robot.getHatch().isPresent();
  }

  @Override
  protected void end() {
    new C_SetHatchClosed(true);
    new C_ElevatorToInch(1).start();
    new C_EFToAngle(90).start();
    new C_FrontClimber(0).start();

  }
}
