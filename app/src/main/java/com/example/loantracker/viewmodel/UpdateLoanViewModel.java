package com.example.loantracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.loantracker.data.Loan;
import com.example.loantracker.data.LoanHistory;
import com.example.loantracker.data.LoanHistoryRepository;
import com.example.loantracker.data.LoanRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateLoanViewModel extends AndroidViewModel {
    private LoanRepository loanRepository;
    private LoanHistoryRepository loanHistoryRepository;
    private LiveData<List<Loan>> loans;
    private MutableLiveData<List<LoanHistory>> loanHistory;

    public UpdateLoanViewModel(@NonNull Application application) {
        super(application);
        loanRepository = new LoanRepository(application);
        loanHistoryRepository = new LoanHistoryRepository(application);
        loans = loanRepository.getLoans();
        loanHistory = new MutableLiveData<>();
    }

    public LiveData<List<Loan>> getLoans() {
        return loans;
    }

    public MutableLiveData<List<LoanHistory>> getLoanHistory() { return loanHistory; }

    public Loan getLoan(int id) {
        return loanRepository.getLoan(id);
    }

    public void insertLoanHistory(LoanHistory loanHistory) {
        loanHistoryRepository.insert(loanHistory);
    }

    public void updateLoan(Loan loan) {
        loanRepository.update(loan);
    }

    public void setLoanHistory(int loanId) {
        loanHistory.setValue(loanHistoryRepository.getLoanHistory(loanId).getValue());
    }
}
