package com.example.loantracker.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoanHistoryRepository {
    private LoanHistoryDao loanHistoryDao;

    public LoanHistoryRepository(Application application) {
        LoanDatabase loanDatabase = LoanDatabase.getInstance(application);
        loanHistoryDao = loanDatabase.loanHistoryDao();
    }

    public void insert(LoanHistory loanHistory) {
        LoanDatabase.dbWriterExecutor.execute(() -> {
            loanHistoryDao.insert(loanHistory);
        });
    }

    public LiveData<List<LoanHistory>> getLoanHistory(int loanId) {
        Future<LiveData<List<LoanHistory>>> loanHistory = LoanDatabase.dbWriterExecutor.submit(() -> {
          return loanHistoryDao.getLoanHistory(loanId);
        });

        LiveData<List<LoanHistory>> castedLoanHistory = null;
        try {
            castedLoanHistory = loanHistory.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return castedLoanHistory;
    }
}
