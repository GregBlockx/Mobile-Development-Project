package com.blockx.greg.becomerich;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 2500;
    Animation uptodown,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView titleText = (TextView) findViewById(R.id.textViewTitle);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Rolina Bold.ttf");
        titleText.setTypeface(type);

        ImageView splashImage = (ImageView) findViewById(R.id.imageViewSplash);

        uptodown = AnimationUtils.loadAnimation(this,R.anim.up_to_down);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.down_to_up);
        titleText.setAnimation(uptodown);
        splashImage.setAnimation(downtoup);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        },SPLASH_TIME);
    }
}
