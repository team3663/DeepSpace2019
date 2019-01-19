/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SS_HolonomicDrivetrain;
import frc.robot.subsystems.SS_Swerve;


public class Robot extends TimedRobot {
  public static final boolean PRACTICE_BOT = true;

  private static SS_Swerve ss_Swerve;
  private static SS_HolonomicDrivetrain ss_HolonomicDrivetrain;
  public static OI m_oi;

  

  @Override
  public void robotInit() {
    m_oi = new OI(this);


    ss_Swerve = new SS_Swerve();
    ss_HolonomicDrivetrain = new SS_HolonomicDrivetrain(14.5, 14.5);

    

		m_oi.registerControls();

    // chooser.addOption("My Auto", new MyAutoCommand());
    

    
  }

  public static OI getOI() {
		return m_oi;
  }

  public static SS_Swerve getSwerve() {
		return ss_Swerve;
  }
  
  public static SS_HolonomicDrivetrain getDrivetrain() {
		return ss_Swerve;///
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    for (int i = 0; i < 4; i++) {
      SmartDashboard.putNumber("Module Angle " + i, ss_Swerve.getSwerveModule(i).getCurrentAngle());
      SmartDashboard.putNumber("Module Pos " + i, (ss_Swerve.getSwerveModule(i).getDriveDistance()));
      SmartDashboard.putNumber("Module Raw Angle " + i, ss_Swerve.getSwerveModule(i).getAngleMotor().getSelectedSensorPosition(0));
      SmartDashboard.putNumber("Module Drive Speed " + i, ss_Swerve.getSwerveModule(i).getDriveMotor().getMotorOutputPercent());
      SmartDashboard.putNumber("Module Current Ticks " + i, ss_Swerve.getSwerveModule(i).getDriveMotor().getSelectedSensorPosition(0));
      SmartDashboard.putNumber("Module Drive % " + i, ss_Swerve.getSwerveModule(i).getDriveMotor().getMotorOutputPercent());
  }


SmartDashboard.putNumber("Drivetrain Angle", ss_Swerve.getGyroAngle());
  }

  @Override
  public void disabledInit() {
    for (int i = 0; i < 4; i++) {
			ss_Swerve.getSwerveModule(i).robotDisabledInit();
		}
  }


  //test comment
  //test comment 2
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
    for (int i = 0; i < 4; i++)
    ss_Swerve.getSwerveModule(i).zeroDistance();
  }
}
