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

    public AttendanceDTO(String id, String employeeId, LocalDate workDate, Time checkIn, Time checkOut) {
        this.id = id;
        this.employeeId = employeeId;
        this.workDate = workDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
    
    
    
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
    
    /**
     * Determines if the employee was late for work.
     * Assumes standard work start time is 8:00 AM.
     * @return true if check-in time is after 8:00 AM, false otherwise
     */
    public boolean isLate() {
        if (checkIn == null) {
            return false; // No check-in recorded, cannot determine lateness
        }

        // Standard work start time: 8:00 AM
        Time standardStartTime = Time.valueOf("08:00:00");

        return checkIn.after(standardStartTime);
    }

    /**
     * Calculates the actual work hours based on check-in and check-out times.
     * Assumes a 1-hour lunch break is deducted if work hours exceed 4 hours.
     * @return actual work hours as a double, or 0 if check-in/check-out is missing
     */
    public double getActualWorkHours() {
        if (checkIn == null || checkOut == null) {
            return 0.0; // Cannot calculate hours without both times
        }

        // Calculate total time in milliseconds
        long totalMillis = checkOut.getTime() - checkIn.getTime();

        // Convert to hours
        double totalHours = totalMillis / (1000.0 * 60.0 * 60.0);

        // Deduct lunch break (1 hour) if worked more than 4 hours
        if (totalHours > 4.0) {
            totalHours -= 1.0;
        }

        // Return 0 if somehow the result is negative
        return Math.max(0.0, totalHours);
    }
    
}
