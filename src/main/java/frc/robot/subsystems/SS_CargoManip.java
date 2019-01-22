/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class SS_CargoManip extends Subsystem {
  private CANSparkMax cargoMotor;

  private double cargoMotorSpeedMultiplier;

  public SS_CargoManip() {
    cargoMotor = new CANSparkMax(RobotMap.CARGO_MOTOR, MotorType.kBrushless);
  }

  public void setCargoMotorSpeed(double speed) {
    cargoMotor.set(speed * cargoMotorSpeedMultiplier);
  }

  public void setCargoMotorSpeedMultiplier(double speedMultiplier) {
    cargoMotorSpeedMultiplier = speedMultiplier;
  }

  @Override
  public void initDefaultCommand() {
  }
}
