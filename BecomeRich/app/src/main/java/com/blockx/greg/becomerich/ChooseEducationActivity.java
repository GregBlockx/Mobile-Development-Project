package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChooseEducationActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<GameItem> educationList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int educationPrice;

    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    public int hunger;
    public int health;

    public int maxValue = 300;
    Set<String> educationOwned = new HashSet<>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hunger = sharedPreferences.getInt("hunger",150);
        health = sharedPreferences.getInt("health",150);
        educationOwned = sharedPreferences.getStringSet("educationOwned",educationOwned);

        listview = (ListView) findViewById(R.id.listViewItems);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);
        yourMoney.setText("€ " + sharedPreferences.getInt("money",0));

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

        educationList.add(new GameItem("Nothing", 0, true));
        educationList.add(new GameItem("Secondary School", 100, false));
        educationList.add(new GameItem("High School", 7500, false));
        educationList.add(new GameItem("General Training", 15000, false));
        educationList.add(new GameItem("College", 25000, false));
        educationList.add(new GameItem("Master's Degree", 100000, false));

        for(int i = 0; i <educationList.size();i++)
        {
            for (Iterator<String> it = educationOwned.iterator(); it.hasNext(); ) {
                String f = it.next();
                if (f.equals(educationList.get(i).getActivityName())){
                    educationList.get(i).setHaveBought(true);
                }
            }
        }

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, educationList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = sharedPreferences.getInt("money", 0);
                educationPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if (yourMoneyInt >= educationPrice && !HaveItem.isChecked()) {
                    yourMoneyInt -= educationPrice;
                    educationList.get(i).setHaveBought(true);
                    educationOwned.add(educationList.get(i).getActivityName());
                    editor.putString("education",educationList.get(i).getActivityName());
                    HaveItem.setChecked(educationList.get(i).isHaveBought());
                } else if (yourMoneyInt < educationPrice) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (HaveItem.isChecked()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You already have this education!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                editor.putInt("money", yourMoneyInt);

                editor.putStringSet("educationOwned", educationOwned);
                yourMoney.setText("€ " + yourMoneyInt);
                editor.commit();
            }
        });
    }

    public void goBackToScreen(View view){
        Intent startGoBackToEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startGoBackToEducationActivity);
    }
}
