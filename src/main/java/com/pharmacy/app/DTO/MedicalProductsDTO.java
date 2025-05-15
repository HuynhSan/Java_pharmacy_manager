/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author LENOVO
 */
public class MedicalProductsDTO {
    private String medicineID;
    private String name;
    private String category;
    private String description;
    private String unit;
    private int quantity;
    private String status;
    private String packingSpecification;
    private String imgPath;

    public MedicalProductsDTO(){
     
    }

    
    
    public MedicalProductsDTO(String medicineID, String name, String category, String description, String unit, int quantity,  String packingSpecification) {
        this.medicineID = medicineID;
        this.name = name;
        this.category = category;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.packingSpecification = packingSpecification;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPackingSpecification() {
        return packingSpecification;
    }

    public void setPackingSpecification(String packingSpecification) {
        this.packingSpecification = packingSpecification;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    
    
}
