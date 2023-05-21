package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class LabDataActivity extends AppCompatActivity {
    AutoCompleteTextView acv;
    TextView date,lab_data;
    int myear,mmonth,mday;
    String lab_picked,date_picked,data;
    RecyclerView rcv;
    LabDataAdapter lab_adapter;
    Button view_data;
    LabDataAdapter.DataChangedCallback callback = new LabDataAdapter.DataChangedCallback() {
        @Override
        public void onDataChangedCallback() {
            //called when there are no entries found for that particular date
            //show a dialog or toast message
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LabDataActivity.this)
                    .setMessage("No records found on this date");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_data);
        acv = findViewById(R.id.lab_drop_down);
        rcv = findViewById(R.id.lab_data_rcv);
        lab_data = findViewById(R.id.lb_date_tv);
        view_data = findViewById(R.id.lab_view_data);
        String[] arr = getResources().getStringArray(R.array.lab_names);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.lab_simple_list_view,arr);
        acv.setAdapter(arrayAdapter);
        data = "Data for the date : ";
        setDate();
        rcv.setLayoutManager(new LinearLayoutManager(this));

        acv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String lab = adapterView.getItemAtPosition(i).toString();
                lab_picked = "SAS_" + lab;
                Toast.makeText(LabDataActivity.this, "Lab selected : "+lab, Toast.LENGTH_SHORT).show();
                Log.d("SRD_test","in spinner : lab & date for lab data : "+lab_picked+" & "+date_picked );

            }
        });

        date = findViewById(R.id.date_tv);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(lab_picked)){
                    Toast.makeText(LabDataActivity.this, "Please select the lab", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(lab_adapter!=null)
                    lab_adapter.stopListening();
                Log.d("SRD_test","in button : lab & date for lab data : "+lab_picked+" & "+date_picked );
                FirebaseRecyclerOptions<LabDataModel> options =
                        new FirebaseRecyclerOptions.Builder<LabDataModel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference("SRD_Table").child("Lab_Data").child(lab_picked).child(date_picked), LabDataModel.class)
                                .build();
                lab_adapter = new LabDataAdapter(options,callback);
                lab_adapter.startListening();
                lab_data.setText(data+date_picked);
                Log.d("SRD_test","snapshots empty ? : "+options.getSnapshots().size());
                rcv.setAdapter(lab_adapter);
            }
        });
    }

    private void setDate() {
        Calendar cr  = Calendar.getInstance();
        myear = cr.get(Calendar.YEAR);
        mmonth = cr.get(Calendar.MONTH) + 1;
        mday = cr.get(Calendar.DAY_OF_MONTH);
        date_picked = (mday<10? "0"+String.valueOf(mday):String.valueOf(mday))+ "-"+ (mmonth < 10 ?"0"+String.valueOf(mmonth):String.valueOf(mmonth))+ "-"+String.valueOf(myear);
    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, com.google.android.material.R.style.Base_Animation_AppCompat_Dialog , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myear = year;
                mmonth = month+1;
                mday = day;
                date_picked = (day<10? "0"+String.valueOf(day):String.valueOf(day))+ "-"+(mmonth < 10 ?"0"+String.valueOf(mmonth):String.valueOf(month))+ "-"+String.valueOf(year);
                //Showing the picked value in the textView
                date.setText(date_picked);
            }
        }, myear, mmonth-1, mday);
        datePickerDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(lab_adapter!=null)
            lab_adapter.stopListening();
    }
}