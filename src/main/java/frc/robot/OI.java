package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.command_groups.*;
import frc.robot.commands.test_commands.C_ElevatorDirect;
import frc.robot.commands.test_commands.C_ElevatorToInch;
import frc.robot.input.AxisButton;
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
        //primaryController.getStartButton().whenPressed(new C_ZeroDrivetrainGyro(mRobot.getDrivetrain()));
        primaryController.getXButton().whenPressed(new CG_BallIntake()); 
        primaryController.getBButton().whenPressed(new CG_CancelIntake());

        primaryController.getYButton().whenPressed(new CG_GetHatch());
        primaryController.getAButton().whenPressed(new CG_PlaceHatch());


        // primaryController.getRightTriggerButton().whenPressed(new C_SetFieldOriented(false));
        // primaryController.getRightTriggerButton().whenPressed(new C_SetFieldOriented(true));
        

        //
        //secondary controller
        //
        // secondaryController.getBackButton().whenPressed(new C_RearClimber(90));

        // secondaryController.getDPadButton(Direction.UP).whenPressed(new C_FrontClimber(0));
        // secondaryController.getDPadButton(Direction.RIGHT).whenPressed(new C_FrontClimber(90));
        // secondaryController.getDPadButton(Direction.DOWN).whenPressed(new C_FrontClimber(180));

        // secondaryController.getAButton().whenPressed(new C_SetFrontClimberIntake(-.5));
        // secondaryController.getAButton().whenReleased(new C_SetFrontClimberIntake(0));

        // secondaryController.getBButton().whenPressed(new C_SetFrontClimberIntake(.5));
        // secondaryController.getBButton().whenReleased(new C_SetFrontClimberIntake(0));

        // secondaryController.getYButton().whenPressed(new C_FrontClimberHold());

        secondaryController.getDPadButton(Direction.UP).whenPressed(new C_ChangeSelectedLevel(1));
        secondaryController.getDPadButton(Direction.DOWN).whenPressed(new C_ChangeSelectedLevel(-1));

        secondaryController.getLeftBumperButton().whenPressed(new CG_GoToSelectedLevel());
        secondaryController.getRightBumperButton().whenPressed(new CG_DownAll());

        secondaryController.getAButton().whenPressed(new C_SetEndEffectorIntakeSpeed(1));
        secondaryController.getAButton().whenReleased(new C_SetEndEffectorIntakeSpeed(0));

        secondaryController.getXButton().whileHeld(new C_Climb());

        
        //
        //testing controller: BE CAREFUL
        //

        testingController.getAButton().whenPressed(new C_RearClimber(15));
        // testingController.getLeftBumperButton().whenPressed(new C_SetHatchClose(true));
        // testingController.getLeftBumperButton().whenReleased(new C_SetHatchClose(false));


        //testingController.getBackButton().whileHeld(new C_Climb(10));

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
