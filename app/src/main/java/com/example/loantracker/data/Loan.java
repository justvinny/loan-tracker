package com.example.loantracker.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "loan_table")
public class Loan {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private double amount;

    public Loan(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
}
