package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UsersScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_screen1);
        LinearLayout lab_btn = findViewById(R.id.us_lab);
        lab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "Lab", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout dg_rds_btn = findViewById(R.id.us_dg);
        dg_rds_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "DG", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout eb_rds_btn = findViewById(R.id.us_eb);
        eb_rds_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "EB", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout water_btn = findViewById(R.id.us_water);
        water_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "Water", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout dg_fuel_btn = findViewById(R.id.us_dgfuel);
        dg_fuel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "DG-Fuel", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout logout_btn = findViewById(R.id.us_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });

    }
}