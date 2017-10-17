package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HealthActivity extends AppCompatActivity {


    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> healthList = new ArrayList<>();
    int yourMoneyInt;
    int healthPrice;

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
        setContentView(R.layout.activity_health);

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

        yourHealth.setProgress(maxValue);
        yourHunger.setProgress(maxValue);

        listview = (ListView) findViewById(R.id.listViewHealth);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        //Test to buy something
        yourMoney.setText("€ 1000");

        healthList.add(new Activity("Sleep on road", 0, 15));
        healthList.add(new Activity("Take a Pill", 2,30));
        healthList.add(new Activity("Go to Small Clinic", 5,45));
        healthList.add(new Activity("Go to PolyClinic", 20,60));
        healthList.add(new Activity("Go to Local Doctor", 65,90));
        healthList.add(new Activity("Go to Hospital", 120,120));
        healthList.add(new Activity("Go to World Class Hospital", 200,150));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, healthList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(health < 300)
                {

                    if((health += Integer.parseInt(activityAdapter.getHealth(i).toString())) > 300)
                    {
                        health = 300;
                    } else
                        {
                            health += Integer.parseInt(activityAdapter.getHealth(i).toString());
                        }


                    yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                    healthPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                    if(yourMoneyInt >= healthPrice){
                        yourMoneyInt -= healthPrice;
                    }else if(yourMoneyInt < healthPrice){
                        Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    yourMoney.setText("€ " + yourMoneyInt);
                    hunger -= 15;

                } else
                {
                    health = 300;

                    Toast toast = Toast.makeText(getApplicationContext(), "Health is full!", Toast.LENGTH_SHORT);
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
