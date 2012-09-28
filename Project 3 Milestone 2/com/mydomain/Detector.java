package com.mydomain;

import lejos.nxt.*;

/**
 * IEOR 140 Team 6
 * Authors: Sherman Siu, Moonsoo Choi
 * Class Name: Detector
 * Class Description: Detects if there are any obstacles within a range of 2-3 feet of the robot's 
 * Ultrasonic Sensor. Also detects if the touch sensors were used by the robot.
 * @author Moonsoo Choi, Sherman Siu
 */
class Detector extends Thread
{
	/**
	 * The constructor creates Detector and calls upon the fields Motor, Ultrasonic Sensor, TouchSensors
	 * left and right.  
	 * @param theMotor
	 * @param ear
	 * @param lefthand
	 * @param righthand
	 */
	public Detector(NXTRegulatedMotor theMotor, UltrasonicSensor ear, TouchSensor lefthand, TouchSensor righthand)
	{
		motor = theMotor;    
		_ear = ear;
		_leftHand = lefthand;
		_rightHand = righthand;
	}

	/**
    * returns the angle in which the motor controlling the ultrasonic sensor is pointing
    * @return the angle
    */
   public int getHeadAngle()
   {
      return motor.getTachoCount();
   }
   
   /**
    * Returns the boolean _detected, which detects if an obstacle is within distance range
    * @param _detected
    * @return
    */   
   public boolean isDetected(boolean _detected)
   {
	   return _detected;
   }
   
   /**
    * The run() method tracks the distance between an object and the robot and the angle at which the object
    * is detected. If the distance between the obstacle and the robot is less than 3 feet or the left/right
    * touch sensors are pressed, then turn boolean _detected=false to _detected=true. This is constantly
    * tracked via a while loop.
    */
   public void run()
   {
      int obstacle_distance; //initialize obstacle_distance, the distance between the robot and an object
      _detected = false; //set boolean _detected to be false initially
      
      //this loop will be constantly running
      while(true)
      {
    	  obstacle_distance = _ear.getDistance(); //obstacle_distance gets the distance b/w robot and object
          _angle = motor.getTachoCount(); //_angle retrieves the angle of the motor at detection time
    	  /*if object is less than three feet away and the absolute value of _angle is less than 40,
    	   * or when the touch sensors are pressed, then change _detected to be true and this will affect
    	   * the methods in Racer.
    	   */
          if((obstacle_distance < 35&&Math.abs(_angle)<40) || _leftHand.isPressed() || _rightHand.isPressed())
    	  {
    		  _detected = true;	  
    	  }
    	  
      }  
   }

   /******* instance variabled ***************/
   NXTRegulatedMotor motor;
   UltrasonicSensor _ear;
   TouchSensor _leftHand;
   TouchSensor _rightHand;
   int _angle;
   boolean _detected = false;
}
