package com.projects.tutofire.database.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projects.tutofire.database.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationsRepo {
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public ReservationsRepo() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MutableLiveData<List<Reservation>> getData() {
        MutableLiveData<List<Reservation>> mutable = new MutableLiveData<>();


        db.collection("users")
                .document(mAuth.getUid())
                .collection("reservations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Reservation> list = new ArrayList<>();
                        documents
                                .stream()
                                .map(doc ->
                                        new Reservation(mAuth.getCurrentUser().getEmail(),
                                                doc.getString("teacher"),
                                                doc.getString("course"),
                                                doc.getString("time_slot"),
                                                doc.getString("status"),
                                                doc.getString("date")))
                                .forEach(list::add);
                        mutable.setValue(list);
                    }
                });
        return mutable;
    }
}
