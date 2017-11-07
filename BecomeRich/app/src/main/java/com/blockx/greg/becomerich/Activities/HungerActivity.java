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

import static com.blockx.greg.becomerich.Activities.MainActivity.showRewardedVideo;

public class HungerActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<GameItem> foodList = new ArrayList<>();
    public int yourMoneyInt;
    public int foodPrice;
    public int age;

    public TextView yourHealthText;
    public TextView yourHungerText;
    public ProgressBar yourHealth;
    public ProgressBar yourHunger;
    public int hunger;
    public int health;

    public int maxValue = 300;

    public TextView yourMoney;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_choose_layout);

        //Roept sharedpreferences aan, haalt waardes eruit en steekt deze in lokale variabelen
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES, context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        hunger = sharedPreferences.getInt("hunger", 150);
        health = sharedPreferences.getInt("health", 150);
        age = sharedPreferences.getInt("age", 0);


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

        listview = (ListView) findViewById(R.id.listViewItems);
        foodList.add(new GameItem("Eat Trash", 0, 10, 5));
        foodList.add(new GameItem("Eat Nuts", 2, 15, 5));
        foodList.add(new GameItem("Eat Donut", 5, 20, 5));
        foodList.add(new GameItem("Eat Burger", 15, 40, 4));
        foodList.add(new GameItem("Eat Organic", 65, 120, 4));
        foodList.add(new GameItem("Eat at Restaurant", 120, 150, 3));
        foodList.add(new GameItem("Eat Internationally", 200, 200, 1));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, foodList);
        listview.setAdapter(activityAdapter);

        //Als je op een item klikt dan gaat je geld en health omlaag maar je hunger omhoog
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (hunger < 300) {
                    hunger += Integer.parseInt(activityAdapter.getHealth(i).toString());
                    if (hunger >= 300) {
                        hunger = 300;
                    }

                    yourMoneyInt = sharedPreferences.getInt("money", 0);
                    foodPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                    if (yourMoneyInt >= foodPrice) {
                        yourMoneyInt -= foodPrice;
                    } else if (yourMoneyInt < foodPrice) {
                        Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    yourMoney.setText("€ " + yourMoneyInt);
                    editor.putInt("money", yourMoneyInt);
                    health -= Integer.parseInt(activityAdapter.getDamage(i).toString());
                    age ++;

                } else {
                    hunger = 300;

                    Toast toast = Toast.makeText(getApplicationContext(), "Hunger is full!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor.putInt("health", health);
                editor.putInt("hunger", hunger);
                editor.putInt("age", age);

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                if (health <= 0 || hunger <= 0) {
                    showAlert("You died","You can watch an ad and keep everything or start over");
                }
                editor.commit();
            }
        });
    }

    //Ga naar main screen
    public void goBackToScreen(View view) {
        Intent startGoBackToScreenActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToScreenActivity);
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

    private void showAlert(String title, String displayMessage) {
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
