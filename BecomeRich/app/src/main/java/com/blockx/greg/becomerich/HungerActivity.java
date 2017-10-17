package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HungerActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> foodList = new ArrayList<>();
    int yourMoneyInt;
    int foodPrice;

    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    public int hunger;
    public int health;

    public int maxValue = 300;

    TextView yourMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        hunger = maxValue;
        health = maxValue;

        listview = (ListView) findViewById(R.id.listViewCriminalJobs);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        yourHealthText = (TextView) findViewById(R.id.textViewHealth);
        yourHungerText = (TextView) findViewById(R.id.textViewHunger);

        yourHealth = (ProgressBar) findViewById(R.id.progressBarHealth);
        yourHunger = (ProgressBar) findViewById(R.id.progressBarHunger);

        yourHealth.setMax(maxValue);
        yourHunger.setMax(maxValue);

        yourHealth.setProgress(150);
        yourHunger.setProgress(150);

        listview = (ListView) findViewById(R.id.listViewFood);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        //Test to buy something
        yourMoney.setText("€ 1000");

        foodList.add(new Activity("Eat Trash", 0,15));
        foodList.add(new Activity("Eat Nuts", 2,30));
        foodList.add(new Activity("Eat Donut", 5,45));
        foodList.add(new Activity("Eat Burger", 15,60));
        foodList.add(new Activity("Eat Organic", 65,90));
        foodList.add(new Activity("Eat at Restaurant", 120,120));
        foodList.add(new Activity("Eat Internationally", 200,150));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, foodList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(hunger < 300)
                {

                    if((hunger += Integer.parseInt(activityAdapter.getHealth(i).toString())) > 300)
                    {
                        hunger = 300;
                    } else
                    {
                        hunger += Integer.parseInt(activityAdapter.getHealth(i).toString());
                    }


                    yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                    foodPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                    if(yourMoneyInt >= foodPrice){
                        yourMoneyInt -= foodPrice;
                    }else if(yourMoneyInt < foodPrice){
                        Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    yourMoney.setText("€ " + yourMoneyInt);
                    health -= 15;

                } else
                {
                    hunger = 300;

                    Toast toast = Toast.makeText(getApplicationContext(), "Hunger is full!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                if(health <= 0 || hunger <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Died! Start again!", Toast.LENGTH_SHORT);
                    toast.show();
                    goToPlayerInfo();
                }
            }
        });
    }

    public void goBackToScreen(View view){
        Intent startGoBackToScreenActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToScreenActivity);
    }

    public void goToPlayerInfo(){
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }
}
