package com.example.srd;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginScreenActivity extends AppCompatActivity {

    private EditText username, password;
    private boolean admin;
    ProgressBar progressBar;
    DatabaseReference dbref;
    SwitchCompat remember_me;
    TextView type;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean loggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        setContentView(R.layout.login_sc2);
        type = findViewById(R.id.login_textview);
        username = findViewById(R.id.username_editText);
        password = findViewById(R.id.password_editText);
        remember_me = findViewById(R.id.rem_swt);
        preferences = getSharedPreferences(ConstantsClass.SPF_NAME,MODE_PRIVATE);
        editor = preferences.edit();

        String str = (String) getIntent().getExtras().get(ConstantsClass.MAIN_ACTIVITY_UTYPE);
        type.setText(str);
        if(ConstantsClass.ADMIN_LOGIN.equals(str))
            admin = true;
        else
            admin = false;
        loggedIn = preferences.getBoolean(ConstantsClass.SPF_SESSION,false);
        if (loggedIn) {
            boolean sv_admin = preferences.getBoolean(ConstantsClass.SPF_ADMIN_TYPE,false);
            if(sv_admin == admin) {
                username.setText(preferences.getString(ConstantsClass.SPF_USERID, ""));
                password.setText(preferences.getString(ConstantsClass.SPF_PWD, ""));
            }
        }
        progressBar = findViewById(R.id.login_progress);
        Button login = (Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //authenticate and then launch
                String uname = String.valueOf(username.getText());
                String pwd = String.valueOf(password.getText());
                if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginScreenActivity.this, "Please enter complete credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbref = FirebaseDatabase.getInstance().getReference(ConstantsClass.CONST_ROOT).child(ConstantsClass.CONST_LOGIN_NODE).child(admin?ConstantsClass.CONST_LOGIN_ADMIN:ConstantsClass.CONST_LOGIN_USER).child(uname);
                progressBar.setVisibility(View.VISIBLE);
                /*dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("SRD_Firebase","onDataChange snapshot "+snapshot.toString());
                        if (snapshot.exists()){
                            String pwrd = snapshot.child("password").getValue(String.class);
                            Log.d("SRD_Firebase","Password from db :"+pwrd);
                            if(pwd.equals(pwrd)){
                                Toast.makeText(LoginScreenActivity.this, "Authentication successfully done", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if(admin)
                                    openAdminScreen();
                                else
                                    openUserScreen();
                            }else{
                                Toast.makeText(LoginScreenActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }else{
                            Toast.makeText(LoginScreenActivity.this, "Enter valid UserID", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginScreenActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });*/

                Query query = FirebaseDatabase.getInstance().getReference(ConstantsClass.CONST_ROOT).child(ConstantsClass.CONST_LOGIN_NODE).child(admin?ConstantsClass.CONST_LOGIN_ADMIN:ConstantsClass.CONST_LOGIN_USER).orderByChild("empId").equalTo(uname);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("SRD_Firebase","onDataChange snapshot "+snapshot.toString());
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String pwrd = ds.child("password").getValue(String.class);
                                if (pwd.equals(pwrd)) {
                                    Log.d("SRD_Firebase", "Password from db :" + pwrd);
                                    Toast.makeText(LoginScreenActivity.this, "Authentication successfully done", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                    if(!loggedIn && remember_me.isChecked()) {
                                        editor.putString(ConstantsClass.SPF_USERID, uname);
                                        editor.putString(ConstantsClass.SPF_PWD, pwd);
                                        editor.putBoolean(ConstantsClass.SPF_SESSION, true);
                                        editor.putBoolean(ConstantsClass.SPF_ADMIN_TYPE,admin);
                                        editor.apply();
                                    }
                                    editor.putString(ConstantsClass.CURR_USER,uname);
                                    editor.putString(ConstantsClass.CURR_USER_PWD,pwd);
                                    editor.apply();
                                    if (admin)
                                        openAdminScreen();
                                    else
                                        openUserScreen(uname);

                                    return;
                                }
                            }
                        }
                        Toast.makeText(LoginScreenActivity.this, "Enter valid UserID", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginScreenActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
                /*query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        Log.d("SRD_Firebase","onDataChange snapshot "+snapshot1.toString());
                        for(DataSnapshot snapshot : snapshot1.getChildren()){
                            String un = snapshot.child("name").getValue(String.class);
                            String pwrd = snapshot.child("password").getValue(String.class);
                            Log.d("SRD_Firebase","Uname = "+un+" & Password from db :"+pwrd);
                            if(uname.equals(un) && pwd.equals(pwrd)){
                                Toast.makeText(LoginScreenActivity.this, "Authentication successfully done", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if(admin)
                                    openAdminScreen();
                                else
                                    openUserScreen();
                                return;
                            }
                        }
                            Toast.makeText(LoginScreenActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        });
    }

    private void openUserScreen(String empid) {
        Intent intent = new Intent(this, UsersScreenActivity.class);
        intent.putExtra("Logged_User",empid);
        startActivity(intent);
        finish();
    }

    private void openAdminScreen() {
        Intent intent=new Intent(this, AdminScreen.class);
        startActivity(intent);
        finish();
    }
}