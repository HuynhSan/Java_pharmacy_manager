/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.time.LocalTime;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkShiftDTO {
    private String shiftId;
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkShiftDTO(String shiftId, LocalTime startTime, LocalTime endTime) {
        this.shiftId = shiftId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    
}
