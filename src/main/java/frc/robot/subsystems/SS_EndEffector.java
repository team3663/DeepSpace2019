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

import edu.wpi.first.hal.sim.SimHooks;
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
  private DoubleSolenoid hatchPickupSolenoid;
  private CANSparkMax endEffectorAngleMotor;

  private double cargoIntakeMotorSpeedMultiplier = 0.3;
  private double endEffectorAngleSpeedMultiplier = 0.3;

  public SS_EndEffector() {
    cargoIntakeMotor = new CANSparkMax(RobotMap.CARGO_MOTOR, MotorType.kBrushless);
    hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_SOLENOID_FORWARD_CHANNEL, RobotMap.HATCH_SOLENOID_REVERSE_CHANNEL);
    endEffectorAngleMotor = new CANSparkMax(RobotMap.ENDEFFECTOR_ANGLE_MOTOR, MotorType.kBrushless);
  }

  public void setIntakeSpeed(double speed) {
    cargoIntakeMotor.set(speed * cargoIntakeMotorSpeedMultiplier);
  }

  public void setIntakeSpeedMultiplier(double speedMultiplier) {
    cargoIntakeMotorSpeedMultiplier = speedMultiplier;
  }

  public void setHatchOpen(boolean state) {
    if(state){
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kForward);
    }else{
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  //TODO: this should not ever exist, as this will break a bunch of things, good for testing tho
  public void setAngleSpeed(double speed){
    endEffectorAngleMotor.set(speed * endEffectorAngleSpeedMultiplier);
  }

  public void setAngleSpeedMultiplier(double speedMultipler){
    endEffectorAngleSpeedMultiplier = speedMultipler;
  }

  @Override
  public void initDefaultCommand() {
  }
}
