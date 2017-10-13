package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class HealthActivity extends AppCompatActivity {


    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> healthList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        listview = (ListView) findViewById(R.id.listViewHealth);

        healthList.add(new Activity("Sleep on road", 0));
        healthList.add(new Activity("Take a Pill", 2));
        healthList.add(new Activity("Go to Small Clinic", 5));
        healthList.add(new Activity("Go to PolyClinic", 20));
        healthList.add(new Activity("Go to Local Doctor", 65));
        healthList.add(new Activity("Go to Hospital", 120));
        healthList.add(new Activity("Go to World Class Hospital", 200));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, healthList);
        listview.setAdapter(activityAdapter);
    }

    public void goBackToScreen(View view){
        Intent startGoBackToScreenActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToScreenActivity);
    }
}
