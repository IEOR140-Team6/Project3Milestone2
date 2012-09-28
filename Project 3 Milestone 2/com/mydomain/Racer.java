package com.mydomain;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * IEOR 140 Team 6
 * Authors: Moonsoo Choi, Sherman Siu
 * Class Name: Racer
 * Class Description: Racer controls the navigation of the robot as it shuffles between beacon lights.
 * The robot will steer towards the light, and will call on the Avoider class when Detector detects an
 * obstacle along the way.
 */
public class Racer 
{	
	// declaring instance variables.
	Avoider myAvoid = new Avoider();
	public DifferentialPilot myPilot;
	int _speed;
	public Scanner mySR;
	public Detector myDetector;
	
	/**
	 * Racer class will be called upon when one provides the fields for DifferentialPilot, Scanner,
	 * and Detector.
	 * @param Pilot
	 * @param SR
	 * @param _Detector
	 */
	public Racer(DifferentialPilot Pilot, Scanner SR, Detector _Detector)
	{
		myPilot = Pilot;
		mySR = SR;
		myDetector = _Detector;
	}
	
	/**
	 * Sets the speed of travel speed of the robot.
	 * @param speed
	 */
	public void Speed(int speed)
	{
		_speed = speed;
		myPilot.setTravelSpeed(_speed);
	}
	
	/**
	 * Navigates the robot as it shuffles between two beacon lights. The robot will steer towards 
	 * the light. Uses the Scanner class to determine the angle with the maximum light intensity.
	 * With the angle acquired, robot will steer at that angle rate*gain. If an obstacle is detected 
	 * by the Detector class, robot will temporarily halt its steering towards the light and call 
	 * upon the Avoider class to react to the obstacle. The method terminates when the robot reaches
	 * the beacon light. 
	 * @param Light
	 */
	public void gotoLight(int Light) 
	{
		// best angle represents the amount of angle where light intensity value is the greatest
		int bestAngle = mySR.getTargetBearing();
		myPilot.setTravelSpeed(_speed); // setting the traveling speed of a robot
		mySR.setSpeed(999);  // setting the rotating speed of the scanner
		float gain = 0.25f; // a variable that can help us determine an appropriate steering amount
		mySR.rotateTo(bestAngle, true); //Rotate the scanner motor to the bestAngle measured.
		mySR.scanTo(80); // scanning to the left 80 degrees
		mySR.scanTo(-80); // scanning to the right 80 degrees
		
		/* 
		 * While the current light intensity value is less than its minimum,
		 * make the robot steer with the angle that produces the most amount of light 
		 * intensity we have so far. If an obstacle is detected, call upon the Avoider class.
		 */
		while (mySR.getLight() < Light)
		{
			//Steer robot using the bestAngle acquired earlier.
			myPilot.steer(gain*bestAngle);	
			// continuously scan for the light, scanning with range of +50 degrees of current angle			
			mySR.scanTo(Math.min(bestAngle+50,90));
			/*
			 * If an obstacle is detected, call upon the Avoider class to react, then wait for a 
			 * button press to continue the steering towards the light.
			 */
			if (myDetector._detected)
			{
				myAvoid.Avoid(); //Call upon Avoider class to avoid.
				Button.waitForAnyPress(); //Wait for button press.
				myDetector._detected = false; //Set _detected to false to not re-trigger.
			}
			/*
			 * The following below does the exact same process as the first part of the while loop,
			 * only that it is now scanning the range of -50 degrees of the current best angle.
			 */
			bestAngle = mySR.getTargetBearing();
			mySR.scanTo(Math.max(bestAngle-50,-90));
			bestAngle = mySR.getTargetBearing();
			if (myDetector._detected)
			{
				myAvoid.Avoid();
				Button.waitForAnyPress();
				myDetector._detected = false;
			}
		}		
	}
}
