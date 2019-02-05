/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.commands.C_Elevator;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_Elevator extends Subsystem {

  private CANSparkMax masterMotor;
  private CANSparkMax slaveMotor;
  private DigitalInput bottomLimitSwitch;
  private DigitalInput topLimitSwitch;

  public double speedMultiplier = 0.3;
  private int selectedLevel = 1;
  private double TICKS_PER_INCH = 2.6;

  private PIDCont PID;

  private PIDCont[] profiles; 

  public SS_Elevator() {
    masterMotor = new CANSparkMax(RobotMap.ELEVATOR_MASTER_MOTOR, MotorType.kBrushless);
    slaveMotor = new CANSparkMax(RobotMap.ELEVATOR_SLAVE_MOTOR, MotorType.kBrushless);
    bottomLimitSwitch = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_LIMIT_SWITCH);
    topLimitSwitch = new DigitalInput(RobotMap.ELEVATOR_TOP_LIMIT_SWITCH);

    masterMotor.setInverted(true);
    masterMotor.setIdleMode(IdleMode.kBrake);
    //default PID profile
    
    profiles = new PIDCont[] {
      new PIDCont(speedMultiplier, 10, .01, 30), 
      new PIDCont(speedMultiplier, 10, .01, 30) //TODO adjust PID values
    };

    PID = profiles[0];

    slaveMotor.follow(masterMotor, true);
  }

  public void setElevatorSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  public void changePIDProfile(int profile){
    PID = profiles[profile];
  }
  
  public void setElevatorSpeed(double speed) {
    if(!getBottomLimitSwitchOutput() || !getTopLimitSwitchOutput()){
      masterMotor.set(speed * speedMultiplier);
    }else{
      masterMotor.set(0);
    }
  }

  public void goToPos(int pos){
    //TODO: implement PID
  }

  public void goToLevel(int selectedLevel){
    this.selectedLevel = selectedLevel;
    //TODO: find position of levels
    if(selectedLevel == 1){

    }
    else if (selectedLevel == 2){

    }
    else if (selectedLevel == 3){

    }
  }

  public void setSelectedLevel(int level){
    selectedLevel = level;
  }

  public double getMasterEncoder(){
    //TODO: compare encoders or replace with better encoders
    return -masterMotor.getEncoder().getPosition();
  }
  public double getSlaveEncoder(){
    return slaveMotor.getEncoder().getPosition();
  }
  public double getNEOEncoder(){
    return (getMasterEncoder()+getSlaveEncoder())/2;
  }
  public double getAverageInch(){
    return getNEOEncoder()/TICKS_PER_INCH;
  }

  public DigitalInput getTopLimitSwitch(){
    return topLimitSwitch;
  }
  public DigitalInput getBottomLimitSwitch() {
    return bottomLimitSwitch;
  }

  public boolean getBottomLimitSwitchOutput() {
    return bottomLimitSwitch.get();
  }

  public boolean getTopLimitSwitchOutput(){
    return topLimitSwitch.get();
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_Elevator());
  }
}
