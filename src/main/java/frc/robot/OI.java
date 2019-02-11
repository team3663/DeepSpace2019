package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.command_groups.CG_BallIntake;
import frc.robot.commands.command_groups.CG_CancelBallIntake;
import frc.robot.commands.command_groups.CG_GoToSelectedLevel;
import frc.robot.commands.test_commands.C_ElevatorDirect;
import frc.robot.commands.test_commands.C_ElevatorToInch;
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
        primaryController.getLeftBumperButton().whenPressed(new C_SetFieldOriented(mRobot.getDrivetrain(), false));
        primaryController.getLeftBumperButton().whenReleased(new C_SetFieldOriented(mRobot.getDrivetrain(), true));
        primaryController.getStartButton().whenPressed(new C_ZeroDrivetrainGyro(mRobot.getDrivetrain()));


        //
        //secondary controller
        //
        secondaryController.getBackButton().whenPressed(new C_RearClimber(90));

        secondaryController.getDPadButton(Direction.UP).whenPressed(new C_FrontClimber(0));
        secondaryController.getDPadButton(Direction.RIGHT).whenPressed(new C_FrontClimber(90));
        secondaryController.getDPadButton(Direction.DOWN).whenPressed(new C_FrontClimber(180));

        secondaryController.getRightBumperButton().whenPressed(new C_SetHatchClose(true));
        secondaryController.getLeftBumperButton().whenPressed(new C_SetHatchClose(false));
        
        //
        //testing controller: BE CAREFUL
        //
        testingController.getStartButton().whenPressed(new CG_GoToSelectedLevel());

        testingController.getXButton().whenPressed(new CG_BallIntake());
        testingController.getBButton().whenPressed(new CG_CancelBallIntake());
        testingController.getYButton().whenPressed(new C_SetEndEffectorIntakeSpeed(1));

        testingController.getDPadButton(Direction.UP).whenPressed(new C_ChangeSelectedLevel(1));
        testingController.getDPadButton(Direction.DOWN).whenPressed(new C_ChangeSelectedLevel(-1));

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
