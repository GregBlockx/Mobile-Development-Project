package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class WorkActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        gestureObject = new GestureDetectorCompat(this, this);

        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setText("Work");
    }

    public void goToMarket(View view) {
        Intent startMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startMarketActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToEducation(View view) {
        Intent startEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startEducationActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToPlayerInfo(View view) {
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
    }

    public void goToWork(View view) {
    }

    public void goToDoWork(View view) {
        Intent startDoWorkActivity = new Intent(this, DoWorkActivity.class);
        startActivity(startDoWorkActivity);
    }

    public void goToDoCriminalActivity(View view) {
        Intent startDoCriminalJobActivity = new Intent(this, DoCriminalJobActivity.class);
        startActivity(startDoCriminalJobActivity);
    }

    public void goToBank(View view) {
        Intent startGoToBankActivity = new Intent(this, BankActivity.class);
        startActivity(startGoToBankActivity);
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
