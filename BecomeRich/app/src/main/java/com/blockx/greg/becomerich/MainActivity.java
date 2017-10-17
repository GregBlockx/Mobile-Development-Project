package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static int maxHealthAndHunger = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        sharedPreferences = context.getSharedPreferences("money",context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("health",context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("hunger",context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        if(!sharedPreferences.contains("money") || !sharedPreferences.contains("health") || !sharedPreferences.contains("hunger")){
            editor.putInt("money", 200);
            editor.putInt("health", 300);
            editor.putInt("hunger",300);
            editor.commit();
        }

        TextView money = (TextView)findViewById(R.id.textViewYourMoney);
        money.setText("€ " + sharedPreferences.getInt("money",0));

        TextView healthText = (TextView) findViewById(R.id.textViewHealth);
        healthText.setText(sharedPreferences.getInt("health",0) + "/300");

        TextView hungerText = (TextView) findViewById(R.id.textViewHunger);
        hungerText.setText(sharedPreferences.getInt("hunger",0) + "/300");

        ProgressBar healthBar = (ProgressBar) findViewById(R.id.progressBarHealth);
        healthBar.setMax(maxHealthAndHunger);
        healthBar.setProgress(sharedPreferences.getInt("health",0));

        ProgressBar hungerBar = (ProgressBar) findViewById(R.id.progressBarHunger);
        hungerBar.setMax(maxHealthAndHunger);
        hungerBar.setProgress(sharedPreferences.getInt("hunger",0));
    }

    public void goToWork(View view){
        Intent startWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startWorkActivity);
    }

    public void goToMarket(View view){
        Intent startMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startMarketActivity);
    }

    public void goToEducation(View view){
        Intent startEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startEducationActivity);
    }

    public void goToHunger(View view){
        Intent startHungerActivity = new Intent(this, HungerActivity.class);
        startActivity(startHungerActivity);
    }

    public void goToHealth(View view){
        Intent startHealthActivity = new Intent(this, HealthActivity.class);
        startActivity(startHealthActivity);
    }
}
