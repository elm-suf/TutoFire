package com.projects.tutofire.database.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.projects.tutofire.database.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseRepo {
    final String TAG = "CourseRepo";
    FirebaseFirestore db;
    private MutableLiveData<List<Course>> data;

    public CourseRepo() {
        db = FirebaseFirestore.getInstance();
        data = new MutableLiveData<>();
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        db.collection("courses")
                .get()
                .addOnCompleteListener((Task<QuerySnapshot> task) -> {
                    if (task.isSuccessful()) {
                        List<Course> list = new ArrayList<>();
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();


                        documents.stream()
                                .map(d -> new Course(d.getId(), (String) d.get("title"), (String) d.get("description")))
                                .forEach(list::add);
                        data.setValue(list);
                        System.out.println(data);
//                        documents.forEach(d -> list.add(new Course(d.getId(), (String) d.get("title"), (String) d.get("description"))));

//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Map<String, Object> map = document.getData();
//                            Log.d(TAG, document.getId() + " => " + document.getData());
//                            list.add(new Course(task.getResult().getDocuments()map.get("title"), map.get("description")))
//                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public MutableLiveData<List<Course>> getData() {
        return data;
    }
}
