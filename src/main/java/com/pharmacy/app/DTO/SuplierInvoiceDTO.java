/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
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
    public String managerID;
    public String managerName;
    public int totalQuantity;
    public BigDecimal totalPrice;
    public LocalDate purchaseDate;

    public SuplierInvoiceDTO(){}
    
    public SuplierInvoiceDTO(String invoiceID, String poID, String supplierID, String managerID, String supplierName, String managerName, int totalQuantity, BigDecimal totalPrice, LocalDate purchaseDate) {
        this.invoiceID = invoiceID;
        this.poID = poID;
        this.supplierID = supplierID;
        this.managerID = managerID;
        this.managerName = managerName;
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
    
    public String getManagerID() {
        return managerID;
    }
    
    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }
    
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
        
    public String getManagerName() {
        return managerName;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    
}
