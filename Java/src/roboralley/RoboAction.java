/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roboralley;

import java.util.logging.Logger;

/**
 *
 * @author Christian Flach <cmfcmf.flach@gmail.com>
 */
public class RoboAction {

    /**
     * Command to send to NXT.
     */
    public String command;
    
    /**
     * Localized display name.
     */
    public String displayName;
    
    public int minNumber;
    
    public int maxNumber;
    
    public int frequency;

    public RoboAction(String command, String displayName, int minNumber, int maxNumber, int frequency) {
        this.command = command;
        this.displayName = displayName;
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.frequency = frequency;
    }
    
    public RoboAction(String command, String displayName) {
        this.command = command;
        this.displayName = displayName;
    }
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }    
}
