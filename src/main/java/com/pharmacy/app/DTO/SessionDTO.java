/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author BOI QUAN
 */
public class SessionDTO {
    private static UserDTO currentUser;
    
    // Map roleID vs ten role
    private static final Map<String, String> roleMap = new HashMap();
    static {
        roleMap.put("ROLE001", "Quản trị viên");
        roleMap.put("ROLE002", "Quản lý");
        roleMap.put("ROLE003", "Nhân viên dược sĩ");
    }
    
    public static void setCurrentUser(UserDTO user){
        currentUser = user;
    }
   
    public static UserDTO getCurrentUser(){
        return currentUser;
    }
    
    public static String getRoleName(){
        if(currentUser != null){
            return roleMap.get(currentUser.getRoleID());
        }
        return null;
    }
    
    public static void clearUser(){
        currentUser = null;
    }
}
