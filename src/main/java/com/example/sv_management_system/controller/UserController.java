package com.example.sv_management_system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.sv_management_system.data.DataBase;
import com.example.sv_management_system.model.Course;
import com.example.sv_management_system.model.User;

@Controller
public class UserController {
    // User : CURD
    // Create, Update, Read, Delete

    List<User> users = new ArrayList<>();
    

    public UserController() {
        users = DataBase.GetAllUser();
    }


    
    @GetMapping("/admin/all-user")
    public String ShowAllUser(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("listUser", users);
        return "admin/ListUser";
    }



    @DeleteMapping("/admin/delete-user/{userID}")
    public String DeleteUser(@PathVariable("userID") int userID) {
        for (User u : users) {
            if (u.getUserID() == userID) {
                users.remove(u);
                DataBase.UpdateUserList(users);
                return "redirect:/admin/all-user";
            }
        }
        return null;
    }



    @GetMapping("/admin/edit-user/{userID}")
    public String ShowFormEditUser(@PathVariable("userID") int user_id, Model model) {
        User user = users.stream()
                        .filter(u -> u.getUserID() == user_id)
                        .findFirst()
                        .orElse(null);

        model.addAttribute("userInfoEdit", user);
        return "admin/FormEditUser";
    }



    @PostMapping("/admin/update-user")
    public String UpdateUser(@ModelAttribute("userInfoEdit") User user) {
        for (User u : users) {
            if (u.getUserID() == user.getUserID()) {
                u.setEmail(user.getEmail());
                u.setPassword(user.getPassword());
                u.setRole(user.getRole());
                DataBase.UpdateUserList(users);
                return "redirect:/admin/all-user";
            }
        }
        return null;
    }



    @PostMapping("/admin/add-user")
    public String SaveUpdateUser(@ModelAttribute("newUser") User user) {
        users.add(user);
        DataBase.UpdateUserList(users);
        return "redirect:/admin/all-user";
    }



    @GetMapping("/user/add-course/{userID}/{courseID}")
    public String UserAddCourse(@PathVariable("userID") int userID,
                                @PathVariable("courseID") int courseID) {
        Course course = DataBase.GetAllCourse()
                        .stream()
                        .filter(c -> c.getCourseID() == courseID)
                        .findFirst()
                        .orElse(null);

        for (User u : users) {
            if (u.getUserID() == userID) {
                u.AddCourse(course);
            }
        }
        return "redirect:/user/home";
    }

}
