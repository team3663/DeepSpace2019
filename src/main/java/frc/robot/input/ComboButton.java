package frc.robot.input;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import frc.robot.util.Stopwatch;

/**
 * A button for different directions on a directional-pad.
 *
 * @author Jacob Bublitz
 * @since 1.0
 */
public class ComboButton extends Button {


	ArrayList<Button> buttons = new ArrayList<Button>();
	private Button shift;
	private int expiraionTime;
	private Stopwatch stopwatch;
	private ButtonTracker tracker;
	public ComboButton(ArrayList<Button> buttons, int expiraionTime) {
		this.buttons = buttons;
		tracker = new ButtonTracker(expiraionTime);
		this.stopwatch = new Stopwatch();
	}


	@Override
	public boolean get() {
		tracker.updateTimes(stopwatch.getMilliseconds());
		
		
		return true;
	}
}
