package com.ezmath.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ezmath.helpers.ButtonHelper;
import com.ezmath.main.R;
import com.ezmath.objects.RowButtonDetails;

import java.util.List;

/**
 * Created by Victor on 29.04.2018.
 */

public class FunctionListAdapter extends BaseAdapter {

    private Context context;
    private ButtonHelper buttonHelper;
    private List<RowButtonDetails> buttonList;

    public FunctionListAdapter(Context context, List<RowButtonDetails> buttonList) {
        this.context = context;
        this.buttonList = buttonList;
        notifyDataSetChanged();
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

    private class ViewHolder{
        public TextView btnNumber;
        public TextView btnName;
        public Spinner spinner;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_function_list, null);

            viewHolder = new ViewHolder();
            viewHolder.btnNumber = (TextView) convertView.findViewById(R.id.btnNumber);
            viewHolder.btnName = (TextView) convertView.findViewById(R.id.btnName);
            viewHolder.spinner = (Spinner) convertView.findViewById(R.id.spinner);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Setting items
        viewHolder.btnNumber.setText(buttonList.get(position).getButtonID());
        viewHolder.btnName.setText(buttonList.get(position).getButtonName());
        //Creating adapter for the spinner
        buttonHelper = new ButtonHelper();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, buttonHelper.getButtonNamesList());
        viewHolder.spinner.setAdapter(adapter);

        //Create on select listener, which saves the selected item position from the spinner to the specific row item (position of the buttonList item in the list)
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int spinnerSelectPosition, long l) {
                //Save the selected item position to the item on the specific row(row represented by posiiton and selected item position from spinner represented by spinnerSelectPosition)
                buttonList.get(position).setSpinnerSelectedPosition(spinnerSelectPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewHolder.spinner.setSelection(buttonList.get(position).getSpinnerSelectedPosition());

        return convertView;
    }
}
