package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseResidencyActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> residencyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_residency);

        listview = (ListView) findViewById(R.id.listViewResidency);

        residencyList.add(new Activity("Sleeping bag", 0, true));
        residencyList.add(new Activity("Rent Basement", 70, false));
        residencyList.add(new Activity("Rent Apartment", 500, false));
        residencyList.add(new Activity("Buy Apartment", 40000, false));
        residencyList.add(new Activity("Buy Penthouse", 150000, false));
        residencyList.add(new Activity("Buy Mansion", 500000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, residencyList);
        listview.setAdapter(activityAdapter);
    }

    public void goBackToMarket(View view){
        Intent startGoBackToMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startGoBackToMarketActivity);
    }
}
