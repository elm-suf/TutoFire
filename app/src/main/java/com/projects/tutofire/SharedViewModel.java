package com.projects.tutofire;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.projects.tutofire.database.entity.Course;
import com.projects.tutofire.database.entity.Reservation;
import com.projects.tutofire.database.entity.Teacher;
import com.projects.tutofire.database.repository.CourseRepo;
import com.projects.tutofire.database.repository.ReservationsRepo;
import com.projects.tutofire.database.repository.TeacherRepo;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<Course>> dataCourses;
    private MutableLiveData<List<Teacher>> dataTeachers;
    private MutableLiveData<List<Reservation>> dataReservations;

    public MutableLiveData<List<Course>> getDataCourses() {
        if (dataCourses == null) {
            CourseRepo repo = new CourseRepo();
            dataCourses = repo.getData();
        }
        return dataCourses;
    }

    public MutableLiveData<List<Teacher>> getTeachers() {
        if (dataTeachers == null) {
            TeacherRepo repo = new TeacherRepo();
            dataTeachers = repo.getData();
        }
        return dataTeachers;
    }

    public MutableLiveData<List<Reservation>> getResrvations() {
        ReservationsRepo repo = new ReservationsRepo();
        dataReservations = repo.getData();
        return dataReservations;
    }
}
