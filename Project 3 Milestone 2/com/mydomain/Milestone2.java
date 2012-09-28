
package com.mydomain;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

/**
 * IEOR 140 Team 6
 * Authors: Moonsoo Choi, Sherman Siu
 * Date: September 27, 2012
 * Class Name: Milestone2
 * Class Description: Robot completes 8 laps between the beacon lights, referring to
 * the Racer class as a means of navigating the robot towards the light and through
 * certain obstacles.
 *  * @author Moonsoo Choi, Sherman Siu
 */
public class Milestone2 
{
	/**
	 * The main method contains three main parts: setting up the constructors, setting robot speeds,
	 * and then commanding the robot to complete 8 laps. 
	 * The following instance variables are set: DifferentialPilot, LightSensor, UltrasonicSensor, 
	 * TouchSensors left and right, and Racer. The UltrasonicSensor connects to the Detector class, 
	 * the LightSensor connects to the Scanner class, and the Racer connects to the Racer class.
	 * The acceleration, rotating speed, and traveling speed are turned on; detector thread is started.
	 * Robot then completes 8 laps, using GotoLight() method to navigate and rotating 180 degrees when
	 * it reaches the light.
	 *  @param args
	 */
	public static void main(String[] args) 
	{	
		//Instance variables set.
		DifferentialPilot myPilot = new DifferentialPilot((float)(56/25.4),5.5f,Motor.A,Motor.C,false);
		LightSensor scanner = new LightSensor(SensorPort.S2);
		UltrasonicSensor UltraSound = new UltrasonicSensor(SensorPort.S3);
		TouchSensor TouchLeft = new TouchSensor(SensorPort.S1);
		TouchSensor TouchRight = new TouchSensor(SensorPort.S4);
		Scanner mySR = new Scanner(Motor.B, scanner);
		Detector myDetector = new Detector(Motor.B,UltraSound,TouchLeft,TouchRight);
		Racer myRacer = new Racer(myPilot, mySR, myDetector);
		
		Button.waitForAnyPress(); //Wait for button press to begin.
		int i = 0; //Initialize i, the number of laps completed.
		myDetector.start(); //Start the Detector thread.
		myPilot.setAcceleration(25); //Set acceleration of robot (in/sec/sec).
		myPilot.setRotateSpeed(720); //Set rotation speed of robot (degree/sec).
		myRacer.Speed(13); //Set traveling speed of robot (in/sec).
		//Loop for i<8, so the robot completes 8 laps.
		while (i<8)
		{
			myRacer.gotoLight(48); //Robot steers using myRacer till it finds light intensity of 48.
			myPilot.rotate(180); //Rotate 180 degrees.
			i++; //Start next lap.
		}
	}

}

