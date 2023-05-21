package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(ConstantsClass.SPF_NAME,MODE_PRIVATE);
        editor = preferences.edit();
        boolean loggedIn = preferences.getBoolean(ConstantsClass.SPF_SESSION,false);
        if(loggedIn){
            boolean sv_admin = preferences.getBoolean(ConstantsClass.SPF_ADMIN_TYPE,false);
            if(sv_admin)
                openLoginScreen(ConstantsClass.ADMIN_LOGIN);
            else
                openLoginScreen(ConstantsClass.USER_LOGIN);
        }
        DBHelper dbHelper = DBHelper.getDBInstance(this.getApplicationContext());
        Button adminbutton = (Button) findViewById(R.id.adminbtn);
        adminbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen(ConstantsClass.ADMIN_LOGIN);
            }

        });
        Button usersbutton = (Button) findViewById(R.id.usersbtn);
        usersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen(ConstantsClass.USER_LOGIN);
            }
        });

    }
    public void openLoginScreen(String utype){
        Intent intent=new Intent(this, LoginScreenActivity.class);
        intent.putExtra(ConstantsClass.MAIN_ACTIVITY_UTYPE,utype);
        startActivity(intent);
        finish();
    }
}
