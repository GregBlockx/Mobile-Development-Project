package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseCriminalSkillsActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> skillsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_criminal_skills);

        listview = (ListView) findViewById(R.id.listViewCriminalSkills);

        skillsList.add(new Activity("Weapon Skills Beginner", 100, false));
        skillsList.add(new Activity("Weapon Skills Intermediate", 600, false));
        skillsList.add(new Activity("Weapon Skills Advanced", 2200, false));
        skillsList.add(new Activity("Thief Skills Beginner", 75, false));
        skillsList.add(new Activity("Thief Skills Intermediate", 500, false));
        skillsList.add(new Activity("Thief Skills Advanced", 2000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, skillsList);
        listview.setAdapter(activityAdapter);
    }

    public void goBackToEducation(View view){
        Intent startGoBackToEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startGoBackToEducationActivity);
    }
}
