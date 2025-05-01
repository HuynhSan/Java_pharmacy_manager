/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.time.LocalDate;

/**
 *
 * @author Giai Cuu Li San
 */
public class PromotionDTO {
    private String promotionId;
    private String programName;
    private String promotionType;
    private Float discountPercent;           // float trong SQL
    private Float minAccumulatedPoints;        // float trong SQL
    private Double discountAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public PromotionDTO(){
        
    }
    public PromotionDTO(String promotionId, String programName, String promotionType,
                    Float discountPercent, Float minAccumulatedPoints, Double discountAmount, LocalDate startDate, LocalDate endDate) {
        this.promotionId = promotionId;
        this.programName = programName;
        this.promotionType = promotionType;
        this.discountPercent = discountPercent;
        this.minAccumulatedPoints = minAccumulatedPoints;
        this.discountAmount = discountAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public Float getDiscountPercent() {
        return discountPercent;
    }

    public String getProgramName() {
        return programName;
    }

    public Float getMinAccumulatedPoints() {
        return minAccumulatedPoints;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public String getPromotionId() {
        return promotionId;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    
    
}
