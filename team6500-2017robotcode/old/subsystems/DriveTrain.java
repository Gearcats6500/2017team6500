package org.usfirst.frc.team6500.robot.subsystems;

import org.usfirst.frc.team6500.robot.commands.dtArcade;

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
		bright = new Spark(br);
		fleft = new Spark(fl);
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
	
	public void arcadeDrive(double moveValue, double rotateValue, double multiplier) {
		double leftMotorSpeed = 0.0;
		double rightMotorSpeed = 0.0;
		
		if (moveValue >= 0.0) {
		    moveValue = moveValue * moveValue;
		} else {
		    moveValue = -(moveValue * moveValue);
		}
		if (rotateValue >= 0.0) {
		    rotateValue = rotateValue * rotateValue;
		} else {
		    rotateValue = -(rotateValue * rotateValue);
		}
		
	    if (moveValue > 0.0) {
	        if (rotateValue > 0.0) {
	          leftMotorSpeed = moveValue - rotateValue;
	          rightMotorSpeed = Math.max(moveValue, rotateValue);
	        } else {
	          leftMotorSpeed = Math.max(moveValue, -rotateValue);
	          rightMotorSpeed = moveValue + rotateValue;
	        }
	      } else {
	        if (rotateValue > 0.0) {
	          leftMotorSpeed = -Math.max(-moveValue, rotateValue);
	          rightMotorSpeed = moveValue + rotateValue;
	        } else {
	          leftMotorSpeed = moveValue - rotateValue;
	          rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	        }
	      }
		
		setLeft(leftMotorSpeed * multiplier);
		setRight(rightMotorSpeed * multiplier);
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
    	setDefaultCommand(new dtArcade());
//        if (driveMode == "Arcade") {
//        	setDefaultCommand(new dtArcade());
//        }else{
//        	setDefaultCommand(new dtTank());
//        }
    }
}

