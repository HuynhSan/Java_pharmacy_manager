/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author BOI QUAN
 */
public class PayrollDTO {
    private String payrollID;
    private String employeeID;
    private boolean status;
    private BigDecimal totalSalary;
    private LocalDate payDate;

    public PayrollDTO(String payrollID, String employeeID, boolean status, BigDecimal totalSalary, LocalDate payDate) {
        this.payrollID = payrollID;
        this.employeeID = employeeID;
        this.status = status;
        this.totalSalary = totalSalary;
        this.payDate = payDate;
    }

    public PayrollDTO() {
    }

    public String getPayrollID() {
        return payrollID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public boolean getStatus() {
        return status;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayrollID(String payrollID) {
        this.payrollID = payrollID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }
   
    
}
