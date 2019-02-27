package com.projects.tutofire.database.entity;

import java.util.Map;

public class Teacher {
    private String id;
    private String username;
    private String email;
    private Map<String, String> teachings;

    public Teacher() {
    }

    public Teacher(String id, String username, String email, Map teachings) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.teachings = teachings;
    }

    public Teacher(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getTeachings() {
        return teachings;
    }

    public void setTeachings(Map<String, String> teachings) {
        this.teachings = teachings;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", teachings=" + teachings +
                '}';
    }
}
