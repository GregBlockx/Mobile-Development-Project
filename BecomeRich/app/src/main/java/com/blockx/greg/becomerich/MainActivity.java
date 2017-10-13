package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //private HealthManager healthManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //healthManager.setCurrentHealth(healthManager.getMaxHealth());
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
