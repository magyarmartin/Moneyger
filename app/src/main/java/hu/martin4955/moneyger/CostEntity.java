package hu.martin4955.moneyger;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by martin4955 on 2016. 12. 20..
 */

public class CostEntity implements Serializable{
    private double amount;
    private Date createdAt;
    private String comment;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(String createdAt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.createdAt = format.parse(createdAt);
        } catch (ParseException e) {
            Log.e("CostEntity", "An error occurred while tried to parse 'createdAt' string", e);
        }
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
