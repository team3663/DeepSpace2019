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
 
  public final int getDriveMotors(int module){
    int motors[] = {
      12,2,
      9,3
    };
    return motors[module];
  }
  public final int getAngleMotors(int module){
    int motors[] = {
      12,10,
      1,8
    };
    return motors[module];
  }

  public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 12;
  public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 10;
  public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 2;
  public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 1;
  public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 9;
  public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 14;
  public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 5;
  public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 3;

  public static final int CLIMBER_FRONT_MOTOR = 11;
  public static final int CLIMBER_FRONT_CARGO_INTAKE = 0;

  public static final int CLIMBER_REAR_MOTOR = 6;

  public static final int CARGO_MOTOR = 7;
  public static final int ENDEFFECTOR_ANGLE_MOTOR = 8;

  public static final int HATCH_SOLENOID_FORWARD_CHANNEL = 0;
  public static final int HATCH_SOLENOID_REVERSE_CHANNEL = 1;

  public static final int ELEVATOR_MASTER_MOTOR = 4;
  public static final int ELEVATOR_SLAVE_MOTOR = 13;

  public static final int CAMERA_MODE = 0;
  public static final int CAMERA_LIGHT_MODE = 3;
  public static final int CAMERA_PIPELINE = 0;

  public static final int PRESSURE_SENSOR = 0;
}
