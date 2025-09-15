package com.example.sv_management_system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

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

    @GetMapping("/user/register-course/{userID}/{courseID}")
    public String RegisterCourse(@PathVariable("courseID") int courseID,
                                 @PathVariable("userID") int userID) {
        for (Course c : courses) {
            if (c.getCourseID() == courseID) {
                if (c.getStudentRegistered() < c.getMaxStudent()) {
                    Course a = DataBase.GetAllUser()
                                        .stream()
                                        .filter(t -> t.getUserID() == userID)
                                        .findFirst()
                                        .orElse(null)
                                        .getCourseRegistered()
                                        .stream()
                                        .filter(kh -> kh.getCourseID() == courseID)
                                        .findFirst()
                                        .orElse(null);
                    if(a == null){
                        c.setStudentRegistered(c.getStudentRegistered() + 1);
                        DataBase.UpdateCourseList(courses);
                        return ("redirect:/user/add-course/" + userID + "/" + courseID);
                    }
                    return "redirect:/user/all-course";
                } else {
                    return "redirect:/user/all-course";
                }
            }
        }
        return null;
    }

    @GetMapping("/user/my-courses/{id}") // xem khoa hoc da dang ki
    public String ShowCourseByUserid(@PathVariable("id") int userid, Model model){
        List<Course> courseRegistered = DataBase.GetAllUser().stream()
                                                .filter(a -> a.getUserID() == userid)
                                                .findFirst()
                                                .orElse(null).getCourseRegistered();
        model.addAttribute("MyCourses", courseRegistered);
        return "user/Mycourse";
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
    
    @PostMapping("/admin/update-course")
    public String UpdateCourse(@ModelAttribute("courseInfoEdit") Course course) {
        for (Course c : courses) {
            if (c.getCourseID() == course.getCourseID()) {
                c.setTitleCourse(course.getTitleCourse());
                c.setSoTinHocPhan(course.getSoTinHocPhan());
                c.setSoTinHocPhi(course.getSoTinHocPhi());
                c.setMaxStudent(course.getMaxStudent());
                // c.setStudentRegistered(course.getStudentRegistered());
                c.setTotalCost(course.getTotalCost());
                DataBase.UpdateCourseList(courses);
                return "redirect:/admin/all-course";
            }
        }
        return null;
    }
    

}
