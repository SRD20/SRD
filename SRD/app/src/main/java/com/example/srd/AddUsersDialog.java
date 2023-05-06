package com.example.srd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddUsersDialog extends DialogFragment {
    View addUsersDiag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addUsersDiag = inflater.inflate(R.layout.addusers2,null);
        addUsersDiag.setClipToOutline(true);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.layout_bg);
        getDialog().setTitle("Add an Employee");
        Button save = addUsersDiag.findViewById(R.id.add_user_save);
        Button cancel = addUsersDiag.findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"User Saved",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Operation cancelled",Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        return addUsersDiag;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
