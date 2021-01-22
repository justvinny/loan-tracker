package com.example.loantracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loantracker.data.Loan;
import com.example.loantracker.data.LoanRepository;

import java.util.List;
import java.util.Objects;

public class UpdateLoanViewModel extends AndroidViewModel {
    private LoanRepository loanRepository;
    private LiveData<List<Loan>> loans;

    public UpdateLoanViewModel(@NonNull Application application) {
        super(application);
        loanRepository = new LoanRepository(application);
        loans = loanRepository.getLoans();
    }

    public LiveData<List<Loan>> getLoans() {
        return loans;
    }

    public Loan getLoan(int id) {
        return loanRepository.getLoan(id);
    }
}
