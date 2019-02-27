package com.projects.tutofire.database.entity;

import java.util.Map;

public class Course {
    private String id;
    private String title;
    private String description;
    private Map<String, String> teachers;

    public Course() {
    }

    public Course(String id, String titolo, String description, Map teachers) {
        this.id = id;
        this.title = titolo;
        this.description = description;
        this.teachers = teachers;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Map<String, String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Map<String, String> teachers) {
        this.teachers = teachers;
    }
}
