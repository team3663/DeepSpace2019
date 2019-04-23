/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_Swerve;
import frc.robot.util.PIDCont;

public class C_DriveDistance extends Command {

  private PIDCont PIDController;

  private double maxSpeed;
  private double target;
  private SS_Swerve drivetrain;

  public C_DriveDistance(double target, double maxSpeed) {

    this.maxSpeed = maxSpeed;
    this.target = target;
    requires(Robot.getDrivetrain());
    this.drivetrain = Robot.getDrivetrain();

  }

  //this only drives forward robot relative
  @Override
  protected void initialize() {
    for(int i=0; i < 4; i++){
      drivetrain.getSwerveModule(i).setTargetAngle(0);
    }
  }
  @Override
  protected void execute() {
    for(int i=0; i < 4; i++){
      drivetrain.getSwerveModule(i).setTargetDistance(Robot.getDrivetrain().getSwerveModule(i).inchesToEncoderTicks(target));
    }   
  }

  @Override
  protected boolean isFinished() {
    double averageInch = 0;
    for(int i=0; i < 4; i++){
      averageInch += drivetrain.getSwerveModule(i).getDriveInch();
    }
    averageInch /= 4;
    return averageInch > target - .5;
  }

  @Override
  protected void end() {
    Robot.getDrivetrain().holonomicDrive(0,0,0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
