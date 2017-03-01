package org.usfirst.frc.team6500.robot.commands;

import org.usfirst.frc.team6500.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class checkPOV extends Command {
	
	@SuppressWarnings("static-access")
	public Joystick controllerR = Robot.control.getJoysticks().rightJoy;

    public checkPOV() {
    	requires(Robot.pantilt);
    	requires(Robot.control);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	int POVpos = controllerR.getPOV();
    	if (POVpos == 180) {
			Robot.pantilt.tiltDown();
		}else{
			if (POVpos == 0) {
				Robot.pantilt.tiltUp();
			}
		}
		
		if (POVpos == 90) {
			Robot.pantilt.panRight();
		}else{
			if (POVpos == 270) {
				Robot.pantilt.panLeft();
			}
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
