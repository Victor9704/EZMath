package com.ezmath.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezmath.activities.OptionsActivity;
import com.ezmath.helpers.ButtonHelper;
import com.ezmath.main.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.LexerException;
import exceptions.ParserException;
import parser.JParser;

public class MainActivity extends AppCompatActivity {

    private final static String APP_DIRECTORY_NAME = "EZMath";
    private final static String APP_PREFERENCES_FILE = "EZMath_Preferences.txt";

    private ButtonHelper buttonHelper;

    //Used to prevent toast overlapping
    private Toast currentToast;

    private EditText editText;
    private int Precision = 8;//Standard result precision is 8

    private String expression = null;
    private String result;
    private int expressionPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request user permissions in runtime
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },100);

        //Get Extras
        Bundle bundle = getIntent().getExtras();

        //Get and set extras data
        if(bundle != null){//If Extras ----->
            //First retrieve the existent data
            retrieveData();

            if(bundle.get("expression") != null)
            this.expression = (String) bundle.get("expression");
            if(bundle.get("expressionPosition") != null)
            this.expressionPosition = (int) bundle.get("expressionPosition");

            //Change buttons or reset button names
            if(bundle.get("selectedOptionsButton") != null){
                if((int)bundle.get("selectedOptionsButton") == 1){
                    buttonHelper = new ButtonHelper();
                    setDefaultButtons((ArrayList<String>) buttonHelper.getDefaultButtonNamesList());

                    displayToastUnderActionBar("Buttons reset!");
                }
                else if((int)bundle.get("selectedOptionsButton") == 2){
                    setNewButtonPreferences(bundle);

                    displayToastUnderActionBar("Changes Saved!");
                }
                //Save data if Preferences changed when returning from Options
                saveData();
            }
        }

        //Set preferences when starting activity(Retrieved from Storage/EZMath/EZMath_Preferences)!
        retrieveData();

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

        //Save current Settings to Preferences file when finished creating
        saveData();

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

        //Close any toast before going to options
        if(currentToast !=null){
            currentToast.cancel();
        }

        startActivity(intent);
        finish();

        return true;
    }

    public void saveData(){
//        Log.d("MyApp","Saving Data");

        //Create list of buttons to store
        ArrayList<String> buttonListToSave;
        buttonListToSave = createButtonList();

        File EZMathFolder = new File(Environment.getExternalStorageDirectory(), APP_DIRECTORY_NAME);

        if(!EZMathFolder.exists()){
            EZMathFolder.mkdirs();
        }

        File storageFile = new File(EZMathFolder.getAbsolutePath(), APP_PREFERENCES_FILE);

        if(!storageFile.exists()){
            try {
                storageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(storageFile);
            for(String str : buttonListToSave){
                fileWriter.write(str);
                fileWriter.write(" ");
            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void retrieveData(){
//        Log.d("MyApp","Retrieving Data");
        ArrayList<String> buttonListToSave = new ArrayList<>();

        FileInputStream fileInputStream;
        File file = new File(Environment.getExternalStorageDirectory() + "/" + APP_DIRECTORY_NAME + "/" + APP_PREFERENCES_FILE);

        try {
            if(file != null) {
                Log.d("File", file.getAbsolutePath());
                fileInputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(fileInputStream);
                while(scanner.hasNext()){
                    buttonListToSave.add(scanner.next());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Log.d("MyApp","Here");
//
//        Log.d("MyApp", Integer.toString(buttonListToSave.size()));

        setSavedButtons(buttonListToSave);

    }

    public void onClick(View v){

        expression = editText.getText().toString();
        Button tempBtn = v.findViewById(v.getId());
        String tempBtnText = tempBtn.getText().toString().toLowerCase();

        if(tempBtn.getText().toString().toLowerCase().equals("\u232b")){
            tempBtnText = "delete";
        }

        if(tempBtn.getText().toString().toLowerCase().equals("\u221a")){
            tempBtnText = "sqrt";
        }

        if(tempBtn.getText().toString().toLowerCase().equals("e^x")){
            tempBtnText = "e^";
        }

        if(!tempBtnText.equals("clear") && !tempBtnText.equals("solve") && !tempBtnText.equals("delete")) {
            if (tempBtnText.length() == 1) {
                //If length 1 check for "0" as initial expression and if you have "0" and add "." to it
                if (expression.equals("0") && !tempBtnText.equals(".") && !tempBtnText.equals("+") && !tempBtnText.equals("-") && !tempBtnText.equals("*")
                        && !tempBtnText.equals("/")) {
                    result = tempBtnText;
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else if(expression.equals("0") && tempBtnText.equals(".") && tempBtnText.equals("+") && tempBtnText.equals("-") && tempBtnText.equals("*")
                        && tempBtnText.equals("/")){
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
        //TO DO - CATCH EXCEPTIONS AND DISPLPAY WRONG EXPRESSION MESSAGE TO USER
        else if(tempBtnText.equals("solve")){
            JParser jp = JParser.getInstance();

            jp.setConstantExpression();

                try {
                    jp.compileExpression(expression);
                }catch (Exception e){
                    displayToastUnderActionBar("Invalid Expression!");
                    e.printStackTrace();
//                    Log.d("My App", "Invalid expression");
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
                        //Compute precision based on the value of the precision defining variable
                        editText.setText(getResultWithPrecision(Precision,result), TextView.BufferType.EDITABLE);
                        expressionPosition = editText.getText().length();
                        editText.setSelection(expressionPosition);
                    }
                } catch (ParserException e) {
                    displayToastUnderActionBar("Invalid Expression!");
                    e.printStackTrace();
                }

        }
        else if(tempBtnText.equals("delete")){
            if(expressionPosition > 1) {//If there is more than 1 char in the expression string
                String result = deleteFromExpression(expression, expressionPosition);
                editText.setText(result, TextView.BufferType.EDITABLE);
                expressionPosition--;
                editText.setSelection(expressionPosition);
            }
            else if(expressionPosition == 1 && expression.length() == 1){//If there is only 1 element
                String result = "0";
                editText.setText(result, TextView.BufferType.EDITABLE);
                expressionPosition = 1;
                editText.setSelection(expressionPosition);
            }
            else if(expressionPosition == 1 && expression.length() > 1){
                String result = deleteFromExpression(expression, expressionPosition);
                editText.setText(result, TextView.BufferType.EDITABLE);
                expressionPosition = 1;
                editText.setSelection(expressionPosition);
            }
        }

        //Reset to new updated string, needed later
        expression = editText.getText().toString();

    }

    public int computeActionBarHeight(){
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();

        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        return actionBarHeight;
    }

    //Uses global var currentToast to avoid overlapping!
    public void displayToastUnderActionBar(String toastString){
        int actionBarHeight = computeActionBarHeight();

        //if already displaying, remove the one that is is displayed
        if(currentToast != null){
            currentToast.cancel();
            currentToast = null;
        }

        //Display message
        currentToast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG);
        currentToast.setGravity(Gravity.TOP,0, actionBarHeight + 10);
        currentToast.show();
    }

    public String concatExpression(String expresion, String toConcat, int expressionPosition){

        String result = expression.substring(0,expressionPosition) + toConcat + expression.substring(expressionPosition,expression.length());

        return result;
    }

    public String deleteFromExpression(String expression, int expressionPosition){

        StringBuilder result = new StringBuilder(expression);
        String finalResult = result.deleteCharAt(expressionPosition-1).toString();

        return finalResult;

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

    public void setDefaultButtons(ArrayList<String> defaultButtonNamesList) {
        Button temp;

        //Delete Button is non-modifiable/not removable
//        temp = findViewById(R.id.btnDelete);
//        temp.setText("\u232b");

        int i = 0;//used to concat the id for the buttons below
        for (String str : defaultButtonNamesList) {
            int id = getResources().getIdentifier("btn" + i, "id", getPackageName());
            temp = (Button) findViewById(id);
            temp.setText(str);
            i++;
        }
    }

    public String getResultWithPrecision(int precision, double result){

        //Create decimal format string
        String precisionString = "#";

        if(precision > 0){
            precisionString = precisionString.concat(".");
        }

        for(int i = 0;i<precision;i++){
            precisionString = precisionString.concat("#");
        }

        DecimalFormat dFormat = new DecimalFormat(precisionString);
        String finalResult = dFormat.format(result).toString();

        return finalResult;
    }

}
