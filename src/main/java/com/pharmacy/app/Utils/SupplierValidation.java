/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import com.pharmacy.app.DAO.SupplierDAO;

/**
 *
 * @author BOI QUAN
 */
public class SupplierValidation {    

//    ADD
    /**
     * Validates if the email already exists in the database
     * @param name
     * @param supplierDAO
     * @return Error message or empty string if valid
     */
    public static String validateNameExists(String name, SupplierDAO supplierDAO) {
        if (supplierDAO.isNameExists(name)) {
            return "Công ty đã tồn tại trong hệ thống";
        }   
        return "";
    }
    
    
    
    
//    UPDATE
    /**
     * Validates if the email already exists in the database
     * @param name
     * @param id
     * @param supplierDAO
     * @return Error message or empty string if valid
     */
    public static String validateNameExists(String name, String id, SupplierDAO supplierDAO) {
        if (supplierDAO.isUpdateNameExists(name, id)) {
            return "Công ty đã tồn tại trong hệ thống";
        }   
        return "";
    }

    /**
     * Validates if the email already exists in the database
     * @param email Email to check
     * @param id
     * @param supplierDAO
     * @return Error message or empty string if valid
     */
    public static String validateEmailExists(String email, String id, SupplierDAO supplierDAO) {
        if (supplierDAO.isUpdateEmailExists(email, id)) {
            return "Email đã tồn tại trong hệ thống";
        }   
        return "";
    }
    
    /**
     * Validates if the phone number already exists in the database
     * @param phone Phone number to check
     * @param id
     * @param supplierDAO
     * @return Error message or empty string if valid
     */
    public static String validatePhoneExists(String phone, String id, SupplierDAO supplierDAO) {
        if (supplierDAO.isUpdatePhoneExists(phone, id)) {
            return "Số điện thoại đã tồn tại trong hệ thống";
        }
        return "";
    }
}
