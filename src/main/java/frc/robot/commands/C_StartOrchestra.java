/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.test_commands.C_ElevatorDirect;
import frc.robot.commands.test_commands.C_EndEffectorDirect;
import frc.robot.commands.test_commands.C_FrontClimberDirect;
import frc.robot.subsystems.SS_Elevator;
import frc.robot.subsystems.SS_EndEffectorAngle;
import frc.robot.subsystems.SS_FrontClimber;

public class C_StartOrchestra extends Command {
  SS_FrontClimber frontClimber;
  SS_Elevator elevator;
  SS_EndEffectorAngle efAngle;
  public C_StartOrchestra() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getElevator());
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getFrontClimber());

    elevator = Robot.getElevator();
    frontClimber = Robot.getFrontClimber();
    efAngle = Robot.getEndEffectorAngle();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.getOI().getTestController().getRightBumperButton().get()){

      
      if(!frontClimber.isInitialized()){
        frontClimber.resetEncoder();
        frontClimber.goToDegree(90);
        frontClimber.setInitialized(true);
      }
      if(!efAngle.isInitialized() && frontClimber.isInitialized()){
        if(elevator.getAverageInch() < elevator.getSafeFlipHeight()){
          if(!efAngle.getIsReset()){
            efAngle.setAngleSpeed(-.6);
          }
          else{
            efAngle.resetEncoder();
            efAngle.setAngleSpeed(0);
            efAngle.setInitialized(true);
          }
        }
      }
      if(!elevator.isInitialized() && frontClimber.isInitialized()){
        if(!elevator.getAtBottom()){
          elevator.setElevatorSpeed(-.4);
        }
        else{
          elevator.setElevatorSpeed(0);
          elevator.resetEncoders();
          elevator.setInitialized(true);
        }
        
      }

  }
  }


  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (elevator.isInitialized() && frontClimber.isInitialized() && efAngle.isInitialized()) || Robot.getOI().getTestController().getRightBumperButton().get();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(Robot.getOI().getTestController().getRightBumperButton().get()){
      new C_ElevatorDirect().start();
      new C_EndEffectorDirect().start();
      new C_FrontClimberDirect().start();
    }
    else{
      new C_FrontClimber(0).start();;
    }

    //TODO: start PID commands after this
  }

}
