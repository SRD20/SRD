package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminScreen extends AppCompatActivity {
    private Button add_users;
    private Button del_users;
    private Button view_users;
    private Button view_compln;
    private EditText emp_id,username,passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        add_users = (Button) findViewById(R.id.add_user_btn);
        del_users = (Button) findViewById(R.id.delete_user_btn);
        view_users = (Button) findViewById(R.id.view_data_btn);
        view_compln = (Button) findViewById(R.id.view_comp_btn);
        //Dialog dialog = new Dialog(MainActivity3.this);
        add_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUsersDialog();
            }
        });

        del_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteUsersDialog();
            }
        });

        view_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view_compln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void showDeleteUsersDialog() {
        if(DBHelper.getDBInstance(this.getApplicationContext()).getRowCount() == 0){
            Toast.makeText(getApplicationContext(),"No Users found",Toast.LENGTH_LONG).show();
        }else{
            DeleteUsersFragment deleteUsersFragment = new DeleteUsersFragment();
            deleteUsersFragment.setCancelable(true);
            deleteUsersFragment.show(getSupportFragmentManager(),"DeleteUsers");
        }
    }

    private void showAddUsersDialog() {
        AddUsersDialog addUsersDialog =new AddUsersDialog();
        addUsersDialog.setCancelable(true);
        addUsersDialog.show(getSupportFragmentManager(),"AddUsers");
    }
}