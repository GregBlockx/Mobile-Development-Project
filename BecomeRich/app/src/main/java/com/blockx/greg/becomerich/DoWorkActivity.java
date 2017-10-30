package com.blockx.greg.becomerich;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import static android.app.PendingIntent.getActivity;

public class DoWorkActivity extends AppCompatActivity {
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
    private Set<String> educationOwned = new HashSet<>();
    private Set<String> residencyOwned = new HashSet<>();
    private Set<String> allOwned = new HashSet<>();

    //Requirements to do certain jobs
    private String[] array1 = {"Foot"};
    private String[] array2 = {"Shoes"};
    private String[] array3 = {"Shoes", "Rent Basement", "Secondary School"};
    private String[] array4 = {"Shoes", "Bicycle", "Rent Basement", "Secondary School"};
    private String[] array5 = {"Shoes", "Car", "Rent Basement", "Secondary School"};
    private String[] array6 = {"Shoes", "Car", "Rent Basement", "General Training"};
    private String[] array7 = {"Shoes", "Car", "Rent Basement", "College", "General Training"};
    private String[] array8 = {"Shoes", "Car", "Rent Apartment", "College", "General Training"};
    private String[] array9 = {"Shoes", "Large Truck", "Buy Apartment", "College", "General Training"};
    private String[] array10 = {"Shoes", "Limo", "Buy Penthouse", "Master's Degree", "General Training"};
    private String[] array11 = {"Shoes", "Helicopter", "Buy Mansion", "Master's Degree", "General Training"};

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
        educationOwned = sharedPreferences.getStringSet("educationOwned", null);
        residencyOwned = sharedPreferences.getStringSet("residencyOwned", null);
        allOwned.addAll(educationOwned);
        allOwned.addAll(transportOwned);
        allOwned.addAll(residencyOwned);

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

        jobList.add(new GameItem("Beg", 1, 5, array1));
        jobList.add(new GameItem("Wash Cars", 5, 7, array2));
        jobList.add(new GameItem("Bartender", 20, 9, array3));
        jobList.add(new GameItem("Deliver Mail", 50, 9, array4));
        jobList.add(new GameItem("Deliver Packages", 75, 10, array5));
        jobList.add(new GameItem("Work in Factory", 100, 11, array6));
        jobList.add(new GameItem("Bank Clerk", 250, 11, array7));
        jobList.add(new GameItem("Office Manager", 500, 11, array8));
        jobList.add(new GameItem("Booze Shop Owner", 1000, 12, array9));
        jobList.add(new GameItem("Supermarket Owner", 2000, 13, array9));
        jobList.add(new GameItem("E-Commerce Shop Owner", 3000, 14, array10));
        jobList.add(new GameItem("Businessman", 5000, 15, array11));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, jobList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] requirements;
                yourMoneyInt = sharedPreferences.getInt("money", 0);

                editor = sharedPreferences.edit();

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
                    yourMoneyInt += Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                    health -= Integer.parseInt(activityAdapter.getDamage(i).toString());
                    hunger -= Integer.parseInt(activityAdapter.getDamage(i).toString());
                    age++;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Need : " + requirementsString, Toast.LENGTH_SHORT);
                    toast.show();
                }
                yourMoney.setText("€ " + yourMoneyInt);
                editor.putInt("money", yourMoneyInt);
                editor.putInt("age", age);
                editor.putInt("health", health);
                editor.putInt("hunger", hunger);

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                if (health <= 0 || hunger <= 0) {
                    showAlert("YOU DIED", "You worked too hard, better luck in your next life!");
                    editor.putInt("health", maxValue);
                    editor.putInt("hunger", maxValue);
                }

                editor.commit();
            }
        });
    }

    public void goBackToScreen(View view) {
        Intent startGoBackToWorkActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToWorkActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void goToPlayerInfo() {
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

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

}
