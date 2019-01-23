/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * The zeros for the motor controller CAN identities are placeholders. (duh)
 */
public class RobotMap {
 
  public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 25;
  public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 23;
  public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 34;
  public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 32;
  public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 26;
  public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 24;
  public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 33;
  public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 31;

  public static final int CLIMBER_FRONT_MOTOR_ONE = 0;
  public static final int CLIMBER_FRONT_MOTOR_TWO = 0;
  public static final int CLIMBER_FRONT_TALON = 0;

  public static final int CLIMBER_REAR_MOTOR = 0;

  public static final int CARGO_MOTOR = 0;
  public static final int HATCH_MOTOR = 0;


  public static final int ELEVATOR_MASTER_MOTOR = 28;
  public static final int ELEVATOR_SLAVE_MOTOR = 27;

  public static final int CARRIAGE_LEFT_MOTOR = 21;
  public static final int CARRIAGE_RIGHT_MOTOR = 22;

  public static final int CAMERA_MODE = 0;
  public static final int CAMERA_LIGHT_MODE = 3;
  public static final int CAMERA_PIPELINE = 0;
  
}
