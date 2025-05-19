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
public class PayrollDetailsDTO {
    private String payrollID;
    private String componentID;
    private int value;
    private BigDecimal amount;

    // Constructor
    public PayrollDetailsDTO(String payrollID, String componentID, int value, BigDecimal amount) {
        this.payrollID = payrollID;
        this.componentID = componentID;
        this.value = value;
        this.amount = amount;
    }

    // Getter & Setter
    public String getPayrollID() {
        return payrollID;
    }

    public void setPayrollID(String payrollID) {
        this.payrollID = payrollID;
    }

    public String getComponentID() {
        return componentID;
    }

    public void setComponentID(String componentID) {
        this.componentID = componentID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
}
