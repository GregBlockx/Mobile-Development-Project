package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoCriminalJobActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> jobList = new ArrayList<>();
    TextView yourMoney;
    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    int age;
    public int hunger;
    public int health;

    public int maxValue = 300;
    int yourMoneyInt;

    String[] array = {};

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_criminal_job);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES, context.MODE_PRIVATE);

        hunger = sharedPreferences.getInt("hunger", 150);
        health = sharedPreferences.getInt("health", 150);
        age = sharedPreferences.getInt("age", 0);

        listview = (ListView) findViewById(R.id.listViewCriminalJobs);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);
        yourMoney.setText("€ " + sharedPreferences.getInt("money", 0));

        yourHealthText = (TextView) findViewById(R.id.textViewHealth);
        yourHealthText.setText(health + "/300");
        yourHungerText = (TextView) findViewById(R.id.textViewHunger);
        yourHungerText.setText(hunger + "/300");

        yourHealth = (ProgressBar) findViewById(R.id.progressBarHealth);
        yourHunger = (ProgressBar) findViewById(R.id.progressBarHunger);

        yourHealth.setMax(maxValue);
        yourHunger.setMax(maxValue);

        yourHealth.setProgress(health);
        yourHunger.setProgress(hunger);


        jobList.add(new Activity("Rob Homeless Person", 20,array));
        jobList.add(new Activity("Rob Local Person", 70,array));
        jobList.add(new Activity("Steal Bike", 200,array));
        jobList.add(new Activity("Deal Drugs", 350,array));
        jobList.add(new Activity("Sell Smuggled Goods", 700,array));
        jobList.add(new Activity("Kidnap Kid", 1100,array));
        jobList.add(new Activity("Assassinate Target", 1800,array));
        jobList.add(new Activity("Rob Rich Person", 10000,array));


        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, jobList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                yourMoneyInt = sharedPreferences.getInt("money", 0);
                yourMoneyInt += Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                editor = sharedPreferences.edit();
                editor.putInt("money", yourMoneyInt);


                yourMoney.setText("€ " + yourMoneyInt);

                health -= 15;
                hunger -= 15;
                age += 1;

                editor.putInt("health", health);
                editor.putInt("hunger", hunger);
                editor.putInt("age",age);

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                if (health <= 0 || hunger <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Died! Start again!", Toast.LENGTH_SHORT);
                    toast.show();
                    goToPlayerInfo();
                    editor.putInt("health", maxValue);
                    editor.putInt("hunger", maxValue);
                }
                editor.commit();
            }
        });
    }

    public void goBackToWork(View view) {
        Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startGoBackToWorkActivity);
    }

    public void goToPlayerInfo() {
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }
}
