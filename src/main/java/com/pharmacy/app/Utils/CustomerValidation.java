/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import com.pharmacy.app.DAO.CustomerDAO;
import com.pharmacy.app.DAO.EmployeeDAO;

/**
 *
 * @author BOI QUAN
 */
public class CustomerValidation {
    
//    UPDATE
    /**
     * Validates if the phone number already exists in the database
     * @param phone Phone number to check
     * @param id
     * @param customerDAO
     * @return Error message or empty string if valid
     */
    public static String validatePhoneExists(String phone, String id, CustomerDAO customerDAO) {
        if (customerDAO.isUpdatePhoneExists(phone, id)) {
            return "Số điện thoại đã tồn tại trong hệ thống";
        }
        return "";
    }
}
