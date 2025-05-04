/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

/**
 *
 * @author phong
 */
public class UserValidation {
    /**
     * Validates if the string is not empty
     * @param value The string value to validate
     * @param fieldName Name of the field for error message
     * @return Error message or empty string if valid
     */
    public static String validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            return fieldName + " không được để trống";
        }
        return "";
    }
    
    /**
     * Validates if the username already exists in the database
     * @param username Username to check
     * @param userDAO DAO to use for database check
     * @return Error message or empty string if valid
     */
    public static String validateUsernameExists(String username, com.pharmacy.app.DAO.UserDAO userDAO) {
        if (userDAO.isUsernameExists(username)) {
            return "Tên đăng nhập đã tồn tại trong hệ thống";
        }
        return "";
    }
    
    /**
     * Validates username format (can customize rules as needed)
     * @param username Username to validate
     * @return Error message or empty string if valid
     */
    public static String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Tên đăng nhập không được để trống";
        }
        
        if (username.length() < 3) {
            return "Tên đăng nhập phải có ít nhất 3 ký tự";
        }
        return "";
    }
    
    /**
     * Validates password strength
     * @param password Password to validate
     * @return Error message or empty string if valid
     */
    public static String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "Mật khẩu không được để trống";
        }
        
        if (password.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự";
        }
        return "";
    }
    
    /**
     * Validates role ID
     * @param roleID Role ID to validate
     * @return Error message or empty string if valid
     */
    public static String validateRoleID(String roleID) {
        if (roleID == null || roleID.trim().isEmpty()) {
            return "Vai trò không được để trống";
        }
        return "";
    }
}
