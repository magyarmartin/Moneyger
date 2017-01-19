package hu.martin4955.moneyger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button decreaseButton;
    private double monthlyLimit;
    private TextView todayLimitView;
    private TextView dailyLimitView;
    private Costs costs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        SQLiteDatabase moneygerDB = openOrCreateDatabase("Costs", MODE_PRIVATE, null);
        initSharedPreferences();
        costs = Costs.getInstance(moneygerDB, monthlyLimit);
        initButtons();
    }

    private void initButtons() {
        decreaseButton = (Button) findViewById(R.id.decreaseBtn);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculatorIntent = new Intent(MainActivity.this, CalculatorActivity.class);
                calculatorIntent.putExtra("isDecrease", true);
                startActivityForResult(calculatorIntent, 1);
            }
        });

        Button increaseBtn = (Button) findViewById(R.id.increaseBtn);
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculatorIntent = new Intent(MainActivity.this, CalculatorActivity.class);
                calculatorIntent.putExtra("isDecrease", false);
                startActivityForResult(calculatorIntent, 1);
            }
        });

        todayLimitView = (TextView) findViewById(R.id.dailyLimit);
        todayLimitView.setText(Double.toString(costs.getTodayLimit()));

        dailyLimitView = (TextView) findViewById(R.id.tomorrowLimit);
        dailyLimitView.setText(Double.toString(costs.getDailyLimit()));
    }

    private void initSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("hu.martin4955.moneyger", Context.MODE_PRIVATE);
        monthlyLimit = Double.parseDouble(sharedPreferences.getString("dailyLimit", "30000"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                CostEntity entity = (CostEntity) data.getSerializableExtra("result");
                costs.addCost(entity);
                todayLimitView.setText(Double.toString(costs.getTodayLimit()));
                dailyLimitView.setText(Double.toString(costs.getDailyLimit()));
            }
        }
    }
}
