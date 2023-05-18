package com.example.srd;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LabScreenFragment extends DialogFragment {
    Context ctx;
    LabDataModel lab_data;
    public LabScreenFragment(Context c,LabDataModel lab){
        ctx = c;
        lab_data = lab;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View labScreen = inflater.inflate(R.layout.lab_readings,null);
        EditText temp = labScreen.findViewById(R.id.lb_temp);
        EditText humidity = labScreen.findViewById(R.id.lb_humid);
        temp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        humidity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        Button save = labScreen.findViewById(R.id.lb_save);
        Button cancel = labScreen.findViewById(R.id.lb_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = temp.getText().toString();
                String hmd = humidity.getText().toString();
                if(TextUtils.isEmpty(tmp) || TextUtils.isEmpty(hmd)){
                    Toast.makeText(ctx, "Please enter both the data", Toast.LENGTH_SHORT).show();
                }else {
                    //Log.d("SRD_test","temp & humidity "+tmp+" & "+hmd);
                    lab_data.setTemp(tmp);
                    lab_data.setHumidity(hmd);
                    //0 date, 1 time, 2 AM/PM
                    String[] datetime = getDateTime();
                    lab_data.setDate(datetime[0]);
                    lab_data.setTime(datetime[1]+" "+datetime[2]);
                    saveData(datetime[0]);
                    Toast.makeText(ctx, "Lab Data saved. " + datetime[0] + " & " + datetime[1] + " " + datetime[2], Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Cancelled", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return labScreen;
    }

    private void saveData(String date) {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("SRD_Table").child("Lab_Data").child(lab_data.getLabId());
        dbref.child(date).push().setValue(lab_data);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    private String[] getDateTime(){
        LocalDateTime currDateTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
        return currDateTime.format(myFormatObj).split(" ");
    }

}
