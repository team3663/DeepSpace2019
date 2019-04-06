/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

import java.util.ArrayList;
import java.util.Iterator;

import edu.wpi.first.wpilibj.buttons.Button;
import frc.robot.util.Stopwatch;

/**
 * Add your docs here.
 */
public class ButtonTracker {

    private ArrayList<Button> buttons = new ArrayList<Button>();
    private ArrayList<Number> expirationList = new ArrayList<Number>();
    private int expirationTime; 
    public ButtonTracker(int expirationTime){
        this.expirationTime = expirationTime; 

    }



    public void addButton(Button button, Number expiration){
        buttons.add(button);
        expirationList.add(expiration);
    }

    public void pruneButtons(){

        Iterator<Number> itr = expirationList.iterator();
        while (itr.hasNext()) {
          Number num = itr.next();
          if(num.doubleValue() > expirationTime){
            removeButton(expirationList.indexOf(num));
          }
        }
    }

    public void removeButton(int index){
        buttons.remove(index);
        expirationList.remove(index);
    }

    public void updateTimes(long delta){
        for(int i = 0; i < expirationList.size(); i++){
            expirationList.set(i, expirationList.get(i).longValue() + delta);
        }
    }

    public boolean equal(ArrayList<Button> btns){
        
        pruneButtons();
        for(int i = 0; i < buttons.size(); i++){
            if(!btns.get(i).equals(buttons.get(i))){
                return false;
            }
        }
        return true;

    }
}
