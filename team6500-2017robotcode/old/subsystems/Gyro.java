package org.usfirst.frc.team6500.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gyro extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public ADXRS450_Gyro gyro;
	
	public void initGyro() {
		this.gyro = new ADXRS450_Gyro();
		this.gyro.calibrate();
	}
	
	public double gyroAngle() {
		return this.gyro.getAngle();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

