/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class SS_Griffon extends Subsystem {
  private TalonSRX cargoMotor;
  private CANSparkMax hatchMotor;

  private double cargoMotorSpeedMultiplier;
  private double hatchMotorSpeedMultiplier = 1;

  public SS_Griffon() {
    cargoMotor = new TalonSRX(RobotMap.CARGO_MOTOR);
    hatchMotor = new CANSparkMax(RobotMap.HATCH_MOTOR, MotorType.kBrushless);
  }

  public void setCargoMotorSpeed(double speed) {
    cargoMotor.set(ControlMode.PercentOutput, speed * cargoMotorSpeedMultiplier);
  }

  public void setCargoMotorSpeedMultiplier(double speedMultiplier) {
    cargoMotorSpeedMultiplier = speedMultiplier;
  }

  public void setHatchMotorSpeed(double speed) {
    hatchMotor.set(speed * hatchMotorSpeedMultiplier);
  }

  public void setHatchMotorSpeedMultiplier(double speedMultipler) {
    hatchMotorSpeedMultiplier = speedMultipler;
  }

  @Override
  public void initDefaultCommand() {
  }
}
