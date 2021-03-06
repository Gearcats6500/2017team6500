package org.usfirst.frc.team6500.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dumper extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public Victor dumper;
	public boolean out;
	
	public void goBackward() {
		this.dumper.set(-0.5);
		this.out = false;
	}
	
	public void goForward() {
		this.dumper.set(0.5);
		this.out = true;
	}
	
	public void stopMoving() {
		this.dumper.set(0.0);
	}
	
	public void initActuator(int port) {
		this.dumper = new Victor(port);
		this.out = false;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

