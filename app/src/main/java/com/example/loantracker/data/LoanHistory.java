package com.example.loantracker.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "loan_history_table")
public class LoanHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private int loan_id;
    private double amount;

    @NonNull
    @ColumnInfo(name = "description", defaultValue = "N/A")
    private String description;

    public LoanHistory(String date, int loan_id, double amount, String description) {
        this.date = date;
        this.loan_id = loan_id;
        this.amount = amount;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() { return date; }

    public int getLoan_id() {
        return loan_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() { return description; }
}
