/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.time.LocalDate;

/**
 *
 * @author LENOVO
 */
public class SuplierInvoiceDetailsDTO {
    private String invoiceID;
    private String batchID;
    private String productID;
    private String name;
    private int quantity;
    private Double unitPrice;
    private Double sellPrice;
    private Double totalPrice;
    private LocalDate manuDate;
    private LocalDate expDate;
    
    public SuplierInvoiceDetailsDTO(){}
    
    public SuplierInvoiceDetailsDTO(String invoiceID, String batchID, String productID, String name, int quantity, Double unitPrice, Double totalPrice) {
        this.invoiceID = invoiceID;
        this.batchID = batchID;
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public Double getTotalPrice(){
        return totalPrice;
    }
    
    public void setTotalPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }

    public LocalDate getManuDate() {
        return manuDate;
    }

    public void setManuDate(LocalDate manuDate) {
        this.manuDate = manuDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice =  sellPrice;
    }
    
    
    
}
