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
  private DigitalInput hatchOpticalSwitch;
  private DigitalInput hatchPressedSwitch;

  private boolean hatchMode = false;

  public SS_Hatch(){
    hatchPickupSolenoid = new DoubleSolenoid(RobotMap.HATCH_PICKUP_SOLENOID_FORWARD, RobotMap.HATCH_PICKUP_SOLENOID_REVERSE);
    hatchExtendSolenoid = new DoubleSolenoid(RobotMap.HATCH_EXTEND_SOLENOID_FORWARD, RobotMap.HATCH_EXTEND_SOLENOID_REVERSE);

    //TODO put back
    hatchSwitch = new DigitalInput(RobotMap.HATCH_SWITCH);
    hatchPressedSwitch = new DigitalInput(RobotMap.HATCH_PRESSED_SWITCH);
    hatchOpticalSwitch = new DigitalInput(RobotMap.HATCH_OPTICAL_SWITCH);
  }

  
  public void setHatchClosed(boolean state) {
    if(state){
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kReverse);
    }else{
      hatchPickupSolenoid.set(DoubleSolenoid.Value.kForward);
    }
  }

  public void extendHatchPickup(boolean state) {
    if(state){
      hatchExtendSolenoid.set(DoubleSolenoid.Value.kReverse);
    } else {
      hatchExtendSolenoid.set(DoubleSolenoid.Value.kForward);
    }
  }

   /**
   * @return the hatchSwitch
   */
  public DigitalInput getHatchSwitch() {
   return hatchSwitch;
  }
  
  public boolean isPresent(){
    return isPhysicalPresent() || isOpticalPresent();
  }

  public boolean isPhysicalPresent(){
    return !hatchSwitch.get();
  }

  public DigitalInput getHatchOptitcalSwitch(){
    return hatchOpticalSwitch;
  }

  public boolean isOpticalPresent(){
    return hatchOpticalSwitch.get();
  }

  public DigitalInput getPressedSwitch(){
    return hatchPressedSwitch;
  }

  public boolean isPressed(){
    return !hatchPressedSwitch.get();
  }

  
  public boolean isHatchMode(){
    return hatchMode;
  }

  public void setHatchMode(boolean hatchMode){
    this.hatchMode = hatchMode;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
