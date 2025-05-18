/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author phong
 */
public class SalaryComponentsDTO {
    private String componentID;
    private String name;
    private String description;
    private boolean isDeleted;
    
    // Constructor
    public SalaryComponentsDTO(String componentID, String name, String description, boolean isDeleted) {
        this.componentID = componentID;
        this.name = name;
        this.description = description;
        this.isDeleted = isDeleted;
    }
    
    //Getter & Setter
    public String getComponentID() {
        return componentID;
    }

    public void setComponentID(String componentID) {
        this.componentID = componentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    
}
