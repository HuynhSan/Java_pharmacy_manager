/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author phong
 */
public class PayrollDTO {
    private String payrollID;
    private String employeeID;
    private BigDecimal totalSalary;
    private boolean status;
    private LocalDateTime payDate;
    private boolean isDeleted;
    
    // Constructor
    public PayrollDTO(String payrollID, String employeeID, BigDecimal totalSalary, boolean status, LocalDateTime payDate, boolean isDeleted) {
        this.payrollID = payrollID;
        this.employeeID = employeeID;
        this.totalSalary = totalSalary;
        this.status = status;
        this.payDate = payDate;
        this.isDeleted = isDeleted;
    }
    
    // Getter & Setter

    public String getPayrollID() {
        return payrollID;
    }

    public void setPayrollID(String payrollID) {
        this.payrollID = payrollID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
