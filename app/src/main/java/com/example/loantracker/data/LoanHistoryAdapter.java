package com.example.loantracker.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loantracker.R;

import java.util.ArrayList;
import java.util.Locale;

public class LoanHistoryAdapter extends RecyclerView.Adapter<LoanHistoryAdapter.ViewHolder> {

    private ArrayList<LoanHistory> loanHistoryList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_history_item, parent,
                false);

        return new LoanHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoanHistory loanHistory = loanHistoryList.get(position);
        TextView amount = holder.textLoanHistoryAmount;
        amount.setText(String.format(Locale.getDefault(), "%.2f", loanHistory.getAmount()));

        TextView date = holder.textLoanHistoryDate;
        date.setText(loanHistory.getDate());
    }

    @Override
    public int getItemCount() {
        return loanHistoryList.size();
    }

    public void setData(ArrayList<LoanHistory> loanHistoryList) {
        this.loanHistoryList = loanHistoryList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLoanHistoryDate;
        public TextView textLoanHistoryAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textLoanHistoryDate = itemView.findViewById(R.id.text_loan_history_date);
            textLoanHistoryAmount = itemView.findViewById(R.id.text_loan_history_amount);
        }
    }
}
