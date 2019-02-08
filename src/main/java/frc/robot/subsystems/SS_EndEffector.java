/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.sun.jdi.Value;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.hal.sim.SimHooks;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.test_commands.C_EndEffectorDirect;

/**
 * Add your docs here.
 */
public class SS_EndEffector extends Subsystem {

  // Rev air pressure sensor variables
  private static final double SUPPLY_VOLTAGE = 5;
  //private AnalogInput pressureSensor;
  
  private CANSparkMax cargoIntakeMotor;
  
  private DigitalInput cargoSwitch;
  
  private DoubleSolenoid hatchPickupSolenoid;
  private DigitalInput hatchPickupSwitch;

  private double cargoIntakeMotorSpeedMultiplier = 1;
  private double endEffectorAngleSpeedMultiplier = 0.3;

  private AnalogInput pressureSensor;


  public SS_EndEffector() {
    cargoIntakeMotor = new CANSparkMax(RobotMap.CARGO_MOTOR, MotorType.kBrushless);
    hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_SOLENOID_FORWARD, RobotMap.HATCH_SOLENOID_REVERSE);
    pressureSensor = new AnalogInput(RobotMap.PRESSURE_SENSOR);


    cargoSwitch = new DigitalInput(RobotMap.CARGO_SWITCH);
    
    hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_SOLENOID_FORWARD, RobotMap.HATCH_SOLENOID_REVERSE);
    hatchPickupSwitch = new DigitalInput(RobotMap.HATCH_PICKUP_SWITCH);
    
    cargoIntakeMotor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new C_EndEffectorDirect());
  }

  public void setIntakeSpeed(double speed) {
    cargoIntakeMotor.set(speed * cargoIntakeMotorSpeedMultiplier);
  }

  public void setIntakeSpeedMultiplier(double speedMultiplier) {
    cargoIntakeMotorSpeedMultiplier = speedMultiplier;
  }

  public void setHatchClose(boolean state) {
    if(state){
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kForward);
    }else{
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

  }

  public double getAirPressure() {
    return 0; //250 * pressureSensor.getVoltage() / SUPPLY_VOLTAGE - 25;
  }

  public DigitalInput getCagroSwitch(){
    return cargoSwitch;
  }
  public boolean getCargoPresent(){
    return !cargoSwitch.get();
  }

  public AnalogInput getPressureSensor() {
    return pressureSensor;
  }

}
