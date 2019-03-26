/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_RearClimber;

public class C_DefenseMode extends Command {
  private SS_RearClimber climber;
  private boolean isDefense;
  private double targetDegree;

  public C_DefenseMode() {
    requires(Robot.getRearClimber());
    climber = Robot.getRearClimber();
    this.isDefense = !climber.getDefense();
  }

  public C_DefenseMode(boolean isDefense) {
    requires(Robot.getRearClimber());
    climber = Robot.getRearClimber();
    this.isDefense = isDefense;
  }

  @Override
  protected void initialize() {
    targetDegree = climber.getSafeAngle();
    if(isDefense) targetDegree = 0;
  }
  
  @Override
  protected void execute() {
    climber.goToDegree(targetDegree);
    
  }
  
  @Override
  protected boolean isFinished() {
    System.out.println("CURRENT DEGREE  :  " + targetDegree + " :=: " + isDefense);
    return climber.atTarget(targetDegree);
  }

  @Override
  protected void end() {
    climber.setDefense(isDefense);

  }

  @Override
  public synchronized boolean isInterruptible() {
    return true;
  }
}
