package com.example.srd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginScreenActivity extends AppCompatActivity {
    private Button login;
    private Button cancel;
    TextView type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        type = findViewById(R.id.login_textview);
        String str = (String) getIntent().getExtras().get("USER_TYPE");
        type.setText(str);
        login = (Button) findViewById(R.id.login_btn);
        cancel = (Button) findViewById(R.id.login_cancel);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //authenticate and then launch
                if("Admin Login".equals(str))
                    openAdminScreen();
                else
                    openUserScreen();
            }
        });
    }

    private void openUserScreen() {
        Intent intent = new Intent(this, UsersScreenActivity.class);
        startActivity(intent);
    }

    private void openAdminScreen() {
        Intent intent=new Intent(this, AdminScreen.class);
        startActivity(intent);
        finish();
    }
}