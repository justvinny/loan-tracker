package com.example.loantracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.loantracker.R;
import com.example.loantracker.data.Loan;
import com.example.loantracker.data.LoanDatabase;
import com.example.loantracker.data.LoanHistory;
import com.example.loantracker.utilities.Message;
import com.example.loantracker.viewmodel.CreateLoanViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CreateLoanActivity extends AppCompatActivity {
    private static final String TAG = "CreateLoanActivity";
    EditText editDebtorName, editLoanAmount;
    CreateLoanViewModel createLoanViewModel;
    FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_loan);

        editDebtorName = findViewById(R.id.edit_debtor_name);
        editLoanAmount = findViewById(R.id.edit_loan_amount);

        createLoanViewModel = new ViewModelProvider(this).get(CreateLoanViewModel.class);

        fabSave = findViewById(R.id.fab_save_loan);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Loan loan = saveNewLoan();
                    Log.d(TAG, "onClick: " + loan.getId());

                    if (loan == null) {
                        Message.show(view, "Fields must not be empty or 0.");
                    } else {
                        createLoanViewModel.insert(loan);
                        createLoanHistory();
                        clearFields();
                    }
                } catch (NumberFormatException e) {
                    Message.show(view, "Amount must not be empty.");
                }
            }
        });
    }

    private Loan saveNewLoan() throws NumberFormatException {
        String debtornName = editDebtorName.getText().toString();
        double loanAmount = Double.parseDouble(editLoanAmount.getText().toString());

        return validateInput(debtornName, loanAmount) ? new Loan(debtornName, loanAmount) : null;
    }

    private boolean validateInput(String debtorName, double loanAmount) {
        return debtorName.length() > 0 && loanAmount > 0;
    }

    private void createLoanHistory() {
        Loan loan = createLoanViewModel.getLatestLoan();
        LoanHistory loanHistory = new LoanHistory(LoanDatabase.format.format(new Date()),
                loan.getId(), loan.getAmount());
        createLoanViewModel.insert(loanHistory);
    }

    private void clearFields() {
        editDebtorName.setText("");
        editLoanAmount.setText("");
    }
}