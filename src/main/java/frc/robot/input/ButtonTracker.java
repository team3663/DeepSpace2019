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

/**
 * A class to track button presses for comparison in combo buttons
 *
 * @author Mark Riise
 * @since 1.1
 */
public class ButtonTracker {

    private ArrayList<Button> buttons = new ArrayList<Button>();
    private ArrayList<Number> expirationList = new ArrayList<Number>();
    private ArrayList<Number> timers = new ArrayList<Number>();
    private long expirationTime; 
    private long timer = 0;
    

    public ButtonTracker(int expirationTime){
        this.expirationTime = expirationTime; 
    }

    public void addButton(Button button, Number expiration){
        buttons.add(button);
        expirationList.add(expiration);
        timers.add(0);
    }
    public void removeButton(int index){
        buttons.remove(index);
        expirationList.remove(index);
        timers.remove(index);
    }

    public void pruneButtons(){
        if(timer < expirationTime){
            for(int i = 0; i < timers.size(); i++){
                if(timers.get(i).doubleValue() > expirationList.get(i).doubleValue()){
                    removeButton(i);
                }
            }
        }
        else{
            clear();
            timer = 0;
        }
    }

    public void clear(){
        buttons.clear();
        expirationList.clear();
        timers.clear();
    }

    public void updateTimes(long delta){
        timer += delta;
        for(int i = 0; i < timers.size(); i++){
            timers.set(i, timers.get(i).longValue() + delta);
        }
    }

    public ArrayList<Button> get(){
        pruneButtons();
        return buttons;
    }
}
