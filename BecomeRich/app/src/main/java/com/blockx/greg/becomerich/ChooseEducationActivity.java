package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseEducationActivity extends AppCompatActivity {

    ListView listview;
    ArrayAdapter<String> adapter;
    String[] educationArray = {"Secondary School: €0", "High School: €3000", "University: €10000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);
        listview = (ListView) findViewById(R.id.listViewEducation);
        listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,educationArray);
        listview.setAdapter(adapter);
    }

    public void goBackToEducation(View view){
        Intent startGoBackToEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startGoBackToEducationActivity);
    }
}
