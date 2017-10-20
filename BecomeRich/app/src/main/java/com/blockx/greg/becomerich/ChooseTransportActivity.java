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

public class ChooseTransportActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<GameItem> transportList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int transportPrice;

    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    public int hunger;
    public int health;
    Set<String> transportOwned = new HashSet<>();

    public int maxValue = 300;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_choose_layout);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hunger = sharedPreferences.getInt("hunger",150);
        health = sharedPreferences.getInt("health",150);
        transportOwned = sharedPreferences.getStringSet("transportOwned",transportOwned);

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

        transportList.add(new GameItem("Foot", 0, true));
        transportList.add(new GameItem("Shoes", 40, false));
        transportList.add(new GameItem("Bicycle", 350, false));
        transportList.add(new GameItem("Car", 5000, false));
        transportList.add(new GameItem("Large Truck", 20000, false));
        transportList.add(new GameItem("Limo", 70000, false));
        transportList.add(new GameItem("Helicopter", 200000, false));

        for(int i = 0; i <transportList.size();i++)
        {
            for (Iterator<String> it = transportOwned.iterator(); it.hasNext(); ) {
                String f = it.next();
                if (f.equals(transportList.get(i).getActivityName())){
                    transportList.get(i).setHaveBought(true);
                }
            }
        }

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, transportList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = sharedPreferences.getInt("money", 0);
                transportPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if(yourMoneyInt >= transportPrice && !HaveItem.isChecked()){
                    yourMoneyInt -= transportPrice;
                    transportList.get(i).setHaveBought(true);
                    transportOwned.add(transportList.get(i).getActivityName());
                    editor.putString("transport",transportList.get(i).getActivityName());
                    HaveItem.setChecked(transportList.get(i).isHaveBought());
                } else if(HaveItem.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "You already have this item!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if(yourMoneyInt < transportPrice) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor.putInt("money", yourMoneyInt);
                editor.putStringSet("transportOwned", transportOwned);
                yourMoney.setText("€ " + yourMoneyInt);
                editor.commit();
            }
        });
    }

    public void goBackToScreen(View view){
        Intent startGoBackToMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startGoBackToMarketActivity);
    }
}
