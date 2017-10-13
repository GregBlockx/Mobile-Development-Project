package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseTransportActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> transportList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_transport);

        listview = (ListView) findViewById(R.id.listViewTransport);

        transportList.add(new Activity("Foot", 0, true));
        transportList.add(new Activity("Shoes", 40, false));
        transportList.add(new Activity("Bicycle", 350, false));
        transportList.add(new Activity("Car", 5000, false));
        transportList.add(new Activity("Large Truck", 20000, false));
        transportList.add(new Activity("Limo", 70000, false));
        transportList.add(new Activity("Helicopter", 200000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, transportList);
        listview.setAdapter(activityAdapter);
    }

    public void goBackToMarket(View view){
        Intent startGoBackToMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startGoBackToMarketActivity);
    }
}
