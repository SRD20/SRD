package com.example.srd;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;


public class UsersScreenActivity extends AppCompatActivity {
    LabDataModel lbdata;
    String login_id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_screen1);
        login_id = (String) getIntent().getExtras().get("Logged_User");
        preferences = getSharedPreferences(ConstantsClass.SPF_NAME,MODE_PRIVATE);
        editor = preferences.edit();
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

        LinearLayout chng_pwd = findViewById(R.id.us_chg_pwd);
        chng_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "Change password", Toast.LENGTH_SHORT).show();
                //show the dialog & update the password
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UsersScreenActivity.this)
                        .setTitle("Change Password")
                        .setMessage("Enter new password");
                EditText editText = new EditText(UsersScreenActivity.this);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setBackgroundResource(R.drawable.cust_edittext);
                alertDialog.setView(editText);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newpwd = editText.getText().toString();
                        String oldpwd = preferences.getString(ConstantsClass.CURR_USER_PWD, "");
                        if(TextUtils.isEmpty(newpwd) || oldpwd.equals(newpwd)){
                            Toast.makeText(UsersScreenActivity.this, "Please enter new password", Toast.LENGTH_SHORT).show();
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
        LinearLayout logout_btn = findViewById(R.id.us_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UsersScreenActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UsersScreenActivity.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure to logout ?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.clear();
                        editor.apply();
                        dialogInterface.dismiss();
                        Intent intent = new Intent(UsersScreenActivity.this,MainActivity.class);
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

    private void updatePassword(String eid, String pwd) {
            HashMap<String,Object> hm = new HashMap<>();
            hm.put("password",pwd);
            DAOEmployee daoEmployee = new DAOEmployee();
            daoEmployee.updateEmployee(eid,hm);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),result -> {
        //Once lab qr code is available, put all these lines under if block.
        //result.getContent should contain lab codeword. Need to add this check.
        if(result.getContents()!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UsersScreenActivity.this);
            builder.setTitle("Scan successful");
            lbdata.setEmpId(login_id);
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