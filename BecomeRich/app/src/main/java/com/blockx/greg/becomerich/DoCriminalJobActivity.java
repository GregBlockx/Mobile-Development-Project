package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DoCriminalJobActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> jobList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_criminal_job);

        listview = (ListView) findViewById(R.id.listViewCriminalJobs);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        jobList.add(new Activity("Rob Homeless Person", 20));
        jobList.add(new Activity("Rob Local Person", 70));
        jobList.add(new Activity("Steal Bike", 200));
        jobList.add(new Activity("Deal Drugs", 350));
        jobList.add(new Activity("Sell Smuggled Goods", 700));
        jobList.add(new Activity("Kidnap Kid", 1100));
        jobList.add(new Activity("Assassinate Target", 1800));
        jobList.add(new Activity("Rob Rich Person", 10000));


        activityAdapter = new ActivityAdapter(this, R.layout.activityrow,jobList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                yourMoneyInt += Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                yourMoney.setText("€ " + yourMoneyInt);
            }
        });
    }

    public void goBackToWork(View view){
        Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startGoBackToWorkActivity);
    }
}
