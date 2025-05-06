/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author LENOVO
 */
public class PurchaseOrderDetailsDTO {
    public String poID;
    public String productID;
    public int quantity;

    public PurchaseOrderDetailsDTO(String poID, String productID, int quantity) {
        this.poID = poID;
        this.productID = productID;
        this.quantity = quantity;
    }
    
        public String getPoID() {
        return poID;
    }

    public void setPoID(String poID) {
        this.poID = poID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
