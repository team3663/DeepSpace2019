  /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class SS_RevAirPressureSensor extends Subsystem {

  //set to normalized voltage = Vo / (0.004 * Po + 0.1)
  private static double SUPPLY_VOLTAGE = 5;
  private AnalogInput pressureSensor;

  public SS_RevAirPressureSensor() {
    pressureSensor = new AnalogInput(RobotMap.PRESSURE_SENSOR);
  }

  /**
   * finds the pressure
   * @return the pressure (PSI) that the sensor reads
   */
  public double getPressure() {
    double voltageOut = pressureSensor.getVoltage();
    return 250 * voltageOut / SUPPLY_VOLTAGE - 25;
  }

  @Override
  public void initDefaultCommand() {}
}
