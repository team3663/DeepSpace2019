package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A button with a shift argument
 *
 * @author Mark Riise
 * @since 1.1
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
 