/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.subsystems.SS_FrontClimber;
import frc.robot.subsystems.SS_RearClimber;

public class C_ClimbPlusDown extends Command {

  private static final double FRONT_CLIMBER_RADIUS = 16.5;
  private static final double REAR_LONG_SIDE_LENGTH = 27;
  private static final double REAR_SHORT_SIDE_LENGTH = 4;
  private static final double REAR_CLIMBER_RADIUS = Math.sqrt(Math.pow(REAR_LONG_SIDE_LENGTH, 2) + Math.pow(REAR_SHORT_SIDE_LENGTH, 2));
  private static final double ABSOLUTE_PITCH_LIMIT = 15;
  private static final double WOAH_THERE = .45;
  private final double FRONT_CLIMBER_SCALAR = (Robot.getRearClimber().getGearRatio() / Robot.getFrontClimber().getGearRatio())
  * (REAR_CLIMBER_RADIUS / FRONT_CLIMBER_RADIUS);

  private SS_FrontClimber ss_FrontClimber;
  private SS_RearClimber ss_RearClimber;

  public C_ClimbPlusDown() {
    requires(Robot.getFrontClimber());
    requires(Robot.getRearClimber());
    requires(Robot.getDrivetrain());
    requires(Robot.getBall());

    ss_FrontClimber = Robot.getFrontClimber();
    ss_RearClimber = Robot.getRearClimber();
  }

  @Override
  protected void initialize() {
    new C_CrabDrive().start();

    Robot.getDrivetrain().softReset();
    Robot.getFrontClimber().setBrakeMode();
    Robot.getRearClimber().setBrakeMode();
    Robot.getBall().setBrakeMode();
  }

  @Override
  protected void execute() {

    double joystickInput = Robot.getOI().getSecondaryController().getLeftYValue();
    double pitch = Robot.getDrivetrain().getPitch();

        //balanceed climb
        if (Math.abs(pitch) <= ABSOLUTE_PITCH_LIMIT) {
          ss_RearClimber.setSpeed(-joystickInput * WOAH_THERE);
          ss_FrontClimber.setSpeed((joystickInput + calcAdjustmentSpeed(pitch)) * FRONT_CLIMBER_SCALAR * WOAH_THERE);
        }
        //compensated climb
        else {
          //tilted back
          if(pitch > 0) {
            ss_RearClimber.setSpeed(-joystickInput * WOAH_THERE);
            ss_FrontClimber.goToDegree(Robot.getFrontClimber().getAngle());
          } 
          //tilted forward
          else {
            ss_RearClimber.goToDegree(Robot.getRearClimber().getAngle());
            ss_FrontClimber.setSpeed(joystickInput * WOAH_THERE);
          }
        }
  }

  private double calcAdjustmentSpeed(double pickles) {
    double adjSpeed = pickles / ABSOLUTE_PITCH_LIMIT;
    return -adjSpeed;

  }


  protected boolean isFinished() {
    return ss_FrontClimber.getAngle() >= 180 || !Robot.getOI().getSecondaryController().getStartButton().get();
  }
  
  @Override
  protected void end() {
    ss_FrontClimber.goToDegree(ss_FrontClimber.getAngle());
    ss_RearClimber.goToDegree(ss_RearClimber.getAngle());
  }

  @Override
  protected void interrupted() {
    end();
  }
}
