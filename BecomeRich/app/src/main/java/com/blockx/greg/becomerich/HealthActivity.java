package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HealthActivity extends AppCompatActivity {


    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<GameItem> healthList = new ArrayList<>();
    int yourMoneyInt;
    int healthPrice;
    int age;

    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    public int hunger;
    public int health;

    public int maxValue = 300;

    TextView yourMoney;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_choose_layout);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hunger = sharedPreferences.getInt("hunger",150);
        health = sharedPreferences.getInt("health",150);
        age = sharedPreferences.getInt("age",0);

        listview = (ListView) findViewById(R.id.listViewItems);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);
        yourMoney.setText("€ " + sharedPreferences.getInt("money",0));

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

        healthList.add(new GameItem("Sleep on road", 0, 15));
        healthList.add(new GameItem("Take a Pill", 2,30));
        healthList.add(new GameItem("Go to Small Clinic", 5,45));
        healthList.add(new GameItem("Go to PolyClinic", 20,60));
        healthList.add(new GameItem("Go to Local Doctor", 65,90));
        healthList.add(new GameItem("Go to Hospital", 120,120));
        healthList.add(new GameItem("Go to World Class Hospital", 200,150));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, healthList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(health < 300)
                {
                    health += Integer.parseInt(activityAdapter.getHealth(i).toString());
                    if(health >= 300)
                    {
                        health = 300;
                    }

                    yourMoneyInt = sharedPreferences.getInt("money",0);
                    healthPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                    if(yourMoneyInt >= healthPrice){
                        yourMoneyInt -= healthPrice;
                    }else if(yourMoneyInt < healthPrice){
                        Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    yourMoney.setText("€ " + yourMoneyInt);
                    editor.putInt("money",yourMoneyInt);
                    hunger -= 15;
                    age += 1;

                } else
                {
                    health = 300;

                    Toast toast = Toast.makeText(getApplicationContext(), "Health is full!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor.putInt("health",health);
                editor.putInt("hunger",hunger);
                editor.putInt("age",age);

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                if(health <= 0 || hunger <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Died! Start again!", Toast.LENGTH_SHORT);
                    toast.show();
                    goToPlayerInfo();
                    editor.putInt("health",maxValue);
                    editor.putInt("hunger",maxValue);
                }
                editor.commit();
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
