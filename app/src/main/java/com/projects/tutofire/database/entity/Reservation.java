package com.projects.tutofire.database.entity;

public class Reservation {
    private String id;
    private String student;
    private String teacher;
    private String course;
    private String timeSlot;
    private String status;
    private String date;

    public Reservation() {
    }

    public Reservation(String id, String student, String teacher, String course, String slot, String status, String date) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.course = course;
        this.timeSlot = slot;
        this.status = status;
        this.date = date;
    }

    public String getStudent() {
        return student;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher d) {
        teacher = d.getUsername();
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String c) {
        course = c;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String s) {
        timeSlot = s;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) {
        status = s;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String d) {
        date = d;
    }

    public String getId() {
        return id;
    }
}
