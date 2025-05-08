/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author LENOVO
 */
public class ProductBatchDTO {
    private String batchID;
    private String medicineID;
    private LocalDate manufacturingDate;
    private LocalDate expirationDate;
    private int quantityInStock;
    private int quantityReceived;
    private double sellPrice;
    private boolean status;
    private String supplierName;
    private String medicineName;
    
    public ProductBatchDTO(){};
    public ProductBatchDTO(String batchID, String medicineID, LocalDate manufacturingDate, LocalDate expirationDate, int quantityInStock, int quantityReceived, double sellPrice, boolean status) {
        this.batchID = batchID;
        this.medicineID = medicineID;
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
        this.quantityInStock = quantityInStock;
        this.quantityReceived = quantityReceived;
        this.sellPrice = sellPrice;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    
    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(int quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
    
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    
}
