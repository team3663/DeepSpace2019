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
 * 
 * CAN Bus sequence:
 * starting from RoboRIO ->
 * 14, SparkMax, EF Intake wheels (green), IWGRN
 * 11, SparkMax, EF Rotation, EFROT
 * Peumatic Control Module, PCM
 * 5, TalonSRX, Swerve Angle Motor FrontRight, SAFR
 * 15 SparkMax, Intake wheels (blue), IWBLU
 * 1, SparkMax, Swerve Drive Motor FrontRight, SDFR
 * 6, TalonSRX, Swerve Angle Motor FrontRight, SABR
 * 2, SparkMax, Swerve Drive Motor, BackRight, SDBR
 * 9, SparkMax, Elevator Master, ELEM
 * 13, SparkMax, Back Climber, BCL
 * 10, SparkMax, Elevator Slave, ELES
 * 3, SparkMax, Swerve Drive Motor, BackLeft, SDBL
 * 7, TalonSRX, Swerve Angle Motor, BackLeft, SABL
 * 8, TalonSRX, Swerve Angle Motor, FrontLeft, SAFL
 * 4, SparkMax, Swerve Drive Motor, FrontLeft, SDFL
 * 12, SparkMax, Front Climber, FCL
 * Power Distribution Panel, PDP
 */
public class RobotMap {
 
public static final int getDriveMotor(int module){
    int motors[] = {
      4,1,
      3,2
    };
    return motors[module];
  }
  public static final int getAngleMotor(int module){
    int motors[] = {
      8,5,
      7,6
    };
    return motors[module];
  }

  public static final int CLIMBER_FRONT_MOTOR = 12;
  public static final int CLIMBER_FRONT_CARGO_INTAKE = 15;

  public static final int CLIMBER_REAR_MOTOR = 13;

  public static final int CARGO_MOTOR = 14;
  public static final int ENDEFFECTOR_ANGLE_MOTOR = 11;

  public static final int ELEVATOR_MASTER_MOTOR = 9;
  public static final int ELEVATOR_SLAVE_MOTOR = 10;


  //Camera
  public static final int CAMERA_MODE = 0;
  public static final int CAMERA_LIGHT_MODE = 3;
  public static final int CAMERA_PIPELINE = 0;

  //Analog
  public static final int PRESSURE_SENSOR = 0;

  // DIO: limit switches
  public static final int CARGO_SWITCH = 0;       // cargo optical switch
  public static final int ANGLE_RESET_SWITCH = 2; // EF angle reset

  public static final int ELEVATOR_BOTTOM_LIMIT_SWITCH = 3; // elevator stage 1
  public static final int ELEVATOR_TOP_LIMIT_SWITCH = 1;    // elevator stage 2

  public static final int REAR_CLIMBER_LIMIT_SWITCH = 4;    // rear climber
  public static final int FRONT_CLIMBER_LIMIT_SWITCH = 5;   // front climber
  
  public static final int HATCH_SWITCH = 6;         // unused hatch switch (replaced by optical)
  public static final int HATCH_OPTICAL_SWITCH = 8; // hatch optical switch
  public static final int HATCH_PRESSED_SWITCH = 7; // hatch pressed

  //Pneumatics
  public static final int KICKSTAND_SOLENOID_FORWARD = 0;
  public static final int KICKSTAND_SOLENOID_REVERSE = 1;

  public static final int HATCH_PICKUP_SOLENOID_FORWARD = 2;  
  public static final int HATCH_PICKUP_SOLENOID_REVERSE = 3;
  public static final int HATCH_EXTEND_SOLENOID_FORWARD = 4;
  public static final int HATCH_EXTEND_SOLENOID_REVERSE = 5;
}

/* 
  CAN Bus sequence
  ------------------------------------------------
  RoboRIO ->
  14, SparkMax, EF Intake wheels (green), IWGRN
  11, SparkMax, EF Rotation, EFROT
  Peumatic Control Module, PCM
  5, TalonSRX, Swerve Angle Motor FrontRight, SAFR
  15 SparkMax, Intake wheels (blue), IWBLU
  1, SparkMax, Swerve Drive Motor FrontRight, SDFR
  6, TalonSRX, Swerve Angle Motor FrontRight, SABR
  2, SparkMax, Swerve Drive Motor, BackRight, SDBR
  9, SparkMax, Elevator Master, ELEM
  13, SparkMax, Back Climber, BCL
  10, SparkMax, Elevator Slave, ELES
  3, SparkMax, Swerve Drive Motor, BackLeft, SDBL
  7, TalonSRX, Swerve Angle Motor, BackLeft, SABL
  8, TalonSRX, Swerve Angle Motor, FrontLeft, SAFL
  4, SparkMax, Swerve Drive Motor, FrontLeft, SDFL
  12, SparkMax, Front Climber, FCL
  Power Distribution Panel, PDP

*/
