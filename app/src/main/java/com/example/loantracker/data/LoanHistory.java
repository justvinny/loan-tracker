package com.example.loantracker.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "loan_history_table")
public class LoanHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int loan_id;
    private double amount;

    public LoanHistory(int loan_id, double amount) {
        this.loan_id = loan_id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public double getAmount() {
        return amount;
    }

}
