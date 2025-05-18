/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.time.LocalDate;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkScheduleDTO {
    private String scheduleId;
    private String employeeId;
    private String shiftId;
    private LocalDate workDate;
    private boolean isWeekend;
    private boolean isHoliday;
    private boolean isDeleted;

    public WorkScheduleDTO() {
    }
    
    

    public WorkScheduleDTO(String scheduleId, String employeeId, String shiftId, LocalDate workDate, boolean isWeekend, boolean isHoliday, boolean isDeleted) {
        this.scheduleId = scheduleId;
        this.employeeId = employeeId;
        this.shiftId = shiftId;
        this.workDate = workDate;
        this.isWeekend = isWeekend;
        this.isHoliday = isHoliday;
        this.isDeleted = isDeleted;
    }

    public WorkScheduleDTO(String employeeId, String shiftId, LocalDate workDate, boolean isWeekend, boolean isHoliday) {
        this.employeeId = employeeId;
        this.shiftId = shiftId;
        this.workDate = workDate;
        this.isWeekend = isWeekend;
        this.isHoliday = isHoliday;
    }
    
    

    
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public boolean isIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    public boolean isIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }



}
