package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText("Work");
    }

    public void goToMarket(View view){
        Intent startMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startMarketActivity);
    }

    public void goToEducation(View view){
        Intent startEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startEducationActivity);
    }

    public void goToPlayerInfo(View view){
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }

    public void goToWork(View view) {}

    public void goToDoWork(View view){
        Intent startDoWorkActivity = new Intent(this, DoWorkActivity.class);
        startActivity(startDoWorkActivity);
    }

    public void goToDoCriminalActivity(View view){
        Intent startDoCriminalJobActivity = new Intent(this, DoCriminalJobActivity.class);
        startActivity(startDoCriminalJobActivity);
    }
}
