package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EducationActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private GestureDetectorCompat gestureObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        gestureObject = new GestureDetectorCompat(this, this);


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
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
        finish();
    }

    public void goToMarket(View view){
        Intent startMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startMarketActivity);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
        finish();
    }

    public void goToPlayerInfo(View view){
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
        finish();
    }

    public void goToEducation(View view){
    }

    public void goToChooseEducation(View view){
        Intent startChooseEducationActivity = new Intent(this, ChooseEducationActivity.class);
        startActivity(startChooseEducationActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void goToChooseCriminalSkills(View view){
        Intent startChooseCriminalSkillActivity = new Intent(this, ChooseCriminalSkillsActivity.class);
        startActivity(startChooseCriminalSkillActivity);
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
            Intent intent = new Intent(EducationActivity.this, MarketActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        } else {
            if (e2.getX() < e1.getX()) {
                Intent intent = new Intent(EducationActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }

        return true;
    }
}
