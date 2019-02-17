/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.commands.test_commands.C_FrontClimberDirect;

/**
 * Add your docs here.
 */
public class SS_FrontClimber extends Subsystem {

  private CANSparkMax frontClimberMotor;
  private CANPIDController PID;

  private final double TOP_ANGLE_LIMIT = -25;
  private final double BOTTOM_ANGLE_LIMIT = 200;

  private final double SAFE_FLIP_TOP = 80;
  private final double SAFE_FLIP_BOT = 100;


  private final double GEAR_RATIO = 1.0/147.0;
  private final double TICKS_PER_DEGREE = 1.0/360.0;
  private double fakeEncoder = 0;

  private boolean initialized = false;

  private double frontClimberSpeedMultiplier = 0.5;//askInitDefault
  private double cargoIntakeSpeedMultiplier = 1;
  public SS_FrontClimber() {
    frontClimberMotor = new CANSparkMax(RobotMap.CLIMBER_FRONT_MOTOR, MotorType.kBrushless);
    
    frontClimberMotor.setClosedLoopRampRate(0);
    frontClimberMotor.setIdleMode(IdleMode.kBrake);
    frontClimberMotor.setInverted(true);
    //TODO: tweak PID values
    PID = new CANPIDController(frontClimberMotor);
    PID.setP(.4);    //.4  for music use commented pid values
    PID.setI(.00001);  //.0001
    PID.setD(0);     //10
    PID.setOutputRange(-.6, .6);
    
    
    PID.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);
  }
  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new C_FrontClimberDirect());
  }

  

  public void setClimberMotorSpeed(double speed){
    frontClimberMotor.set(speed * frontClimberSpeedMultiplier);
  }

  public void setCargoSpeedIntakeSpeedMultiplier(double speedMuliplier){
    cargoIntakeSpeedMultiplier = speedMuliplier;
  }

  public void setClimberSpeedMulitplier(double speedMuliplier){
    frontClimberSpeedMultiplier = speedMuliplier;
  }

  public double degreeToRotation(double degree) {
    return degree/360;
  }

  public double ticksToDegrees(double ticks) {
    return ticks/TICKS_PER_DEGREE;
  }

  public double getRawEncoder() {
    return frontClimberMotor.getEncoder().getPosition();
  }

  public void resetEncoder(){
    frontClimberMotor.getEncoder().setPosition(0);
  }


  public double getAngle(){
    double position = getRawEncoder() * GEAR_RATIO * 360;
    if(position > 360 || position < -360) {
      position %= 360;
      position *= 360;
    }
    return position;
  }

  public boolean atTarget(double angle){
    return getAngle() < angle + 1 && getAngle() > angle - 1;
  }

  public void goToDegree(double degree) {
    frontClimberMotor.getPIDController().setReference(degreeToRotation(degree) * (1/GEAR_RATIO), 
      ControlType.kPosition);
  }



  public double getTopAngleLimit() {
    return TOP_ANGLE_LIMIT;
  }

  public double getBottomAngleLimit() {
    return BOTTOM_ANGLE_LIMIT;
  }

  public boolean isInitialized(){
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

  public boolean safeToFlip(){
    return getAngle() > SAFE_FLIP_TOP && getAngle() < SAFE_FLIP_BOT;
  }


}
