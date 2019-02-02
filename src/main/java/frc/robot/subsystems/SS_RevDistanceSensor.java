/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class SS_RevDistanceSensor extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private I2C distanceSensor;

  public SS_RevDistanceSensor(I2C.Port port) {
    distanceSensor = new I2C(port, 0X52);
  }

  public double getDistance() {
    //distanceSensor.read(registerAddress, count, buffer);
    return 0;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
