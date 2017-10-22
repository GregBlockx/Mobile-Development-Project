package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText("Market");
    }

    public void goToWork(View view){
        Intent startWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startWorkActivity);
    }

    public void goToEducation(View view){
        Intent startEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startEducationActivity);
    }

    public void goToPlayerInfo(View view){
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }

    public void goToMarket(View view){}

    public void goToChooseResidency(View view){
        Intent startChooseResidencyActivity = new Intent(this, ChooseResidencyActivity.class);
        startActivity(startChooseResidencyActivity);
    }

    public void goToChooseTransport(View view){
        Intent startChooseTransportActivity = new Intent(this, ChooseTransportActivity.class);
        startActivity(startChooseTransportActivity);
    }

    public void goToChooseWeapon(View view){
        Intent startChooseWeaponActivity = new Intent(this, ChooseWeaponActivity.class);
        startActivity(startChooseWeaponActivity);
    }
}
