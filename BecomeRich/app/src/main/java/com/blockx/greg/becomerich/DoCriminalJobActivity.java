package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

    public int maxValue = 300;
    int yourMoneyInt;

    private Set<String> transportOwned = new HashSet<>();
    private Set<String> weaponsOwned = new HashSet<>();
    private Set<String> residencyOwned = new HashSet<>();
    private Set<String> skillsOwned = new HashSet<>();
    private Set<String> allOwned = new HashSet<>();

    //Requirements to do certain jobs
    private String[] array1 = {"Shoes", "Pocket Knife","Thief Skills Beginner"};
    private String[] array2 = {"Shoes", "Pistol","Thief Skills Beginner","Weapon Skills Beginner"};
    private String[] array3 = {"Shoes", "Rent Apartment", "Pistol","Weapon Skills Intermediate"};
    private String[] array4 = {"Shoes", "Rent Apartment", "Pistol", "Weapon Skills Intermediate","Thief Skills Intermediate"};
    private String[] array5 = {"Buy Apartment", "Car", "Pistol", "Weapon Skills Intermediate","Thief Skills Intermediate"};
    private String[] array6 = {"Buy Apartment", "Shoes", "Sniper Rifle", "Bullet Proof Jacket","Thief Skills Advanced","Weapon Skills Advanced"};
    private String[] array7 = {"Buy Penthouse","Shoes","Car", "Sniper Rifle", "C4-Explosives", "Bullet Proof Jacket","Thief Skills Advanced","Weapon Skills Advanced"};

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_choose_layout);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES, context.MODE_PRIVATE);

        hunger = sharedPreferences.getInt("hunger", 150);
        health = sharedPreferences.getInt("health", 150);
        age = sharedPreferences.getInt("age", 0);
        transportOwned = sharedPreferences.getStringSet("transportOwned", null);
        skillsOwned = sharedPreferences.getStringSet("skillsOwned", null);
        residencyOwned = sharedPreferences.getStringSet("residencyOwned", null);
        weaponsOwned = sharedPreferences.getStringSet("weaponOwned", null);
        allOwned.addAll(transportOwned);allOwned.addAll(skillsOwned);
        allOwned.addAll(residencyOwned);allOwned.addAll(weaponsOwned);

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


        jobList.add(new GameItem("Rob Homeless Person", 20,array1));
        jobList.add(new GameItem("Rob Local Person", 70,array2));
        jobList.add(new GameItem("Steal Bike", 200,array2));
        jobList.add(new GameItem("Deal Drugs", 350,array3));
        jobList.add(new GameItem("Sell Smuggled Goods", 700,array4));
        jobList.add(new GameItem("Kidnap Kid", 1100,array5));
        jobList.add(new GameItem("Assassinate Target", 1800,array6));
        jobList.add(new GameItem("Rob Rich Person", 10000,array7));


        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, jobList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                yourMoneyInt = sharedPreferences.getInt("money", 0);
                editor = sharedPreferences.edit();

                String[] requirements;
                requirements = jobList.get(i).getRequirements();
                String requirementsString = "";
                int counter = 0;
                int counterhigh = 0;
                for (int x = 0; x < requirements.length; x++) {
                    for (Iterator<String> it = allOwned.iterator(); it.hasNext(); ) {
                        String f = it.next();
                        if (f.equals(requirements[x])) {
                            counter++;
                        }
                    }
                    if(counter > counterhigh){
                        counterhigh = counter;
                    } else {
                        requirementsString += "\n" +requirements[x];
                    }
                }

                if (counter == requirements.length) {
                    yourMoneyInt += Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                    health -= 15;
                    hunger -= 15;
                    age += 1;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Need : " + requirementsString, Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor.putInt("money", yourMoneyInt);
                yourMoney.setText("€ " + yourMoneyInt);
                editor.putInt("health", health);
                editor.putInt("hunger", hunger);
                editor.putInt("age",age);

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                if (health <= 0 || hunger <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Died! Start again!", Toast.LENGTH_SHORT);
                    toast.show();
                    goToPlayerInfo();
                    editor.putInt("health", maxValue);
                    editor.putInt("hunger", maxValue);
                }
                editor.commit();
            }
        });
    }

    public void goBackToScreen(View view) {
        Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startGoBackToWorkActivity);
    }

    public void goToPlayerInfo() {
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }
}
