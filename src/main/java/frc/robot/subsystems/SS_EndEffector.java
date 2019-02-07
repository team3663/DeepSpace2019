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
  private CANSparkMax endEffectorAngleMotor;
  
  private DigitalInput cargoSwitch;
  
  private DoubleSolenoid hatchPickupSolenoid;
  private DigitalInput hatchPickupSwitch;

  private double cargoIntakeMotorSpeedMultiplier = 1;
  private double endEffectorAngleSpeedMultiplier = 0.3;

  //TODO: double check these angles
  private double FRONT_ANGLE_LIMIT = 95; 
  private double BACK_ANGLE_LIMIT = 270;

  private double ANGLE_MOTOR_GEAR_RATIO = 1/100; 
  private double TICKS_PER_DEGREE = 1/360;


  public SS_EndEffector() {
    cargoIntakeMotor = new CANSparkMax(RobotMap.CARGO_MOTOR, MotorType.kBrushless);
    
    //not part of the physical robot yet
    //hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_SOLENOID_FORWARD, RobotMap.HATCH_SOLENOID_REVERSE);
    endEffectorAngleMotor = new CANSparkMax(RobotMap.ENDEFFECTOR_ANGLE_MOTOR, MotorType.kBrushless);
    //pressureSensor = new AnalogInput(RobotMap.PRESSURE_SENSOR);
    endEffectorAngleMotor.getEncoder().setPosition(30);


    cargoSwitch = new DigitalInput(RobotMap.CARGO_SWITCH);
    
    hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_SOLENOID_FORWARD, RobotMap.HATCH_SOLENOID_REVERSE);
    hatchPickupSwitch = new DigitalInput(RobotMap.HATCH_PICKUP_SWITCH);
    
    cargoIntakeMotor.setIdleMode(IdleMode.kCoast);
    endEffectorAngleMotor.setIdleMode(IdleMode.kBrake);
    endEffectorAngleMotor.setInverted(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_EndEffectorDirect());
  }

  public double ticksToDegree(double ticks){
    return (ticks * ANGLE_MOTOR_GEAR_RATIO) / TICKS_PER_DEGREE;
  }
  public double degreesToTicks(double degrees){
    return degrees * TICKS_PER_DEGREE;
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

  public double getRawAngleEncoder(){
    return endEffectorAngleMotor.getEncoder().getPosition();
  }

  public DigitalInput getCagroSwitch(){
    return cargoSwitch;
  }
  public boolean getCargoPresent(){
    return !cargoSwitch.get();
  }
  public double getAngle(){

    double angle = -getRawAngleEncoder() * .01 * 360;
    if(angle < 0){
      angle += 360;
    }
    return angle;
  }



  //TODO: this should not ever exist, as this will break a bunch of things, good for testing tho
  public void setAngleSpeed(double speed){
    endEffectorAngleMotor.set(speed * endEffectorAngleSpeedMultiplier);
  }

  public void setAngleSpeedMultiplier(double speedMultipler){
    endEffectorAngleSpeedMultiplier = speedMultipler;
  }
}
