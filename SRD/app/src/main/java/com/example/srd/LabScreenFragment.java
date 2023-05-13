package com.example.srd;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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
                String[] datetime = getDateTime();
                Toast.makeText(ctx, "Lab Data saved. "+datetime[0]+" & "+datetime[1] + " "+datetime[2], Toast.LENGTH_SHORT).show();
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
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
        return currDateTime.format(myFormatObj).split(" ");
    }

}
