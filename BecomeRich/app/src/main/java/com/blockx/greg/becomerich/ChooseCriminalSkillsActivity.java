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

public class ChooseCriminalSkillsActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<GameItem> skillsList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int criminalSkillPrice;

    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    public int hunger;
    public int health;

    public int maxValue = 300;
    Set<String> skillsOwned = new HashSet<>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_criminal_skills);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hunger = sharedPreferences.getInt("hunger",150);
        health = sharedPreferences.getInt("health",150);
        skillsOwned = sharedPreferences.getStringSet("skillsOwned",skillsOwned);

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

        skillsList.add(new GameItem("Weapon Skills Beginner", 100, false));
        skillsList.add(new GameItem("Weapon Skills Intermediate", 600, false));
        skillsList.add(new GameItem("Weapon Skills Advanced", 2200, false));
        skillsList.add(new GameItem("Thief Skills Beginner", 75, false));
        skillsList.add(new GameItem("Thief Skills Intermediate", 500, false));
        skillsList.add(new GameItem("Thief Skills Advanced", 2000, false));

        for(int i = 0; i <skillsList.size();i++)
        {
            for (Iterator<String> it = skillsOwned.iterator(); it.hasNext(); ) {
                String f = it.next();
                if (f.equals(skillsList.get(i).getActivityName())){
                    skillsList.get(i).setHaveBought(true);
                }
            }
        }

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, skillsList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = sharedPreferences.getInt("money", 0);
                criminalSkillPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if(yourMoneyInt >= criminalSkillPrice && !HaveItem.isChecked()){
                    yourMoneyInt -= criminalSkillPrice;
                    skillsList.get(i).setHaveBought(true);
                    skillsOwned.add(skillsList.get(i).getActivityName());
                    HaveItem.setChecked(skillsList.get(i).isHaveBought());
                }else if(HaveItem.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "You already have this skill!", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(yourMoneyInt < criminalSkillPrice){
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor = sharedPreferences.edit();
                editor.putInt("money", yourMoneyInt);
                editor.putStringSet("skillsOwned", skillsOwned);
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
