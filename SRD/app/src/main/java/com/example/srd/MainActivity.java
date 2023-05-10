package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = DBHelper.getDBInstance(this.getApplicationContext());
        Button adminbutton = (Button) findViewById(R.id.adminbtn);
        adminbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen("Admin Login");
            }

        });
        Button usersbutton = (Button) findViewById(R.id.usersbtn);
        usersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen("User Login");
            }
        });

    }
    public void openLoginScreen(String utype){
        Intent intent=new Intent(this, LoginScreenActivity.class);
        intent.putExtra("USER_TYPE",utype);
        startActivity(intent);
        finish();
    }
}
