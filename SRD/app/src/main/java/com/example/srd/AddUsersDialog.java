package com.example.srd;

import android.os.Bundle;
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

public class AddUsersDialog extends DialogFragment {
    View addUsersDiag;
    EditText emp_id, emp_name, emp_pwd, emp_phno;
    private DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addUsersDiag = inflater.inflate(R.layout.addusers2,null);
        addUsersDiag.setClipToOutline(true);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.layout_bg);
        getDialog().setTitle("Add an Employee");
        Button save = addUsersDiag.findViewById(R.id.add_user_save);
        Button cancel = addUsersDiag.findViewById(R.id.cancel);
        emp_id = addUsersDiag.findViewById(R.id.empid);
        emp_name = addUsersDiag.findViewById(R.id.username);
        emp_pwd = addUsersDiag.findViewById(R.id.pwd);
        emp_phno = addUsersDiag.findViewById(R.id.phn_no);
        dbHelper = DBHelper.getDBInstance(getActivity().getApplicationContext());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emp_id.getText().toString().isEmpty() || emp_name.getText().toString().isEmpty() ||
                emp_pwd.getText().toString().isEmpty() || emp_phno.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    //Need to save hash of pwd instead of pwd
                    EmployeeModel employee = new EmployeeModel(emp_id.getText().toString(),emp_name.getText().toString(),
                            emp_pwd.getText().toString(),emp_phno.getText().toString());
                    Log.d("SRD_test",employee.empId+","+employee.getName());
                    addUserToRTDB(employee);
                    if(dbHelper.addEmployee(employee))
                        Toast.makeText(getActivity().getApplicationContext(), "User Saved", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity().getApplicationContext(), "User couldn't be Saved", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
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


    private void addUserToRTDB(EmployeeModel employeeModel) {
        DAOEmployee daoEmployee = new DAOEmployee();
        daoEmployee.addEmployee(employeeModel).addOnSuccessListener(success ->{
            //Toast.makeText(getActivity().getApplicationContext(), "User Saved in RTDB", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(fail -> {
            //Toast.makeText(getActivity().getApplicationContext(), "Failed to save in RTDB "+ fail.getMessage(), Toast.LENGTH_SHORT).show();
        });
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
