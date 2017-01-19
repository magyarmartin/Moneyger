package hu.martin4955.moneyger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by martin4955 on 2017. 01. 05..
 */
public class Costs {

    private static Costs ourInstance;
    private List<CostEntity> costs;
    private double monthlyLimit = 30000;
    private SQLiteDatabase moneygerDB;

    public static Costs getInstance(SQLiteDatabase moneygerDB, double monthlyLimit) {
        return new Costs(moneygerDB, monthlyLimit);
    }

    private Costs(SQLiteDatabase moneygerDB, double monthlyLimit) {
        costs = new ArrayList<>();
        this.monthlyLimit = monthlyLimit;
        this.moneygerDB = moneygerDB;

        moneygerDB.execSQL("CREATE TABLE IF NOT EXISTS costs (amount REAL, createdAt TEXT, comment TEXT)");

        Cursor c = moneygerDB.rawQuery("SELECT * FROM costs", null);
        int amountIndex = c.getColumnIndex("amount");
        int dateIndex = c.getColumnIndex("createdAt");
        int commentIndex = c.getColumnIndex("comment");

        if(c.moveToFirst()) {
            boolean hasNext = true;
            while (hasNext) {
                CostEntity e = new CostEntity();
                e.setAmount(c.getDouble(amountIndex));
                e.setComment(c.getString(commentIndex));
                e.setCreatedAt(c.getString(dateIndex));

                Log.i("MainActivity", "" +e.getComment());
                costs.add(e);

                hasNext = c.moveToNext();

                Date today = new Date();
            }
        }
    }

    public List<CostEntity> getCosts() {
        return costs;
    }

    public void addCost(CostEntity cost) {
        costs.add(cost);
        final double amountOfMoney = cost.getAmount();
        final String comment = cost.getComment();
        new Thread() {
            public void run() {
                try {
                    ContentValues content = new ContentValues();
                    content.put("amount", amountOfMoney);
                    content.put("comment", comment);
                    String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    content.put("createdAt", today);
                    moneygerDB.insert("costs", null, content);
                    Log.i("CalculatorActivity", "Data saved to DB successfully");
                } catch (Exception e) {
                    Log.e("CalculatorActivity", "An error occurred while tired to insert a row to database", e);
                }
            }
        }.start();
    }

    public void removeCost(CostEntity cost) {
        costs.remove(cost);
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public double getDailyLimit() {
        double dailySpendings = getDailySpendings();
        if(getDailySpendings() == 0) {
            return getTodayLimit();
        } else {
            double dailyLimit = (monthlyLimit - dailySpendings) / (getRemainingDaysOfThisMonth() - 1);
            return Math.round(dailyLimit * 100.0) / 100.0;
        }
    }

    public double getDailySpendings() {
        double spentToday = 0d;
        for(CostEntity cost: costs) {
            Date today = new Date();
            if(cost.getCreatedAt().getYear() == today.getYear()
                    && cost.getCreatedAt().getMonth() == today.getMonth()
                    && cost.getCreatedAt().getDay() == today.getDay()) {
                spentToday += cost.getAmount();
            }
        }
        return spentToday;
    }

    public double getTodayLimit() {
        double todayLimit = monthlyLimit / getRemainingDaysOfThisMonth() -  getDailySpendings();
        return Math.round(todayLimit * 100.0) / 100.0;
    }

    private int getRemainingDaysOfThisMonth() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH));
    }
}
