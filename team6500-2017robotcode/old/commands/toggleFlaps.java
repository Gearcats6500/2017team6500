package org.usfirst.frc.team6500.robot.commands;

import org.usfirst.frc.team6500.robot.Blargh;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class toggleFlaps extends Command {

    public toggleFlaps() {
    	requires(Blargh.gearFlaps);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Blargh.gearFlaps.toggleFlaps();
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
