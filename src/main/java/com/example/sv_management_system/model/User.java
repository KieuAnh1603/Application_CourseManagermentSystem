package com.example.sv_management_system.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userID;
    private String email;
    private String password;
    private String role; // "ADMIN" or "USER"
    private List<Course> courseRegistered = new ArrayList<>();

    // static, enum

    public void AddCourse(Course course) {
        this.courseRegistered.add(course);
    }
}
