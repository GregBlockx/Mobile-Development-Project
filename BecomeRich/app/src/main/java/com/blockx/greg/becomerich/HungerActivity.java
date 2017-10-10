package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class HungerActivity extends AppCompatActivity {
    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        listview = (ListView) findViewById(R.id.listViewFood);

        foodList.add(new Activity("Eat Trash", 0));
        foodList.add(new Activity("Eat Nuts", 2));
        foodList.add(new Activity("Eat Donut", 5));
        foodList.add(new Activity("Eat Burger", 15));
        foodList.add(new Activity("Eat Organic", 65));
        foodList.add(new Activity("Eat at Restaurant", 120));
        foodList.add(new Activity("Eat Internationally", 200));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, foodList);
        listview.setAdapter(activityAdapter);
    }

    public void goBackToScreen(View view){
        Intent startGoBackToScreenActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToScreenActivity);
    }
}
