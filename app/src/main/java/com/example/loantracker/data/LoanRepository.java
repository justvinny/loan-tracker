package com.example.loantracker.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class LoanRepository {
    private static final String TAG = "LoanRepository";
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

    public Loan getLoan(int searchId) {
        // Used Future since we're trying to get a value back from a concurrent operation.
        Future<Loan> loan = LoanDatabase.dbWriterExecutor.submit(() -> {
            return loanDao.getLoan(searchId);
        });

        Loan castedLoan = null;
        try {
            castedLoan = (Loan) loan.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return castedLoan;
    }

    public void deleteAllLoans() {
        LoanDatabase.dbWriterExecutor.execute(() -> {
            loanDao.deletaAllLoans();
        });
    }
}
