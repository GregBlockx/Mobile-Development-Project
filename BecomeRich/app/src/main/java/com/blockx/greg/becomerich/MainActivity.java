package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private SharedPreferences sharedPreferences;
    public static final String GAME_PREFERENCES = "GamePrefs";
    public static final String AD_ID = "ca-app-pub-8105138302379586~1001667350";
    static int AGE = 6570;
    private SharedPreferences.Editor editor;
    LoginButton loginButton;
    CallbackManager callbackManager;
    Set<String> transportOwned = new HashSet<>();
    Set<String> educationOwned = new HashSet<>();
    Set<String> weaponOwned = new HashSet<>();
    Set<String> residencyOwned = new HashSet<>();
    Set<String> skillsOwned = new HashSet<>();
    private GestureDetectorCompat gestureObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, AD_ID);

        gestureObject = new GestureDetectorCompat(this, this);

        TextView title = (TextView) findViewById(R.id.textViewTitle);
        title.setVisibility(View.INVISIBLE);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            displayUserInfo(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "last_name,id,picture");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        Context context = getApplicationContext();

        sharedPreferences = context.getSharedPreferences(GAME_PREFERENCES, context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("firstrun", true)) {
            editor.putBoolean("firstrun", false);

            transportOwned.add("Foot");
            educationOwned.add("Nothing");
            residencyOwned.add("Sleeping bag");

            editor.putInt("money", 200);
            editor.putInt("bankmoney", 0);
            editor.putInt("health", 300);
            editor.putInt("hunger", 300);
            editor.putInt("adcounter",0);
            editor.putString("playername", "Davidson");
            editor.putInt("age", AGE);
            editor.putString("residency", "Sleeping bag");
            editor.putString("transport", "Foot");
            editor.putString("education", "Nothing");
            editor.putStringSet("transportOwned", transportOwned);
            editor.putStringSet("educationOwned", educationOwned);
            editor.putStringSet("weaponOwned", weaponOwned);
            editor.putStringSet("residencyOwned", residencyOwned);
            editor.putStringSet("skillsOwned", skillsOwned);
            editor.putString("url", null);
            editor.commit();
        }

        TextView yourResidency = (TextView) findViewById(R.id.textViewYourResidency);
        yourResidency.setText(sharedPreferences.getString("residency", null));

        TextView yourTransport = (TextView) findViewById(R.id.textViewYourTransport);
        yourTransport.setText(sharedPreferences.getString("transport", null));

        TextView yourEducation = (TextView) findViewById(R.id.textViewYourEducation);
        yourEducation.setText(sharedPreferences.getString("education", null));

        TextView ageTextView = (TextView) findViewById(R.id.textViewYourAge);
        ageTextView.setText(getAge(sharedPreferences.getInt("age", AGE)));

        TextView lastNameTextView = (TextView) findViewById(R.id.textViewName);
        lastNameTextView.setText("Mr. " + sharedPreferences.getString("playername", "Davidson"));

        TextView money = (TextView) findViewById(R.id.textViewYourMoney);
        money.setText(" € " + sharedPreferences.getInt("money", 0));

        TextView bankMoney = (TextView) findViewById(R.id.textViewBankMoney);
        bankMoney.setText(" € " + sharedPreferences.getInt("bankmoney", 0));

        TextView healthText = (TextView) findViewById(R.id.textViewHealth);
        healthText.setText(sharedPreferences.getInt("health", 0) + "/300");

        TextView hungerText = (TextView) findViewById(R.id.textViewHunger);
        hungerText.setText(sharedPreferences.getInt("hunger", 0) + "/300");

        ProgressBar healthBar = (ProgressBar) findViewById(R.id.progressBarHealth);
        int maxHealthAndHunger = 300;
        healthBar.setMax(maxHealthAndHunger);
        healthBar.setProgress(sharedPreferences.getInt("health", 0));

        ProgressBar hungerBar = (ProgressBar) findViewById(R.id.progressBarHunger);
        hungerBar.setMax(maxHealthAndHunger);
        hungerBar.setProgress(sharedPreferences.getInt("hunger", 0));

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void goToWork(View view) {
        Intent startWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startWorkActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
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

    public void goToHunger(View view) {
        Intent startHungerActivity = new Intent(this, HungerActivity.class);
        startActivity(startHungerActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void goToHealth(View view) {
        Intent startHealthActivity = new Intent(this, HealthActivity.class);
        startActivity(startHealthActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void goToPlayerInfo(View view) {
    }

    public void displayUserInfo(JSONObject object) throws JSONException {
        String lastName, imageUrl;
        lastName = "" + object.getString("last_name");

        editor.putString("playername", lastName);
        editor.commit();
        TextView lastNameTextView = (TextView) findViewById(R.id.textViewName);
        lastNameTextView.setText("Mr. " + sharedPreferences.getString("playername", "Davidson"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String getAge(int ageInDays) {
        int years = ageInDays / 365;
        int days = ageInDays % 365;

        return " " + years + " years " + days + " days";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            Intent startInfoActivity = new Intent(this, InfoActivity.class);
            startActivity(startInfoActivity);
        }
        return super.onOptionsItemSelected(item);
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
            Intent intent = new Intent(MainActivity.this, EducationActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        } else {
            if (e2.getX() < e1.getX()) {
                Intent intent = new Intent(MainActivity.this, WorkActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }


}

