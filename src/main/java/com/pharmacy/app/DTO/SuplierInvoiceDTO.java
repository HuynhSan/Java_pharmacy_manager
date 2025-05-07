/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class SuplierInvoiceDTO {
    public String invoiceID;
    public String poID;

    public String supplierID;
    public LocalDate purchaseDate;
    public String managerName;
    
    public String supplierName;
    public String managerID;
    public int totalQuantity;

    public BigDecimal totalPrice;
    public LocalDate purchaseDate;

    public Double totalPrice;
    public LocalDate importDate;
    public List<SuplierInvoiceDetailsDTO> details;


    public List<SuplierInvoiceDetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(List<SuplierInvoiceDetailsDTO> details) {
        this.details = details;
    }
            
    public SuplierInvoiceDTO(){}
    

    public SuplierInvoiceDTO(String invoiceID, String poID, String supplierID, String managerID, String supplierName, String managerName, int totalQuantity, BigDecimal totalPrice, LocalDate purchaseDate) {

    public SuplierInvoiceDTO(String invoiceID, String poID, String supplierID, String managerID, Double totalPrice, LocalDate importDate) {

        this.invoiceID = invoiceID;
        this.poID = poID;
        this.supplierID = supplierID;
        this.managerID = managerID;
        this.totalPrice = totalPrice;
        this.importDate = importDate;
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
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }
    
    
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
    
}
