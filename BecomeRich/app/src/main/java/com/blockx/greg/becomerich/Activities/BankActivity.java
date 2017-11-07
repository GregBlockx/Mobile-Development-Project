package com.blockx.greg.becomerich.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blockx.greg.becomerich.R;

public class BankActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int yourMoney;
    private int yourBankMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        //Roept sharedpreferences aan, haalt waardes eruit en steekt deze in lokale variabelen
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MainActivity.GAME_PREFERENCES, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        yourMoney = sharedPreferences.getInt("money", 0);
        yourBankMoney = sharedPreferences.getInt("bankmoney", 0);

        setTextViews();

    }

    //Druk op de back knop en roept de vorige klasse aan, gevolgd door een animatie
    public void goBackToScreen(View view) {
        Intent startGoBackToWorkActivity = new Intent(this, MainActivity.class);
        startActivity(startGoBackToWorkActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    //Bij het drukken op Deposit word de value gechecked of hij niet groter is dan de huidige cash, anders wordt er een toast geshowt
    public void Deposit(View view) {
        int value = 0;
        EditText input = (EditText) findViewById(R.id.bankInput);
        String ed_text = input.getText().toString().trim();

        if (!TextUtils.isEmpty(ed_text)) {
            value = Integer.parseInt(input.getText().toString());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a value to deposit", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (value > yourMoney) {
            Toast toast = Toast.makeText(getApplicationContext(), "You don't have that much money!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            yourMoney -= value;
            yourBankMoney += value;
            editor.putInt("money", yourMoney);
            editor.putInt("bankmoney", yourBankMoney);
            editor.commit();
            setTextViews();
        }
    }

    //Bij het drukken op Withdraw wordt de value gechecked of hij niet groter is dan de huidige bankwaarde
    public void Withdraw(View view) {
        int value = 0;
        EditText input = (EditText) findViewById(R.id.bankInput);
        String ed_text = input.getText().toString().trim();

        if (!TextUtils.isEmpty(ed_text)) {
            value = Integer.parseInt(input.getText().toString());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a value to withdraw", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (value > yourBankMoney) {
            Toast toast = Toast.makeText(getApplicationContext(), "You don't have that much money in your account!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            yourMoney += value;
            yourBankMoney -= value;
            editor.putInt("money", yourMoney);
            editor.putInt("bankmoney", yourBankMoney);
            editor.commit();
            setTextViews();
        }
    }

    //Steek de lokale waardes in de textviews
    private void setTextViews() {
        TextView money = (TextView) findViewById(R.id.textViewMoney);
        money.setText(" € " + sharedPreferences.getInt("money", 0));

        TextView bankMoney = (TextView) findViewById(R.id.textViewBankMoney);
        bankMoney.setText(" € " + sharedPreferences.getInt("bankmoney", 0));
    }
}
