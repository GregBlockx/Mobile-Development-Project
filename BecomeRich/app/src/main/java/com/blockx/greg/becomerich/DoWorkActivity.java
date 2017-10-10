package com.blockx.greg.becomerich;

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

    ListView listJobs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_work);
        
        listJobs = (ListView)findViewById(R.id.listViewJobs);

        String[] jobsArray = {
                "Beg: € 1",
                "Wash Bikes/Cars: € 5",
                "Bartender: € 20"
        };

        List<String> jobs = new ArrayList<String>(Arrays.asList(jobsArray));

        ArrayAdapter<String> jobAdapter = new ArrayAdapter<String>(
          this,
          R.layout.activity_do_work,
          R.id.listViewJobs,
          jobs
        );

        listJobs.setAdapter(jobAdapter);
    }
}
