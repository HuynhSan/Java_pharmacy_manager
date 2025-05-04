/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 *
 * @author Giai Cuu Li San
 */
public class CartItemDTO {
    private String batchId;
    private String productId;
    private String name;
    private BigDecimal discountAmount;
    private BigDecimal sellPrice;
    private BigDecimal finalPrice;
    private int quantity;

    private JPanel panel;
    private JSpinner spinner;

    public CartItemDTO(String batchId, String productId, String name, BigDecimal discountAmount,
                    BigDecimal sellPrice, BigDecimal finalPrice, int quantity) {
        this.batchId = batchId;
        this.productId = productId;
        this.name = name;
        this.discountAmount = discountAmount;
        this.sellPrice = sellPrice;
        this.finalPrice = finalPrice;
        this.quantity = quantity;
    }

    // Getters & Setters cho tất cả các trường...

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchId() {
        return batchId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public String getName() {
        return name;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }
    
    
    
    
    public JPanel getPanel() { return panel; }
    public void setPanel(JPanel panel) { this.panel = panel; }

    public JSpinner getSpinner() { return spinner; }
    public void setSpinner(JSpinner spinner) { this.spinner = spinner; }

    public int getQuantityFromSpinner() {
        return (int) spinner.getValue();
    }
}
