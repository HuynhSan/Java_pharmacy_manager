/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author Giai Cuu Li San
 */
public class SupplierDTO {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private boolean isDeleted;

    // Constructor
    public SupplierDTO(String id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
//        this.isDeleted = isDeleted;
    }
    
    public SupplierDTO(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
//        
    }
    
    public SupplierDTO() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getter & Setter
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public boolean getIsDeleted() { return isDeleted; }
    
    public void setId(String id){ this.id = id; }
    public void setName(String name){ this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email){ this.email = email; }
    public void setAddress(String address){ this.address = address; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
}
