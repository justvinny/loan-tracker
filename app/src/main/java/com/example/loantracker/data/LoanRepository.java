package com.example.loantracker.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LoanRepository {
    private LoanDao loanDao;
    private LiveData<List<Loan>> loans;

    public LoanRepository(Application application) {
        LoanDatabase loanDatabase = LoanDatabase.getInstance(application);
        loanDao = loanDatabase.loanDao();
        loans = loanDao.getAllLoans();
    }

    public LiveData<List<Loan>> getLoans() {
        return loans;
    }

    public void insert(Loan loan) {
        LoanDatabase.dbWriterExecutor.execute(() -> {
            loanDao.insert(loan);
        });
    }

    public void update(Loan loan) {
        LoanDatabase.dbWriterExecutor.execute(() -> {
            loanDao.update(loan);
        });
    }

    public void delete(Loan loan) {
        LoanDatabase.dbWriterExecutor.execute(() -> {
            loanDao.delete(loan);
        });
    }

    public void deleteAllLoans() {
        LoanDatabase.dbWriterExecutor.execute(() -> {
            loanDao.deletaAllLoans();
        });
    }
}
