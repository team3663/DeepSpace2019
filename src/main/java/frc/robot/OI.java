package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.command_groups.*;
import frc.robot.input.IGamepad;
import frc.robot.input.XboxGamepad;
import frc.robot.input.DPadButton.Direction;


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

        primaryController.getStartButton().whenPressed(new C_ZeroDrivetrainGyro(mRobot.getDrivetrain()));
        primaryController.getXButton().whenPressed(new C_IntakeSelect()); 
        primaryController.getBButton().whenPressed(new CG_CancelIntake());

        // primaryController.getRightTriggerButton().whenPressed(new C_SetFieldOriented(false));
        // primaryController.getRightTriggerButton().whenPressed(new C_SetFieldOriented(true));
        

        //
        //secondary controller
        //

        secondaryController.getDPadButton(Direction.UP).whenPressed(new C_SetSelectedLevel(3));
        secondaryController.getDPadButton(Direction.LEFT).whenPressed(new C_SetSelectedLevel(2));
        secondaryController.getDPadButton(Direction.DOWN).whenPressed(new C_SetSelectedLevel(1));
        secondaryController.getDPadButton(Direction.RIGHT).whenPressed(new C_SetSelectedLevel(15));


        secondaryController.getLeftBumperButton().whenPressed(new CG_GoToSelectedLevel());
        secondaryController.getLeftBumperButton().whenReleased(new C_DownSelect());

        secondaryController.getBackButton().whenPressed(new C_ToggleHatchMode());
        secondaryController.getBackButton().whenReleased(new C_ModeSelect());

        secondaryController.getAButton().whenPressed(new C_SetEndEffectorIntakeSpeed(1));
        secondaryController.getAButton().whenReleased(new C_SetEndEffectorIntakeSpeed(0));

        secondaryController.getBButton().whenPressed(new CG_HatchDrop());

        secondaryController.getYButton().whenPressed(new CG_HatchHold());

        secondaryController.getStartButton().whileHeld(new C_Climb());

        
        //
        //testing controller: BE CAREFUL
        //

        testingController.getAButton().whenPressed(new C_RearClimber(15));
        // testingController.getLeftBumperButton().whenPressed(new C_SetHatchClose(true));
        // testingController.getLeftBumperButton().whenReleased(new C_SetHatchClose(false));


        //testingController.getBackButton().whileHeld(new C_Climb(10));

        testingController.getYButton().whenPressed(new C_VisionAlign());
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
