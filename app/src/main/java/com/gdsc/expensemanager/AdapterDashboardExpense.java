package com.gdsc.expensemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gdsc.expensemanager.Model.Data;

public class AdapterDashboardExpense extends FirebaseRecyclerAdapter<Data, AdapterDashboardExpense.ExpenseViewHolder> {

    public AdapterDashboardExpense(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position, @NonNull Data model) {
        holder.setExpenseType(model.getType());
        holder.setExpenseDate(model.getDate());
        holder.setExpenseAmount(model.getAmount());


    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_expense,parent,false);

        return new ExpenseViewHolder(view);
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder{

        View mExpenseView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            mExpenseView = itemView;

        }

        public void setExpenseType(String type){
            TextView mType = mExpenseView.findViewById(R.id.typeExpenseDs);
            mType.setText(type);
        }
        public void setExpenseAmount(int amount){
            TextView mAmount = mExpenseView.findViewById(R.id.amountExpenseDs);

            String strAmount = String.valueOf(amount);

            mAmount.setText(strAmount);

        }
        public void setExpenseDate(String date){
            TextView mDate = mExpenseView.findViewById(R.id.dateExpenseDs);
            mDate.setText(date);
        }

    }
}
