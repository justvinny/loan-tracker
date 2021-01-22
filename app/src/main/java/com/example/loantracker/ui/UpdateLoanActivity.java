package com.example.loantracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.loantracker.R;
import com.example.loantracker.data.LoanRecyclerAdapter;
import com.example.loantracker.viewmodel.UpdateLoanViewModel;

public class UpdateLoanActivity extends AppCompatActivity {
    private static final String TAG = "UpdateLoanActivity";
    private UpdateLoanViewModel updateLoanViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_loan);

        updateLoanViewModel = new ViewModelProvider(this).get(UpdateLoanViewModel.class);

        Intent intent = getIntent();
        int id = intent.getIntExtra(LoanRecyclerAdapter.USER_ID, 0);

        String title = String.format("%s's Loan Details", updateLoanViewModel.getLoan(id).getName());
        setTitle(title);
    }
}