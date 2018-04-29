package com.ezmath.objects;

/**
 * Created by Victor on 29.04.2018.
 */

public class ButtonDetails {

    private String buttonID;
    private String buttonName;
    private String newButtonName;

    public ButtonDetails(String buttonID, String buttonName, String newButtonName) {
        this.buttonID = buttonID;
        this.buttonName = buttonName;
        this.newButtonName = newButtonName;
    }

    public String getButtonID() {
        return buttonID;
    }

    public void setButtonID(String buttonID) {
        this.buttonID = buttonID;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getNewButtonName() {
        return newButtonName;
    }

    public void setNewButtonName(String newButtonName) {
        this.newButtonName = newButtonName;
    }
}
