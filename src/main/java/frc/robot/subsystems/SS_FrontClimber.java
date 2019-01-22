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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class SS_FrontClimber extends Subsystem {

  private CANSparkMax frontNeoOne;
  private CANSparkMax frontNeoTwo;
  private CANPIDController PID;
  private TalonSRX frontTalon;

  private double TICKS_PER_DEGREE = 0;

  public SS_FrontClimber(){
    frontNeoOne = new CANSparkMax(RobotMap.CLIMBER_FRONT_MOTOR_ONE, MotorType.kBrushless);
    frontNeoTwo = new CANSparkMax(RobotMap.CLIMBER_FRONT_MOTOR_TWO, MotorType.kBrushless);
    frontNeoTwo.follow(frontNeoOne);

    //TODO: tweak PID values
    PID = new CANPIDController(frontNeoOne);
    PID.setP(1);
    PID.setI(.01);
    PID.setD(3);
    PID.setOutputRange(-1, 1);

    frontTalon = new TalonSRX(0); //TODO put in robomap
    }

  public void setClimberFrontMotorsSpeed(double speed) {
    frontTalon.set(ControlMode.PercentOutput, speed);
  }

  public double degreeToTicks(int degree){
    return degree*TICKS_PER_DEGREE;
  }

  public double ticksToDegrees(double ticks){
    return ticks/TICKS_PER_DEGREE;
  }

  public double getEncoder(){
    return frontNeoOne.getEncoder().getPosition();
  }


  public double getAngle(){
    return ticksToDegrees(getEncoder());
  }


  @Override
  public void initDefaultCommand() {
  }
}
