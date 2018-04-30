package com.ezmath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.ezmath.adapters.FunctionListAdapter;
import com.ezmath.helpers.ButtonHelper;
import com.ezmath.main.MainActivity;
import com.ezmath.main.R;
import com.ezmath.objects.RowButtonDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 28.04.2018.
 */

public class OptionsActivity extends AppCompatActivity {

    private ButtonHelper buttonHelper;
    private ListView listView;
    private FunctionListAdapter functionListAdapter;

    //"Cached" from MainActivity
    private String expression;
    private int expressionPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);

        setTitle("Options");

        //Back Button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //get extras
        Bundle bundle = getIntent().getExtras();
        List<String> btnList = new ArrayList<>();

        if(bundle != null){
            btnList = (List<String>) bundle.get("btnList");
            this.expression = (String) bundle.get("expression");
            this.expressionPosition = (int) bundle.get("expressionPosition");
//            Log.d("Expression Position", Integer.toString(expressionPosition));
        }

        buttonHelper = new ButtonHelper();

        //Create Button Details object list
        List<RowButtonDetails> buttonDetailsList = new ArrayList<>();
        int count = 0;
        for(String i : btnList){
            RowButtonDetails temp = new RowButtonDetails("Button " + (count+1), btnList.get(count), 0);
            buttonDetailsList.add(temp);
            count++;
        }

        listView = (ListView) findViewById(R.id.list_view);
        functionListAdapter = new FunctionListAdapter(getApplicationContext(), buttonDetailsList);
        listView.setAdapter(functionListAdapter);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//makes sure can't go back to it while pressing back button

                //Put extras (in this case only the expression)
                intent.putExtra("expression",expression);
                intent.putExtra("expressionPosition", expressionPosition);

                startActivity(intent);
                finish();
                break;
        }

        return true;
    }

    public void onClick(View view){

        Intent intent;

        switch (view.getId()) {
            case R.id.btnReset:
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//makes sure can't go back to it while pressing back button

                //Put extras (in this case only the expression)
                intent.putExtra("expression", expression);
                intent.putExtra("expressionPosition", expressionPosition);
                intent.putExtra("selectedOptionsButton", 1);

                startActivity(intent);
                finish();

                break;

            case R.id.btnSave:
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//makes sure can't go back to it while pressing back button

                //Create new button preferences array
                List<String> newPreferencesButtonList = createNewPreferencesArray();

                //Put extras (in this case only the expression)
                intent.putExtra("expression", expression);
                intent.putExtra("expressionPosition", expressionPosition);
                intent.putExtra("selectedOptionsButton", 2);
                intent.putStringArrayListExtra("newPreferenceArray", (ArrayList<String>) newPreferencesButtonList);


                startActivity(intent);
                finish();

                break;

        }
    }

    public List<String> createNewPreferencesArray(){

        List<String> newPreferencesButtonList = new ArrayList<>();
        ButtonHelper buttonHelper = new ButtonHelper();

        for(int i =0; i<listView.getAdapter().getCount(); i++){
            String newButtonPreference = buttonHelper.getButtonNamesList().get(((RowButtonDetails)listView.getAdapter().getItem(i)).getSpinnerSelectedPosition());
            newPreferencesButtonList.add(newButtonPreference);
        }

        return newPreferencesButtonList;
    }
}
