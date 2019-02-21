package com.projects.tutofire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projects.tutofire.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "Main_Act";
    private Button btn_login;
    private EditText edt_email_login;
    private EditText edt_password_login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;
    private Intent RegistrationActivity;
    TextView txv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        txv_register = findViewById(R.id.txv_register);
        edt_password_login = findViewById(R.id.edt_password_login);
        edt_email_login = findViewById(R.id.edt_email_login);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.login_progress);

        Toast.makeText(getApplicationContext(), "DocumentSnapshot added", Toast.LENGTH_SHORT).show();


        HomeActivity = new Intent(this, Home.class);
        RegistrationActivity = new Intent(this, RegisterActivity.class);

        btn_login.setOnClickListener(v -> login());
        txv_register.setOnClickListener(v -> startActivity(RegistrationActivity));
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String email = edt_email_login.getText().toString();
        String pwd = edt_password_login.getText().toString();
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if (email.isEmpty() || pwd.isEmpty()) {
            showMessage("all fields must be filled");
        } else if (!pattern.matcher(email).matches()) {
            showMessage("enter a valid email");
        } else if (pwd.length() < 5) {
            showMessage("pwd mus be at least 6 char");
        } else {
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            showMessage("Sign in: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            showMessage("sign in: failure");
                        }
                    });
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        //user is already connected  so we need to redirect to HomeActivity
        if (user != null) {
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();
    }
}
