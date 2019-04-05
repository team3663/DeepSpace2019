package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A button for different directions on a directional-pad.
 *
 * @author Jacob Bublitz
 * @since 1.0
 */
public class ShiftButton extends Button {


	private Button button;
	private Button shift;
	private boolean inverse;

	public ShiftButton(Button button, Button shift, boolean inverse) {
		this.button = button;
		this.shift = shift;
		this.inverse = inverse;
	}

	public ShiftButton(Button button, Button shift) {
		this(button, shift, false);
	}


	@Override
	public boolean get() {
		return button.get() == !inverse && shift.get() == true;
	}
}
