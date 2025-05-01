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
public class PurchaseOrderDTO {
    public int poID;
    public int managerUserID;
    public int supplierID;
    public int totalQuantity;
    public LocalDate orderDate; // hoặc dùng java.time.LocalDate
    public LocalDate status;
    public int adminID;

    public PurchaseOrderDTO(int poID, int managerUserID, int supplierID, int totalQuantity, LocalDate orderDate, LocalDate status, int adminID) {
        this.poID = poID;
        this.managerUserID = managerUserID;
        this.supplierID = supplierID;
        this.totalQuantity = totalQuantity;
        this.orderDate = orderDate;
        this.status = status;
        this.adminID = adminID;
    }

    public int getPoID() {
        return poID;
    }

    public void setPoID(int poID) {
        this.poID = poID;
    }

    public int getManagerUserID() {
        return managerUserID;
    }

    public void setManagerUserID(int managerUserID) {
        this.managerUserID = managerUserID;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getStatus() {
        return status;
    }

    public void setStatus(LocalDate status) {
        this.status = status;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}
