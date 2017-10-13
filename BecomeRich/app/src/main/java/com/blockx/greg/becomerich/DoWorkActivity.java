package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class DoWorkActivity extends AppCompatActivity {
    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> jobList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_work);

        listview = (ListView) findViewById(R.id.listViewJobs);

        jobList.add(new Activity("Beg", 1));
        jobList.add(new Activity("Wash Cars", 5));
        jobList.add(new Activity("Bartender", 20));
        jobList.add(new Activity("Deliver Mail", 50));
        jobList.add(new Activity("Deliver Packages", 75));
        jobList.add(new Activity("Work in Factory", 100));
        jobList.add(new Activity("Bank Clerk", 250));
        jobList.add(new Activity("Office Manager", 500));
        jobList.add(new Activity("Booze Shop Owner", 1000));
        jobList.add(new Activity("Supermarket Owner", 2000));
        jobList.add(new Activity("E-Commerce Shop Owner", 3000));
        jobList.add(new Activity("Businessman", 5000));


        activityAdapter = new ActivityAdapter(this, R.layout.activityrow,jobList);
        listview.setAdapter(activityAdapter);
    }

  public void goBackToWork(View view){
      Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
      startActivity(startGoBackToWorkActivity);
  }
}
