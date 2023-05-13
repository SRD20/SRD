package com.example.srd;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class UsersScreenActivity extends AppCompatActivity {
    LabDataModel lbdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_screen1);
        LinearLayout lab_btn = findViewById(R.id.us_lab);
        lab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "Lab", Toast.LENGTH_SHORT).show();
                // show the qr scanner
                lbdata = new LabDataModel();
                scanCode();
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
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),result -> {
        //Once lab qr code is available, put all these lines under if block.
        if(result.getContents()!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UsersScreenActivity.this);
            builder.setTitle("Scan successful");
            lbdata.setLabId(result.getContents());
            builder.setMessage("You're entering data for " + result.getContents());
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    LabScreenFragment labScreenFragment = new LabScreenFragment(getApplicationContext(), lbdata);
                    labScreenFragment.setCancelable(false);
                    labScreenFragment.show(getSupportFragmentManager(), "LabScreen");
                }
            }).show();
        }
    });

    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to turn flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(QRScanner.class);
        barLauncher.launch(options);
    }

}