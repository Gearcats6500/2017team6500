package org.usfirst.frc.team6500.robot.subsystems;

import org.usfirst.frc.team6500.robot.commands.dtArcade;
import org.usfirst.frc.team6500.robot.commands.dtTank;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static Spark fright, bright, fleft, bleft;
	static double rightpower;
	static double leftpower;
	
	public static String driveMode = "Arcade";
	
	public void initMotors(int fr, int br, int fl, int bl) {
		fright = new Spark(fr);
		bright = new Spark(fl);
		fleft = new Spark(br);
		bleft = new Spark(bl);
		fright.setInverted(true);
		bright.setInverted(true);
	}
	
	public void setLeft(double power){
		fleft.set(power);
		bleft.set(power);
	}
	
	public void setRight(double power){
		fright.set(power);
		bright.set(power);
	}
	
	public void arcadeDrive(double yval, double xval, double multiplier) {
		double leftPower = 0.0;
		double rightPower = 0.0;
		if (yval > 0.0) {
			if (xval > 0.0) {
				leftPower = yval - xval;
		        rightPower = Math.max(yval, xval);
			}else{
				leftPower = Math.max(yval, -xval);
		        rightPower = yval + xval;
			}
		}else{
			if (xval > 0.0) {
				leftPower = -Math.max(-yval, xval);
		        rightPower = yval + xval;
			}else{
				leftPower = yval - xval;
		        rightPower = -Math.max(-yval, -xval);
			}
		}
		setLeft(leftPower * multiplier);
		setRight(rightPower * multiplier);
	}
	
	public void toggleDT(){
		if (driveMode == "Arcade"){
			driveMode = "Tank";
		}else{
			driveMode = "Arcade";
		}
	}
	
	public void tankDrive(double leftval, double rightval, double multiplier) {
		setLeft(leftval * multiplier);
		setRight(rightval * multiplier);
	}
	
	public void brakeStop(){
		setLeft(0.0);
		setRight(0.0);
	}

    public void initDefaultCommand() {
        if (driveMode == "Arcade") {
        	setDefaultCommand(new dtArcade());
        }else{
        	setDefaultCommand(new dtTank());
        }
    }
}
