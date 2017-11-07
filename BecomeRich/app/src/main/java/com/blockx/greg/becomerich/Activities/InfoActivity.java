package com.blockx.greg.becomerich.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blockx.greg.becomerich.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    //Ga naar main screen
    public void goBackToScreen(View view){
        Intent startGoBackToMainActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToMainActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
