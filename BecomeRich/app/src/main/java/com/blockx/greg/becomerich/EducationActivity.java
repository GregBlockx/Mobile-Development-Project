package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText("Education");
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    public void goToWork(View view){
        Intent startWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startWorkActivity);
    }

    public void goToMarket(View view){
        Intent startMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startMarketActivity);
    }

    public void goToPlayerInfo(View view){
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }

    public void goToEducation(View view){
    }

    public void goToChooseEducation(View view){
        Intent startChooseEducationActivity = new Intent(this, ChooseEducationActivity.class);
        startActivity(startChooseEducationActivity);
    }

    public void goToChooseCriminalSkills(View view){
        Intent startChooseCriminalSkillActivity = new Intent(this, ChooseCriminalSkillsActivity.class);
        startActivity(startChooseCriminalSkillActivity);
    }
}
