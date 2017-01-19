package hu.martin4955.moneyger;

import android.view.View;
import android.widget.TextView;

/**
 * Created by martin4955 on 2016. 12. 05..
 */

public class Calculator {

    public static class CalculatorNumberOnClickListener implements View.OnClickListener {

        private String number;
        private static TextView valueLabel;
        private static StringBuilder valueString;

        public CalculatorNumberOnClickListener(String number, TextView valueLabel, StringBuilder valueBuilder) {
            this.number = number;
            this.valueString = valueBuilder;
            this.valueLabel = valueLabel;
        }


        @Override
        public void onClick(View v) {
            valueString.append(number);
            valueLabel.setText(valueString.toString());
        }
    }

    public static class CalculatorOperatorOnClickListener implements  View.OnClickListener {

        private Operator operator;
        private static TextView valueLabel;
        private static StringBuilder valueString;

        public CalculatorOperatorOnClickListener(Operator operator, TextView valueLabel, StringBuilder valueBuilder) {
            this.operator = operator;
            this.valueString = valueBuilder;
            this.valueLabel = valueLabel;
        }

        @Override
        public void onClick(View v) {
            String[] labelTexts = valueLabel.getText().toString().split(" ");
            if(!labelTexts[0].isEmpty()) {
                if(labelTexts.length == 3) {
                    valueString.setLength(0);
                    valueString.append(calculate(labelTexts[0], labelTexts[2], labelTexts[1]));
                } else if (labelTexts.length == 2) {
                    valueString.setLength(0);
                    valueString.append(labelTexts[0]);
                }
                switch(operator) {
                    case PLUS:
                        valueString.append(" + ");
                        break;
                    case MINUS:
                        valueString.append(" - ");
                        break;
                    case MULTIPLY:
                        valueString.append(" * ");
                        break;
                    case DIVIDE:
                        valueString.append(" / ");
                        break;
                }
                valueLabel.setText(valueString.toString());
            }
        }

    }

    public static class CalculatorEqualsOnClickListener implements  View.OnClickListener {

        private static TextView valueLabel;
        private static StringBuilder valueString;

        public CalculatorEqualsOnClickListener(TextView valueLabel, StringBuilder valueBuilder) {
            this.valueString = valueBuilder;
            this.valueLabel = valueLabel;
        }

        @Override
        public void onClick(View v) {
            equalEvent(valueLabel, valueString);
        }
    }

    public static class CalculatorDotOnClickListener implements  View.OnClickListener {

        private static TextView valueLabel;
        private static StringBuilder valueString;
        private boolean isThereDot = false;

        public CalculatorDotOnClickListener(TextView valueLabel, StringBuilder valueBuilder) {
            this.valueString = valueBuilder;
            this.valueLabel = valueLabel;
        }

        @Override
        public void onClick(View v) {
            String[] labelTexts = valueLabel.getText().toString().split(" ");
            isThereDot = labelTexts[0].contains(".") ? true : false;
            if(isThereDot) {
            //    valueLabel.setText("con");
            }
            if(!isThereDot && labelTexts.length == 1 && !labelTexts[0].isEmpty()) {
                valueString.append(".");
                valueLabel.setText(valueString.toString());
                isThereDot = true;
            }
        }

    }

    private static double calculate(String first, String second, String operator) {
        switch (operator) {
            case "+" :
                return Double.parseDouble(first) + Double.parseDouble(second);
            case "-" :
                return Double.parseDouble(first) - Double.parseDouble(second);
            case "*" :
                return Double.parseDouble(first) * Double.parseDouble(second);
            default :
                return Double.parseDouble(first) / Double.parseDouble(second);
        }
    }

    public static void equalEvent(TextView valueLabel, StringBuilder valueString) {
        valueString.setLength(0);
        String[] labelTexts = valueLabel.getText().toString().split(" ");
        if(labelTexts.length == 3) {
            valueString.append(calculate(labelTexts[0], labelTexts[2], labelTexts[1]));
            valueLabel.setText(valueString);
        } else if(labelTexts.length < 3) {
            valueString.append(labelTexts[0]);
            valueLabel.setText(labelTexts[0]);
        }
    }

    enum Operator {
        PLUS, MINUS, DIVIDE, MULTIPLY;
    }

}
