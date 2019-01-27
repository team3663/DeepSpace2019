/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.sun.jdi.Value;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class SS_EndEffector extends Subsystem {
  private CANSparkMax cargoIntakeMotor;
  private DoubleSolenoid hatchIntakeMotor;
  private CANSparkMax endEffectorAngleMotor;

  private double cargoIntakeMotorSpeedMultiplier;
  private double endEffectorAngleMotorMultiplier;
  private double hatchMotorSpeedMultiplier = 1;

  public SS_EndEffector() {
    cargoIntakeMotor = new CANSparkMax(RobotMap.CARGO_MOTOR, MotorType.kBrushless);
    hatchIntakeMotor = new DoubleSolenoid(RobotMap.HATCH_FORWARD_CHANNEL, RobotMap.HATCH_REVERSE_CHANNEL);
    endEffectorAngleMotor = new CANSparkMax(RobotMap.ENDEFFECTOR_ANGLE_MOTOR, MotorType.kBrushless);
  }

  public void setcargoIntakeMotorSpeed(double speed) {
    cargoIntakeMotor.set(speed * cargoIntakeMotorSpeedMultiplier);

  }

  public void setcargoIntakeMotorSpeedMultiplier(double speedMultiplier) {
    cargoIntakeMotorSpeedMultiplier = speedMultiplier;
  }

  public void grabHatch(Boolean state) {
    if(state){
      hatchIntakeMotor.set(DoubleSolenoid.Value.kForward);
    }else{
      hatchIntakeMotor.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void setHatchMotorSpeedMultiplier(double speedMultipler) {
    hatchMotorSpeedMultiplier = speedMultipler;
  }

  public void setEndEffectorAngleMotor(double speed){
    endEffectorAngleMotor.set(speed * endEffectorAngleMotorMultiplier);
  }

  public void setEndEffectorAngleMotorMultiplier(double speedMultipler){
    endEffectorAngleMotorMultiplier = speedMultipler;
  }
  @Override
  public void initDefaultCommand() {
  }
}
