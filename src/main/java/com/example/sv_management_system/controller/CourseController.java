package com.example.sv_management_system.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.sv_management_system.data.DataBase;
import com.example.sv_management_system.model.Course;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class CourseController {
    List<Course> courses = new ArrayList<>();

    public CourseController() {
        courses = DataBase.GetAllCourse();
    }

    @GetMapping("/{role}/all-course")
    public String ShowAllCourse(@PathVariable("role") String role,Model model) {
        model.addAttribute("listCourse", courses);
        if (role.equals("admin")) {
            model.addAttribute("newCourse", new Course());
            return "admin/ListCourse";
        }
        return "user/AllCourse";
    }

    @PostMapping("/admin/add-course")
    public String SaveUpdateCourse(@ModelAttribute("newCourse") Course course) {
        courses.add(course);
        DataBase.UpdateCourseList(courses);
        return "redirect:/admin/all-course";
    }

    @GetMapping("/admin/delete-course/{courseID}")
    public String DeleteCourse(@PathVariable("courseID") int courseID) {
        for (Course c : courses) {
            if (c.getCourseID() == courseID) {
                courses.remove(c);
                DataBase.UpdateCourseList(courses);
                return "redirect:/admin/all-course";
            }
        }
        return null;
    }

    @GetMapping("/admin/edit-course/{courseID}")
    public String ShowFormEditCourse(@PathVariable("courseID") int courseID, Model model) {
        Course course = courses.stream()
                        .filter(c -> c.getCourseID() == courseID)
                        .findFirst()
                        .orElse(null);
        model.addAttribute("courseInfoEdit", course);
        return "admin/FormEditCourse";
    }

    @PutMapping("/admin/update-course")
    public String UpdateCourse(@ModelAttribute("courseInfoEdit") Course course) {
        for (Course c : courses) {
            if (c.getCourseID() == course.getCourseID()) {
                c.setTitleCourse(course.getTitleCourse());
                c.setSoTinHocPhan(course.getSoTinHocPhan());
                c.setSoTinHocPhi(course.getSoTinHocPhi());
                c.setMaxStudent(course.getMaxStudent());
                c.setStudentRegistered(course.getStudentRegistered());
                c.setTotalCost(course.getTotalCost());
                DataBase.UpdateCourseList(courses);
                return "redirect:/admin/all-course";
            }
        }
        return null;
    }

    // changed from @PutMapping to @GetMapping so anchor link in template works
    @GetMapping("/user/register-course/{userID}/{courseID}")
    public String RegisterCourse(@PathVariable("courseID") int courseID,
                                 @PathVariable("userID") int userID) {
        for (Course c : courses) {
            if (c.getCourseID() == courseID) {
                if (c.getStudentRegistered() < c.getMaxStudent()) {
                    c.setStudentRegistered(c.getStudentRegistered() + 1);
                    DataBase.UpdateCourseList(courses);
                    return ("redirect:/user/add-course/" + userID + "/" + courseID);
                } else {
                    return "redirect:/user/all-course";
                }
            }
        }
        return null;
    }
}
