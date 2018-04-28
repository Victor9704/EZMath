package com.ezmath.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.ezmath.main.ezmath.R;

import java.text.DecimalFormat;

import exceptions.LexerException;
import exceptions.ParserException;
import parser.JParser;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    EditText editText;

    String expression;
    String result;
    int expressionPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Edit Text
        editText = findViewById(R.id.editText);
        editText.setSelection(expressionPosition);
        editText.setClickable(true);
        editText.setShowSoftInputOnFocus(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionPosition = editText.getSelectionEnd();
                //String hehe = editText.getSelectionEnd() + "";
                //Log.d("Position", hehe);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onClick(View v){

        expression = editText.getText().toString();
        Button tempBtn = v.findViewById(v.getId());
        String tempBtnText = tempBtn.getText().toString().toLowerCase();

        if(tempBtn.getText().toString().toLowerCase().equals("\u221a")){
            tempBtnText = "sqrt";
        }

        if(!tempBtnText.equals("clear") && !tempBtnText.equals("solve")) {
            if (tempBtnText.length() == 1) {
                if (expression.equals("0")) {
                    result = tempBtnText;
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                } else {
                    result = concatExpression(expression, tempBtnText, expressionPosition);
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    expressionPosition++;
                    editText.setSelection(expressionPosition);
                }
             //If Function
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
                    Double result = jp.evaluate();

                    //Remove Trailing 0's
                    DecimalFormat dFormat = new DecimalFormat("0.#");

                    editText.setText(dFormat.format(result).toString(), TextView.BufferType.EDITABLE);
                    expressionPosition = editText.getText().length();
                    editText.setSelection(expressionPosition);
                } catch (ParserException e) {
                    e.printStackTrace();
                }

        }

    }


//    public void onClick(View v) {
//
//        expression = editText.getText().toString();
//        Button tempBtn = v.findViewById(v.getId());
//
//        switch (v.getId()) {
//            case R.id.btn0:
//                if(!expression.equals("0")) {
//                    result = concatExpression(expression,"0", expressionPosition);
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn1:
//                if(expression.equals("0")) {
//                    result = "1";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = concatExpression(expression,"1", expressionPosition);
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn2:
//                if(expression.equals("0")) {
//                    result = "2";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = expression.substring(0, editText.getSelectionEnd()) + "2" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn3:
//                if(expression.equals("0")) {
//                    result = "3";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = expression.substring(0, editText.getSelectionEnd()) + "3" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn4:
//                if(expression.equals("0")) {
//                    result = "4";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = expression.substring(0, editText.getSelectionEnd()) + "4" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn5:
//                if(expression.equals("0")) {
//                    result = "5";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = expression.substring(0, editText.getSelectionEnd()) + "5" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn6:
//                if(expression.equals("0")) {
//                    result = "6";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = expression.substring(0, editText.getSelectionEnd()) + "6" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn7:
//                if(expression.equals("0")) {
//                    result = "7";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = expression.substring(0, editText.getSelectionEnd()) + "7" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn8:
//                if(expression.equals("0")) {
//                    result = "8";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    expressionPosition++;
//                    result = expression.substring(0, editText.getSelectionEnd()) + "8" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btn9:
//                if(expression.equals("0")) {
//                    result = "9";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    editText.setSelection(expressionPosition);
//                }
//                else{
//                    result = expression.substring(0, editText.getSelectionEnd()) + "9" + expression.substring(editText.getSelectionEnd(),expression.length());
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition++;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btnAdd:
//
//                expressionPosition += 3;
//                result = expression.concat(" + ");
//                editText.setText(result, TextView.BufferType.EDITABLE);
//                editText.setSelection(editText.getText().length());
//
//                break;
//
//            case R.id.btnCOS:
//                if(expression.equals("0")) {
//                    result = "cos()";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition = 4;
//                    editText.setSelection(expressionPosition);
//                }
//                else {
//                    //result = expression.concat("cos()");
//                    result = expression.substring(0,expressionPosition) + "cos()" + expression.substring(expressionPosition,expression.length());
//                    editText.setText(result.toString(), TextView.BufferType.EDITABLE);
//                    expressionPosition += 4;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btnSIN:
//                if(expression.equals("0")) {
//                    result = tempBtn.getText().toString().toLowerCase() + "()";
//                    editText.setText(result, TextView.BufferType.EDITABLE);
//                    expressionPosition = 4;
//                    editText.setSelection(expressionPosition);
//                }else {
//                    result = concatExpression(expression,"sin()", expressionPosition);
//                    editText.setText(result.toString(), TextView.BufferType.EDITABLE);
//                    expressionPosition += 4;
//                    editText.setSelection(expressionPosition);
//                }
//                break;
//
//            case R.id.btnSOLVE:
//
//
//                JParser jp = JParser.getInstance();
//
//                try {
//                    jp.compileExpression(expression);
//                } catch (LexerException e) {
//                    e.printStackTrace();
//                } catch (ParserException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    Double result = jp.evaluate();
//                    editText.setText(result.toString(), TextView.BufferType.EDITABLE);
//                    expressionPosition = editText.getText().length();
//                    editText.setSelection(expressionPosition);
//                } catch (ParserException e) {
//                    e.printStackTrace();
//                }
//
//                break;
//
//            case R.id.btnCLEAR:
//
//                editText.setText("0");
//                expressionPosition = 1;
//                editText.setSelection(expressionPosition);
//                break;
//        }
//
//    }

    public String concatExpression(String expresion, String toConcat, int expressionPosition){

        String result = expression.substring(0,expressionPosition) + toConcat + expression.substring(expressionPosition,expression.length());

        return result;
    }
}
