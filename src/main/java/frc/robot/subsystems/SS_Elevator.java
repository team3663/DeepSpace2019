/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.commands.C_GoToLevel;
import frc.robot.commands.test_commands.C_ElevatorDirect;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_Elevator extends Subsystem {

  private CANSparkMax masterMotor;
  private CANSparkMax slaveMotor;
  private DigitalInput bottomLimitSwitch;
  private DigitalInput topLimitSwitch;

  public double speedMultiplier = .3  ;
  private int selectedLevel = 1;
  private double TICKS_PER_INCH = 2.6;
  private double GEAR_RATIO = 1/10;

  private boolean initialized = false;

  private final double SAFE_FLIP_LIMIT = 3.5;
  private final double LEVEL_1 = 12;
  private final double LEVEL_2 = 24;
  private final double LEVEL_3 = 36;

  private CANPIDController PID;

  private PIDCont[] profiles; 

  public SS_Elevator() {
    masterMotor = new CANSparkMax(RobotMap.ELEVATOR_MASTER_MOTOR, MotorType.kBrushless);
    slaveMotor = new CANSparkMax(RobotMap.ELEVATOR_SLAVE_MOTOR, MotorType.kBrushless);
    bottomLimitSwitch = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_LIMIT_SWITCH);
    topLimitSwitch = new DigitalInput(RobotMap.ELEVATOR_TOP_LIMIT_SWITCH);



    masterMotor.setInverted(true);
    masterMotor.setIdleMode(IdleMode.kBrake);
    //default PID profile
    PID = new CANPIDController(masterMotor);

    PID.setP(30);
    PID.setI(.01);
    PID.setD(50);
    PID.setOutputRange(-.1, .1);



    slaveMotor.follow(masterMotor, true);

    //slaveMotor.setEncPosition(0);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_ElevatorDirect());
  }

  public void setElevatorSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }



  public void setElevatorSpeed(double speed) {
      masterMotor.set(speed * speedMultiplier);
  }


  public void goToPos(double pos){
    masterMotor.getPIDController().setReference(pos, ControlType.kPosition);
  }

  public void goToLevel(int selectedLevel){
    this.selectedLevel = selectedLevel;
    //TODO: find position of levels
    if(selectedLevel == 1){
      goToPos(-LEVEL_1 * TICKS_PER_INCH * GEAR_RATIO);
      
      System.out.println(masterMotor.get() + ", " + slaveMotor.get());
    }
    else if (selectedLevel == 2){

    }
    else if (selectedLevel == 3){

    }
  }

  public CANPIDController elevatorPID(){
    return masterMotor.getPIDController();
  }

  public void setSelectedLevel(int level){
    selectedLevel = level;
  }

  public double getSafeFlipLimit(){
    return SAFE_FLIP_LIMIT;
  }

  public double getMasterEncoder(){
    return masterMotor.getEncoder().getPosition();
  }
  public double getSlaveEncoder(){
    return slaveMotor.getEncoder().getPosition();
  }
  public void resetEncoders(){
    masterMotor.getEncoder().setPosition(0);
    slaveMotor.getEncoder().setPosition(0);
  }
  public double getAverageEncoder(){
    return (getMasterEncoder()+getSlaveEncoder())/2;
  }
  public double getAverageInch(){
    return getMasterEncoder()/TICKS_PER_INCH;
  }

  public DigitalInput getTopLimitSwitch(){
    return topLimitSwitch;
  }
  public DigitalInput getBottomLimitSwitch() {
    return bottomLimitSwitch;
  }

  public boolean getBottomLimitSwitchOutput() {
    return !bottomLimitSwitch.get();
  }

  public boolean getTopLimitSwitchOutput() {
    return !topLimitSwitch.get();
  }

  public boolean getAtBottom(){
    return getTopLimitSwitchOutput() && getBottomLimitSwitchOutput();
  }
  public int getSelectedLevel() {
    return selectedLevel;
  }

  
  public boolean isInitialized(){
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }


}
