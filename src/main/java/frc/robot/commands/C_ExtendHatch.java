/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_ExtendHatch extends Command {

  private boolean state;

  /**
   * extends the hatch pickup
   * 
   * @param state extend (true) retract (false)
   */
  public C_ExtendHatch(boolean state) {
    requires(Robot.getHatch());
    this.state = state;
  }

  @Override
  protected void execute() {
    Robot.getHatch().extendHatchPickup(state);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}
