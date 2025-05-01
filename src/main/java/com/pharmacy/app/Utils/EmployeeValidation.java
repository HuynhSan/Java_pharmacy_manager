/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Utility class for validating employee form inputs
 * @author phong
 */
public class EmployeeValidation {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
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
     * Validates email format
     * @param email Email to validate
     * @return Error message or empty string if valid
     */
    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống";
        }
        
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            return "Email không đúng định dạng";
        }
        
        return "";
    }
    
    /**
     * Validates phone number format
     * @param phone Phone number to validate
     * @return Error message or empty string if valid
     */
    public static String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }
        
        if (!Pattern.matches(PHONE_REGEX, phone)) {
            return "Số điện thoại phải có 10 chữ số";
        }
        
        return "";
    }
    
    /**
     * Validates and parses date string
     * @param dateStr Date string in format dd/MM/yyyy
     * @return Error message or empty string if valid
     */
    public static String validateDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return "Ngày sinh không được để trống";
        }
        
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            if (date.isAfter(LocalDate.now())) {
                return "Ngày sinh không thể là ngày trong tương lai";
            }
        } catch (DateTimeParseException e) {
            return "Ngày sinh không đúng định dạng (dd/MM/yyyy)";
        }
        
        return "";
    }
    
    /**
     * Converts string date to LocalDate
     * @param dateStr Date string in format dd/MM/yyyy
     * @return LocalDate object or null if parsing fails
     */
    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Validates if the email already exists in the database
     * @param email Email to check
     * @param employeeDAO DAO to use for database check
     * @return Error message or empty string if valid
     */
    public static String validateEmailExists(String email, com.pharmacy.app.DAO.EmployeeDAO employeeDAO) {
        if (employeeDAO.isEmailExists(email)) {
            return "Email đã tồn tại trong hệ thống";
        }
        return "";
    }

    /**
     * Validates if the phone number already exists in the database
     * @param phone Phone number to check
     * @param employeeDAO DAO to use for database check
     * @return Error message or empty string if valid
     */
    public static String validatePhoneExists(String phone, com.pharmacy.app.DAO.EmployeeDAO employeeDAO) {
        if (employeeDAO.isPhoneExists(phone)) {
            return "Số điện thoại đã tồn tại trong hệ thống";
        }
        return "";
    }
}
