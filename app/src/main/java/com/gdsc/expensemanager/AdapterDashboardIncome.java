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

public class AdapterDashboardIncome extends FirebaseRecyclerAdapter<Data, AdapterDashboardIncome.IncomeViewHolder> {

    public AdapterDashboardIncome(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IncomeViewHolder holder, int position, @NonNull Data model) {

        holder.setIncomeType(model.getType());
        holder.setIncomeDate(model.getDate());
        holder.setIncomeAmount(model.getAmount());

    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_income,parent,false);

        return new IncomeViewHolder(view);
    }

    public static class IncomeViewHolder extends RecyclerView.ViewHolder{

        View mIncomeView;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            mIncomeView = itemView;

        }

        public void setIncomeType(String type){
            TextView mType = mIncomeView.findViewById(R.id.typeIncomeDs);
            mType.setText(type);
        }
        public void setIncomeAmount(int amount){
            TextView mAmount = mIncomeView.findViewById(R.id.amountIncomeDs);

            String strAmount = String.valueOf(amount);

            mAmount.setText(strAmount);

        }
        public void setIncomeDate(String date){
            TextView mDate = mIncomeView.findViewById(R.id.dateIncomeDs);
            mDate.setText(date);
        }

    }
}
