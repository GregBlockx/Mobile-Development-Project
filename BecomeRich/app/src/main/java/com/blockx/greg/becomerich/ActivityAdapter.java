package com.blockx.greg.becomerich;

import android.content.Context;
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
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activityrow,null);
        TextView textView = (TextView) v.findViewById(R.id.text);
        TextView textView2 = (TextView) v.findViewById(R.id.text2);
        textView.setText(items.get(position).getActivityName());
        textView2.setText(concat + items.get(position).getActivityAmount());
        return v;
    }
}

