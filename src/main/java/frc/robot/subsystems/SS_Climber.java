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
import frc.robot.commands.C_ClimberPos;

/**
 * Add your docs here.
 */
public class SS_Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private CANSparkMax frontMotor;
  private CANSparkMax backMotor;

  public SS_Climber(){
    frontMotor = new CANSparkMax(0, MotorType.kBrushless);
    backMotor = new CANSparkMax(1, MotorType.kBrushless);
  }

  public void climb(double rightVal, double leftVal){
    frontMotor.set(rightVal);
    backMotor.set(-leftVal);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new C_ClimberPos());
  }
}
