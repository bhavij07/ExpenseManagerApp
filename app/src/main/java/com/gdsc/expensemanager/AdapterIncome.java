package com.gdsc.expensemanager;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gdsc.expensemanager.Model.Data;

public class AdapterIncome extends FirebaseRecyclerAdapter<Data, AdapterIncome.MyViewHolder>{


    public AdapterIncome(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {

        holder.setType(model.getType());
        holder.setNote(model.getNote());
        holder.setDate(model.getDate());
        holder.setAmount(model.getAmount());

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateDataItem();
//            }
//        });


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_data,parent,false);

        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        void setType(String type){
            TextView mType = mView.findViewById(R.id.typeTextIncome);
            mType.setText(type);
        }

        void setNote(String note){
            TextView mNote = mView.findViewById(R.id.noteTextIncome);
            mNote.setText(note);
        }

        void setDate(String date){
            TextView mDate = mView.findViewById(R.id.dateTextIncome);
            mDate.setText(date);
        }

        void setAmount(int amount){
            TextView mAmount = mView.findViewById(R.id.amountTextIncome);
            String stAmount = String.valueOf(amount);
            mAmount.setText(stAmount);
        }
    }

        //update edit text
        private EditText editAmount;
        private EditText editType;
        private EditText editNote;

        //button
        private Button btnUpdate;
        private Button btnDelete;

//        private void updateDataItem() {
//            Dialog myDialog = new Dialog(MyAdapterIncome.this);
//
//            LayoutInflater inflater = ;
//
//            View myView = inflater.inflate(R.layout.update_data_item, null);
//            myDialog.setView(myView);
//
//            editAmount = myView.findViewById(R.id.amountEdit);
//            editType = myView.findViewById(R.id.typeEdit);
//            editNote = myView.findViewById(R.id.noteEdit);
//
//            btnUpdate = myView.findViewById(R.id.btn_update);
//            btnDelete = myView.findViewById(R.id.btn_delete);
//
//            AlertDialog dialog = myDialog.create();
//
//            btnUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//
//                }
//            });
//
//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//
//                }
//            });
//            dialog.show();
//        }
}
