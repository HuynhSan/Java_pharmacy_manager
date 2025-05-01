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
public class SuplierInvoiceDTO {
    public String invoiceID;
    public String poID;
    public String supplierID;
    public String supplierName;
    public int totalQuantity;
    public double totalPrice;
    public LocalDate purchaseDate;

    public SuplierInvoiceDTO(){}
    
    public SuplierInvoiceDTO(String invoiceID, String poID, String supplierID, String supplierName, int totalQuantity, double totalPrice, LocalDate purchaseDate) {
        this.invoiceID = invoiceID;
        this.poID = poID;
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getPoID() {
        return poID;
    }

    public void setPoID(String poID) {
        this.poID = poID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }
    
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    
}
