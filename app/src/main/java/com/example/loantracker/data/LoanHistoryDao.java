package com.example.loantracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LoanHistoryDao {

    @Insert
    public void insert(LoanHistory loanHistory);

    @Query("SELECT * FROM loan_history_table WHERE loan_id = :loanId")
    public LiveData<List<LoanHistory>> getLoanHistory(int loanId);
}
