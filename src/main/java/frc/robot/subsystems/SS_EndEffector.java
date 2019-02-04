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
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.hal.sim.SimHooks;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.C_EndEffectorDirect;

/**
 * Add your docs here.
 */
public class SS_EndEffector extends Subsystem {
  private CANSparkMax cargoIntakeMotor;
  private DoubleSolenoid hatchPickupSolenoid;
  private CANSparkMax endEffectorAngleMotor;

  private double cargoIntakeMotorSpeedMultiplier = 1;
  private double endEffectorAngleSpeedMultiplier = 0.3;

  private double TICKS_PER_ANGLE = 1/100;

  public SS_EndEffector() {
    cargoIntakeMotor = new CANSparkMax(RobotMap.CARGO_MOTOR, MotorType.kBrushless);
    
    //not part of the physical robot yet
    //hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_SOLENOID_FORWARD, RobotMap.HATCH_SOLENOID_REVERSE);
    endEffectorAngleMotor = new CANSparkMax(RobotMap.ENDEFFECTOR_ANGLE_MOTOR, MotorType.kBrushless);
    
    cargoIntakeMotor.setIdleMode(IdleMode.kCoast);
    endEffectorAngleMotor.setIdleMode(IdleMode.kBrake);
  }

  public void setIntakeSpeed(double speed) {
    cargoIntakeMotor.set(speed * cargoIntakeMotorSpeedMultiplier);
  }

  public void setIntakeSpeedMultiplier(double speedMultiplier) {
    cargoIntakeMotorSpeedMultiplier = speedMultiplier;
  }

  public void setHatch(Boolean isOpen) {
    if(isOpen){
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else{
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

  }

  public double getAngleEncoder(){
    return endEffectorAngleMotor.getEncoder().getPosition();
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
    setDefaultCommand(new C_EndEffectorDirect());
  }
}
