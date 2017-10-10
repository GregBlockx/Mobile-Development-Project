package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DoCriminalJobActivity extends AppCompatActivity {

    ListView listview;
    ArrayAdapter<String> adapter;
    String[] criminalJobsobsArray = {"Rob: €30", "Steal Bike: €200", "Sell Drugs: €400"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_criminal_job);

        listview = (ListView) findViewById(R.id.listViewCriminalJobs);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,criminalJobsobsArray);
        listview.setAdapter(adapter);
    }

    public void goBackToWork(View view){
        Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startGoBackToWorkActivity);
    }
}
