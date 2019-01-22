/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
import frc.robot.commands.C_Elevator;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_Elevator extends Subsystem {

  private CANSparkMax masterMotor;
  private CANSparkMax slaveMotor;

  public double speedMultiplier = 1;

  private PIDCont PID;

  private PIDCont[] Profiles; 

  public SS_Elevator() {
    masterMotor = new CANSparkMax(RobotMap.ELEVATOR_MASTER_MOTOR, MotorType.kBrushless);
    slaveMotor = new CANSparkMax(RobotMap.ELEVATOR_SLAVE_MOTOR, MotorType.kBrushless);

    //default PID profile
    PID = new PIDCont(speedMultiplier, 10, .01, 30);
    Profiles[] = {
      new PIDCont(speedMultiplier, 10, .01, 30),
      new PIDCont(speedMultiplier, 10, .01, 30)
    };

    slaveMotor.follow(masterMotor);
  }

  public void setElevatorSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  public void changePIDProfile(int profile){
    
  }
  
  public void setElevatorSpeed(double speed) {
    masterMotor.set(speed * speedMultiplier);
  }

  public void goToPos(){

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_Elevator());
  }
}
