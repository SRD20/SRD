package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
     private Button adminbutton;
     private Button usersbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = DBHelper.getDBInstance(this.getApplicationContext());
        adminbutton = (Button) findViewById(R.id.adminbtn);
        adminbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
                return;
            }

        });
        usersbutton = (Button) findViewById(R.id.usersbtn);
        usersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
                return;
            }
        });

    }
    public void openActivity2(){
        Intent intent=new Intent(this, MainActivity2.class);
                    startActivity(intent);
    }
}
