/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.commands.test_commands.C_RearClimberDirect;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_RearClimber extends Subsystem {
  private CANSparkMax rearClimberMotor;
  private CANPIDController PID;

  private DigitalInput rearClimberReset;

  private double fakeEncoder = 0;
  private double speedMultiplier = 0.3;

  private final double GEAR_RATIO = 1.0/208.0;

  private double ANGLE_LIMIT = 180;

  private boolean initilized = false;


  public SS_RearClimber() {
    rearClimberMotor = new CANSparkMax(RobotMap.CLIMBER_REAR_MOTOR, MotorType.kBrushless);

    rearClimberReset = new DigitalInput(RobotMap.REAR_CLIMBER_LIMIT_SWITCH);

    rearClimberMotor.getEncoder().setPosition(0);
    rearClimberMotor.setIdleMode(IdleMode.kBrake);

    PID = new CANPIDController(rearClimberMotor);
    PID.setP(1);
    PID.setI(0);
    PID.setD(0);
    PID.setOutputRange(-.6, .6);
  }

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new C_RearClimberDirect());
  }

  public void setInitilized(boolean initilized){
    this.initilized = initilized;
  }

  public boolean isInitilized(){
    return initilized;
  }
  
  public void setSpeed(double speed){
    rearClimberMotor.set( -speed * speedMultiplier);
  }

  public void setSpeedMultiplier(double speedMultiplier){
    this.speedMultiplier = speedMultiplier;
  }
  
  public void setBrakeMode(){
    rearClimberMotor.setIdleMode(IdleMode.kBrake);
  }

  public double degreeToRotation(double degree){
    return degree/360;
  }

  public double rotationToDegrees(double rotations){
    return rotations*360;
  }

  public double getRawEncoder(){
    return rearClimberMotor.getEncoder().getPosition();
  }

  public double getEncoder(){
    return gearMultiply(getRawEncoder());
  }

  public double gearMultiply(double rotation){
    return rotation * GEAR_RATIO;
  }

  public double getAngleLimit() {
    return ANGLE_LIMIT;
  }

  public double getAngle(){
    double position = rotationToDegrees(getEncoder());
    return position;
  }

  public boolean atTarget(double angle){
    return getAngle() < angle + 1 && getAngle() > angle - 1;
  }
  
  public void goToDegree(double degrees){
    rearClimberMotor.getPIDController().setReference(gearMultiply(degrees) * 360, 
      ControlType.kPosition);
  }

  public void resetEncoder(){
    rearClimberMotor.getEncoder().setPosition(0); //TODO: find what is the reset pos
  }

  public DigitalInput getRearClimberReset(){
    return rearClimberReset;
  }

  public boolean isReset(){
    return rearClimberReset.get();
  }

  public double getGearRatio() {
    return GEAR_RATIO;
  }

}
