package com.example.victor.ezmath;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import exceptions.LexerException;
import exceptions.ParserException;
import parser.JParser;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String expression;
    boolean operation = false;
    String result;
    int expressionPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText.setSelection(expressionPosition);
        editText.setClickable(true);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionPosition = editText.getSelectionEnd();
                //String hehe = editText.getSelectionEnd() + "";
                //Log.d("Position", hehe);
            }
        });

    }

    public void onClick(View v) {

        expression = editText.getText().toString();

        switch (v.getId()) {
            case R.id.btn0:
                if(!expression.equals("0")) {
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "0" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn1:
                if(expression.equals("0")) {
                    result = "1";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "1" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn2:
                if(expression.equals("0")) {
                    result = "2";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "2" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn3:
                if(expression.equals("0")) {
                    result = "3";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "3" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn4:
                if(expression.equals("0")) {
                    result = "4";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "4" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn5:
                if(expression.equals("0")) {
                    result = "5";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "5" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn6:
                if(expression.equals("0")) {
                    result = "6";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "6" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn7:
                if(expression.equals("0")) {
                    result = "7";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "7" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn8:
                if(expression.equals("0")) {
                    result = "8";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "8" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btn9:
                if(expression.equals("0")) {
                    result = "9";
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                else{
                    expressionPosition++;
                    result = expression.substring(0, editText.getSelectionEnd()) + "9" + expression.substring(editText.getSelectionEnd(),expression.length());
                    editText.setText(result, TextView.BufferType.EDITABLE);
                    editText.setSelection(expressionPosition);
                }
                break;
            case R.id.btnAdd:

                expressionPosition += 3;
                result = expression.concat(" + ");
                editText.setText(result, TextView.BufferType.EDITABLE);
                editText.setSelection(editText.getText().length());

                break;

//                if(operation == true){
//                    Double result = additionFunc();
//                    editText.setText(result.toString(), TextView.BufferType.EDITABLE);
//                    editText.setSelection(editText.getText().length());
//                    operation = false;
//                    break;
//                }
//
//                operation = true;
//
//                result = expression.concat("\n + \n");
//                editText.setText(result, TextView.BufferType.EDITABLE);
//                editText.setSelection(editText.getText().length());
//                break;
            case R.id.btnCOS:

                expressionPosition += 4;
                result = expression.concat("cos()");
                editText.setText(result.toString(), TextView.BufferType.EDITABLE);
                editText.setSelection(expressionPosition);
                break;

            case R.id.btnSIN:

                expressionPosition += 4;
                result = expression.concat("sin()");
                editText.setText(result.toString(), TextView.BufferType.EDITABLE);
                editText.setSelection(expressionPosition);
                break;

            case R.id.btnSOLVE:


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
                    editText.setText(result.toString(), TextView.BufferType.EDITABLE);
                    expressionPosition = editText.getText().length();
                    editText.setSelection(expressionPosition);
                } catch (ParserException e) {
                    e.printStackTrace();
                }

//                DoubleEvaluator evaluator = new DoubleEvaluator();
//
//                Double result = evaluator.evaluate(expression);
//                editText.setText(result.toString(), TextView.BufferType.EDITABLE);
//                expressionPosition = editText.getText().length();
//                editText.setSelection(expressionPosition);

                break;

            case R.id.btnCLEAR:

                editText.setText("0");
                expressionPosition = 1;
                editText.setSelection(expressionPosition);
                break;
        }

    }

    public double additionFunc() {

        String [] parts = expression.split("\\+",2);
        String left = parts[0];
        String right = parts[1];

        Double result = Double.parseDouble(left) + Double.parseDouble(right);

        return result;
    }

    public double COSINE(){

        return 0;
    }
}
