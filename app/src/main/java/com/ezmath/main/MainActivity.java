package com.ezmath.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ezmath.activities.OptionsActivity;
import com.ezmath.helpers.ButtonHelper;
import com.ezmath.main.R;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import exceptions.LexerException;
import exceptions.ParserException;
import parser.JParser;

public class MainActivity extends AppCompatActivity {

    private ButtonHelper buttonHelper;

    private EditText editText;
    private int Precision;

    private String expression = null;
    private String result;
    private int expressionPosition = 1;

    private ArrayList<String> buttonListToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TO DO save data and recover on app restart using files!

        Bundle bundle = getIntent().getExtras();

        //Get and set extras data
        if(bundle != null){

            if(bundle.get("expression") != null)
            this.expression = (String) bundle.get("expression");
            if(bundle.get("expressionPosition") != null)
            this.expressionPosition = (int) bundle.get("expressionPosition");

            //Change buttons or reset button names
            if(bundle.get("selectedOptionsButton") != null){
                if((int)bundle.get("selectedOptionsButton") == 1){
                    //TO DO IMPLEMENT AND CALL RESET FROM HELPER
                    Log.d("MyApp","Options requested button reset!");

                }
                else if((int)bundle.get("selectedOptionsButton") == 2){
                    setNewButtonPreferences(bundle);
                }
            }
        }

        //Set Edit Text
        editText = findViewById(R.id.editText);
        if(expression != null){
            editText.setText(expression, TextView.BufferType.EDITABLE);
        }else{
            expression = editText.getText().toString();//Get the initial 0
        }

        editText.setSelection(expressionPosition);
        editText.setClickable(true);
        editText.setShowSoftInputOnFocus(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionPosition = editText.getSelectionEnd();
                //Log.d("Position", Integer.ToString(editText.getSelectionEnd()));
            }
        });

        //Create list of buttons to store
        buttonListToSave = createButtonList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Create list of buttons
        ArrayList<String> btnList = createButtonList();

        Intent intent = new Intent(this, OptionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//makes sure can't go back to it while pressing back button

        //Put Extra data
        intent.putStringArrayListExtra("btnList", (ArrayList<String>) btnList);
        intent.putExtra("expression", this.expression);
        intent.putExtra("expressionPosition", this.expressionPosition);

        startActivity(intent);
        finish();

        return true;
    }

    public void onClick(View v){

        expression = editText.getText().toString();
        Button tempBtn = v.findViewById(v.getId());
        String tempBtnText = tempBtn.getText().toString().toLowerCase();

        if(tempBtn.getText().toString().toLowerCase().equals("\u221a")){
            tempBtnText = "sqrt";
        }

        if(tempBtn.getText().toString().toLowerCase().equals("e^x")){
            tempBtnText = "e^";
        }

        if(!tempBtnText.equals("clear") && !tempBtnText.equals("solve")) {
            if (tempBtnText.length() == 1) {
                //If length 1 check for "0" as initial expression and if you have "0" and add "." to it
                if (expression.equals("0") && !tempBtnText.equals(".")) {
                    result = tempBtnText;
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else if(expression.equals("0") && tempBtnText.equals(".")){
                    result = "0" + tempBtnText;
                    expressionPosition++;//For "0." string length is 2
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else {
                    result = concatExpression(expression, tempBtnText, expressionPosition);
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    expressionPosition++;
                    editText.setSelection(expressionPosition);
                }
             //If Function ex sin, cos etc...
            } else {
                if (expression.equals("0")) {
                    result = tempBtnText + "()";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    expressionPosition = 0;
                    expressionPosition += tempBtnText.length() + 1;
                    editText.setSelection(expressionPosition);
                } else {
                    result = expression.substring(0, expressionPosition) + tempBtnText + "()" + expression.substring(expressionPosition, expression.length());
                    editText.setText(result.toString(), TextView.BufferType.EDITABLE);
                    expressionPosition += tempBtnText.length() + 1;
                    editText.setSelection(expressionPosition);
                }
            }
        }
        else if(tempBtnText.equals("clear")){
            editText.setText("0");
            expressionPosition = 1;
            editText.setSelection(expressionPosition);

        }
        else if(tempBtnText.equals("solve")){
            JParser jp = JParser.getInstance();

                try {
                    jp.compileExpression(expression);
                } catch (LexerException e) {
                    e.printStackTrace();
                } catch (ParserException e) {
                    e.printStackTrace();
                }

                try {
                    DecimalFormat dFormat;

                    Double result = jp.evaluate();

                    //Remove Trailing 0 from 0.0
                    if(result % 1 == 0) {
                        dFormat = new DecimalFormat("0.#");
                        editText.setText(dFormat.format(result).toString(), TextView.BufferType.EDITABLE);
                        expressionPosition = editText.getText().length();
                        editText.setSelection(expressionPosition);
                    }else {
                        editText.setText(result.toString(), TextView.BufferType.EDITABLE);
                        expressionPosition = editText.getText().length();
                        editText.setSelection(expressionPosition);
                    }
                } catch (ParserException e) {
                    e.printStackTrace();
                }

        }

        //Reset to new updated string, needed later
        expression = editText.getText().toString();

    }

    public String concatExpression(String expresion, String toConcat, int expressionPosition){

        String result = expression.substring(0,expressionPosition) + toConcat + expression.substring(expressionPosition,expression.length());

        return result;
    }

    public ArrayList<String> createButtonList(){

        List<String> btnList = new ArrayList<String>();
        Button temp;
        for (int i = 0; i < 25; i++) {
            int id = getResources().getIdentifier("btn"+i, "id", getPackageName());
            temp = (Button) findViewById(id);
            btnList.add(temp.getText().toString().toLowerCase());
        }

        return (ArrayList<String>) btnList;
    }

    public void setNewButtonPreferences(Bundle bundle){

        ArrayList<String> optionsButtonList = bundle.getStringArrayList("newPreferenceArray");
        Button temp;

        for(int i = 0; i< optionsButtonList.size(); i++){
            int id = getResources().getIdentifier("btn"+i, "id", getPackageName());
            temp = (Button) findViewById(id);
            if(!optionsButtonList.get(i).equals("Select")){
                temp.setText(optionsButtonList.get(i));
            }
        }
    }

    public void setSavedButtons(ArrayList<String> buttonListToSave){

        Button temp;

        for(int i = 0; i< buttonListToSave.size(); i++){
            int id = getResources().getIdentifier("btn"+i, "id", getPackageName());
            temp = (Button) findViewById(id);
            temp.setText(buttonListToSave.get(i));
        }
    }

}
