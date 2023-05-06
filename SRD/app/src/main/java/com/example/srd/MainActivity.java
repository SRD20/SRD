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
        adminbutton = (Button) findViewById(R.id.adminbtn);
	 DBHelper dbHelper = new DBHelper(this);

        // Inserting Contact
        dbHelper.addEmployee(new EmployeeModel("qqq2w", "weee", "dfeses", "dfggf","wwee"));
        dbHelper.addEmployee(new EmployeeModel("qqq12", "weee", "dfeses", "dfggf","wwee"));
        dbHelper.addEmployee(new EmployeeModel("qqq1234", "weee", "dfeses", "dfggf","wwee"));

        adminbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }

        });
        usersbutton = (Button) findViewById(R.id.usersbtn);
        usersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }

        });
    }
    public void openActivity2(){
        Intent intent=new Intent(this, MainActivity2.class);
                    startActivity(intent);
    }

    //Database creation




}
