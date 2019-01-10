/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public class RobotMap {



/*
 *  front
 * 0 ---- 1
 * |      |
 * |      |
 * 2 ---- 3
 */

  public static final int[] getDriveMotors(){
    int[] motors= {1,3,5,7};
    return motors;
  }
  public static final int[] getAngleMotors(){
    
    int[] motors= {2,4,5,8};
    return motors;
  }

 

  
}
