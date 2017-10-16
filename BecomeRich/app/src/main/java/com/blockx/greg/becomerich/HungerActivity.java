package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HungerActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> foodList = new ArrayList<>();
    int yourMoneyInt;
    int foodPrice;
    TextView yourMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        listview = (ListView) findViewById(R.id.listViewFood);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        //Test to buy something
        yourMoney.setText("€ 1000");

        foodList.add(new Activity("Eat Trash", 0));
        foodList.add(new Activity("Eat Nuts", 2));
        foodList.add(new Activity("Eat Donut", 5));
        foodList.add(new Activity("Eat Burger", 15));
        foodList.add(new Activity("Eat Organic", 65));
        foodList.add(new Activity("Eat at Restaurant", 120));
        foodList.add(new Activity("Eat Internationally", 200));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, foodList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                foodPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                if(yourMoneyInt >= foodPrice){
                    yourMoneyInt -= foodPrice;
                }else if(yourMoneyInt < foodPrice){
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                yourMoney.setText("€ " + yourMoneyInt);

            }
        });
    }

    public void goBackToScreen(View view){
        Intent startGoBackToScreenActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToScreenActivity);
    }
}
