package com.projects.tutofire;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.projects.tutofire.database.entity.Course;
import com.projects.tutofire.database.repository.CourseRepo;

import java.util.List;

public class SharedViewModel extends ViewModel {
    CourseRepo repo;
    private MutableLiveData<List<Course>> data;

    public void init() {
        repo = new CourseRepo();
        if (data == null)
            data = repo.getData();
    }

    public MutableLiveData<List<Course>> getData() {
        if (data == null)
            init();
        return data;
    }
}
