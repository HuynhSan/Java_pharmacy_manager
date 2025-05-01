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
    public int invoiceID;
    public int poID;
    public int supplierID;
    public int totalQuantity;
    public double totalPrice;
    public LocalDate purchaseDate;

    public SuplierInvoiceDTO(int invoiceID, int poID, int supplierID, int totalQuantity, double totalPrice, LocalDate purchaseDate) {
        this.invoiceID = invoiceID;
        this.poID = poID;
        this.supplierID = supplierID;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getPoID() {
        return poID;
    }

    public void setPoID(int poID) {
        this.poID = poID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
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
