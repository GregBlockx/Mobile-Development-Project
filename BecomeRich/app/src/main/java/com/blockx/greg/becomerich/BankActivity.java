package com.blockx.greg.becomerich;

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

import static com.blockx.greg.becomerich.MainActivity.GAME_PREFERENCES;

public class BankActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int yourMoney;
    private int yourBankMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(GAME_PREFERENCES, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        yourMoney = sharedPreferences.getInt("money", 0);
        yourBankMoney = sharedPreferences.getInt("bankmoney", 0);

        setTextViews();

    }

    public void goBackToScreen(View view) {
        Intent startGoBackToWorkActivity = new Intent(this, WorkActivity.class);
        startActivity(startGoBackToWorkActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

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

    private void setTextViews() {
        TextView money = (TextView) findViewById(R.id.textViewMoney);
        money.setText(" € " + sharedPreferences.getInt("money", 0));

        TextView bankMoney = (TextView) findViewById(R.id.textViewBankMoney);
        bankMoney.setText(" € " + sharedPreferences.getInt("bankmoney", 0));
    }
}
