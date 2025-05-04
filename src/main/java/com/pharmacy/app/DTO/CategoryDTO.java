/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author LENOVO
 */
public class CategoryDTO {
    private String categoryID;
    private String categoryName;

    public CategoryDTO(String id, String name) {
        this.categoryID = id;
        this.categoryName = name;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() { return categoryID; }
    public String getCategoryName() { return categoryName; }

    @Override
    public String toString() {
        return this.categoryName; 
    }
}
