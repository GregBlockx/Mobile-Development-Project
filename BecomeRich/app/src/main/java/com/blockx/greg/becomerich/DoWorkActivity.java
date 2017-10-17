package com.blockx.greg.becomerich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class DoWorkActivity extends AppCompatActivity {
    ListView listview;
    ActivityAdapter activityAdapter;
    ArrayList<Activity> jobList = new ArrayList<>();
    TextView yourMoney;

    TextView yourHealthText;
    TextView yourHungerText;
    ProgressBar yourHealth;
    ProgressBar yourHunger;
    public int hunger;
    public int health;

    public int maxValue = 300;

    int yourMoneyInt;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_work);

        Context context = this;
        sharedPreferences = this.getSharedPreferences("money",context.MODE_PRIVATE);
        sharedPreferences = this.getSharedPreferences("health",context.MODE_PRIVATE);
        sharedPreferences = this.getSharedPreferences("hunger",context.MODE_PRIVATE);


        hunger = sharedPreferences.getInt("hunger",150);
        health = sharedPreferences.getInt("health",150);

        listview = (ListView) findViewById(R.id.listViewCriminalJobs);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        yourMoney.setText("€ " + sharedPreferences.getInt("money",0));

        yourHealthText = (TextView) findViewById(R.id.textViewHealth);
        yourHungerText = (TextView) findViewById(R.id.textViewHunger);

        yourHealth = (ProgressBar) findViewById(R.id.progressBarHealth);
        yourHunger = (ProgressBar) findViewById(R.id.progressBarHunger);

        yourHealth.setMax(maxValue);
        yourHunger.setMax(maxValue);

        yourHealth.setProgress(health);
        yourHunger.setProgress(hunger);

        listview = (ListView) findViewById(R.id.listViewJobs);
        yourMoney = (TextView) findViewById(R.id.textViewYourMoney);

        jobList.add(new Activity("Beg", 1));
        jobList.add(new Activity("Wash Cars", 5));
        jobList.add(new Activity("Bartender", 20));
        jobList.add(new Activity("Deliver Mail", 50));
        jobList.add(new Activity("Deliver Packages", 75));
        jobList.add(new Activity("Work in Factory", 100));
        jobList.add(new Activity("Bank Clerk", 250));
        jobList.add(new Activity("Office Manager", 500));
        jobList.add(new Activity("Booze Shop Owner", 1000));
        jobList.add(new Activity("Supermarket Owner", 2000));
        jobList.add(new Activity("E-Commerce Shop Owner", 3000));
        jobList.add(new Activity("Businessman", 5000));


        activityAdapter = new ActivityAdapter(this, R.layout.activityrow,jobList);
        listview.setAdapter(activityAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                yourMoneyInt = Integer.parseInt(yourMoney.getText().toString().substring(2));
                yourMoneyInt += Integer.parseInt(adapterView.getItemAtPosition(i).toString());

                editor = sharedPreferences.edit();
                editor.putInt("money", yourMoneyInt);


                yourMoney.setText("€ " + sharedPreferences.getInt("money",0));

                health -= 15;
                hunger -= 15;

                yourHealthText.setText(health + "/" + maxValue);
                yourHungerText.setText(hunger + "/" + maxValue);

                yourHealth.setProgress(health);
                yourHunger.setProgress(hunger);

                editor.putInt("health",health);
                editor.putInt("hunger",hunger);

                if(health <= 0 || hunger <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You Died! Start again!", Toast.LENGTH_SHORT);
                    toast.show();
                    goToPlayerInfo();
                    editor.putInt("health",maxValue);
                    editor.putInt("hunger",maxValue);
                }

                editor.commit();
            }
        });
    }

  public void goBackToWork(View view){
      Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
      startActivity(startGoBackToWorkActivity);
  }

    public void goToPlayerInfo(){
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
    }

}
