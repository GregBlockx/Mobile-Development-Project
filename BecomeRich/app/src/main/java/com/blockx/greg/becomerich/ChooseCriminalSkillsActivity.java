package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseCriminalSkillsActivity extends AppCompatActivity {

    ListView listview;
    ArrayAdapter<String> adapter;
    String[] criminalSkillsArray = {"Thief Skills Starter: €100", "Thief Skills Intermediate: €500", "Thief Skills Advanced: €2000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_criminal_skills);

        listview = (ListView) findViewById(R.id.listViewCriminalSkills);
        listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,criminalSkillsArray);
        listview.setAdapter(adapter);
    }

    public void goBackToEducation(View view){
        Intent startGoBackToEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startGoBackToEducationActivity);
    }
}
