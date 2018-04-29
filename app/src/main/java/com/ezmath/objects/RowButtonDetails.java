package com.ezmath.objects;

/**
 * Created by Victor on 29.04.2018.
 */

public class RowButtonDetails {

    private String buttonID;
    private String buttonName;
    private int spinnerSelectedPosition;

    public RowButtonDetails(String buttonID, String buttonName, int selected) {
        this.buttonID = buttonID;
        this.buttonName = buttonName;
        this.spinnerSelectedPosition = selected;//Initialized with 0 in OptionsActivity, representing "Selected" in function List from ButtonHelper
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

    public int getSpinnerSelectedPosition() {
        return spinnerSelectedPosition;
    }

    public void setSpinnerSelectedPosition(int spinnerSelectedPosition) {
        this.spinnerSelectedPosition = spinnerSelectedPosition;
    }
}
