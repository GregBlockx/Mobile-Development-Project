package com.blockx.greg.becomerich;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bryan on 10/10/2099.
 */

public class ActivityAdapter extends ArrayAdapter<String> {

    ArrayList<Activity> items = new ArrayList<>();
    String concat = "€ ";
    public ActivityAdapter(Context context, int resource, ArrayList<Activity> objects) {
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
        v = inflater.inflate(R.layout.activityrow,null);
        TextView textView = v.findViewById(R.id.textViewActivity);
        TextView textView2 = v.findViewById(R.id.textViewMoney);
        textView.setText(items.get(position).getActivityName());
        textView2.setText(concat + items.get(position).getActivityAmount());
        return v;
    }

    //When you click on an item in a listview this will return the money you get or have to pay

    @Nullable
    @Override
    public String getItem(int position){
        return String.valueOf(items.get(position).getActivityAmount());
    }
}

