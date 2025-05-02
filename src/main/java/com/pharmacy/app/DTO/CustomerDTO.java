/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author BOI QUAN
 */
public class CustomerDTO {
    private String id;
    private String name;
    private String phone;
    private float point;
    private boolean isDeleted;
     
    // Constructor
    public CustomerDTO(String id, String name, String phone, float point) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.point = point;
    }
    
    public CustomerDTO(String name, String phone, float point) {
//        this.id = id;
        this.name = name;
        this.phone = phone;
        this.point = point;
//        this.isDeleted = isDeleted;
    }
    
    public CustomerDTO() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getter & Setter
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public float getPoint() { return point; }
    public boolean getIsDeleted() { return isDeleted; }
    
    public void setId(String id){ this.id = id; }
    public void setName(String name){ this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPoint(float point) { this.point = point; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
    
}
