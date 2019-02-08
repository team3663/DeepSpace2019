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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.commands.test_commands.C_FrontClimberDirect;

/**
 * Add your docs here.
 */
public class SS_FrontClimber extends Subsystem {

  private CANSparkMax frontClimberMotor;
  private TalonSRX cargoIntake;
  private CANPIDController PID;
  private DigitalInput limitSwitch;

  private final double TOP_ANGLE_LIMIT = -25;
  private final double BOTTOM_ANGLE_LIMIT = 200;
  private final double GEAR_RATIO = 1/147;
  private final double TICKS_PER_DEGREE = 1/360;
  private double fakeEncoder = 0;

  private double frontClimberSpeedMultiplier = 0.3;//askInitDefault
  private double cargoIntakeSpeedMultiplier = 1;
  public SS_FrontClimber() {
    frontClimberMotor = new CANSparkMax(RobotMap.CLIMBER_FRONT_MOTOR, MotorType.kBrushless);
    cargoIntake = new TalonSRX(RobotMap.CLIMBER_FRONT_CARGO_INTAKE);
    limitSwitch = new DigitalInput(RobotMap.CLIMBER_FRONT_LIMIT_SWITCH);
    


    frontClimberMotor.setIdleMode(IdleMode.kBrake);
    //TODO: tweak PID values
    PID = new CANPIDController(frontClimberMotor);
    PID.setP(1);
    PID.setI(.01);
    PID.setD(3);
    PID.setOutputRange(-1, 1);
  }

  public void setCargoIntakeSpeed(double speed) {
    cargoIntake.set(ControlMode.MotionProfile.PercentOutput, speed * cargoIntakeSpeedMultiplier);
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

  public double degreeToTicks(int degree) {
    return degree*TICKS_PER_DEGREE;
  }

  public double ticksToDegrees(double ticks) {
    return ticks/TICKS_PER_DEGREE;
  }

  public double getRawEncoder() {
    return frontClimberMotor.getEncoder().getPosition();
  }

  public double getAngle(){
    double position = getRawEncoder() * GEAR_RATIO * 360;
    if(position > 360 || position < -360) {
      position %= 360;
      position *= 360;
    }
    return position;
  }

  public void goToDegree(double degree) {
    frontClimberMotor.getPIDController().setReference(degree * TICKS_PER_DEGREE / GEAR_RATIO, 
      ControlType.kPosition);
  }

  public double getTopAngleLimit() {
    return TOP_ANGLE_LIMIT;
  }

  public double getBottomAngleLimit() {
    return BOTTOM_ANGLE_LIMIT;
  }

  public DigitalInput getLimitSwitch() {
    return limitSwitch;
  }

  public boolean limitSwitchIsPressed() {
    return limitSwitch.get();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_FrontClimberDirect());
  }
}
