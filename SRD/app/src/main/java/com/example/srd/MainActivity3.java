package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
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
        DeleteUsersFragment deleteUsersFragment = new DeleteUsersFragment();
        deleteUsersFragment.setCancelable(true);
        deleteUsersFragment.show(getSupportFragmentManager(),"DeleteUsers");
    }

    private void showAddUsersDialog() {
        AddUsersDialog addUsersDialog =new AddUsersDialog();
        addUsersDialog.setCancelable(true);
        addUsersDialog.show(getSupportFragmentManager(),"AddUsers");
    }
}