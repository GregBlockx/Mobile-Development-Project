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

import java.util.ArrayList;

public class ChooseResidencyActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> residencyList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int residencyPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_residency);

        listview = (ListView) findViewById(R.id.listViewResidency);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        //To test substraction
        yourMoney.setText("€ 2000");

        residencyList.add(new Activity("Sleeping bag", 0, true));
        residencyList.add(new Activity("Rent Basement", 70, false));
        residencyList.add(new Activity("Rent Apartment", 500, false));
        residencyList.add(new Activity("Buy Apartment", 40000, false));
        residencyList.add(new Activity("Buy Penthouse", 150000, false));
        residencyList.add(new Activity("Buy Mansion", 500000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, residencyList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                residencyPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if(yourMoneyInt >= residencyPrice && !HaveItem.isChecked()){
                    yourMoneyInt -= residencyPrice;
                    residencyList.get(i).setHaveBought(true);
                    HaveItem.setChecked(residencyList.get(i).isHaveBought());
                }else if(yourMoneyInt < residencyPrice){
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
