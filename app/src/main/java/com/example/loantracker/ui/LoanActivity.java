package com.example.loantracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loantracker.R;
import com.example.loantracker.data.LoanAdapter;
import com.example.loantracker.viewmodel.LoanViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoanActivity extends AppCompatActivity {
    private LoanViewModel loanViewModel;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoanAdapter adapter = new LoanAdapter();
        recyclerView.setAdapter(adapter);

        loanViewModel = new ViewModelProvider(this).get(LoanViewModel.class);
        loanViewModel.getLoans().observe(this, loans -> {
            adapter.setData(loans);
        });

        fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateLoanActivity.class);
                startActivity(intent);
            }
        });
    }
}