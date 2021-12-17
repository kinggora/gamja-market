package com.example.gamjamarket.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gamjamarket.R;

public class RegisteredActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        Button loginGo = (Button)findViewById(R.id.registered_loginGo);
        loginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(RegisteredActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });
    }
}
