/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.sun.jdi.Value;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
public class SS_Ball extends Subsystem {
  
  private CANSparkMax EndEffectorIntakeMotor;
  private TalonSRX climberIntakeMotor;

  private DigitalInput cargoSwitch;
  



  public SS_Ball() {
    climberIntakeMotor = new TalonSRX(RobotMap.CLIMBER_FRONT_CARGO_INTAKE);
    climberIntakeMotor.setNeutralMode(NeutralMode.Coast);
    climberIntakeMotor.setInverted(true);
    EndEffectorIntakeMotor = new CANSparkMax(RobotMap.CARGO_MOTOR, MotorType.kBrushless);
    EndEffectorIntakeMotor.setIdleMode(IdleMode.kCoast);

    cargoSwitch = new DigitalInput(RobotMap.CARGO_SWITCH);
  }

  @Override
  public void initDefaultCommand() {}

  public void setIntakeSpeed(double speed) {
    EndEffectorIntakeMotor.set(speed);
  }

  public void setCargoIntakeSpeed(double speed) {
    climberIntakeMotor.set(ControlMode.MotionProfile.PercentOutput, speed);
  }

  public void setBrakeMode(){
    climberIntakeMotor.setNeutralMode(NeutralMode.Brake);
  }

  public DigitalInput getCagroSwitch(){
    return cargoSwitch;
  }
  public boolean cargoIsPresent(){
    return !cargoSwitch.get();
  }


}
