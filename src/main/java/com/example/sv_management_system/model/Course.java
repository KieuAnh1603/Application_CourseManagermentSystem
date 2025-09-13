package com.example.sv_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
   private int courseID;
   private String titleCourse;
   private int soTinHocPhan;
   private int soTinHocPhi;
   private int maxStudent;
   private double totalCost; 
   private int studentRegistered;
}
