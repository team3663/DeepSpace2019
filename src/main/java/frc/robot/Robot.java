/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.commands.C_StartOrchestra;
import frc.robot.commands.test_commands.*;


public class Robot extends TimedRobot {
  public static final boolean PRACTICE_BOT = true;

  private static SS_HolonomicDrivetrain ss_HolonomicDrivetrain;

  private static SS_EndEffector ss_EndEffector;
  private static SS_EndEffectorAngle ss_EndEffectorAngle;
  private static SS_FrontClimber ss_FrontClimber;
  private static SS_Elevator ss_Elevator;
  private static SS_RearClimber ss_RearClimber;
  private static SS_Vision ss_Vision;
  private static SS_ColorSensor ss_ColorSensor;
  private static SS_PressureSensor ss_PressureSensor;

  private ShuffleboardTab driver;

  public static OI m_oi;

  

  @Override
  public void robotInit() {
    m_oi = new OI(this);

    //some subsystems commented out
    ss_HolonomicDrivetrain = new SS_HolonomicDrivetrain();
    ss_EndEffector = new SS_EndEffector();
    ss_EndEffectorAngle = new SS_EndEffectorAngle();
    ss_FrontClimber = new SS_FrontClimber();
    ss_Elevator = new SS_Elevator();
    ss_RearClimber = new SS_RearClimber();
    ss_ColorSensor = new SS_ColorSensor();
    ss_PressureSensor = new SS_PressureSensor();
    ss_Vision = new SS_Vision();


		m_oi.registerControls();
    // chooser.addOption("My Auto", new MyAutoCommand());
    
    driver = Shuffleboard.getTab("driver");

    //show cameras on the driver tab of shuffleboard
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(320, 240);
    camera.setPixelFormat(PixelFormat.kMJPEG);
    driver.add(camera).withWidget(BuiltInWidgets.kCameraStream);

    HttpCamera limelightCamera = new HttpCamera("limelight", "http://10.36.63.11:5800" );
    driver.add(limelightCamera).withWidget(BuiltInWidgets.kCameraStream).withProperties(Map.of("Show controls", false)); 
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

  public static SS_EndEffectorAngle getEndEffectorAngle() {
    return ss_EndEffectorAngle;
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
  public static SS_PressureSensor getPressureSensor(){
    return ss_PressureSensor;
  }
  public static SS_ColorSensor getColorSensor(){
    return ss_ColorSensor;
  }

  @Override
  public void robotPeriodic() {

    for (int i = 0; i < 4; i++) {
      SmartDashboard.putNumber("Module Angle " + i, ss_HolonomicDrivetrain.getSwerveModule(i).getCurrentAngle());
    }
    //Gyro
    //TODO: decide how to refrence the gyro properly
    SmartDashboard.putNumber("Selected Level", ss_Elevator.getSelectedLevel());
    SmartDashboard.putNumber("Average Inch", ss_Elevator.getAverageInch());
    SmartDashboard.putBoolean("At Bottom", ss_Elevator.getAtBottom());



    SmartDashboard.putNumber("Air Pressure", ss_PressureSensor.getPressure());

    SmartDashboard.putNumber("End Effector Angle", ss_EndEffectorAngle.getAngle());
    SmartDashboard.putBoolean("Cargo Present", ss_EndEffector.getCargoPresent());    
    
    SmartDashboard.putNumber("Rear RawEncoder", ss_RearClimber.getRawEncoder());
    SmartDashboard.putNumber("Rear Encoder", ss_RearClimber.getEncoder());
    SmartDashboard.putNumber("Front Encoder", ss_FrontClimber.getRawEncoder());
    SmartDashboard.putNumber("Front Angle", ss_FrontClimber.getAngle());

    SmartDashboard.putNumber("End Effector Angle", ss_EndEffectorAngle.getAngle());
    SmartDashboard.putNumber("End Effector Encoder", ss_EndEffectorAngle.getRawEncoder());


    //tests
    
   
  }

  
  @Override
  public void testPeriodic() {
    //Drivetrain
    for (int i = 0; i < 4; i++) {
      SmartDashboard.putNumber("Module Distance Pos " + i, (ss_HolonomicDrivetrain.getSwerveModule(i).getDrivePos()));
      SmartDashboard.putNumber("Module Raw Angle " + i, ss_HolonomicDrivetrain.getSwerveModule(i).getAngleMotor().getSelectedSensorPosition(0));
    }
    SmartDashboard.putNumber("Drivetrain Angle", ss_HolonomicDrivetrain.getGyroAngle());

    //Elevator
    SmartDashboard.putNumber("Master Encoder", ss_Elevator.getMasterEncoder());
    SmartDashboard.putNumber("Slave Encoder", ss_Elevator.getSlaveEncoder());
    SmartDashboard.putNumber("Average Encoder", ss_Elevator.getAverageEncoder());
    SmartDashboard.putBoolean("Top", ss_Elevator.getTopLimitSwitchOutput());
    SmartDashboard.putBoolean("Bottom", ss_Elevator.getBottomLimitSwitchOutput());

    //climber


    //Color sensor
    SmartDashboard.putNumber("White", ss_ColorSensor.getWhite());
    SmartDashboard.putNumber("Color Proximity", ss_ColorSensor.getProximity());

  
    //End Effector
    SmartDashboard.putNumber("End Effector Encoder", ss_EndEffectorAngle.getRawEncoder());




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
    for (int i = 0; i < 4; i++){
    ss_HolonomicDrivetrain.getSwerveModule(i).zeroDistance();
    }
    new C_StartOrchestra().start();
  }
}
