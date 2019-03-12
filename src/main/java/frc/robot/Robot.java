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
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.util.Mode;
import frc.robot.util.Side;
import frc.robot.commands.C_StartOrchestra;

public class Robot extends TimedRobot {
  public static final boolean PRACTICE_BOT = false;

  private static SS_Swerve ss_Swerve;

  private static SS_EndEffectorAngle ss_EndEffectorAngle;
  private static SS_FrontClimber ss_FrontClimber;
  private static SS_Elevator ss_Elevator;
  private static SS_RearClimber ss_RearClimber;
  private static SS_Vision ss_Vision;
  private static SS_ColorSensor ss_ColorSensor;
  private static SS_PressureSensor ss_PressureSensor;
  private static SS_Ball ss_Ball;
  private static SS_Hatch ss_Hatch;

  private ShuffleboardTab driver;

  public static OI m_oi;

  @Override
  public void robotInit() {
    m_oi = new OI(this);

    //some subsystems commented out
    ss_Swerve = new SS_Swerve();
    ss_EndEffectorAngle = new SS_EndEffectorAngle();
    ss_FrontClimber = new SS_FrontClimber();
    ss_Elevator = new SS_Elevator();
    ss_RearClimber = new SS_RearClimber();
    ss_ColorSensor = new SS_ColorSensor();
    ss_PressureSensor = new SS_PressureSensor();
    ss_Vision = new SS_Vision();
    ss_Ball = new SS_Ball();
    ss_Hatch = new SS_Hatch();

		m_oi.registerControls();    
    driver = Shuffleboard.getTab("driver");

    //show cameras on the driver tab of shuffleboard
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(320, 240);
    camera.setPixelFormat(PixelFormat.kMJPEG);
    
    driver.add(camera).withWidget(BuiltInWidgets.kCameraStream).withProperties(Map.of("Rotation", "NONE")); //HALF

    HttpCamera limelightCamera = new HttpCamera("limelight", "http://10.36.63.11:5800" );
    driver.add(limelightCamera).withWidget(BuiltInWidgets.kCameraStream).withProperties(Map.of("Show controls", false)); 
   }

  public static OI getOI() {
		return m_oi;
  }

  
  public static SS_Hatch getHatch(){
    return ss_Hatch;
  }
  
  public static SS_Swerve getDrivetrain() {
		return ss_Swerve;
  }

  public static SS_Ball getBall() {
    return ss_Ball;
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
      SmartDashboard.putNumber("Module Angle " + i, ss_Swerve.getSwerveModule(i).getCurrentAngle());
    }
    //Gyro
    SmartDashboard.putNumber("Gyro angle", ss_Swerve.getGyroAngle());
    SmartDashboard.putNumber("Gyro pitch", ss_Swerve.getNavX().getPitch());

    SmartDashboard.putNumber("Selected Level", ss_Elevator.getSelectedLevel());
    SmartDashboard.putBoolean("Selected Side Front", ss_Elevator.getSelectedSide() == Side.kFront);

    SmartDashboard.putNumber("Average Inch", ss_Elevator.getAverageInch());
    SmartDashboard.putBoolean("At Bottom", ss_Elevator.getAtBottom());

    SmartDashboard.putNumber("Air Pressure", ss_PressureSensor.getPressure());

    SmartDashboard.putNumber("slave Volt", ss_Elevator.getSVoltage());
    SmartDashboard.putNumber("master Volt", ss_Elevator.getMVoltage());//TODO 

    SmartDashboard.putBoolean("Hatch Mode", ss_Hatch.getMode() == Mode.kHatch);
    SmartDashboard.putNumber("End Effector Angle", ss_EndEffectorAngle.getAngle());
    SmartDashboard.putBoolean("Cargo Present", ss_Ball.isPresent());
    SmartDashboard.putBoolean("Angle Switch", ss_EndEffectorAngle.isReset());    
    SmartDashboard.putBoolean("Hatch Present", ss_Hatch.isPresent());    
    SmartDashboard.putBoolean("ef init", ss_EndEffectorAngle.isInitialized());
    SmartDashboard.putBoolean("ele init", ss_Elevator.isInitialized());    
    SmartDashboard.putBoolean("fclimb init", ss_FrontClimber.isInitialized());    
    SmartDashboard.putBoolean("rclimb init", ss_RearClimber.isInitilized());   
    
    SmartDashboard.putNumber("Rear Angle", ss_RearClimber.getAngle());
    SmartDashboard.putNumber("Front Angle", ss_FrontClimber.getAngle());

    SmartDashboard.putNumber("End Effector Angle", ss_EndEffectorAngle.getAngle());
    SmartDashboard.putNumber("End Effector Encoder", ss_EndEffectorAngle.getRawEncoder());

    
    //tests
    
   
  }

  
  @Override
  public void testPeriodic() {
    //Drivetrain
    for (int i = 0; i < 4; i++) {
      SmartDashboard.putNumber("Module Distance Pos " + i, (ss_Swerve.getSwerveModule(i).getDrivePos()));
      SmartDashboard.putNumber("Module Raw Angle " + i, ss_Swerve.getSwerveModule(i).getAngleMotor().getSelectedSensorPosition(0));
    }
    SmartDashboard.putNumber("Drivetrain Angle", ss_Swerve.getGyroAngle());

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
			ss_Swerve.getSwerveModule(i).robotDisabledInit();
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


  @Override
  public void teleopInit() {
    for (int i = 0; i < 4; i++){
    ss_Swerve.getSwerveModule(i).zeroDistance();
    }
    getVision().setLightMode(0);
    new C_StartOrchestra().start();
  }

  @Override
  public void autonomousInit() {
    teleopInit();
  }

  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
  }
  
}
