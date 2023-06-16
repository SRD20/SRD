package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminScreen extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main3);
        setContentView(R.layout.adminscreen1);
        preferences = getSharedPreferences(ConstantsClass.SPF_NAME,MODE_PRIVATE);
        editor = preferences.edit();
        LinearLayout add_users = findViewById(R.id.add_user_btn);
        LinearLayout view_users = findViewById(R.id.view_users_btn);
        LinearLayout view_compln = findViewById(R.id.view_comp_btn);
        LinearLayout view_data = findViewById(R.id.view_data_btn);
        LinearLayout chg_pwd_btn = findViewById(R.id.as_chg_pwd);
        LinearLayout log_out = findViewById(R.id.as_logout);

        add_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUsersDialog();
            }
        });

        view_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showViewUsersDialog();
            }
        });

        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showViewDataActivity();
            }
        });

        view_compln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        chg_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminScreen.this, "Change password", Toast.LENGTH_SHORT).show();
                //show the dialog & update the password
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminScreen.this)
                        .setTitle("Change Password")
                        .setMessage("Enter new password");
                EditText editText = new EditText(AdminScreen.this);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //editText.setBackgroundResource(R.drawable.cust_edittext);
                alertDialog.setView(editText);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newpwd = editText.getText().toString();
                        String oldpwd = preferences.getString(ConstantsClass.CURR_USER_PWD, "");
                        if(TextUtils.isEmpty(newpwd) || oldpwd.equals(newpwd)){
                            Toast.makeText(AdminScreen.this, "Please enter new password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("SRD_Test","curr user "+preferences.getString(ConstantsClass.CURR_USER, ""));
                        updatePassword(preferences.getString(ConstantsClass.CURR_USER, ""),newpwd);
                        if(preferences.getBoolean(ConstantsClass.SPF_SESSION,false)) {
                            editor.putString(ConstantsClass.SPF_PWD, newpwd);
                            editor.apply();
                        }
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminScreen.this, "Logout", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminScreen.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure to logout ?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.clear();
                        editor.apply();
                        dialogInterface.dismiss();
                        Intent intent = new Intent(AdminScreen.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
    }

    private void updatePassword(String empid, String newpwd) {
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("password",newpwd);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(ConstantsClass.CONST_ROOT).child(ConstantsClass.CONST_LOGIN_NODE).child(ConstantsClass.CONST_LOGIN_ADMIN);
        databaseReference.child(empid).updateChildren(hm);

    }

    private void showViewDataActivity() {
        Intent intent=new Intent(this, ViewDataActivity.class);
        startActivity(intent);
    }

    private void showViewUsersDialog() {
        if(DBHelper.getDBInstance(this.getApplicationContext()).getRowCount() == 0){
            Toast.makeText(getApplicationContext(),"No Users found",Toast.LENGTH_LONG).show();
        }else{
            ViewUsersFragment viewUsersFragment = new ViewUsersFragment();
            viewUsersFragment.setCancelable(true);
            viewUsersFragment.show(getSupportFragmentManager(),"ViewUsers");
        }
    }

    private void showAddUsersDialog() {
        AddUsersDialog addUsersDialog =new AddUsersDialog();
        addUsersDialog.setCancelable(true);
        addUsersDialog.show(getSupportFragmentManager(),"AddUsers");
    }
}