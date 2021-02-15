package com.example.loantracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.loantracker.R;
import com.example.loantracker.data.Loan;
import com.example.loantracker.data.LoanAdapter;
import com.example.loantracker.data.LoanDatabase;
import com.example.loantracker.data.LoanHistory;
import com.example.loantracker.data.LoanHistoryAdapter;
import com.example.loantracker.utilities.Message;
import com.example.loantracker.viewmodel.UpdateLoanViewModel;

import java.util.ArrayList;
import java.util.Date;

public class UpdateLoanActivity extends AppCompatActivity {
    private static final String TAG = "UpdateLoanActivity";
    private UpdateLoanViewModel updateLoanViewModel;
    private EditText editAdd, editMinus;
    private Button btnUpdate;
    private int id;
    Loan loan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_loan);

        updateLoanViewModel = new ViewModelProvider(this).get(UpdateLoanViewModel.class);

        Intent intent = getIntent();
        id = intent.getIntExtra(LoanAdapter.USER_ID, 0);

        loan = updateLoanViewModel.getLoan(id);
        String title = String.format("%s's Loan Details", loan.getName());
        setTitle(title);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_loan_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LoanHistoryAdapter adapter = new LoanHistoryAdapter();
        updateLoanViewModel.setLoanHistory(id);
        adapter.setData((ArrayList<LoanHistory>) updateLoanViewModel.getLoanHistory().getValue());
        recyclerView.setAdapter(adapter);

        editAdd = findViewById(R.id.edit_add_amount);
        editMinus = findViewById(R.id.edit_minus_amount);

        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update()) {
                    adapter.setData((ArrayList<LoanHistory>) updateLoanViewModel.getLoanHistory().getValue());
                    clearFields();
                } else {
                    Message.show(view, "Field must not be empty or 0.");
                }
            }
        });
    }

    public boolean update() {
        String addValue = editAdd.getText().toString();
        if (validate(addValue)) {
            double addAmount = Double.parseDouble(addValue);
            loan.addValue(addAmount);
            updateLoanViewModel.updateLoan(loan);
        }

        String minusValue = editMinus.getText().toString();
        if (validate(minusValue)) {
            double minusAmount = Double.parseDouble(minusValue);
            loan.minusValue(minusAmount);
            updateLoanViewModel.updateLoan(loan);
        }

        if (!addValue.isEmpty() || !minusValue.isEmpty()) {
            LoanHistory loanHistory = new LoanHistory(LoanDatabase.format.format(new Date()),
                    id, loan.getAmount());
            updateLoanViewModel.insertLoanHistory(loanHistory);
            updateLoanViewModel.setLoanHistory(id);
        }

        return validate(addValue) || validate(minusValue);
    }

    public boolean validate(String amount) {
        return (!amount.isEmpty()) && Double.parseDouble(amount) > 0;
    }
    public void clearFields() {
        editMinus.setText("");
        editAdd.setText("");
    }
}