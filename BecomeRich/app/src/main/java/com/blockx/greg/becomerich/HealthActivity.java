package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HealthActivity extends AppCompatActivity {


    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> healthList = new ArrayList<>();
    int yourMoneyInt;
    int healthPrice;
    TextView yourMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        listview = (ListView) findViewById(R.id.listViewHealth);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        //Test to buy something
        yourMoney.setText("€ 1000");

        healthList.add(new Activity("Sleep on road", 0));
        healthList.add(new Activity("Take a Pill", 2));
        healthList.add(new Activity("Go to Small Clinic", 5));
        healthList.add(new Activity("Go to PolyClinic", 20));
        healthList.add(new Activity("Go to Local Doctor", 65));
        healthList.add(new Activity("Go to Hospital", 120));
        healthList.add(new Activity("Go to World Class Hospital", 200));

        activityAdapter = new ActivityAdapter(this, R.layout.activityrow, healthList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                healthPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                if(yourMoneyInt >= healthPrice){
                    yourMoneyInt -= healthPrice;
                }else if(yourMoneyInt < healthPrice){
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                yourMoney.setText("€ " + yourMoneyInt);

            }
        });
    }

    public void goBackToScreen(View view){
        Intent startGoBackToScreenActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToScreenActivity);
    }
}
