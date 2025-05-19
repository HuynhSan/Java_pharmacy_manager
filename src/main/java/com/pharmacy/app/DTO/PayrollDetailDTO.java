/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;

/**
 *
 * @author BOI QUAN
 */
public class PayrollDetailDTO {
    private String name;
    private int value;
    private BigDecimal amount;

    public PayrollDetailDTO() {
    }

    public PayrollDetailDTO(String name, int value, BigDecimal amount) {
        this.name = name;
        this.value = value;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    
}
