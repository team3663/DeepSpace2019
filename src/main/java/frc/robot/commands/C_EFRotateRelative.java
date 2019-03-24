/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Side;

public class C_EFRotateRelative extends Command {
  private Side side;
  private double absAdjustmentAngle;
  private double adjustment; 

  /**
   * rotates relative to the default flip angles
   * 
   * @param side check wether it thinks it is before running
   * @param absAdjustmentAngle angle to adjust to
   */
  public C_EFRotateRelative(Side sdie, double absAdjustmentAngle) {
   requires(Robot.getEndEffectorAngle());
   this.side = side;
   this.absAdjustmentAngle = Math.abs(absAdjustmentAngle);
  }

  @Override
  protected void initialize() {
    if(side == Side.kFront){
      this.adjustment = Robot.getEndEffectorAngle().getSafeFlipAngle(side) - absAdjustmentAngle;

    }
    else{
      this.adjustment = Robot.getEndEffectorAngle().getSafeFlipAngle(side) + absAdjustmentAngle;

    }
  }
  @Override
  protected void execute() {

    if(Robot.getEndEffectorAngle().isFlipped(side) && !Robot.getEndEffectorAngle().getFlipFailed()){
      Robot.getEndEffectorAngle().goToDegree(adjustment);
    }
  }

  @Override
  protected boolean isFinished() {
    System.out.println(" C EF ROTATE RELTAVITE IS RUNNING");
    return Robot.getEndEffectorAngle().atTarget(adjustment) || !Robot.getEndEffectorAngle().isFlipped(side) || Robot.getEndEffectorAngle().getFlipFailed();
  }
}
