package com.example.srd;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOEmployee {
    private DatabaseReference databaseReference;
    public DAOEmployee(){
        databaseReference = FirebaseDatabase.getInstance().getReference("SRD_Table").child("login").child("Normal");
    }
    public Task<Void> addEmployee(EmployeeModel emp){
        return databaseReference.child(emp.getEmpId()).setValue(emp);
    }
    public Task<Void> updateEmployee(String key, HashMap<String,Object> hm){
            return databaseReference.child(key).updateChildren(hm);
    }
}
