package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ViewDataActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        preferences = getSharedPreferences(ConstantsClass.SPF_NAME,MODE_PRIVATE);
        editor = preferences.edit();
        LinearLayout lab_btn = findViewById(R.id.as_lab);
        lab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLabDataActivity();
                Toast.makeText(ViewDataActivity.this, "Lab-Readings", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout dg_btn = findViewById(R.id.as_dg);
        dg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDataActivity.this, "DG-Readings", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout eb_btn = findViewById(R.id.as_eb);
        eb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDataActivity.this, "EB-Readings", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout water_btn = findViewById(R.id.as_water);
        water_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDataActivity.this, "Water-Readings", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout dg_fuel_btn = findViewById(R.id.as_fuel);
        dg_fuel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDataActivity.this, "Fuel-Readings", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout chg_pwd_btn = findViewById(R.id.as_chg_pwd);
        chg_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDataActivity.this, "Change password", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout logout_btn = findViewById(R.id.as_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDataActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewDataActivity.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure to logout ?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.clear();
                        editor.apply();
                        dialogInterface.dismiss();
                        Intent intent = new Intent(ViewDataActivity.this,MainActivity.class);
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

    private void showLabDataActivity() {
        Intent intent=new Intent(this, LabDataActivity.class);
        startActivity(intent);
    }
}