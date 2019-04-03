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
import frc.robot.subsystems.SS_Vision;

public class C_VisionAlign extends Command {
  private SS_Vision vision;
  private SS_Swerve drivetrain;

  private double angleToSnap = 0;

  public C_VisionAlign() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.getVision());
    requires(Robot.getDrivetrain());
    vision = Robot.getVision();
    drivetrain = Robot.getDrivetrain();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    vision.setLightMode(3);
    drivetrain.setFieldOriented(false);
    
    angleToSnap = bestSnapAngle(drivetrain.getGyroAngle());
  }

  private double bestSnapAngle(double pAngle){
    // all directions are based on driver's perspective
    final double CARGOSHIP_CENTER = 0.;
    final double CARGOSHIP_RIGHT_AND_L_ROCKET_CENTER = 270.;
    final double CARGOSHIP_LEFT_AND_R_ROCKET_CENTER = 90.;
    final double R_ROCKET_NEAR = 29.;
    final double R_ROCKET_FAR = 151.;
    final double L_ROCKET_NEAR = 331.;
    final double L_ROCKET_FAR = 209.;
    final double LOAD_STATION = 180.;
    final int NORTH = 0;
    final int NE = 1;
    final int EAST = 2;
    final int SE = 3;
    final int SOUTH = 4;
    final int SW = 5;
    final int WEST = 6;
    final int NW = 7;

    // find the closes 45 degree angle
    // near 0 degree (pointing away from driver)
    int best45 = (int) (pAngle + 22.5) % 360 / 45;    
    if(best45 == NORTH){        // nearest to 0 degree
      return CARGOSHIP_CENTER;
    }
    else if(best45 == NE){      // nearest to 45 degrees
      return R_ROCKET_NEAR;
    }
    else if(best45 == EAST){    // nearest to 90 degrees
      return CARGOSHIP_LEFT_AND_R_ROCKET_CENTER;
    }
    else if(best45 == SE){      // nearest to 135 degrees
      return R_ROCKET_FAR;
    }                           
    else if(best45 == SOUTH){   // nearest to 180 degrees
      return LOAD_STATION;
    }                       
    else if(best45 == SW){      // nearest to 225 degrees
      return L_ROCKET_FAR;
    }
    else if(best45 == WEST){    // nearest to 270 degrees
      return CARGOSHIP_RIGHT_AND_L_ROCKET_CENTER;
    }
    else{         // NW direction, nearest to 315 degrees
      return L_ROCKET_NEAR;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    drivetrain.holonomicDrive(.2, vision.getXOffset()/2, 0); //make Drive forward proportional to target area
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !vision.validTarget() || vision.getTargetArea() > 30 || Robot.getOI().getPrimaryController().getRightBumperButton().get();
    //TODO find the proper area for it to be hitting
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    vision.setLightMode(0);
    drivetrain.setFieldOriented(true);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
