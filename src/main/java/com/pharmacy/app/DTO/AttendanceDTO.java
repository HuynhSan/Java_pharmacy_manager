/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.sql.Time;
import java.time.LocalDate;

/**
 *
 * @author BOI QUAN
 */
public class AttendanceDTO {
    private String id;
    private String employeeId;
    private LocalDate workDate;
    private Time checkIn;
    private Time checkOut;
    private boolean isDeleted;
    
    public AttendanceDTO(){}
    
    public AttendanceDTO(String id, String employeeId, LocalDate workDate, Time checkIn, Time checkOut, boolean isDeleted) {
        this.id = id;
        this.employeeId = employeeId;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public Time getCheckIn() {
        return checkIn;
    }

    public Time getCheckOut() {
        return checkOut;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public void setCheckIn(Time checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(Time checkOut) {
        this.checkOut = checkOut;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    
}
