package com.ezmath.helpers;

import android.view.View;
import android.widget.Button;

import com.ezmath.main.ezmath.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Victor on 27.04.2018.
 */

public class ButtonHelper {

    private View view;

    private static List<String> buttonNamesList = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+",
            "-", "*", "/", ".", "(", ")", "^", "e^x","sin", "cos", "tan", "sec", "csc", "ctg", "asin", "acos", "atan", "sinh", "cosh", "tanh", "ln", "sqrt", "solve", "clear"));

    public ButtonHelper(View view){
        this.view = view;
    }

    public ButtonHelper(){

    }

    public void setButtonFunction(String functionName, int buttonID){
        Button tempBtn = view.findViewById(buttonID);
        tempBtn.setText(functionName);
    }

    public void setDefaultButtons(){
        Button tempBtn = null;
        tempBtn = view.findViewById(R.id.btn0);
        tempBtn.setText("clear");

    }

    public List<String> getButtonNamesList(){
        return this.buttonNamesList;
    }


}
