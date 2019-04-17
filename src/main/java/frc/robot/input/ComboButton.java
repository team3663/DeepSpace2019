package frc.robot.input;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import frc.robot.util.Stopwatch;

/**
 * A button pressed after a certian combination of buttons have been pressed in time.
 *
 * @author Mark Riise
 * @since 1.1
 */
public class ComboButton extends Button {


	ArrayList<Button> buttons = new ArrayList<Button>();
	ArrayList<Number> expiraions = new ArrayList<Number>();
	private Stopwatch stopwatch;
	private ButtonTracker tracker;
	public ComboButton(ArrayList<Button> buttons, ArrayList<Number> expiraions, int expiraionTime) {
		this.buttons = buttons;
		this.expiraions = expiraions;
		tracker = new ButtonTracker(expiraionTime);
		this.stopwatch = new Stopwatch();
	}


	@Override
	public boolean get() {
		tracker.updateTimes(stopwatch.getMilliseconds());
		for(int i = 0; i < buttons.size(); i++){
			if(buttons.get(i).get()){
				tracker.addButton(buttons.get(i), expiraions.get(i));
			}
			if(!buttons.get(i).equals(tracker.get().get(i))){
				tracker.clear();
			}
		}

		if(buttons.equals(tracker.get())){
			return true;
		}
		else{
			return false;
		}
	}
}
