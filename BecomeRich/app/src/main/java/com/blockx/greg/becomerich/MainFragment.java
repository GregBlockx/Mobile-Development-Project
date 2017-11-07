package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MainFragment extends Fragment {

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
    private TextView name;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getActivity().getApplicationContext();
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
            editor.putInt("adcounter", 0);
            editor.putString("tab_opened","0");
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
       name = view.findViewById(R.id.textViewName);

        loginButton = view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
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



        TextView yourResidency =  view.findViewById(R.id.textViewYourResidency);
        yourResidency.setText(sharedPreferences.getString("residency", null));

        TextView yourTransport =  view.findViewById(R.id.textViewYourTransport);
        yourTransport.setText(sharedPreferences.getString("transport", null));

        TextView yourEducation =  view.findViewById(R.id.textViewYourEducation);
        yourEducation.setText(sharedPreferences.getString("education", null));

        TextView ageTextView =  view.findViewById(R.id.textViewYourAge);
        ageTextView.setText(getAge(sharedPreferences.getInt("age", AGE)));

        name.setText("Mr. " + sharedPreferences.getString("playername", "Davidson"));

        TextView money =  view.findViewById(R.id.textViewYourMoney);
        money.setText(" € " + sharedPreferences.getInt("money", 0));

        TextView bankMoney =  view.findViewById(R.id.textViewBankMoney);
        bankMoney.setText(" € " + sharedPreferences.getInt("bankmoney", 0));

        TextView healthText =  view.findViewById(R.id.textViewHealth);
        healthText.setText(sharedPreferences.getInt("health", 0) + "/300");

        TextView hungerText =  view.findViewById(R.id.textViewHunger);
        hungerText.setText(sharedPreferences.getInt("hunger", 0) + "/300");

        ProgressBar healthBar =  view.findViewById(R.id.progressBarHealth);
        int maxHealthAndHunger = 300;
        healthBar.setMax(maxHealthAndHunger);
        healthBar.setProgress(sharedPreferences.getInt("health", 0));

        ProgressBar hungerBar = view.findViewById(R.id.progressBarHunger);
        hungerBar.setMax(maxHealthAndHunger);
        hungerBar.setProgress(sharedPreferences.getInt("hunger", 0));

        AdView mAdView =  view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
    }

    public void displayUserInfo(JSONObject object) throws JSONException {
        String lastName, imageUrl;
        lastName = "" + object.getString("last_name");

        editor.putString("playername", lastName);
        editor.commit();

        name.setText("Mr. " + sharedPreferences.getString("playername", "Davidson"));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String getAge(int ageInDays) {
        int years = ageInDays / 365;
        int days = ageInDays % 365;

        return " " + years + " years " + days + " days";
    }


}
