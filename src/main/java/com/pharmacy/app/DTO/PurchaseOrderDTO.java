/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class PurchaseOrderDTO {
    private String poID;
    private String managerUserID;
    private String supplierID;
    private int totalQuantity;
    private LocalDate orderDate; // hoặc dùng java.time.LocalDate
    private String status;
    private String adminID;
    private List<PurchaseOrderDetailsDTO> poDetails;

    public PurchaseOrderDTO(String poID, String managerUserID, String supplierID, LocalDate orderDate, String status, String adminID) {
        this.poID = poID;
        this.managerUserID = managerUserID;
        this.supplierID = supplierID;
        this.orderDate = orderDate;
        this.status = status;
        this.adminID = adminID;
    }

    public List<PurchaseOrderDetailsDTO> getPoDetails() {
        return poDetails;
    }

    public void setPoDetails(List<PurchaseOrderDetailsDTO> poDetails) {
        this.poDetails = poDetails;
    }
    
    
    public PurchaseOrderDTO(){
        
    }
    public String getPoID() {
        return poID;
    }

    public void setPoID(String poID) {
        this.poID = poID;
    }

    public String getManagerUserID() {
        return managerUserID;
    }

    public void setManagerUserID(String managerUserID) {
        this.managerUserID = managerUserID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
}
