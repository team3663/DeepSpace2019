package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.C_GoToLevel;
import frc.robot.commands.test_commands.C_ElevatorDirect;
import frc.robot.input.IGamepad;
import frc.robot.input.XboxGamepad;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private IGamepad primaryController = new XboxGamepad(0);
    private IGamepad secondaryController = new XboxGamepad(1);
    private IGamepad testingController = new XboxGamepad(2);

    private Robot mRobot;

    public OI(Robot robot) {
        mRobot = robot;
    }

    public void registerControls() {
        //
        //primary controller
        //
        primaryController.getLeftBumperButton().whenPressed(new C_SetFieldOriented(mRobot.getDrivetrain(), false));
        primaryController.getLeftBumperButton().whenReleased(new C_SetFieldOriented(mRobot.getDrivetrain(), true));
        primaryController.getStartButton().whenPressed(new C_ZeroDrivetrainGyro(mRobot.getDrivetrain()));

        secondaryController.getRightBumperButton().whileHeld(new C_ElevatorDirect());
        //
        //secondary controller
        //
        // grabbing/releasing hatches
        // secondaryController.getRightBumperButton().whenPressed(new C_SetHatchPos(true));
        // secondaryController.getLeftBumperButton().whenPressed(new C_SetHatchPos(false));
        // collecting cargo
        testingController.getYButton().whenPressed(new C_SetCargoIntakeSpeed(-1));
        testingController.getYButton().whenReleased(new C_SetCargoIntakeSpeed(0));

        // releasing cargo
        testingController.getXButton().whenPressed(new C_SetCargoIntakeSpeed(1));
        testingController.getXButton().whenReleased(new C_SetCargoIntakeSpeed(0));
        
        //climber intake
        testingController.getBButton().whenPressed(new C_SetFrontClimberIntake(-1));
        testingController.getBButton().whenReleased(new C_SetFrontClimberIntake(0));


        testingController.getAButton().whenPressed(new C_SetFrontClimberIntake(1));
        testingController.getAButton().whenReleased(new C_SetFrontClimberIntake(0));
        //
        //testing controller: BE CAREFUL
        //
        testingController.getStartButton().whenPressed(new C_GoToLevel(1));


    }
    
    public IGamepad getTestController(){
        return testingController;
    }
    public IGamepad getSecondaryController(){
        return secondaryController;
    }
    public IGamepad getPrimaryController() {
        return primaryController;
    }

}
