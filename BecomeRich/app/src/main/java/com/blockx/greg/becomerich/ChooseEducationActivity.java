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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseEducationActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> educationList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int educationPrice;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);

        Context context = this;
        sharedPreferences = this.getSharedPreferences("money",context.MODE_PRIVATE);

        listview = (ListView) findViewById(R.id.listViewEducation);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        yourMoney.setText("€ " + sharedPreferences.getInt("money",0));

        educationList.add(new Activity("Nothing", 0, true));
        educationList.add(new Activity("Secondary School", 100, false));
        educationList.add(new Activity("High School", 7500, false));
        educationList.add(new Activity("General Training", 15000, false));
        educationList.add(new Activity("College", 25000, false));
        educationList.add(new Activity("Master's Degree", 100000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, educationList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = sharedPreferences.getInt("money", 0);
                educationPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if(yourMoneyInt >= educationPrice && !HaveItem.isChecked()){
                    yourMoneyInt -= educationPrice;
                    educationList.get(i).setHaveBought(true);
                    HaveItem.setChecked(educationList.get(i).isHaveBought());
                }else if(yourMoneyInt < educationPrice){
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(HaveItem.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "You already have this item!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor = sharedPreferences.edit();
                editor.putInt("money", yourMoneyInt);
                editor.commit();

                yourMoney.setText("€ " + yourMoneyInt);
            }
        });
    }

    public void goBackToEducation(View view){
        Intent startGoBackToEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startGoBackToEducationActivity);
    }
}
