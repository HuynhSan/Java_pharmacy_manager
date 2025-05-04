/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceDetailDTO {
    private String invoiceId;
    private String productId;
    private int quantity;
    private BigDecimal totalPrice;

    public SalesInvoiceDetailDTO(String invoiceId, String productId, int quantity, BigDecimal totalPrice) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
