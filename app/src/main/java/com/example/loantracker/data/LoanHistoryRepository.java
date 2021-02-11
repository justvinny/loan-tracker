package com.example.loantracker.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoanHistoryRepository {
    private LoanHistoryDao loanHistoryDao;

    public LoanHistoryRepository(Application application) {
        LoanDatabase loanDatabase = LoanDatabase.getInstance(application);
        loanHistoryDao = loanDatabase.loanHistoryDao();
    }

    public void insert(LoanHistory loanHistory) {
        LoanDatabase.dbWriterExecutor.submit(() -> {
            loanHistoryDao.insert(loanHistory);
        });
    }

    public MutableLiveData<List<LoanHistory>> getLoanHistory(int loanId) {
        Future<MutableLiveData<List<LoanHistory>>> loanHistory = LoanDatabase.dbWriterExecutor.submit(() -> {
          return new MutableLiveData<>(loanHistoryDao.getLoanHistory(loanId));
        });

        MutableLiveData<List<LoanHistory>> castedLoanHistory = null;
        try {
            castedLoanHistory = loanHistory.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(castedLoanHistory);
    }
}
