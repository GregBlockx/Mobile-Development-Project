package com.blockx.greg.becomerich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseResidencyActivity extends AppCompatActivity {

    ListView listview;
    ArrayAdapter<String> adapter;
    String[] residencyArray = {"Streets: €0", "Rent Apartment: €500", "Buy Apartment: €4000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_residency);
        listview = (ListView) findViewById(R.id.listViewResidency);
        listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,residencyArray);
        listview.setAdapter(adapter);
    }

    public void goBackToMarket(View view){
        Intent startGoBackToMarketActivity = new Intent(this, MarketActivity.class);
        startActivity(startGoBackToMarketActivity);
    }
}
