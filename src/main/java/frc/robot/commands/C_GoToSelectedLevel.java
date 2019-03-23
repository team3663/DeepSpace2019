/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_Elevator;

public class C_GoToSelectedLevel extends Command {
  SS_Elevator elevator;

  public C_GoToSelectedLevel() {

    requires(Robot.getElevator());

    elevator = Robot.getElevator();
  }

  @Override
  protected void execute() {
    if(!Robot.getEndEffectorAngle().getFlipFailed())
      elevator.goToSelectedLevel();

  }

  @Override
  protected boolean isFinished() {
    System.out.println("GO TO LEVEL RUNNING");
    //ends if current inch is within range of target level
    return elevator.atTarget(elevator.getSelectedLevelInch()) || Robot.getEndEffectorAngle().getFlipFailed();
  }




}
