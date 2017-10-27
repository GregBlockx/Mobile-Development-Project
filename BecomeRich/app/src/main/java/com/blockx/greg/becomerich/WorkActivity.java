package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.blockx.greg.becomerich.MainActivity.AD_ID;

public class WorkActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private GestureDetectorCompat gestureObject;
    private InterstitialAd mInterstitialAd;
    private ImageButton work;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int adCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        gestureObject = new GestureDetectorCompat(this, this);

        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText("Work");

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        adCounter = sharedPreferences.getInt("adcounter", 0);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Intent startDoWorkActivity = new Intent(WorkActivity.this, DoWorkActivity.class);
                startActivity(startDoWorkActivity);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
                finish();

            }
        });
    }

    public void goToMarket(View view) {
        Intent startMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startMarketActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    public void goToEducation(View view) {
        Intent startEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startEducationActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    public void goToPlayerInfo(View view) {
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }

    public void goToWork(View view) {
    }

    public void goToDoWork(View view) {
        Intent startDoWorkActivity = new Intent(this, DoWorkActivity.class);

        if (mInterstitialAd.isLoaded() && adCounter >= 5) {
            mInterstitialAd.show();
            adCounter = 0;
            editor.putInt("adcounter", adCounter);
            editor.commit();
        } else {
            adCounter++;
            editor.putInt("adcounter", adCounter);
            editor.commit();
            startActivity(startDoWorkActivity);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    public void goToDoCriminalActivity(View view) {
        Intent startDoCriminalJobActivity = new Intent(this, DoCriminalJobActivity.class);
        startActivity(startDoCriminalJobActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void goToBank(View view) {
        Intent startGoToBankActivity = new Intent(this, BankActivity.class);
        startActivity(startGoToBankActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() > e1.getX()) {
            Intent intent = new Intent(WorkActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        } else {
            if (e2.getX() < e1.getX()) {
                Intent intent = new Intent(WorkActivity.this, MarketActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }

        return true;
    }
}
