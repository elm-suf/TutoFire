package com.projects.tutofire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.projects.tutofire.R;

public class HomeActivity extends AppCompatActivity {

    private Button btn_goto_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = new Intent(this, Home.class);
        btn_goto_register = findViewById(R.id.btn_goto_register);

        btn_goto_register.setOnClickListener(v -> {
            startActivity(intent);
            finish();
        });
    }
}
