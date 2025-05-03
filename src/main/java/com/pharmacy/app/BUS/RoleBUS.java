/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.RoleDAO;
import com.pharmacy.app.DTO.RoleDTO;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class RoleBUS {
    public RoleDAO roleDAO;
    private ArrayList<RoleDTO> roleList;
    
    public RoleBUS() {
        roleDAO = new RoleDAO();
        roleList = new ArrayList<>();
    }
    
    public ArrayList<RoleDTO> getRoleList() {
        return roleList;
    }
    
    public void loadRoleList() {
        roleList = roleDAO.selectAll();
    }
    
    public RoleDTO getRoleByID(String roleID) {
        for (RoleDTO role : roleList) {
            if (role.getRoleID().equals(roleID)) {
                return role;
            }
        }
        return roleDAO.selectByID(roleID);
    }
    
    public String getRoleIDByName(String roleName) {
        for (RoleDTO role : roleList) {
            if (role.getRoleName().equals(roleName)) {
                return role.getRoleID();
            }
        }
        return null;
    }
}
