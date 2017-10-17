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

public class ChooseCriminalSkillsActivity extends AppCompatActivity {

    ListView listview;
    ActivityAdapterCheckBox activityAdapter;
    ArrayList<Activity> skillsList = new ArrayList<>();
    TextView yourMoney;
    int yourMoneyInt;
    int criminalSkillPrice;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_criminal_skills);

        Context context = this;
        sharedPreferences = this.getSharedPreferences("money",context.MODE_PRIVATE);

        listview = (ListView) findViewById(R.id.listViewCriminalSkills);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        yourMoney.setText("€ " + sharedPreferences.getInt("money",0));

        skillsList.add(new Activity("Weapon Skills Beginner", 100, false));
        skillsList.add(new Activity("Weapon Skills Intermediate", 600, false));
        skillsList.add(new Activity("Weapon Skills Advanced", 2200, false));
        skillsList.add(new Activity("Thief Skills Beginner", 75, false));
        skillsList.add(new Activity("Thief Skills Intermediate", 500, false));
        skillsList.add(new Activity("Thief Skills Advanced", 2000, false));

        activityAdapter = new ActivityAdapterCheckBox(this, R.layout.activityrow_checkbox, skillsList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                criminalSkillPrice = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                CheckBox HaveItem = view.findViewById(R.id.checkBoxHaveItem);

                if(yourMoneyInt >= criminalSkillPrice && !HaveItem.isChecked()){
                    yourMoneyInt -= criminalSkillPrice;
                    skillsList.get(i).setHaveBought(true);
                    HaveItem.setChecked(skillsList.get(i).isHaveBought());
                }else if(yourMoneyInt < criminalSkillPrice){
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(HaveItem.isChecked()){
                    Toast toast = Toast.makeText(getApplicationContext(), "You already have this item!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                editor = sharedPreferences.edit();
                editor.putInt("money", yourMoneyInt);
                editor.commit();

                yourMoney.setText("€ " + sharedPreferences.getInt("money",0));
            }
        });
    }

    public void goBackToEducation(View view){
        Intent startGoBackToEducationActivity = new Intent(this, EducationActivity.class);
        startActivity(startGoBackToEducationActivity);
    }
}
