package com.example.loantracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loantracker.data.Loan;
import com.example.loantracker.data.LoanHistory;
import com.example.loantracker.data.LoanHistoryRepository;
import com.example.loantracker.data.LoanRepository;

import java.util.List;

public class CreateLoanViewModel extends AndroidViewModel {
    private LoanRepository loanRepository;
    private LoanHistoryRepository loanHistoryRepository;
    private LiveData<List<Loan>> loans;

    public CreateLoanViewModel(@NonNull Application application) {
        super(application);
        loanRepository = new LoanRepository(application);
        loanHistoryRepository = new LoanHistoryRepository(application);
        loans = loanRepository.getLoans();
    }

    public void insert(Loan loan) {
        loanRepository.insert(loan);
    }

    public void insert(LoanHistory loanHistory) {
        loanHistoryRepository.insert(loanHistory);
    }

    public Loan getLatestLoan() {
        return loanRepository.getLatestLoan();
    }
}
