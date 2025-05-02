/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author LENOVO
 */
public class SuplierInvoiceDetailsDTO {
    public String invoiceID;
    public String batchID;
    public String productID;
    public String name;
    public int quantity;
    public double unitPrice;
    public double totalPrice;
    
    public SuplierInvoiceDetailsDTO(){}
    
    public SuplierInvoiceDetailsDTO(String invoiceID, String batchID, String productID, String name, int quantity, double unitPrice, double totalPrice) {
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public double getTotalPrice(){
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
    
}
