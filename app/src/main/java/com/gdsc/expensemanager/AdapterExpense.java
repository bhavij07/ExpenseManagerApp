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

public class AdapterExpense extends FirebaseRecyclerAdapter<Data, AdapterExpense.MyViewHolder> {

    public AdapterExpense(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull AdapterExpense.MyViewHolder holder, int position, @NonNull Data model) {

        holder.setType(model.getType());
        holder.setNote(model.getNote());
        holder.setDate(model.getDate());
        holder.setAmount(model.getAmount());

    }

    @NonNull
    @Override
    public AdapterExpense.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_data,parent,false);

        return new AdapterExpense.MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        //        TextView mType, mNote, mDate, mAmount;
        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        private void setType(String type){
            TextView mType = mView.findViewById(R.id.typeTextExpense);
            mType.setText(type);
        }

        private void setNote(String note){
            TextView mNote = mView.findViewById(R.id.noteTextExpense);
            mNote.setText(note);
        }

        private void setDate(String date){
            TextView mDate = mView.findViewById(R.id.dateTextExpense);
            mDate.setText(date);
        }

        private void setAmount(int amount){
            TextView mAmount = mView.findViewById(R.id.amountTextExpense);
            String stAmount = String.valueOf(amount);
            mAmount.setText(stAmount);
        }
    }

}
