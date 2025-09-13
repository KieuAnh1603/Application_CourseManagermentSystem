package com.example.sv_management_system.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.example.sv_management_system.model.Course;
import com.example.sv_management_system.model.User;

@Controller
public class DataBase {
    private static List<User> allUsers = new ArrayList<>();
    private static List<Course> allCourses = new ArrayList<>();

    public DataBase() {
        // seeding data voi 5 ban ghi moi list
        allUsers.add(new User(1, "admin@example.com", "12345678", "ADMIN", new ArrayList<>()));
        allUsers.add(new User(2, "user@example.com", "12345678", "USER", new ArrayList<>()));

        allCourses.add(new Course(1, "Java Programming", 3, 1500, 30, 1500, 10));
        allCourses.add(new Course(2, "Web Development", 4, 2000, 25, 2000, 5));
        allCourses.add(new Course(3, "Database Systems", 3, 1800, 20,  1800, 15));
        allCourses.add(new Course(4, "Data Structures", 3, 1600, 30,  1600, 20));
        allCourses.add(new Course(5, "Operating Systems", 4, 2200, 25, 2200, 10));
    }

    public static List<User> GetAllUser() {
        return allUsers;
    }

    public static List<Course> GetAllCourse() {
        return allCourses;
    }

    public static void UpdateCourseList(List<Course> courses) {
        allCourses = courses;
    }

    public static void UpdateUserList(List<User> users) {
        allUsers = users;
    }
}
