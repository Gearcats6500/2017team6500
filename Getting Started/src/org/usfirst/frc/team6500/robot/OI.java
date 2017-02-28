package org.usfirst.frc.team6500.robot;

import org.usfirst.frc.team6500.robot.commands.ChangeMode;
import org.usfirst.frc.team6500.robot.subsystems.Control;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public JoystickPair controllers;
	public Joystick controllerL, controllerR;
	
	@SuppressWarnings("unused")
	public OI () {
		controllers = Control.getJoysticks();
		controllerL = controllers.leftJoy;
		controllerR = controllers.rightJoy;
		Button button1 = new JoystickButton(controllerR, 1);
		Button button2 = new JoystickButton(controllerR, 2);
		Button button3 = new JoystickButton(controllerR, 3);
		Button button4 = new JoystickButton(controllerR, 4);
		Button button5 = new JoystickButton(controllerR, 5);
		Button button6 = new JoystickButton(controllerR, 6);
		Button button7 = new JoystickButton(controllerR, 7);
		Button button8 = new JoystickButton(controllerR, 8);
		Button button9 = new JoystickButton(controllerR, 9);
		Button button10 = new JoystickButton(controllerR, 10);
		Button button11 = new JoystickButton(controllerR, 11);
		Button button12 = new JoystickButton(controllerR, 12);
		button2.whenPressed(new ChangeMode());
	}
}
