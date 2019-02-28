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
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.commands.C_GoToSelectedLevel;
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
  private double GEAR_RATIO = 1.0/10.0;

  private boolean initialized = false;

  private final double SAFE_FLIP_HEIGHT = 3.5;
  private final double LEVEL_1 = 5;
  private final double LEVEL_2 = 26;
  private final double LEVEL_3 = 26;
  private final double LEVEL_7 = 16;

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

    PID.setP(.06);
    PID.setI(.0);
    PID.setD(1);
    PID.setOutputRange(-.4, .4);
    masterMotor.getPIDController().setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);



    slaveMotor.follow(masterMotor, true);

    //slaveMotor.setEncPosition(0);
  }
  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new C_ElevatorDirect());
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

  public void goToInch(double inch){
    goToPos(inch * TICKS_PER_INCH);
  }


  public void goToSelectedLevel(){
    
    if(selectedLevel == 1){
      goToInch(LEVEL_1);
    }
    else if (selectedLevel == 2){
      goToInch(LEVEL_2);
    }
    else if (selectedLevel == 3){
      goToInch(LEVEL_3);
    }
    else if(selectedLevel == 7){
      goToInch(LEVEL_7);
    }
  }

  public CANPIDController elevatorPID(){
    return masterMotor.getPIDController();
  }

  public double getSelectedLevelInch(){
    
    if(selectedLevel == 1){
      return LEVEL_1;
    }
    else if (selectedLevel == 2){
      return LEVEL_2;
    }
    else if (selectedLevel == 3){
      return LEVEL_3;
    }
    else{
      return 0;
    }
  }

  public double getSafeFlipHeight(){
    return SAFE_FLIP_HEIGHT;
  }


  public boolean atTarget(double targetInch){
    return getAverageInch() < targetInch + .5 && getAverageInch() > targetInch - .5;
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
  public void setSelectedLevel(int level){
    selectedLevel = level;
  }
  
  public boolean isInitialized(){
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }


}
