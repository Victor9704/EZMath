package com.ezmath.helpers;

import android.view.View;
import android.widget.Button;

import com.ezmath.main.ezmath.R;

/**
 * Created by Victor on 27.04.2018.
 */

public class ButtonHelper {

    private View view;

    public ButtonHelper(View view){
        this.view = view;
    }

    public void setButtonFunction(String functionName, int buttonID){
        Button tempBtn = view.findViewById(buttonID);
        tempBtn.setText(functionName);
    }

    public void setDefaultButton(int id){

    }


}
