package com.example.loantracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LoanDao {

    @Insert
    public void insert(Loan loan);

    @Update
    public void update(Loan loan);

    @Delete
    public void delete(Loan loan);

    @Query("SELECT * FROM loan_table")
    public LiveData<List<Loan>> getAllLoans();

    @Query("DELETE FROM loan_table")
    public void deletaAllLoans();
}
