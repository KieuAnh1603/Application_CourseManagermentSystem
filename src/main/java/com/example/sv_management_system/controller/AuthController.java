package com.example.sv_management_system.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sv_management_system.data.DataBase;
import com.example.sv_management_system.model.User;

@Controller
public class AuthController {
    // Auth : authentication
    // Login & Register
    
    // 1 method: login
    // 1 method: register
    // 2 method chuyen trang

    private List<User> users = new ArrayList<>();

    public AuthController() {
        users = DataBase.GetAllUser();
    }

    // login: luu thong tin dang nhap session de dung cho cac request sau
    @PostMapping("/login")
    public String Login(@ModelAttribute("userInfoLogin") User user,
                        Model model, 
                        HttpSession session) {
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword())) {
                // session
                session.setAttribute("user", u);
                // attribute
                model.addAttribute("user", u);

                if ("ADMIN".equals(u.getRole())) {
                    return "redirect:/admin/home";
                } else {
                    return "redirect:/user/home";
                }
            }
        }
        return "redirect:/showlogin";
    }

    @GetMapping("/showlogin")
    public String ShowLogin(Model model) {
        model.addAttribute("userInfoLogin", new User());
        return "auth/Login";
    }

    @GetMapping("/logout")
    public String Logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/")
    public String ShowHome(){
        return "auth/homeauth";
    }

    @GetMapping("/showregister")
    public String ShowRegister(Model model) {
        model.addAttribute("newInfoRegister", new User());
        return "auth/Register";
    }

    @PostMapping("/dangki")
    public String addRegister(@ModelAttribute("newInfoRegister") User user){
        user.setRole("USER");
        user.setUserID();
        users.add(user);
        DataBase.UpdateUserList(users);
        return "redirect:/";
    }

    @GetMapping("/{role}/home")
    public String ShowHome(@PathVariable("role") String role) {
        if (role.equals("admin")) {
            return "admin/home";
        }
        return "user/home";
    }
}
