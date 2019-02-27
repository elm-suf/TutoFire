package com.projects.tutofire;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projects.tutofire.activities.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    TextView txv_user_username;
    TextView txv_user_mail;
    ImageButton nav_logout;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        txv_user_mail = getView().findViewById(R.id.txv_user_mail);
        txv_user_username = getView().findViewById(R.id.txv_user_username);
        nav_logout = getView().findViewById(R.id.nav_logout);


        firestore.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                txv_user_username.setText(task.getResult().getString("username"));
            }
        });
        txv_user_mail.setText(mAuth.getCurrentUser().getEmail());

        nav_logout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

}
