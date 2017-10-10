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
    ArrayAdapter<String> adapter;
    String[] jobsArray = {"Beg: €1", "Wash bikes: €5", "Bartender: €20"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_work);
        listview = (ListView) findViewById(R.id.listViewJobs);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,jobsArray);
        listview.setAdapter(adapter);
  }

  public void goBackToWork(View view){
      Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
      startActivity(startGoBackToWorkActivity);
  }
}
