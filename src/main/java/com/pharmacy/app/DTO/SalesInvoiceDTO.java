/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceDTO {
    private String invoiceId;
    private String userId;
    private String customerId;
    private int totalQuantity;
    private BigDecimal totalAmount;
    private BigDecimal totalDiscount;
    private BigDecimal finalTotal;
    private LocalDate createDate;

    // constructor, getter, setter ...
    public SalesInvoiceDTO(){}
    
    public SalesInvoiceDTO(String invoiceId, String userId, String customerId, int totalQuantity, BigDecimal totalAmount, BigDecimal totalDiscount, BigDecimal finalTotal, LocalDate createDate) {
        this.invoiceId = invoiceId;
        this.userId = userId;
        this.customerId = customerId;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.finalTotal = finalTotal;
        this.createDate = createDate;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
    
    
}

