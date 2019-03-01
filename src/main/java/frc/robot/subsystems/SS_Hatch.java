/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class SS_Hatch extends Subsystem {

  private DoubleSolenoid hatchPickupSolenoid;
  private DoubleSolenoid hatchExtendSolenoid;

  private DigitalInput hatchSwitch;

  public SS_Hatch(){
    hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_PICKUP_SOLENOID_FORWARD, RobotMap.HATCH_PICKUP_SOLENOID_REVERSE);
    hatchExtendSolenoid = new DoubleSolenoid(RobotMap.HATCH_EXTEND_SOLENOID_FORWARD, RobotMap.HATCH_EXTEND_SOLENOID_REVERSE);

    //hatchSwitch = new DigitalInput(RobotMap.HATCH_SWITCH);
  }

  
  public void setHatchClosed(boolean state) {
    if(state){
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kForward);
    }else{
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void extendHatchPickup(boolean state) {
    if(state){
      hatchExtendSolenoid.set(DoubleSolenoid.Value.kForward);
    } else {
      hatchExtendSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

   /**
   * @return the hatchSwitch
   */
  public DigitalInput getHatchSwitch() {
   return hatchSwitch;
  }
  
  public boolean isPresent(){
    return !hatchSwitch.get();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
