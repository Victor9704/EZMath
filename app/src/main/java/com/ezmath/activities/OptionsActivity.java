package com.ezmath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ezmath.adapters.FunctionListAdapter;
import com.ezmath.helpers.ButtonHelper;
import com.ezmath.main.MainActivity;
import com.ezmath.main.ezmath.R;
import com.ezmath.objects.ButtonDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Victor on 28.04.2018.
 */

public class OptionsActivity extends AppCompatActivity {

    private ButtonHelper buttonHelper;
    private ListView listView;
    private FunctionListAdapter functionListAdapter;

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
        }

        buttonHelper = new ButtonHelper();

        //Create Button Details object list
        List<ButtonDetails> buttonDetailsList = new ArrayList<>();
        int count = 0;
        for(String i : btnList){
            ButtonDetails temp = new ButtonDetails("Button " + count, btnList.get(count), "Select");
            buttonDetailsList.add(temp);
            count++;
        }
        //Log.d("awd", btnList.get(15));


        listView = (ListView) findViewById(R.id.list_view);
        functionListAdapter = new FunctionListAdapter(getApplicationContext(), buttonDetailsList);
        listView.setAdapter(functionListAdapter);

        //Creating adapter for the spinner
//        spinner = findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, buttonHelper.getButtonNamesList());
//        spinner.setAdapter(adapter);

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
                startActivity(intent);
                finish();
                break;
        }

        return true;
    }
}
