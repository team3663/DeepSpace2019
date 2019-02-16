/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Add your docs here.
 */
public class SS_Gyro extends Subsystem {
  private AHRS navX;
  private double offset;

  public SS_Gyro(){ 
    navX = new AHRS(Port.kUSB);
  }

  public double getPitch() {
    double angle = navX.getPitch();
    if(angle >= 360){
      angle %= 360;
    }
    return angle;
  }

  public double getRawAngle() {
    return navX.getAngle();
  }

  public void hardReset(){
    navX.reset();
  }

  public void softReset(){
    offset = -getPitch();
  }

  public double getOffsetPitch(){
    return getPitch() + offset;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
