package com.gdsc.expensemanager;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gdsc.expensemanager.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;


public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DashboardFragment() {

    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //Floating Button
    private FloatingActionButton fabMain;
    private FloatingActionButton fabIncomeBtn;
    private FloatingActionButton fabExpenseBtn;

    private TextView fabIncomeText;
    private TextView fabExpenseText;

    private boolean isOpen;

    private Animation fateOpen, fateClose;

    //Dashboard income adn expense

    private TextView totalIncomeResult, totalExpenseResult;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    //Recycler view
    private RecyclerView mRecyclerIncome, mRecyclerExpense;
    private AdapterDashboardIncome incomeAdapter;
    private AdapterDashboardExpense expenseAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        mRecyclerIncome=(RecyclerView) myView.findViewById(R.id.recyclerIncome);

        mRecyclerIncome.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Data> optionsIncome =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mIncomeDatabase, Data.class)
                        .build();

        incomeAdapter = new AdapterDashboardIncome(optionsIncome);

        mRecyclerIncome.setAdapter(incomeAdapter);

        mRecyclerExpense=(RecyclerView) myView.findViewById(R.id.recyclerExpense);

        mRecyclerExpense.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Data> optionsExpense =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mExpenseDatabase, Data.class)
                        .build();

        expenseAdapter = new AdapterDashboardExpense(optionsExpense);

        mRecyclerExpense.setAdapter(expenseAdapter);


        fabMain = myView.findViewById(R.id.fbMainPlusBtn);
        fabIncomeBtn = myView.findViewById(R.id.incomeFtBtn);
        fabExpenseBtn = myView.findViewById(R.id.expenseFtBtn);

        //Connect Floating Text
        fabIncomeText = myView.findViewById(R.id.incomeFtText);
        fabExpenseText = myView.findViewById(R.id.expenseFtText);

        totalIncomeResult = myView.findViewById(R.id.incomeSetResult);
        totalExpenseResult = myView.findViewById(R.id.expenseSetResult);

        //Recycler
        mRecyclerIncome = myView.findViewById(R.id.recyclerIncome);
        mRecyclerExpense = myView.findViewById(R.id.recyclerExpense);

        fateOpen= AnimationUtils.loadAnimation(getActivity(),R.anim.fate_open);
        fateClose= AnimationUtils.loadAnimation(getActivity(),R.anim.fate_close);

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();

                if(isOpen){
                    fabIncomeBtn.startAnimation(fateClose);
                    fabExpenseBtn.startAnimation(fateClose);
                    fabIncomeBtn.setClickable(false);
                    fabExpenseBtn.setClickable(false);

                    fabIncomeText.startAnimation(fateClose);
                    fabExpenseText.startAnimation(fateClose);
                    fabIncomeText.setClickable(false);
                    fabExpenseText.setClickable(false);
                    isOpen=false;
                }
                else{
                    fabIncomeBtn.startAnimation(fateOpen);
                    fabExpenseBtn.startAnimation(fateOpen);
                    fabIncomeBtn.setClickable(true);
                    fabExpenseBtn.setClickable(true);

                    fabIncomeText.startAnimation(fateOpen);
                    fabExpenseText.startAnimation(fateOpen);
                    fabIncomeText.setClickable(true);
                    fabExpenseText.setClickable(true);
                    isOpen=true;
                }

            }
        });

        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalSum = 0;

                for (DataSnapshot mySnap:snapshot.getChildren()){
                    Data data = mySnap.getValue(Data.class);

                    totalSum+=data.getAmount();

                    String stResult =String.valueOf(totalSum);

                    totalIncomeResult.setText(stResult);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalSum = 0;

                for (DataSnapshot mySnap:snapshot.getChildren()){
                    Data data = mySnap.getValue(Data.class);

                    totalSum+=data.getAmount();

                    String stResult =String.valueOf(totalSum);

                    totalExpenseResult.setText(stResult);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Recycler
        LinearLayoutManager layoutManagerIncome = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManagerIncome.setStackFromEnd(true);
        layoutManagerIncome.setReverseLayout(true);
        mRecyclerIncome.setHasFixedSize(true);
        mRecyclerIncome.setLayoutManager(layoutManagerIncome);

        LinearLayoutManager layoutManagerExpense = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManagerExpense.setStackFromEnd(true);
        layoutManagerExpense.setReverseLayout(true);
        mRecyclerExpense.setHasFixedSize(true);
        mRecyclerExpense.setLayoutManager(layoutManagerExpense);


        return myView;
    }

    //Floating Button animation
    private void ftAnimation(){

                if(isOpen){
                    fabIncomeBtn.startAnimation(fateClose);
                    fabExpenseBtn.startAnimation(fateClose);
                    fabIncomeBtn.setClickable(false);
                    fabExpenseBtn.setClickable(false);

                    fabIncomeText.startAnimation(fateClose);
                    fabExpenseText.startAnimation(fateClose);
                    fabIncomeText.setClickable(false);
                    fabExpenseText.setClickable(false);
                    isOpen=false;
                }
                else{
                    fabIncomeBtn.startAnimation(fateOpen);
                    fabExpenseBtn.startAnimation(fateOpen);
                    fabIncomeBtn.setClickable(true);
                    fabExpenseBtn.setClickable(true);

                    fabIncomeText.startAnimation(fateOpen);
                    fabExpenseText.startAnimation(fateOpen);
                    fabIncomeText.setClickable(true);
                    fabExpenseText.setClickable(true);
                    isOpen=true;
                }

            }

    private void addData(){

        fabIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                incomeDataInsert();

            }
        });

        fabExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseDataInsert();
            }
        });

    }

    public void incomeDataInsert(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myView = inflater.inflate(R.layout.custom_layout_for_insert_data,null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText editAmount = myView.findViewById(R.id.amountEdit);
        EditText editType = myView.findViewById(R.id.typeEdit);
        EditText editNote = myView.findViewById(R.id.noteEdit);

        Button btnSave = myView.findViewById(R.id.btn_save);
        Button btnCancel = myView.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = editType.getText().toString().trim();
                String amount = editAmount.getText().toString().trim();
                String note = editNote.getText().toString().trim();

                if(TextUtils.isEmpty(type)){
                    editType.setError("Required Field");
                    return;
                }
                if(TextUtils.isEmpty(amount)){
                    editAmount.setError("Required Field");
                    return;
                }

                int ourAmountInt = Integer.parseInt(amount);

                String id =mIncomeDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data= new Data(ourAmountInt, type, note, id, mDate);
                mIncomeDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(), "DATA ADDED", Toast.LENGTH_SHORT).show();

                ftAnimation();
                dialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void ExpenseDataInsert() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myView = inflater.inflate(R.layout.custom_layout_for_insert_data, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();

        dialog.setCancelable(false);

        final EditText amount = myView.findViewById(R.id.amountEdit);
        EditText type = myView.findViewById(R.id.typeEdit);
        EditText note = myView.findViewById(R.id.noteEdit);

        Button btnSave = myView.findViewById(R.id.btn_save);
        Button btnCancel = myView.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmType = type.getText().toString().trim();
                String tmAmount = amount.getText().toString().trim();
                String tmNote = note.getText().toString().trim();

                if (TextUtils.isEmpty(tmType)) {
                    type.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(tmAmount)) {
                    amount.setError("Required Field");
                    return;
                }

                int ourAmountInt = Integer.parseInt(tmAmount);

                String id = mExpenseDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(ourAmountInt, tmType, tmNote, id, mDate);
                mExpenseDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(), "DATA ADDED", Toast.LENGTH_SHORT).show();

                ftAnimation();
                dialog.dismiss();


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ftAnimation();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        incomeAdapter.startListening();
        expenseAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        incomeAdapter.stopListening();
        expenseAdapter.stopListening();
    }
}