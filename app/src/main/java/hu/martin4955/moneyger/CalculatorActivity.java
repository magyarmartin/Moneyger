package hu.martin4955.moneyger;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculatorActivity extends AppCompatActivity {

    private Button backBtn;
    private TextView valueLabel;
    private StringBuilder valueString;
    private boolean isDecrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        isDecrease = getIntent().getBooleanExtra("isDecrease", false);
        Log.i("Decrease", Boolean.toString(isDecrease));
        initButtons();
    }

    private void initButtons() {
        backBtn = (Button) findViewById(R.id.calcBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent returnIntent = new Intent();
                //setResult(Activity.RESULT_CANCELED, returnIntent);
                CalculatorActivity.super.finish();
            }
        });

        valueString = new StringBuilder("");

        valueLabel = (TextView) findViewById(R.id.calcValueLabel);
        valueLabel.setText(valueString.toString());

        Button zero = (Button) findViewById(R.id.calcZero);
        zero.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("0", valueLabel, valueString));

        Button one = (Button) findViewById(R.id.calcOne);
        one.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("1", valueLabel, valueString));

        Button two = (Button) findViewById(R.id.calcTwo);
        two.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("2", valueLabel, valueString));

        Button three = (Button) findViewById(R.id.calcThree);
        three.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("3", valueLabel, valueString));

        Button four = (Button) findViewById(R.id.calcFour);
        four.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("4", valueLabel, valueString));

        Button five = (Button) findViewById(R.id.calcFive);
        five.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("5", valueLabel, valueString));

        Button six = (Button) findViewById(R.id.calcSix);
        six.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("6", valueLabel, valueString));

        Button seven = (Button) findViewById(R.id.calcSeven);
        seven.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("7", valueLabel, valueString));

        Button eight = (Button) findViewById(R.id.calcEight);
        eight.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("8", valueLabel, valueString));

        Button nine = (Button) findViewById(R.id.calcNine);
        nine.setOnClickListener(new Calculator.CalculatorNumberOnClickListener("9", valueLabel, valueString));

        Button dot = (Button) findViewById(R.id.calcDot);
        dot.setOnClickListener(new Calculator.CalculatorDotOnClickListener(valueLabel, valueString));

        Button plus = (Button) findViewById(R.id.calcPlus);
        plus.setOnClickListener(new Calculator.CalculatorOperatorOnClickListener(Calculator.Operator.PLUS, valueLabel, valueString));

        Button minus = (Button) findViewById(R.id.calcMinus);
        minus.setOnClickListener(new Calculator.CalculatorOperatorOnClickListener(Calculator.Operator.MINUS, valueLabel, valueString));

        Button multiply = (Button) findViewById(R.id.calcMultiply);
        multiply.setOnClickListener(new Calculator.CalculatorOperatorOnClickListener(Calculator.Operator.MULTIPLY, valueLabel, valueString));

        Button division = (Button) findViewById(R.id.calcDivision);
        division.setOnClickListener(new Calculator.CalculatorOperatorOnClickListener(Calculator.Operator.DIVIDE, valueLabel, valueString));

        Button equal = (Button) findViewById(R.id.calcEql);
        equal.setOnClickListener(new Calculator.CalculatorEqualsOnClickListener(valueLabel, valueString));

        Button clear = (Button) findViewById(R.id.calcClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueString.setLength(0);
                valueLabel.setText(valueString);
            }
        });

        final EditText comment = (EditText) findViewById(R.id.calcEditText);

        Button save = (Button) findViewById(R.id.calcSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator.equalEvent(valueLabel, valueString);
                if(valueString.length() == 0) {
                    CalculatorActivity.super.finish();
                } else {
                    double amount = Double.parseDouble(valueString.toString());
                    Intent returnIntent = new Intent();
                    CostEntity entity = new CostEntity();
                    entity.setCreatedAt(new Date());
                    entity.setComment(comment.getText().toString());
                    if (!isDecrease) {
                        amount = amount * -1;
                    }
                    entity.setAmount(amount);
                    returnIntent.putExtra("result", entity);
                    CalculatorActivity.super.setResult(Activity.RESULT_OK, returnIntent);
                    CalculatorActivity.super.finish();
                }
            }
        });
    }

}
