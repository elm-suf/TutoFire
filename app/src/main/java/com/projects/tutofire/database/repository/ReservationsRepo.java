package com.projects.tutofire.database.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projects.tutofire.database.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationsRepo {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    CollectionReference collection;

    public ReservationsRepo() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        collection = db.collection("users")
                .document(mAuth.getUid())
                .collection("reservations");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MutableLiveData<List<Reservation>> getData() {
        MutableLiveData<List<Reservation>> mutable = new MutableLiveData<>();

        collection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Reservation> list = new ArrayList<>();
                        documents
                                .stream()
                                .map(doc ->
                                        new Reservation(doc.getId(), mAuth.getCurrentUser().getEmail(),
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

    public void delete(Reservation reservation) {
        collection.document(reservation.getId())
                .update("status", "canceled").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("mTAG", "onComplete() called with: task = [" + task + "]");
                }
            }
        });
        //todo delete reservation from user subcollection
        //todo update teacher availability
    }

    public void update(Reservation reservation) {
        collection.document(reservation.getId()).update("status", reservation.getStatus());
    }
}
