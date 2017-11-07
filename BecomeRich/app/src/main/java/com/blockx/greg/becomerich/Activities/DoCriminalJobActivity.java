package com.blockx.greg.becomerich.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blockx.greg.becomerich.Util.GameItem;
import com.blockx.greg.becomerich.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.blockx.greg.becomerich.Activities.MainActivity.showRewardedVideo;

public class DoCriminalJobActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<GameItem> jobList = new ArrayList<>();
    TextView yourMoney;
    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    int age;
    public int hunger;
    public int health;
    Random rand = new Random();
    public int maxValue = 300;
    int yourMoneyInt;
    private Set<String> transportOwned = new HashSet<>();
    private Set<String> weaponsOwned = new HashSet<>();
    private Set<String> residencyOwned = new HashSet<>();
    private Set<String> skillsOwned = new HashSet<>();
    private Set<String> allOwned = new HashSet<>();

    //Requirements to do certain jobs
    private String[] array1 = {"Shoes", "Pocket Knife", "Thief Skills Beginner"};
    private String[] array2 = {"Shoes", "Pistol", "Thief Skills Beginner", "Weapon Skills Beginner"};
    private String[] array3 = {"Shoes", "Rent Apartment", "Pistol", "Weapon Skills Intermediate"};
    private String[] array4 = {"Shoes", "Rent Apartment", "Pistol", "Weapon Skills Intermediate", "Thief Skills Intermediate"};
    private String[] array5 = {"Buy Apartment", "Car", "Pistol", "Weapon Skills Intermediate", "Thief Skills Intermediate"};
    private String[] array6 = {"Buy Apartment", "Shoes", "Sniper Rifle", "Bullet Proof Jacket", "Thief Skills Advanced", "Weapon Skills Advanced"};
    private String[] array7 = {"Buy Penthouse", "Shoes", "Car", "Sniper Rifle", "C4-Explosives", "Bullet Proof Jacket", "Thief Skills Advanced", "Weapon Skills Advanced"};
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_choose_layout);

        //Roept sharedpreferences aan, haalt waardes eruit en steekt deze in lokale variabelen
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES, context.MODE_PRIVATE);

        hunger = sharedPreferences.getInt("hunger", 150);
        health = sharedPreferences.getInt("health", 150);
        age = sharedPreferences.getInt("age", 0);
        transportOwned = sharedPreferences.getStringSet("transportOwned", null);
        skillsOwned = sharedPreferences.getStringSet("skillsOwned", null);
        residencyOwned = sharedPreferences.getStringSet("residencyOwned", null);
        weaponsOwned = sharedPreferences.getStringSet("weaponOwned", null);
        allOwned.addAll(transportOwned);
        allOwned.addAll(skillsOwned);
        allOwned.addAll(residencyOwned);
        allOwned.addAll(weaponsOwned);

        listview = (ListView) findViewById(R.id.listViewItems);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);
        yourMoney.setText("€ " + sharedPreferences.getInt("money", 0));

        yourHealthText = (TextView) findViewById(R.id.textViewHealth);
        yourHealthText.setText(health + "/300");
        yourHungerText = (TextView) findViewById(R.id.textViewHunger);
        yourHungerText.setText(hunger + "/300");

        yourHealth = (ProgressBar) findViewById(R.id.progressBarHealth);
        yourHunger = (ProgressBar) findViewById(R.id.progressBarHunger);

        yourHealth.setMax(maxValue);
        yourHunger.setMax(maxValue);

        yourHealth.setProgress(health);
        yourHunger.setProgress(hunger);

        jobList.add(new GameItem("Rob Homeless Person", 20, 8, array1));
        jobList.add(new GameItem("Rob Local Person", 70, 8, array2));
        jobList.add(new GameItem("Steal Bike", 200, 10, array2));
        jobList.add(new GameItem("Deal Drugs", 350, 10, array3));
        jobList.add(new GameItem("Sell Smuggled Goods", 700, 12, array4));
        jobList.add(new GameItem("Kidnap Kid", 1100, 12, array5));
        jobList.add(new GameItem("Assassinate Target", 1800, 14, array6));
        jobList.add(new GameItem("Rob Rich Person", 10000, 15, array7));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, jobList);
        listview.setAdapter(activityAdapter);

        //Als je op een criminal activity klikt dan krijg je geld en de kans dat je opgepakt wordt en je health en hunger ommlaag
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                yourMoneyInt = sharedPreferences.getInt("money", 0);
                int chance = rand.nextInt(101 - 1) + 1;
                editor = sharedPreferences.edit();

                String[] requirements;
                requirements = jobList.get(i).getRequirements();
                String requirementsString = "";
                int counter = 0;
                int counterhigh = 0;
                for (String requirement : requirements) {
                    for (String f : allOwned) {
                        if (f.equals(requirement)) {
                            counter++;
                        }
                    }
                    if (counter > counterhigh) {
                        counterhigh = counter;
                    } else {
                        requirementsString += "\n" + requirement;
                    }
                }

                if (counter == requirements.length) {
                    if (chance < 90) {
                        yourMoneyInt += Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                        health -= Integer.parseInt(activityAdapter.getDamage(i).toString());
                        hunger -= Integer.parseInt(activityAdapter.getDamage(i).toString());
                        age++;
                    } else {
                        showAlert("ARRESTED", "You have been arrested, your weapons and money have been seized!");
                        yourMoneyInt = 0;
                        weaponsOwned = null;
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Need : " + requirementsString, Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor.putInt("money", yourMoneyInt);
                editor.putStringSet("weaponOwned", weaponsOwned);
                yourMoney.setText("€ " + yourMoneyInt);
                editor.putInt("health", health);
                editor.putInt("hunger", hunger);
                editor.putInt("age", age);

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                if (health <= 0 || hunger <= 0) {
                    showAlertDied("YOU DIED", "You worked too hard, better luck in your next life!");
                }
                editor.commit();
            }
        });
    }

    //Ga terug naar work screen
    public void goBackToScreen(View view) {
        Intent startGoBackToWorkActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToWorkActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    //Ga naar player info screen
    public void goToPlayerInfo() {
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    //Als je wordt opgepakt of dood bent
    private void showAlert(String title, String displayMessage) {
        AlertDialog.Builder arrestAlert = new AlertDialog.Builder(this);
        arrestAlert.setMessage(displayMessage);
        arrestAlert.setTitle(title);
        arrestAlert.setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToPlayerInfo();
            }
        });
        arrestAlert.setCancelable(false);
        arrestAlert.create();
        arrestAlert.show();

    }

    private void showAlertDied(String title, String displayMessage) {
        AlertDialog.Builder allert = new AlertDialog.Builder(this);
        allert.setMessage(displayMessage);
        allert.setTitle(title);
        allert.setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("firstrun",true);
                editor.commit();
                goToPlayerInfo();
            }
        });
        allert.setNegativeButton("Watch Ad", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showRewardedVideo();
            }
        });
        allert.setCancelable(false);
        allert.create();
        allert.show();

    }
}
