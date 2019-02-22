package com.projects.tutofire;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.projects.tutofire.database.entity.Course;
import com.projects.tutofire.database.entity.Teacher;
import com.projects.tutofire.database.repository.CourseRepo;
import com.projects.tutofire.database.repository.TeacherRepo;

import java.util.List;

public class SharedViewModel extends ViewModel {
    CourseRepo repo;
    private MutableLiveData<List<Course>> dataCourses;
    private MutableLiveData<List<Teacher>> dataTeachers;

    public void init() {
        repo = new CourseRepo();
        if (dataCourses == null)
            dataCourses = repo.getData();
    }

    public MutableLiveData<List<Course>> getDataCourses() {
        if (dataCourses == null)
            init();
        return dataCourses;
    }

    public MutableLiveData<List<Teacher>> getTeachers() {
        if (dataTeachers == null) {
            TeacherRepo repo = new TeacherRepo();
            dataTeachers = repo.getData();
        }
        return dataTeachers;
    }
}
