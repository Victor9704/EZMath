package com.ezmath.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ezmath.helpers.ButtonHelper;
import com.ezmath.main.ezmath.R;
import com.ezmath.objects.ButtonDetails;

import java.util.List;

/**
 * Created by Victor on 29.04.2018.
 */

public class FunctionListAdapter extends BaseAdapter {

    private Context context;
    private ButtonHelper buttonHelper;
    private List<ButtonDetails> buttonList;

    public FunctionListAdapter(Context context, List<ButtonDetails> buttonList) {
        this.context = context;
        this.buttonList = buttonList;
    }

    @Override
    public int getCount() {
        return buttonList.size();
    }

    @Override
    public Object getItem(int position) {
        return buttonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item_function_list, null);
        TextView btnNumber = (TextView) v.findViewById(R.id.btnNumber);
        TextView btnName = (TextView) v.findViewById(R.id.btnName);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);

        btnNumber.setText(buttonList.get(position).getButtonID());
        btnName.setText(buttonList.get(position).getButtonName());
        buttonHelper = new ButtonHelper();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, buttonHelper.getButtonNamesList());
        spinner.setAdapter(adapter);

        v.setTag(buttonList.get(position).getButtonID());

        Log.d("awd", "populating");

        return v;
    }
}
