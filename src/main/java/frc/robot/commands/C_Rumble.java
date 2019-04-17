/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.util.RumbleSide;
import frc.robot.util.RumbleType;
import frc.robot.util.Stopwatch;



public class C_Rumble extends Command {
  
  private RumbleType type;
  private RumbleSide side;
  private int iterations, currentIteration, duration, joystick;
  private long intervalOn, intervalOff;
  private Stopwatch timeOff, timeOn, runningTimer;
  private OI oi;

  public C_Rumble(int joystick, long intervalOn, long intervalOff, RumbleType type, RumbleSide side, int iterations, int duration) {
    this.joystick = joystick;
    this.intervalOn = intervalOn;
    this.intervalOff = intervalOff;
    this.type = type;
    this.side = side;
    this.iterations = iterations;
    this.duration = duration;
    timeOff = new Stopwatch();
    runningTimer = new Stopwatch();
    timeOn = new Stopwatch();
    oi = Robot.getOI();
  }

  public C_Rumble(int joystick, long intervalOn, long intervalOff, RumbleType type, RumbleSide side, int iterations) {
    this(joystick, intervalOn, intervalOff, type, side, iterations, Integer.MAX_VALUE);
  }

  public C_Rumble(int joystick, long intervalOn, long intervalOff, int duration, RumbleType type, RumbleSide side ) {
    this(joystick, intervalOn, intervalOff, type, side, Integer.MAX_VALUE, duration);
  }



  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timeOff.reset();
    timeOn.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    //TODO: only ping is currently working
    if(type == RumbleType.kPing){
      ping();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return currentIteration >= iterations || duration < runningTimer.getMilliseconds() ;
  }

  @Override
  protected void end() {
    setBoth(0);
  }
  @Override
  protected void interrupted() {
    end();
  }

  private void setLeft(double value){
    switch (joystick) {
      case 0: 
        oi.getPrimaryController().setLeftRumble(value);
        break;
      case 1: 
        oi.getSecondaryController().setLeftRumble(value);
        break;
      case 2: 
        oi.getTestController().setLeftRumble(value);
        break;   
      default:
        break;
    }
  }

  private void setRight(double value){
    switch (joystick) {
      case 0: 
        oi.getPrimaryController().setRightRumble(value);
        break;
      case 1: 
        oi.getSecondaryController().setRightRumble(value);
        break;
      case 2: 
        oi.getTestController().setRightRumble(value);
        break;   
      default:
        break;
    }
  }

  private void setBoth(double value){
    setLeft(value);
    setRight(value);
  }

  private void sweep(){
    double output = Math.cos(2*Math.PI * (timeOn.getMilliseconds()/iterations));
    
    if(side == RumbleSide.kBoth){
      setBoth(output);
    }
    else if(side == RumbleSide.kLeft){
      setLeft(output);
    }
    else if(side == RumbleSide.kRight){
      setRight(output);
    }
    if(timeOn.getMilliseconds() > iterations){
      timeOn.reset();
    }
  }

  private void ping(){

    if(timeOn.getMilliseconds() < intervalOn){
      timeOff.reset();
      if(side == RumbleSide.kBoth){
        setBoth(1);
      }
      else if(side == RumbleSide.kLeft){
        setLeft(1);
      }
      else if(side == RumbleSide.kRight){
        setRight(1);
      }
    }
    else if (timeOff.getMilliseconds() < intervalOff){
      
      if(side == RumbleSide.kBoth){
        setBoth(0);
      }
      else if(side == RumbleSide.kLeft){
        setLeft(0);
      }
      else if(side == RumbleSide.kRight){
        setRight(0);
      }
      
    }
    else if (timeOff.getMilliseconds() >= intervalOff){
      timeOn.reset();
      currentIteration++;
    }

  }

  //TODO: maybe use sin? this is doing sawtooth
  private void leftRightSweep(){
    double output = timeOn.getMilliseconds()/iterations;

    if(side == RumbleSide.kBoth){
      setLeft(output);
      setRight(iterations - output);
    }
    if(timeOn.getMilliseconds() > iterations){
      timeOn.reset();
    }
  }
}
