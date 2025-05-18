/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.time.LocalDate;

/**
 *
 * @author phong
 */
public class EmployeeDTO {
    private String employeeID;
    private String userID;
    private String name;
    private LocalDate dob;
    private boolean gender;
    private String email;
    private String phone;
    private String address;
    private boolean isDeleted;
    
    // Constructor
    public EmployeeDTO(String employeeID, String userID, String name, LocalDate dob, boolean gender, String email, String phone, String address, boolean isDeleted) {
        this.employeeID = employeeID;
        this.userID = userID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.isDeleted = isDeleted;
    }

    public EmployeeDTO() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // Getter & Setter
    public String getEmployeeID() {
        return employeeID;
    }
    
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getDob() {
        return dob;
    }
    
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
    public boolean getGender() {
        return gender;
    }
    
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public boolean isDeleted() {
        return isDeleted;
    }
    
    public void setDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
