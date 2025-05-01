/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author phong
 */
public class ContractDTO {
    private String contractID;
    private String employeeID;
    private LocalDate signingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
    private float experienceYears;
    private String position;
    private String workDescription;
    private BigDecimal baseSalary;
    private boolean isDeleted;
    
    // Constructor 
    public ContractDTO(String contractID, String employeeID, LocalDate signingDate, LocalDate startDate, LocalDate endDate,
            String degree, float experienceYears, String position, String workDescription, BigDecimal baseSalary, boolean isDeleted) {
        this.contractID = contractID;
        this.employeeID = employeeID;
        this.signingDate = signingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.degree = degree;
        this.experienceYears = experienceYears;
        this.position = position;
        this.workDescription = workDescription;
        this.baseSalary = baseSalary;
        this.isDeleted = isDeleted;
    }
    
    // Getter & Setter
    public String getContractID() {
        return contractID;
    }
    
    public void setContractID(String contractID) {
        this.contractID = contractID;
    }
    
    public String getEmployeeID() {
        return employeeID;
    }
    
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    
    public LocalDate getSigningDate() {
        return signingDate;
    }
    
    public void setSigningDate(LocalDate signingDate) {
        this.signingDate = signingDate;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getDegree() {
        return degree;
    }
    
    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    public float getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(float experienceYears) {
        this.experienceYears = experienceYears;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getWorkDescription() {
        return workDescription;
    }
    
    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }
    
    public BigDecimal getBaseSalary() {
        return baseSalary;
    }
    
    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }
    
    public boolean isDeleted() {
        return isDeleted;
    }
    
    public void setDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
