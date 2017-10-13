package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseEducationActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> educationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);

        listview = (ListView) findViewById(R.id.listViewEducation);

        educationList.add(new Activity("Nothing", 0, true));
        educationList.add(new Activity("Secondary School", 100, false));
        educationList.add(new Activity("High School", 7500, false));
        educationList.add(new Activity("General Training", 15000, false));
        educationList.add(new Activity("College", 25000, false));
        educationList.add(new Activity("Master's Degree", 100000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, educationList);
        listview.setAdapter(activityAdapter);
    }

    public void goBackToEducation(View view){
        Intent startGoBackToEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startGoBackToEducationActivity);
    }
}
