/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;


public class Robot extends TimedRobot {
  public static final boolean PRACTICE_BOT = true;

  private static SS_HolonomicDrivetrain ss_HolonomicDrivetrain;

  private static SS_EndEffector ss_EndEffector;
  private static SS_FrontClimber ss_FrontClimber;
  private static SS_Elevator ss_Elevator;
  private static SS_RearClimber ss_RearClimber;
  private static SS_Vision ss_Vision;
  private static SS_RevColorSensor ss_RevColorSensor;


  public static OI m_oi;

  

  @Override
  public void robotInit() {
    m_oi = new OI(this);

    //some subsystems commented out
    ss_HolonomicDrivetrain = new SS_HolonomicDrivetrain();
    ss_EndEffector = new SS_EndEffector();
    ss_FrontClimber = new SS_FrontClimber();
    ss_Elevator = new SS_Elevator();
    ss_RearClimber = new SS_RearClimber();
    ss_RevColorSensor = new SS_RevColorSensor(Port.kOnboard);
    //ss_Vision = new SS_Vision();


    

		m_oi.registerControls();
    // chooser.addOption("My Auto", new MyAutoCommand());
    

    
  }

  public static OI getOI() {
		return m_oi;
  }


  
  public static SS_HolonomicDrivetrain getDrivetrain() {
		return ss_HolonomicDrivetrain;
  }

  public static SS_EndEffector getEndEffector() {
    return ss_EndEffector;
  }

  public static SS_FrontClimber getFrontClimber() {
    return ss_FrontClimber;
  }

  public static SS_Elevator getElevator() {
    return ss_Elevator;
  }

  public static SS_RearClimber getRearClimber() {
    return ss_RearClimber;
  }

  public static SS_Vision getVision() {
    return ss_Vision;
  }

  @Override
  public void robotPeriodic() {
    //Drivetrain
    for (int i = 0; i < 4; i++) {
      SmartDashboard.putNumber("Module Angle " + i, ss_HolonomicDrivetrain.getSwerveModule(i).getCurrentAngle());
      SmartDashboard.putNumber("Module Distance Pos " + i, (ss_HolonomicDrivetrain.getSwerveModule(i).getDriveDistance()));
      SmartDashboard.putNumber("Module Raw Angle " + i, ss_HolonomicDrivetrain.getSwerveModule(i).getAngleMotor().getSelectedSensorPosition(0));
    }
    SmartDashboard.putNumber("Drivetrain Angle", ss_HolonomicDrivetrain.getGyroAngle());

    //Elevator
    SmartDashboard.putNumber("Master Encoder", ss_Elevator.getMasterEncoder());
    SmartDashboard.putNumber("Slave Encoder", ss_Elevator.getSlaveEncoder());
    SmartDashboard.putNumber("Average Encoder", ss_Elevator.getNEOEncoder());
    SmartDashboard.putNumber("Average Inch", ss_Elevator.getAverageInch());
    SmartDashboard.putBoolean("Top", ss_Elevator.getTopLimitSwitchOutput());
    SmartDashboard.putBoolean("Bottom", ss_Elevator.getBottomLimitSwitchOutput());
    SmartDashboard.putBoolean("At Bottom", ss_Elevator.getAtBottom());

    //climber
    SmartDashboard.putNumber("Rear Encoder", ss_RearClimber.getRawEncoder());
    SmartDashboard.putNumber("Rear Angle", ss_RearClimber.getAngle());
    SmartDashboard.putNumber("Front Encoder", ss_FrontClimber.getRawEncoder());
    SmartDashboard.putNumber("Front Angle", ss_FrontClimber.getAngle());

    //Color sensor
    SmartDashboard.putNumber("White", ss_RevColorSensor.getWhite());
    SmartDashboard.putNumber("Color Proximity", ss_RevColorSensor.getProximity());

    //End Effector
    SmartDashboard.putNumber("End Effector Encoder", ss_EndEffector.getRawAngleEncoder());
    SmartDashboard.putNumber("End Effector Angle", ss_EndEffector.getAngle());
    SmartDashboard.putBoolean("Cargo Present", ss_EndEffector.getCargoPresent());    

    //Gyro
    //TODO: decide how to refrence the gyro properly

    //test values
  }

  @Override
  public void disabledInit() {
    for (int i = 0; i < 4; i++) {
			ss_HolonomicDrivetrain.getSwerveModule(i).robotDisabledInit();
		}
  }


  
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    
  
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    for (int i = 0; i < 4; i++){
    ss_HolonomicDrivetrain.getSwerveModule(i).zeroDistance();
    }
  }
}
