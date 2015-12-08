package org.usfirst.frc.team4924.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick leftStick;  // set to ID 1 in DriverStation
    Talon leftMotor;
    Talon rightMotor;
    Solenoid leftUp;
    Solenoid rightUp;
    Solenoid leftDown;
    Solenoid rightDown;
    Relay relayIn;
    Compressor pump;
	int autoLoopCounter;
	Servo servo;

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(0, 1);
        myRobot.setExpiration(0.1); 
        leftStick = new Joystick(0);
        leftMotor = new Talon(2);
        rightMotor = new Talon(3);
        leftUp = new Solenoid(2);
        rightDown = new Solenoid(4);
        leftDown = new Solenoid(1);
        rightUp = new Solenoid(3);
        relayIn = new Relay(0);
        pump = new Compressor();
        servo = new Servo(4);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(0.2, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
		} else if(autoLoopCounter < 102) {
			myRobot.drive(0.0, 0.0); 	// stop robot
			autoLoopCounter++;
		} else if(autoLoopCounter < 110) {
			leftUp.set(false);
    		rightUp.set(false);
    		leftDown.set(true);
    		rightDown.set(true);
    		autoLoopCounter++;
		} else if(autoLoopCounter < 200) {
			leftDown.set(true);
    		rightDown.set(true);
    		autoLoopCounter++;
		} else if(autoLoopCounter < 210) {
			leftDown.set(false);
    		rightDown.set(false);
			leftUp.set(true);
    		rightUp.set(true);
    		autoLoopCounter++;
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	while (isOperatorControl() && isEnabled()) {
        	myRobot.arcadeDrive(leftStick.getY()*-1, leftStick.getX()*-1);
        	if(leftStick.getRawButton(4)) {
        		leftMotor.set(-1);
        		rightMotor.set(-1);
        	} else if(leftStick.getRawButton(6)) {
           		leftMotor.set(1);
        		rightMotor.set(1);
        	}else{
           		leftMotor.set(0);
        		rightMotor.set(0);
        	};
        	if(leftStick.getRawButton(3)){
        		leftDown.set(false);
        		rightDown.set(false);
        		leftUp.set(true);
        		rightUp.set(true);
        	}else if(leftStick.getRawButton(5)){
        		leftUp.set(false);
        		rightUp.set(false);
        		leftDown.set(true);
        		rightDown.set(true);
        	};
        	if(leftStick.getRawButton(1)){
        		relayIn.set(Relay.Value.kForward);
      
        	}else{        		
        		relayIn.set(Relay.Value.kReverse);        		
        	};
        	if(leftStick.getRawButton(7)) {
        		pump.start();
        	}else if(leftStick.getRawButton(8)){
        		pump.stop();
        	};
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}


