package com.projects.tutofire.database.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.projects.tutofire.database.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherRepo {
    final String TAG = "TeacherRepo";
    FirebaseFirestore db;
    private MutableLiveData<List<Teacher>> data;

    public TeacherRepo() {
        db = FirebaseFirestore.getInstance();
        data = new MutableLiveData<>();
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        db.collection("teachers")
                .get()
                .addOnCompleteListener((Task<QuerySnapshot> task) -> {
                    if (task.isSuccessful()) {
                        List<Teacher> list = new ArrayList<>();
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();


                        documents.stream()
                                .map(d -> new Teacher(d.getId(), (String) d.get("username"), (String) d.get("email")))
                                .forEach(list::add);
                        data.setValue(list);
                        System.out.println(data);
//                        documents.forEach(d -> list.add(new Course(d.getId(), (String) d.get("title"), (String) d.get("description"))));

//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Map<String, Object> map = document.getDataCourses();
//                            Log.d(TAG, document.getId() + " => " + document.getDataCourses());
//                            list.add(new Course(task.getResult().getDocuments()map.get("title"), map.get("description")))
//                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public MutableLiveData<List<Teacher>> getData() {
        return data;
    }
}

