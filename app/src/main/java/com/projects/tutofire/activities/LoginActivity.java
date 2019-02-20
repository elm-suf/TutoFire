package com.projects.tutofire.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projects.tutofire.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    String TAG = "Main_Act";
    Button btn_login;
    EditText edt_email_login;
    EditText edt_password_login;
    TextView txv_register;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        edt_password_login = findViewById(R.id.edt_password_login);
        edt_email_login = findViewById(R.id.edt_email_login);
        btn_login = findViewById(R.id.btn_login);
        Toast.makeText(getApplicationContext(), "DocumentSnapshot added", Toast.LENGTH_SHORT).show();


        HomeActivity = new Intent(this, HomeActivity.class);

        btn_login.setOnClickListener(v -> login());
        txv_register.setOnClickListener(v -> gotoRegistration());
    }

    private void gotoRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        String email = edt_email_login.getText().toString();
        String pwd = edt_password_login.getText().toString();
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if (email.isEmpty() || pwd.isEmpty()) {
            showMessage("all fields must be filled");
        } else if (pwd.length() < 5) {
            showMessage("pwd mus be at least 6 char");
        } else if (!pattern.matcher(email).matches()) {
            showMessage("enter a valid email");
        } else {
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                showMessage("Sign in: success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI();
                            } else {
                                showMessage("sign in: failure");
                            }
                        }
                    });
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            //user is already connected  so we need to redirect to HomeActivity
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();
    }
}
