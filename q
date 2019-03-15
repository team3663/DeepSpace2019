[33mcommit 28804ed24b1ece5c331880c16db963363c561225[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m, [m[1;31morigin/master[m[33m, [m[1;31morigin/HEAD[m[33m)[m
Author: RandomPotato26 <random.potato26@gmail.com>
Date:   Sun Mar 3 21:37:41 2019 -0800

    add comments

[1mdiff --git a/src/main/java/frc/robot/commands/C_AdjustFrontClimberAngle.java b/src/main/java/frc/robot/commands/C_AdjustFrontClimberAngle.java[m
[1mindex bd6f77d..ef2efd4 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_AdjustFrontClimberAngle.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_AdjustFrontClimberAngle.java[m
[36m@@ -13,6 +13,9 @@[m [mimport frc.robot.Robot;[m
 public class C_AdjustFrontClimberAngle extends Command {[m
   private static final double DEADBAND = 0.1;[m
 [m
[32m+[m[32m  /**[m
[32m+[m[32m   * makes small adjustments to the front climber angle[m
[32m+[m[32m   */[m
   public C_AdjustFrontClimberAngle() {[m
     requires(Robot.getFrontClimber());[m
   }[m
[36m@@ -21,6 +24,7 @@[m [mpublic class C_AdjustFrontClimberAngle extends Command {[m
   protected void initialize() {[m
   }[m
 [m
[32m+[m[32m  //TODO make while its held then revert back to whatever it was[m
   private double ignoreDeadband(double input){[m
     if(Math.abs(input) < DEADBAND){[m
       return 0;[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_CrabDrive.java b/src/main/java/frc/robot/commands/C_CrabDrive.java[m
[1mindex c21bda7..6cc1981 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_CrabDrive.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_CrabDrive.java[m
[36m@@ -11,37 +11,23 @@[m [mimport edu.wpi.first.wpilibj.command.Command;[m
 import frc.robot.Robot;[m
 [m
 public class C_CrabDrive extends Command {[m
[32m+[m
[32m+[m[32m  /**[m
[32m+[m[32m   * starts trigger controll of fclimb intake for climbing[m
[32m+[m[32m   *[m[41m [m
[32m+[m[32m   */[m
   public C_CrabDrive() {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
     requires(Robot.getBall());[m
   }[m
 [m
[31m-  // Called just before this Command runs the first time[m
[31m-  @Override[m
[31m-  protected void initialize() {[m
[31m-  }[m
[31m-[m
[31m-  // Called repeatedly when this Command is scheduled to run[m
   @Override[m
   protected void execute() {[m
     Robot.getBall().setCargoIntakeSpeed(-Robot.getOI().getSecondaryController().getRightTriggerValue());[m
   }[m
 [m
[31m-  // Make this return true when this Command no longer needs to run execute()[m
   @Override[m
   protected boolean isFinished() {[m
     return false;[m
   }[m
 [m
[31m-  // Called once after isFinished returns true[m
[31m-  @Override[m
[31m-  protected void end() {[m
[31m-  }[m
[31m-[m
[31m-  // Called when another command which requires one or more of the same[m
[31m-  // subsystems is scheduled to run[m
[31m-  @Override[m
[31m-  protected void interrupted() {[m
[31m-  }[m
 }[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_EFRestart.java b/src/main/java/frc/robot/commands/C_EFRestart.java[m
[1mindex 32d682f..4d9b38e 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_EFRestart.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_EFRestart.java[m
[36m@@ -12,21 +12,16 @@[m [mimport edu.wpi.first.wpilibj.command.Command;[m
 import frc.robot.Robot;[m
 [m
 public class C_EFRestart extends Command {[m
[31m-  [m
[32m+[m[32m  /**[m
[32m+[m[32m   * initilizes (restarts the initilization) the end effector after placing a hatch, if started with a hatch[m
[32m+[m[32m   */[m
   public C_EFRestart() {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
[32m+[m
     requires(Robot.getEndEffectorAngle());[m
     requires(Robot.getElevator());[m
     requires(Robot.getHatch());[m
   }[m
 [m
[31m-  // Called just before this Command runs the first time[m
[31m-  @Override[m
[31m-  protected void initialize() {[m
[31m-  }[m
[31m-[m
[31m-  // Called repeatedly when this Command is scheduled to run[m
   @Override[m
   protected void execute() {[m
     if(!Robot.getEndEffectorAngle().isInitialized()){[m
[36m@@ -48,20 +43,9 @@[m [mpublic class C_EFRestart extends Command {[m
     }[m
   }[m
 [m
[31m-  // Make this return true when this Command no longer needs to run execute()[m
   @Override[m
   protected boolean isFinished() {[m
     return Robot.getEndEffectorAngle().isReset() || Robot.getHatch().isPresent();[m
   }[m
 [m
[31m-  // Called once after isFinished returns true[m
[31m-  @Override[m
[31m-  protected void end() {[m
[31m-  }[m
[31m-[m
[31m-  // Called when another command which requires one or more of the same[m
[31m-  // subsystems is scheduled to run[m
[31m-  @Override[m
[31m-  protected void interrupted() {[m
[31m-  }[m
 }[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_EFRotateRelative.java b/src/main/java/frc/robot/commands/C_EFRotateRelative.java[m
[1mindex fa054a1..af3f592 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_EFRotateRelative.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_EFRotateRelative.java[m
[36m@@ -14,13 +14,19 @@[m [mpublic class C_EFRotateRelative extends Command {[m
   private boolean isFront;[m
   private double absAdjustmentAngle;[m
   private double adjustment; [m
[32m+[m
[32m+[m[32m  /**[m
[32m+[m[32m   * rotates relative to the default flip angles[m
[32m+[m[32m   *[m[41m [m
[32m+[m[32m   * @param isFront check wether it thinks it is before running[m
[32m+[m[32m   * @param absAdjustmentAngle angle to adjust to[m
[32m+[m[32m   */[m
   public C_EFRotateRelative(boolean isFront, double absAdjustmentAngle) {[m
    requires(Robot.getEndEffectorAngle());[m
    this.isFront = isFront;[m
    this.absAdjustmentAngle = Math.abs(absAdjustmentAngle);[m
   }[m
 [m
[31m-  // Called just before this Command runs the first time[m
   @Override[m
   protected void initialize() {[m
     if(isFront){[m
[36m@@ -32,8 +38,6 @@[m [mpublic class C_EFRotateRelative extends Command {[m
 [m
     }[m
   }[m
[31m-[m
[31m-  // Called repeatedly when this Command is scheduled to run[m
   @Override[m
   protected void execute() {[m
 [m
[36m@@ -42,21 +46,9 @@[m [mpublic class C_EFRotateRelative extends Command {[m
     }[m
   }[m
 [m
[31m-  // Make this return true when this Command no longer needs to run execute()[m
   @Override[m
   protected boolean isFinished() {[m
     System.out.println(" C EF ROTATE RELTAVITE IS RUNNING");[m
     return Robot.getEndEffectorAngle().atTarget(adjustment) || !Robot.getEndEffectorAngle().isFlipped(isFront);[m
   }[m
[31m-[m
[31m-  // Called once after isFinished returns true[m
[31m-  @Override[m
[31m-  protected void end() {[m
[31m-  }[m
[31m-[m
[31m-  // Called when another command which requires one or more of the same[m
[31m-  // subsystems is scheduled to run[m
[31m-  @Override[m
[31m-  protected void interrupted() {[m
[31m-  }[m
 }[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_EFToAngle.java b/src/main/java/frc/robot/commands/C_EFToAngle.java[m
[1mindex 7177d04..4957219 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_EFToAngle.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_EFToAngle.java[m
[36m@@ -16,6 +16,13 @@[m [mpublic class C_EFToAngle extends Command {[m
   private double forwardAngleLimit;[m
   private double backwardAngleLimit;[m
 [m
[32m+[m[32m  /**[m
[32m+[m[32m   *[m[41m [m
[32m+[m[32m   * goes to end effector angle[m
[32m+[m[32m   *[m[41m [m
[32m+[m[32m   * @param angle target angle[m
[32m+[m[32m   */[m
[32m+[m
   public  C_EFToAngle(double angle) {[m
     requires(Robot.getEndEffectorAngle());[m
     this.angle = angle;[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_ElevatorHold.java b/src/main/java/frc/robot/commands/C_ElevatorHold.java[m
[1mindex a538eec..8a8aafd 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_ElevatorHold.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_ElevatorHold.java[m
[36m@@ -13,25 +13,23 @@[m [mimport frc.robot.Robot;[m
 public class C_ElevatorHold extends Command {[m
   private double holdPos;[m
 [m
[32m+[m[32m  /**[m
[32m+[m[32m   * grabs the current encoder position and sets the PID in an infinate loop until interrupted[m
[32m+[m[32m   */[m
   public C_ElevatorHold() {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
     requires(Robot.getElevator());[m
   }[m
 [m
[31m-  // Called just before this Command runs the first time[m
   @Override[m
   protected void initialize() {[m
     holdPos = Robot.getElevator().getAverageEncoder();[m
   }[m
 [m
[31m-  // Called repeatedly when this Command is scheduled to run[m
   @Override[m
   protected void execute() {[m
     Robot.getElevator().goToPos(holdPos);[m
   }[m
 [m
[31m-  // Make this return true when this Command no longer needs to run execute()[m
   @Override[m
   protected boolean isFinished() {[m
     return false;[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_EndEffectorHold.java b/src/main/java/frc/robot/commands/C_EndEffectorHold.java[m
[1mindex d780521..025c027 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_EndEffectorHold.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_EndEffectorHold.java[m
[36m@@ -12,15 +12,17 @@[m [mimport frc.robot.Robot;[m
 [m
 public class C_EndEffectorHold extends Command {[m
   private double holdAngle;[m
[32m+[m[32m  /**[m
[32m+[m[32m   * grabs the current encoder position and sets the PID in an infinate loop until interrupted[m
[32m+[m[32m   */[m
   public C_EndEffectorHold() {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
     requires(Robot.getEndEffectorAngle());[m
   }[m
 [m
   // Called just before this Command runs the first time[m
   @Override[m
   protected void initialize() {[m
[32m+[m[32m    isInterruptible();[m
     holdAngle = Robot.getEndEffectorAngle().getAngle();[m
   }[m
 [m
[36m@@ -36,8 +38,8 @@[m [mpublic class C_EndEffectorHold extends Command {[m
     return false;[m
   }[m
 [m
[31m-  // Called once after isFinished returns true[m
   @Override[m
[31m-  protected void end() {[m
[32m+[m[32m  public synchronized boolean isInterruptible() {[m
[32m+[m[32m    return true;[m
   }[m
 }[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_ExtendHatch.java b/src/main/java/frc/robot/commands/C_ExtendHatch.java[m
[1mindex 0a84768..b4c400b 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_ExtendHatch.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_ExtendHatch.java[m
[36m@@ -14,6 +14,11 @@[m [mpublic class C_ExtendHatch extends Command {[m
 [m
   private boolean state;[m
 [m
[32m+[m[32m  /**[m
[32m+[m[32m   * extends the hatch pickup[m
[32m+[m[32m   *[m[41m [m
[32m+[m[32m   * @param state extend (true) retract (false)[m
[32m+[m[32m   */[m
   public C_ExtendHatch(boolean state) {[m
     requires(Robot.getHatch());[m
     this.state = state;[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_Flip.java b/src/main/java/frc/robot/commands/C_Flip.java[m
[1mindex d56c68c..3249c9b 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_Flip.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_Flip.java[m
[36m@@ -16,9 +16,12 @@[m [mpublic class C_Flip extends Command {[m
   private double defaultElevatorEndTop = Robot.getElevator().getSafeFlipTop() - 1;[m
   private double defaultElevatorEndBot = Robot.getElevator().getSafeFlipBot() + .5;[m
 [m
[31m-[m
[31m-  public [m
[31m-  C_Flip(boolean isFront) {[m
[32m+[m[32m/**[m
[32m+[m[32m * cooridinates the end effector with the elevator to make sure it flips saefly[m
[32m+[m[32m *[m[41m [m
[32m+[m[32m * @param isFront which side the end effector is being flipped to[m[41m [m
[32m+[m[32m */[m
[32m+[m[32m  public C_Flip(boolean isFront) {[m
     requires(Robot.getEndEffectorAngle());[m
     requires(Robot.getElevator());[m
     requires(Robot.getFrontClimber());[m
[36m@@ -27,6 +30,12 @@[m [mpublic class C_Flip extends Command {[m
     this.elevatorEnd = defaultElevatorEndTop;[m
   }[m
 [m
[32m+[m[32m  /**[m
[32m+[m[32m   * cooridinates the end effector with the elevator to make sure it flips saefly[m
[32m+[m[32m   *[m[41m [m
[32m+[m[32m   * @param isFront which side the end effector is being flipped to[m[41m [m
[32m+[m[32m   * @param elevatorEnd the height the elevator will end at[m
[32m+[m[32m   */[m
   C_Flip(boolean isFront, double elevatorEnd) {[m
     requires(Robot.getEndEffectorAngle());[m
     requires(Robot.getElevator());[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_FrontClimberHold.java b/src/main/java/frc/robot/commands/C_FrontClimberHold.java[m
[1mindex 2b02599..0810f89 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_FrontClimberHold.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_FrontClimberHold.java[m
[36m@@ -14,41 +14,27 @@[m [mpublic class C_FrontClimberHold extends Command {[m
 [m
   private double holdPos;[m
   public C_FrontClimberHold() {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
     requires(Robot.getFrontClimber());[m
   }[m
 [m
[31m-  // Called just before this Command runs the first time[m
   @Override[m
   protected void initialize() {[m
[32m+[m[32m    isInterruptible();[m
     holdPos = Robot.getFrontClimber().getAngle();[m
   }[m
 [m
[31m-  // Called repeatedly when this Command is scheduled to run[m
   @Override[m
   protected void execute() {[m
     Robot.getFrontClimber().goToDegree(holdPos);[m
   }[m
 [m
[31m-  // Make this return true when this Command no longer needs to run execute()[m
   @Override[m
   protected boolean isFinished() {[m
     return false;[m
   }[m
 [m
[31m-  // Called once after isFinished returns true[m
[31m-  @Override[m
[31m-  protected void end() {[m
[31m-  }[m
[31m-[m
[31m-  // Called when another command which requires one or more of the same[m
[31m-  // subsystems is scheduled to run[m
[31m-  @Override[m
[31m-  protected void interrupted() {[m
[31m-  }[m
[31m-  @Override[m
[31m-  public synchronized boolean isInterruptible() {[m
[31m-    return true;[m
[31m-  }[m
[32m+[m[32m @Override[m
[32m+[m[32m public synchronized boolean isInterruptible() {[m
[32m+[m[32m   return true;[m
[32m+[m[32m }[m
 }[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_GoToRelativeLevel.java b/src/main/java/frc/robot/commands/C_GoToRelativeLevel.java[m
[1mindex 2faaa96..3322c65 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_GoToRelativeLevel.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_GoToRelativeLevel.java[m
[36m@@ -12,19 +12,17 @@[m [mimport frc.robot.Robot;[m
 [m
 public class C_GoToRelativeLevel extends Command {[m
   private double adjustment;[m
[32m+[m
[32m+[m[32m  /**[m
[32m+[m[32m   * move to inchaes relative of selected level[m
[32m+[m[32m   *[m[41m [m
[32m+[m[32m   * @param adjustment inches to move from selected level[m
[32m+[m[32m   */[m
   public C_GoToRelativeLevel(double adjustment) {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
     requires(Robot.getElevator());[m
     this.adjustment = adjustment;[m
   }[m
 [m
[31m-  // Called just before this Command runs the first time[m
[31m-  @Override[m
[31m-  protected void initialize() {[m
[31m-  }[m
[31m-[m
[31m-  // Called repeatedly when this Command is scheduled to run[m
   @Override[m
   protected void execute() {[m
     Robot.getElevator().goToSelectedWithAdjustment(adjustment);[m
[36m@@ -33,7 +31,7 @@[m [mpublic class C_GoToRelativeLevel extends Command {[m
   // Make this return true when this Command no longer needs to run execute()[m
   @Override[m
   protected boolean isFinished() {[m
[31m-    return Robot.getElevator().atTarget(Robot.getElevator().getAverageInch() - 1);[m
[32m+[m[32m    return Robot.getElevator().atTarget(Robot.getElevator().getAverageInch() + adjustment);[m
   }[m
 [m
   // Called once after isFinished returns true[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_GoToSelectedLevel.java b/src/main/java/frc/robot/commands/C_GoToSelectedLevel.java[m
[1mindex a6d8617..8fdb65d 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_GoToSelectedLevel.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_GoToSelectedLevel.java[m
[36m@@ -15,37 +15,24 @@[m [mpublic class C_GoToSelectedLevel extends Command {[m
   SS_Elevator elevator;[m
 [m
   public C_GoToSelectedLevel() {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
[32m+[m
     requires(Robot.getElevator());[m
 [m
     elevator = Robot.getElevator();[m
   }[m
 [m
[31m-  // Called just before this Command runs the first time[m
[31m-  @Override[m
[31m-  protected void initialize() {[m
[31m-[m
[31m-  }[m
[31m-[m
[31m-  // Called repeatedly when this Command is scheduled to run[m
   @Override[m
   protected void execute() {[m
     elevator.goToSelectedLevel();[m
 [m
   }[m
 [m
[31m-  // Make this return true when this Command no longer needs to run execute()[m
   @Override[m
   protected boolean isFinished() {[m
     System.out.println("GO TO LEVEL RUNNING");[m
[32m+[m[32m    //ends if current inch is within range of target level[m
     return elevator.atTarget(elevator.getSelectedLevelInch());[m
   }[m
 [m
[31m-  // Called once after isFinished returns true[m
[31m-  @Override[m
[31m-  protected void end() {[m
[31m-  [m
[31m-  }[m
 [m
 }[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_SelectDown.java b/src/main/java/frc/robot/commands/C_SelectDown.java[m
[1mindex 522bc7b..022565f 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_SelectDown.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_SelectDown.java[m
[36m@@ -15,10 +15,13 @@[m [mimport frc.robot.commands.command_groups.CG_DownToHatch;[m
 [m
 public class C_SelectDown extends Command {[m
 [m
[32m+[m[41m  [m
   private CommandGroup down;[m
[32m+[m[32m  /**[m
[32m+[m[32m   * Selects elevator down based on hatch mode[m
[32m+[m[32m   */[m
   public C_SelectDown() {[m
[31m-    // Use requires() here to declare subsystem dependencies[m
[31m-    // eg. requires(chassis);[m
[32m+[m
     requires(Robot.getHatch());[m
   }[m
 [m
[1mdiff --git a/src/main/java/frc/robot/commands/C_SelectMode.java b/src/main/java/frc/robot/commands/C_SelectMode.java[m
[1mindex 6a0149c..233f3d6 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_SelectMode.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_SelectMode.java[m
[36m@@ -13,6 +13,9 @@[m [mimport frc.robot.commands.command_groups.CG_BallMode;[m
 import frc.robot.commands.command_groups.CG_HatchMode;[m
 [m
 public class C_SelectMode extends Command {[m
[32m+[m[32m  /**[m
[32m+[m[32m   * goes to selected mode, hatch (true) or ball (false)[m
[32m+[m[32m   */[m
   public C_SelectMode() {[m
     requires(Robot.getHatch());[m
   }[m
[1mdiff --git a/src/main/java/frc/robot/commands/C_SelectUp.java b/src/main/java/frc/robot/commands/C_SelectUp.java[m
[1mindex 3ab7aaa..ac8b6aa 100644[m
[1m--- a/src/main/java/frc/robot/commands/C_SelectUp.java[m
[1m+++ b/src/main/java/frc/robot/commands/C_SelectUp.java[m
[36m@@ -14,6 +14,9 @@[m [mimport frc.robot.commands.command_groups.CG_GoToSelectedLevelFront;[m
 [m
 public class C_SelectUp extends Command {[m
 [m
[32m+[m[32m  /**[m
[32m+[m[32m   * selectes the CG to go to selected level based on what is the selected side[m
[32m+[m[32m   */[m
   [m
   public C_SelectUp() {[m
 [m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_BallIntake.java b/src/main/java/frc/robot/commands/command_groups/CG_BallIntake.java[m
[1mindex 850b6a0..0496a6e 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_BallIntake.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_BallIntake.java[m
[36m@@ -21,7 +21,7 @@[m [mimport frc.robot.commands.test_commands.C_ElevatorToInch;[m
 [m
 public class CG_BallIntake extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * goes to ball mode and waits for ball , then goes resets[m
    */[m
   public CG_BallIntake() {[m
     addSequential(new C_FrontClimber(95, true));[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_BallMode.java b/src/main/java/frc/robot/commands/command_groups/CG_BallMode.java[m
[1mindex 68057fe..b2798ae 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_BallMode.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_BallMode.java[m
[36m@@ -16,7 +16,7 @@[m [mimport frc.robot.commands.test_commands.C_ElevatorToInch;[m
 [m
 public class CG_BallMode extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * go to ball mode[m
    */[m
   public CG_BallMode() {[m
     addSequential(new C_ExtendHatch(false));[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_CancelIntake.java b/src/main/java/frc/robot/commands/command_groups/CG_CancelIntake.java[m
[1mindex af560e9..d05ae6f 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_CancelIntake.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_CancelIntake.java[m
[36m@@ -15,7 +15,7 @@[m [mimport frc.robot.commands.C_SetFrontClimberIntake;[m
 [m
 public class CG_CancelIntake extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * stops all intake wheels[m
    */[m
   public CG_CancelIntake() {[m
     addSequential(new C_Flip(true));[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_ClimbMode.java b/src/main/java/frc/robot/commands/command_groups/CG_ClimbMode.java[m
[1mindex 662ae66..27917cf 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_ClimbMode.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_ClimbMode.java[m
[36m@@ -13,7 +13,7 @@[m [mimport frc.robot.commands.C_RearClimberToAngle;[m
 [m
 public class CG_ClimbMode extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * readies climbers to climb[m
    */[m
   public CG_ClimbMode() {[m
     addParallel(new C_RearClimberToAngle(100));[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_DownToBall.java b/src/main/java/frc/robot/commands/command_groups/CG_DownToBall.java[m
[1mindex d8d238f..44f909a 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_DownToBall.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_DownToBall.java[m
[36m@@ -15,7 +15,7 @@[m [mimport frc.robot.commands.test_commands.C_ElevatorToInch;[m
 [m
 public class CG_DownToBall extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * elevator goes down to ball default position[m
    */[m
   public CG_DownToBall() {[m
     setInterruptible(false);[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_DownToHatch.java b/src/main/java/frc/robot/commands/command_groups/CG_DownToHatch.java[m
[1mindex 48787e6..9f33c2f 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_DownToHatch.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_DownToHatch.java[m
[36m@@ -15,7 +15,7 @@[m [mimport frc.robot.commands.test_commands.C_ElevatorToInch;[m
 [m
 public class CG_DownToHatch extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * goes down to the hatch default position[m
    */[m
   public CG_DownToHatch() {[m
     setInterruptible(false);[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_FeederBallIntake.java b/src/main/java/frc/robot/commands/command_groups/CG_FeederBallIntake.java[m
[1mindex 3f90d0f..e77087c 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_FeederBallIntake.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_FeederBallIntake.java[m
[36m@@ -17,7 +17,7 @@[m [mimport frc.robot.commands.test_commands.C_ElevatorToInch;[m
 [m
 public class CG_FeederBallIntake extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * aligns to intake from feeder station[m
    */[m
   public CG_FeederBallIntake() {[m
     addSequential(new C_Flip(true));[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelBack.java b/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelBack.java[m
[1mindex 63ad111..7e4a23d 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelBack.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelBack.java[m
[36m@@ -20,7 +20,7 @@[m [mimport frc.robot.commands.C_SetFrontClimberIntake;[m
 [m
 public class CG_GoToSelectedLevelBack extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * goes to selected level on the back side[m
    */[m
   public CG_GoToSelectedLevelBack() {[m
     setInterruptible(false);[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelFront.java b/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelFront.java[m
[1mindex 67f60b5..c7fed0f 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelFront.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_GoToSelectedLevelFront.java[m
[36m@@ -17,7 +17,7 @@[m [mimport frc.robot.commands.C_SetFrontClimberIntake;[m
 [m
 public class CG_GoToSelectedLevelFront extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * goes to selected level on the front side[m
    */[m
   public CG_GoToSelectedLevelFront() {[m
     setInterruptible(false);[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_HatchDrop.java b/src/main/java/frc/robot/commands/command_groups/CG_HatchDrop.java[m
[1mindex ceb3378..371c95b 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_HatchDrop.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_HatchDrop.java[m
[36m@@ -16,7 +16,7 @@[m [mimport frc.robot.commands.test_commands.C_ElevatorToInch;[m
 [m
 public class CG_HatchDrop extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * releases hatch and goes down an inch for a second[m
    */[m
   public CG_HatchDrop() {[m
     addSequential(new C_SetHatchClosed(false));[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_HatchHold.java b/src/main/java/frc/robot/commands/command_groups/CG_HatchHold.java[m
[1mdeleted file mode 100644[m
[1mindex d3fca46..0000000[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_HatchHold.java[m
[1m+++ /dev/null[m
[36m@@ -1,22 +0,0 @@[m
[31m-/*----------------------------------------------------------------------------*/[m
[31m-/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */[m
[31m-/* Open Source Software - may be modified and shared by FRC teams. The code   */[m
[31m-/* must be accompanied by the FIRST BSD license file in the root directory of */[m
[31m-/* the project.                                                               */[m
[31m-/*----------------------------------------------------------------------------*/[m
[31m-[m
[31m-package frc.robot.commands.command_groups;[m
[31m-[m
[31m-import edu.wpi.first.wpilibj.command.CommandGroup;[m
[31m-import frc.robot.commands.C_EndEffectorHold;[m
[31m-import frc.robot.commands.C_SetHatchClosed;[m
[31m-[m
[31m-public class CG_HatchHold extends CommandGroup {[m
[31m-  /**[m
[31m-   * Add your docs here.[m
[31m-   */[m
[31m-  public CG_HatchHold() {[m
[31m-    addSequential(new C_SetHatchClosed(true));[m
[31m-    //addSequential(new C_EndEffectorHold());[m
[31m-  }[m
[31m-}[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_HatchMode.java b/src/main/java/frc/robot/commands/command_groups/CG_HatchMode.java[m
[1mindex a7f5f3c..d33b7b9 100644[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_HatchMode.java[m
[1m+++ b/src/main/java/frc/robot/commands/command_groups/CG_HatchMode.java[m
[36m@@ -18,7 +18,7 @@[m [mimport frc.robot.commands.test_commands.C_ElevatorToInch;[m
 [m
 public class CG_HatchMode extends CommandGroup {[m
   /**[m
[31m-   * Add your docs here.[m
[32m+[m[32m   * goes to hatch default position[m
    */[m
   public CG_HatchMode() {[m
     addSequential(new C_Flip(false));[m
[1mdiff --git a/src/main/java/frc/robot/commands/command_groups/CG_PlaceHatch.java b/src/main/java/frc/robot/commands/command_groups/CG_PlaceHatch.java[m
[1mdeleted file mode 100644[m
[1mindex 2920253..0000000[m
[1m--- a/src/main/java/frc/robot/commands/command_groups/CG_PlaceHatch.java[m
[1m+++ /dev/null[m
[36m@@ -1,24 +0,0 @@[m
[31m-/*----------------------------------------------------------------------------*/[m
[31m-/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */[m
[31m-/* Open Source Software - may be modified and shared by FRC teams. The code   */[m
[31m-/* must be accompanied by the FIRST BSD license file in the root directory of */[m
[31m-/* the project.                                                               */[m
[31m-/*----------------------------------------------------------------------------*/[m
[31m-[m
[31m-package frc.robot.commands.command_groups;[m
[31m-[m
[31m-import edu.wpi.first.wpilibj.command.CommandGroup;[m
[31m-import frc.robot.commands.*;[m
[31m-[m
[31m-public class CG_PlaceHatch extends CommandGroup {[m
[31m-  /**[m
[31m-   * Add your docs here.[m
[31m-   */[m
[31m-  public CG_PlaceHatch() {[m
[31m-    addSequential(new C_SetHatchClosed(false));[m
[31m-    addSequential(new C_WaitForHatch(false)); //Waits for the Hatch to stop being sensed by the optical limit switch[m
[31m-    addSequential(new C_Wait(1000));[m
[31m-    addParallel(new C_ExtendHatch(false));[m
[31m-    addSequential(new C_SetHatchClosed(true));[m
[31m-  }[m
[31m-}[m
