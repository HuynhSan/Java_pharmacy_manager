/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author phong
 */
public class RoleDTO {
    private String roleID;
    private String roleName;
    private String description;
    private boolean isDeleted;
    
    //Constructor 
    public RoleDTO(String roleID, String roleName, String description, boolean isDeleted) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.description = description;
        this.isDeleted = isDeleted;
    }
    
    //Getter & Setter
    public String getRoleID() {
        return roleID;
    }
    
    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
