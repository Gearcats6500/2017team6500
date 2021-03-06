package org.usfirst.frc.team6500.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public Victor elevator;
	
	public void driveClimber(double power) {
		if (power > 1.0) {
			power = 1.0;
		}
		if (power < -1.0) {
			power = -1.0;
		}
		elevator.set(power);
	}
	
	public void initMotor(int port) {
		elevator = new Victor(port);
	}
	
	public void stop() {
		elevator.set(0);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

