/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.commands.test_commands.C_ElevatorDirect;
import frc.robot.commands.test_commands.C_ElevatorToInch;
import frc.robot.commands.test_commands.C_EndEffectorDirect;
import frc.robot.commands.test_commands.C_FrontClimberDirect;
import frc.robot.commands.test_commands.C_RearClimberDirect;
import frc.robot.subsystems.SS_Elevator;
import frc.robot.subsystems.SS_EndEffectorAngle;
import frc.robot.subsystems.SS_FrontClimber;
import frc.robot.subsystems.SS_RearClimber;
import frc.robot.util.Side;

public class C_StartOrchestra extends Command {
  SS_FrontClimber frontClimber;
  SS_RearClimber rearClimber;
  SS_Elevator elevator;
  SS_EndEffectorAngle efAngle;
  public C_StartOrchestra() {
    requires(Robot.getElevator());
    requires(Robot.getEndEffectorAngle());
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getHatch());

    frontClimber = Robot.getFrontClimber();
    rearClimber = Robot.getRearClimber();
    elevator = Robot.getElevator();
    efAngle = Robot.getEndEffectorAngle();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.getHatch().extendHatchPickup(false);
    Robot.getHatch().setHatchClosed(true);
    if(Robot.getHatch().isPresent()){
      Robot.getEndEffectorAngle().setEncoder(-16.5); 
    }
  }

  @Override
  protected void execute() {
    if(!Robot.getOI().getTestController().getRightBumperButton().get()){


      if(!rearClimber.isInitilized()){
        /*
        rear climber
        */
        if(!rearClimber.isReset()){
          rearClimber.setSpeed(.2);
        }
        else{
          rearClimber.setSpeed(0);
          rearClimber.resetEncoder();

          rearClimber.setInitilized(true);
        }
      }

      if(!frontClimber.isInitialized()){
        /*
        frontClimber.
        */
        if(!frontClimber.isReset()){
          frontClimber.setSpeed(.5);
        }
        else{
          frontClimber.setSpeed(0);
          frontClimber.resetEncoder();

          frontClimber.setInitialized(true);
        }
      }
      else{
        frontClimber.goToDegree(frontClimber.getSafeTop());
      }
      if(!efAngle.isInitialized() && frontClimber.isInitialized()){
        /*
        end effector
        */
        if(!Robot.getHatch().isPresent()){

          if(elevator.getAverageInch() < elevator.getSafeFlipTop()){
            if(!efAngle.isReset()){
              efAngle.setAngleSpeed(-.6);
            }
            else{
              efAngle.resetEncoder();
              efAngle.setAngleSpeed(0);
              efAngle.setInitialized(true);
            }
          }
        }
      }
      else if (efAngle.isInitialized()){
       // efAngle.goToDegree(90);
      }
      if(!elevator.isInitialized() && frontClimber.isInitialized()){
        /*
        * elevator
        */
        if(!Robot.getHatch().isPresent()){
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
  }


  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((elevator.isInitialized() || Robot.getHatch().isPresent()) && 
      frontClimber.isInitialized() && 
      rearClimber.isInitilized() && 
      (efAngle.isInitialized() || Robot.getHatch().isPresent())) ||
      Robot.getOI().getTestController().getRightBumperButton().get();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    //enter testing mode
    if(Robot.getOI().getTestController().getRightBumperButton().get()){
      Scheduler.getInstance().removeAll();
      new C_ElevatorDirect().start();
      new C_EndEffectorDirect().start();
      new C_FrontClimberDirect().start();
      new C_RearClimberDirect().start();
    }
    else{
      if(!Robot.getHatch().isPresent()){
        new C_ElevatorToInch(1).start();
      }
      else if(!Robot.getEndEffectorAngle().isHatchRestarted()){
        new C_EFStartHatch(Robot.getEndEffectorAngle().getSafeFlipAngle(Side.kBack) + 10).start();
      }
      new C_FrontClimber(0).start();
      new C_RearClimberToAngle(Robot.getRearClimber().getSafeAngle()).start();
    }
  }

  @Override
  public synchronized boolean isInterruptible() {
    return false;
  }

}
