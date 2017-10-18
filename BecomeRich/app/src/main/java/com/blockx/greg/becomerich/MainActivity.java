package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static int maxHealthAndHunger = 300;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");

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
                parameters.putString("fields", "first_name, last_name, email,id");
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

        sharedPreferences = context.getSharedPreferences("money",context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("health",context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("hunger",context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        if(!sharedPreferences.contains("money") || !sharedPreferences.contains("health") || !sharedPreferences.contains("hunger")){
            editor.putInt("money", 200);
            editor.putInt("health", 300);
            editor.putInt("hunger",300);
            editor.commit();
        }

        TextView money = (TextView)findViewById(R.id.textViewYourMoney);
        money.setText(" € " + sharedPreferences.getInt("money",0));

        TextView healthText = (TextView) findViewById(R.id.textViewHealth);
        healthText.setText(sharedPreferences.getInt("health",0) + "/300");

        TextView hungerText = (TextView) findViewById(R.id.textViewHunger);
        hungerText.setText(sharedPreferences.getInt("hunger",0) + "/300");

        ProgressBar healthBar = (ProgressBar) findViewById(R.id.progressBarHealth);
        healthBar.setMax(maxHealthAndHunger);
        healthBar.setProgress(sharedPreferences.getInt("health",0));

        ProgressBar hungerBar = (ProgressBar) findViewById(R.id.progressBarHunger);
        hungerBar.setMax(maxHealthAndHunger);
        hungerBar.setProgress(sharedPreferences.getInt("hunger",0));
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

    public void displayUserInfo(JSONObject object) throws JSONException {
        String firstName, lastName, Email, ID;
        firstName = object.getString("first_name");
        lastName = object.getString("last_name");
        Email = object.getString("email");
        ID = object.getString("id");

        TextView lastNameTextView = (TextView) findViewById(R.id.textViewName);
        lastNameTextView.setText("Mr. " + lastName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
