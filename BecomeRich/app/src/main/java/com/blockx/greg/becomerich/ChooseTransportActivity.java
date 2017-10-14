package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseTransportActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> transportList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int transportPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_transport);

        listview = (ListView) findViewById(R.id.listViewTransport);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        //To test substraction
        yourMoney.setText("€ 2000");

        transportList.add(new Activity("Foot", 0, true));
        transportList.add(new Activity("Shoes", 40, false));
        transportList.add(new Activity("Bicycle", 350, false));
        transportList.add(new Activity("Car", 5000, false));
        transportList.add(new Activity("Large Truck", 20000, false));
        transportList.add(new Activity("Limo", 70000, false));
        transportList.add(new Activity("Helicopter", 200000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, transportList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                transportPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if(yourMoneyInt >= transportPrice && !HaveItem.isChecked()){
                    yourMoneyInt -= transportPrice;
                    transportList.get(i).setHaveBought(true);
                    HaveItem.setChecked(transportList.get(i).isHaveBought());
                }else if(yourMoneyInt < transportPrice){
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(HaveItem.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "You already have this item!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                yourMoney.setText("€ " + yourMoneyInt);

            }
        });
    }

    public void goBackToMarket(View view){
        Intent startGoBackToMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startGoBackToMarketActivity);
    }
}
