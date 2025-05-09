/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Giai Cuu Li San
 */
public class SaleItemDTO {
    private String batchId;
    private String productId;
    private String name;
    private String unit;
    private BigDecimal sellPrice;
    private LocalDate expirationDate;
    private int inventoryQuantity;
    private Float percentDiscount;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;
    private String promoId;

    public SaleItemDTO(String batchId, String productId, String name, String unit,
                    BigDecimal sellPrice, LocalDate expirationDate, int inventoryQuantity,
                    Float percentDiscount, BigDecimal discountAmount, BigDecimal finalPrice, String promoId) {
        this.batchId = batchId;
        this.productId = productId;
        this.name = name;
        this.unit = unit;
        this.sellPrice = sellPrice;
        this.expirationDate = expirationDate;
        this.inventoryQuantity = inventoryQuantity;
        this.percentDiscount = percentDiscount;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
        this.promoId = promoId;
    }

    // Getters and setters for all fields
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Float getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Float percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }
    
    
}
