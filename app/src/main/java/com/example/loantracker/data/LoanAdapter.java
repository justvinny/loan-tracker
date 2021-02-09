package com.example.loantracker.data;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loantracker.R;
import com.example.loantracker.ui.UpdateLoanActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {
    public static final String USER_ID = "USER_ID";
    public static final int RESULT_REQUEST = 1;
    private List<Loan> loanList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_item, parent,
                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Loan loan = loanList.get(position);

        TextView name = holder.nameTxtView;
        name.setText(loan.getName());

        TextView loanAmount = holder.loanAmountTxtView;
        loanAmount.setText(String.format(Locale.getDefault(),"Owes: $%.2f", loan.getAmount()));

        RelativeLayout layout = holder.layoutView;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateLoanActivity.class);
                intent.putExtra(USER_ID, loan.getId());
                ((Activity) view.getContext()).startActivityForResult(intent, RESULT_REQUEST);
            }
        });
    }

    @Override
    public int getItemCount() {
        return loanList.size();
    }

    public void setData(List<Loan> loanList) {
        this.loanList = loanList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout layoutView;
        public TextView nameTxtView;
        public TextView loanAmountTxtView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutView =  itemView.findViewById(R.id.loan_item_layout);
            nameTxtView = itemView.findViewById(R.id.txt_name);
            loanAmountTxtView = itemView.findViewById(R.id.txt_loan_amount);
        }
    }
}
