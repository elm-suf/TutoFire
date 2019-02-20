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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projects.tutofire.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    String TAG = "Register_Act";
    EditText edt_email_register;
    EditText edt_password_register;
    EditText edt_password_confirm;
    EditText edt_uname_register;
    Button btn_register;
    TextView txv_login;
    ProgressBar progressBar_register;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;
    private Intent LoginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        edt_password_register = findViewById(R.id.edt_password_register);
        edt_password_confirm = findViewById(R.id.edt_password_confirm);
        edt_uname_register = findViewById(R.id.edt_uname_register);
        btn_register = findViewById(R.id.btn_register);
        txv_login = findViewById(R.id.txv_login);
        progressBar_register = findViewById(R.id.progressBar_register);

        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(v -> registration());

        txv_login.setOnClickListener(v -> updateUI());

    }

    private void registration() {
        String email = edt_email_register.getText().toString();
        String pwd = edt_password_register.getText().toString();
        String pwd_confirm = edt_password_confirm.getText().toString();
        String username = edt_uname_register.getText().toString();
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        btn_register.setActivated(false);
        if (email.isEmpty() || pwd.isEmpty()) {
            showMessage("all fields must be filled");
        } else if (pwd.length() < 5) {
            showMessage("pwd mus be at least 6 char");
        } else if (!pattern.matcher(email).matches()) {
            showMessage("enter a valid email");
        } else if (!pwd.equals(pwd_confirm)) {
            showMessage("Passwords must match");
        } else {
            progressBar_register.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this, task -> {
                        progressBar_register.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            showMessage("User Registered");
                            updateUserInfo(username, Objects.requireNonNull(mAuth.getCurrentUser()));
                            updateUI();
                        } else {
                            btn_register.setActivated(true);
                            showMessage("sign in: failure");
                        }
                    });
        }

    }

    private void updateUserInfo(String username, FirebaseUser currentUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("id", currentUser.getUid());
        user.put("email", Objects.requireNonNull(currentUser.getEmail()));
        user.put("username", username);

        // Add a new document with a generated ID
        db.collection("users")
                .document(currentUser.getUid())
                .set(user)
                .addOnSuccessListener(v -> showMessage("DocumentSnapshot successfully write!"))
                .addOnFailureListener(e -> showMessage(e.getMessage()));
    }

    private void updateUI() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
