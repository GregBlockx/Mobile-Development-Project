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
    public int hunger;
    public int health;

    public int maxValue = 300;
    int yourMoneyInt;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_criminal_job);

        Context context = this;
        sharedPreferences = this.getSharedPreferences("money",context.MODE_PRIVATE);

        hunger = maxValue;
        health = maxValue;

        listview = (ListView) findViewById(R.id.listViewCriminalJobs);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        yourMoney.setText("€ " + sharedPreferences.getInt("money",0));

        yourHealthText = (TextView) findViewById(R.id.textViewHealth);
        yourHungerText = (TextView) findViewById(R.id.textViewHunger);

        yourHealth = (ProgressBar) findViewById(R.id.progressBarHealth);
        yourHunger = (ProgressBar) findViewById(R.id.progressBarHunger);

        yourHealth.setMax(maxValue);
        yourHunger.setMax(maxValue);

        yourHealth.setProgress(maxValue);
        yourHunger.setProgress(maxValue);


        jobList.add(new Activity("Rob Homeless Person", 20));
        jobList.add(new Activity("Rob Local Person", 70));
        jobList.add(new Activity("Steal Bike", 200));
        jobList.add(new Activity("Deal Drugs", 350));
        jobList.add(new Activity("Sell Smuggled Goods", 700));
        jobList.add(new Activity("Kidnap Kid", 1100));
        jobList.add(new Activity("Assassinate Target", 1800));
        jobList.add(new Activity("Rob Rich Person", 10000));


        activityAdapter = new ActivityAdapter(this, R.layout.activityrow,jobList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                yourMoneyInt = sharedPreferences.getInt("money", 0);
                yourMoneyInt += Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                editor = sharedPreferences.edit();
                editor.putInt("money", yourMoneyInt);
                editor.commit();

                yourMoney.setText("€ " + yourMoneyInt);

                health -= 15;
                hunger -= 15;

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

    public void goBackToWork(View view){
        Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startGoBackToWorkActivity);
    }

    public void goToPlayerInfo(){
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }
}
