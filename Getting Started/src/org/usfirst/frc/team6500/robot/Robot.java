package org.usfirst.frc.team6500.robot;

import org.usfirst.frc.team6500.robot.autonomous.DriveBaseline;
import org.usfirst.frc.team6500.robot.subsystems.Control;
import org.usfirst.frc.team6500.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Command autonomousCommand;
	
	//We have a Victor motor rightcontroller to rightcontroller the motor for the elevator
	Victor elevator;
	
	//Creating a RobotDrive object called drive so we can control the motors as a whole, not individually
	public static DriveTrain drive;
	
	public static Control control;
	
	//Our gyroscope is a model ADXRS450, and there is a class for that so we make a gyro object with it
	ADXRS450_Gyro gyro;
	
	//The sonar/ultrasonic sensor we have does not have a dedicated class but it has an analog output so we can plug it
	//into one of the analog inputs on the RoboRio and read the value from there
	AnalogInput sonar;
	
	//A variable that makes sure the robot doesn't try and run the autonomous portion of code more than once
	boolean autonomous = true;
	
	static String driveMode = "Disabled";
	
	//If a control delay variable is not created, the joystick's input is read too fast
	//and the driver cycles through options way too fast to control it.
	static int controldelay = 0;
	
	//A variable for timing things that we don't want to happen super often
	int tick = 0;
	
	Servo leftGear, rightGear, pan, tilt;
	
	boolean nitro = false;
	
	double tiltpos = 0.635;
	double panpos = 0.1;
	
	boolean flaps = false;
	
	public void updateFlaps() {
		if (flaps) {
			leftGear.set(0.5);
			rightGear.set(0);
		}else{
			leftGear.set(0);
			rightGear.set(0.4);
		}
	}
	
	//The @Override tag means we are overriding the method that was defined in the superclass (IterativeRobot in this case)
	//The robotInit method happens once when the RoboRio starts up and never again.
	//We need to define everything that we will use later now
	@Override
	public void robotInit() {
		//We create instances for the 4 motors objects from earlier
		//and then create the new RobotDrive object using those instances
		
		drive = new DriveTrain();
		
		elevator = new Victor(Ports.elevator);
		
		rightGear = new Servo(Ports.rightGear);
		leftGear = new Servo(Ports.leftGear);
		updateFlaps();
		pan = new Servo(Ports.pan);
		tilt = new Servo(Ports.tilt);
		
		//tilt range is 50 to 180
		//50 mapped to 0 to 1 is approximately .27 (key word is approximately)
		//"calibrating" the pan/tilt servos
		tilt.set(tiltpos);
		pan.set(panpos);
		
		//Making instances for the sensors and calibrating them/setting them up
		gyro = new ADXRS450_Gyro();
		sonar = new AnalogInput(1);
		gyro.calibrate();
		
		//Setting the robot to be soft disabled by default.
		//This way if the joystick is being pushed when the
		//robot is enabled, it doesn't shoot forward/backward
		driveMode = "Disable";
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	public static void switchMode(){
		if (controldelay == 0){
			if (driveMode == "Normal" || driveMode == "Debugging"){
				if (driveMode == "Debugging"){
					driveMode = "Disable";
				}
				if (driveMode == "Normal"){
					driveMode = "Debugging";
					System.out.println("MODE: Debugging Mode");
				}
			} else {
				driveMode = "Normal";
				System.out.println("MODE: Normal Movement");
			}
			controldelay = 20;
		}
	}
	
	
	public void autonomousInit(){
		autonomousCommand = new DriveBaseline();
	}
	
	//This code runs when the autonomous portion of the match is happening,
	//and it (should) only runs once (see the autonomous variable)
	@Override
	public void autonomousPeriodic() {
		if (autonomous) {
			//Now set the autonomous variable to false so the code won't keep running
			autonomous = false;
		}
	}
	
	@Override
	public void teleopInit(){
		autonomousCommand.cancel();
	}
	
	//This method loops as fast as possible while the robot is in
	@SuppressWarnings("deprecation")
	//the teleoperated (controlled by driver) period of the match
	@Override
	public void teleopPeriodic() {
		nitro = false;
		
		//If the the robot is in normal drive,
		if (driveMode == "Normal") {
			if (rightcontroller.getRawButton(12) && rightcontroller.getRawButton(1)) {
				nitro = true;
			}else{
				nitro = false;
			}
			//tell the RobotDrive object to do tank drive.
			//It gets the power for the left and right side and multiplies it by the multiplier form the throttle
			//drive.tankDrive(getLeftPower() * multiplier, getRightPower() * multiplier);
			//drive.arcadeDrive(rightcontroller);
			if (!nitro) {
				
				double leftboost = 0.0;
				if (leftcontroller.getTrigger()) {
					leftboost = 0.05;
				}
				double rightboost = 0.0;
				if (rightcontroller.getTrigger()) {
					rightboost = 0.05;
				}
				
				if (controldelay == 0 && rightcontroller.getRawButton(11)) {
					if (driveType == "Tank") {
						driveType = "Arcade";
					}else{
						driveType = "Tank";
					}
					System.out.println(driveType);
					controldelay = 15;
				}
				
				if (driveType == "Tank") {
					drive.tankDrive(leftcontroller.getY() * -1, rightcontroller.getY() * -1, multiplier + rightboost);
				}else{
					drive.arcadeDrive(rightcontroller.getY() * -1, rightcontroller.getX() * -1, multiplier + rightboost);
				}
				
				
				
				//Actuator code
				//if (rightcontroller.getXButton()) {
				if (rightcontroller.getRawButton(3) && controldelay == 0) {
					if (relaystate == "Reverse") {
						relaystate = "Forward";
						relayone.set(Relay.Value.kForward);
					}else{
						relaystate = "Reverse";
						relayone.set(Relay.Value.kReverse);
					}
					controldelay = 15;
				}
				
				//Pan\Tilt code
				int POVPos = rightcontroller.getPOV();
				if (POVPos == 180) {
					if (tiltpos > 0.29){
					tiltpos = tiltpos - 0.02;
					}
					tilt.set(tiltpos);
				}else{
					if (POVPos == 0) {
						if (tiltpos < 0.98){
							tiltpos = tiltpos + 0.02;
						}
						tilt.set(tiltpos);
					}
				}
				
				if (POVPos == 90) {
					if (panpos < .98){
					panpos = panpos + .02;
					}
					pan.set(panpos);
				}else{
					if (POVPos == 270) {
						if (panpos > .02){
						panpos = panpos - .02;
						}
						pan.set(panpos);
					}
				}
				
				//Elevator code
				if (rightcontroller.getRawButton(4)) {
					elevator.set(-1);
				}else{
					if (rightcontroller.getRawButton(6)) {
						elevator.set(1);
					}else{
						elevator.set(0.0);
					}
				}
				
				if (rightcontroller.getRawButton(5) && controldelay == 0) {
					if (flaps) {
						flaps = false;
					}else{
						flaps = true;
					}
					updateFlaps();
					controldelay = 15;
				}
				
				if (rightcontroller.getRawButton(7)) {
					panpos = 0.0;
					pan.set(panpos);
					tiltpos = 0.9;
					tilt.set(tiltpos);
				}else{
					if (rightcontroller.getRawButton(8)) {
						panpos = 0.5;
						pan.set(panpos);
						tiltpos = 0.9;
						tilt.set(tiltpos);
					}else{
						if (rightcontroller.getRawButton(9)) {
							panpos = 1.0;
							pan.set(panpos);
							tiltpos = 0.1;
							tilt.set(tiltpos);
						}
					}
				}
				
//				//if (rightcontroller.getYButton()) {
//					double LeftOrRight = testtable.getNumber("LeftOrRight");
//					if (LeftOrRight != 0){
//						if (LeftOrRight == 1){
//							fleft.set(.75);
//							bleft.set(.75);
//							fright.set(.25);
//							bright.set(.25);
//						}
//						if (LeftOrRight == -1){
//							fleft.set(.25);
//							bleft.set(.25);
//							fright.set(.75);
//							bright.set(.75);
//						}
//					}
//				}
			} else {
				drive.tankDrive(1, 1, 1);
			}
		}
		
		//If the robot is in debugging mode,
		if (driveMode == "Debugging") {
			
		}
		//If the driveMode 
		if (driveMode == "Disable") {
			drive.brakeStop();
		}
//		drive.arcadeDrive(rightcontroller.getY() * multiplier, -rightcontroller.getX() * multiplier);
		if (controldelay > 0) { controldelay = controldelay - 1; }
		if (tick < 100) { tick = tick + 1; }
		if (tick == 100) { tick = 0; }
		if (tick == 10) {
			//System.out.println("Angle: " + gyro.getAngle() + ", Rate:" + gyro.getRate());
			//System.out.println(readUltrasonic());
//			if (testServo.get() > 0.9) {
//				testServo.set(-1);
//			}else{
//				testServo.set(1);
//			}
//			rightcontroller.setRumble(GenericHID.RumbleType.kRightRumble, 1.0);
		}
		
		//Putting values to the SmartDashboard
		SmartDashboard.putNumber("Gyroscope Angle", gyro.getAngle());
		SmartDashboard.putNumber("Pan Position", panpos);
		SmartDashboard.putNumber("Tilt Position", tiltpos);
		SmartDashboard.putString("Drive Mode", driveMode);
		SmartDashboard.putBoolean("Zoom Zoom!", nitro);
	}

	@Override
	public void testPeriodic() {

	}
}
