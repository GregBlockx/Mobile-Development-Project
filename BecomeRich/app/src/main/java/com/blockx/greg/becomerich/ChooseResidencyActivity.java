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

public class ChooseResidencyActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> residencyList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int residencyPrice;

    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    public int hunger;
    public int health;

    public int maxValue = 300;

    Set<String>residencyOwned = new HashSet<>();


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_residency);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hunger = sharedPreferences.getInt("hunger",150);
        health = sharedPreferences.getInt("health",150);
        residencyOwned = sharedPreferences.getStringSet("residencyOwned",residencyOwned);

        listview = (ListView) findViewById(R.id.listViewResidency);
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

        residencyList.add(new Activity("Sleeping bag", 0, true));
        residencyList.add(new Activity("Rent Basement", 70,false));
        residencyList.add(new Activity("Rent Apartment", 500,false));
        residencyList.add(new Activity("Buy Apartment", 40000,false));
        residencyList.add(new Activity("Buy Penthouse", 150000,false));
        residencyList.add(new Activity("Buy Mansion", 500000,false));

        for(int i = 0; i <residencyList.size();i++)
        {
            for (Iterator<String> it = residencyOwned.iterator(); it.hasNext(); ) {
                String f = it.next();
                if (f.equals(residencyList.get(i).getActivityName())){
                    residencyList.get(i).setHaveBought(true);
                }
            }
        }

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, residencyList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = sharedPreferences.getInt("money", 0);
                residencyPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if(yourMoneyInt >= residencyPrice && !HaveItem.isChecked()){
                    yourMoneyInt -= residencyPrice;
                    residencyList.get(i).setHaveBought(true);
                    residencyOwned.add(residencyList.get(i).getActivityName());
                    editor.putString("residency",residencyList.get(i).getActivityName());
                    HaveItem.setChecked(residencyList.get(i).isHaveBought());
                }else if(HaveItem.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "You already own this residence!", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(yourMoneyInt < residencyPrice){
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor.putInt("money", yourMoneyInt);
                editor.putStringSet("residencyOwned", residencyOwned);
                yourMoney.setText("€ " + yourMoneyInt);
                editor.commit();
            }
        });
    }

    public void goBackToMarket(View view){
        Intent startGoBackToMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startGoBackToMarketActivity);
    }
}
