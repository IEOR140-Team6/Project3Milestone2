package com.mydomain;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
/**
 * IEOR 140 Team 6
 * Authors: Sherman Siu, Moonsoo Choi
 * Class Name: Avoider
 * Class Description: The Avoider class only comes into affect when the robot either detects a nearby
 * object via its ultrasonic sensor or its touch sensors are touched (see Detector.java). The method
 * Avoid() just stops the robot and the robot moves backwards 10 cm.
 * @author Moonsoo Choi, Sherman Siu
 *
 */
public class Avoider 
{	
	//set instance variable for DifferentialPilot
	DifferentialPilot myPilot = new DifferentialPilot((float)(56/25.4),5.5f,Motor.A,Motor.C,false);
	
	/**
	 * The Avoid() method merely stops the robot and makes the robot travel back 10 cm.
	 */
	public void Avoid()
	{
		myPilot.stop();
		myPilot.travel(-(10/2.54));
	}
}
