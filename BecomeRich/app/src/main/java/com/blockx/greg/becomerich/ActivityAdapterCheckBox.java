package com.blockx.greg.becomerich;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bryan on 13/10/2017.
 */

public class ActivityAdapterCheckBox extends ArrayAdapter<String>{


    private ArrayList<Activity> items = new ArrayList<>();
    private String concat = "€ ";

    public ActivityAdapterCheckBox(Context context, int resource, ArrayList<Activity> objects) {
        super(context, resource);
        items = objects;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activityrow_checkbox,null);
        TextView textView = v.findViewById(R.id.textViewActivity_CheckBox);
        TextView textView2 = v.findViewById(R.id.textViewMoney_CheckBox);
        CheckBox checkBox = v.findViewById(R.id.checkBoxHaveItem);
        textView.setText(items.get(position).getActivityName());
        textView2.setText(concat + items.get(position).getActivityAmount());
        checkBox.setChecked(items.get(position).isHaveBought());

        if(checkBox.isChecked())
        {
            checkBox.setEnabled(false);
        }

        return v;
    }

}
