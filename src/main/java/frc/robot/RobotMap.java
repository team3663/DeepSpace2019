/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * The zeros for the motor controller CAN identities are placeholders. (duh)
 */
public class RobotMap {
 
public static final int getDriveMotor(int module){
    int motors[] = {
      12,1,
      9,3
    };
    return motors[module];
  }
  public static final int getAngleMotor(int module){
    int motors[] = {
      10,2,
      14,5
    };
    return motors[module];
  }

  public static final int CLIMBER_FRONT_MOTOR = 11;
  public static final int CLIMBER_FRONT_CARGO_INTAKE = 15;

  public static final int CLIMBER_REAR_MOTOR = 6;

  public static final int CARGO_MOTOR = 8;
  public static final int ENDEFFECTOR_ANGLE_MOTOR = 7;

  public static final int ELEVATOR_MASTER_MOTOR = 4;
  public static final int ELEVATOR_SLAVE_MOTOR = 13;


  //Camera
  public static final int CAMERA_MODE = 0;
  public static final int CAMERA_LIGHT_MODE = 3;
  public static final int CAMERA_PIPELINE = 0;

  public static final int PRESSURE_SENSOR = 0;

  //DIO
  public static final int ELEVATOR_BOTTOM_LIMIT_SWITCH = 0;
  public static final int ELEVATOR_TOP_LIMIT_SWITCH = 1;

  public static final int CLIMBER_FRONT_LIMIT_SWITCH = 2;
  
  public static final int HATCH_PICKUP_SWITCH = 3;
  public static final int CARGO_SWITCH = 8;


  //Pneumatics
  public static final int HATCH_SOLENOID_FORWARD = 0;
  public static final int HATCH_SOLENOID_REVERSE = 1;

}
