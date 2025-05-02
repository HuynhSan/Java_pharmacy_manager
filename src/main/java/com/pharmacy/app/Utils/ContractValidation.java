/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author phong
 */
public class ContractValidation {
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
     * Validates and parses date string
     * @param dateStr Date string in format dd/MM/yyyy
     * @param fieldName Name of the date field
     * @return Error message or empty string if valid
     */
    public static String validateDate(String dateStr, String fieldName) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return fieldName + " không được để trống";
        }
        
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return fieldName + " không đúng định dạng (dd/MM/yyyy)";
        }
        
        return "";
    }
    
    /**
     * Validates date logical relationships (signing date <= start date, start date <= end date)
     * @param signingDateStr Signing date string
     * @param startDateStr Start date string
     * @param endDateStr End date string
     * @return Error message or empty string if valid
     */
    public static String validateDateRelationships(String signingDateStr, String startDateStr, String endDateStr) {
        try {
            // Parse all dates first
            LocalDate signingDate = LocalDate.parse(signingDateStr, DATE_FORMATTER);
            LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
            LocalDate endDate = null;
            
            // End date can be null for indefinite contracts
            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
            }
            
            // Validate relationships
            if (signingDate.isAfter(LocalDate.now())) {
                return "Ngày ký không thể là ngày trong tương lai";
            }
            
            if (startDate.isBefore(signingDate)) {
                return "Ngày bắt đầu không thể trước ngày ký hợp đồng";
            }
            
            if (endDate != null && endDate.isBefore(startDate)) {
                return "Ngày kết thúc không thể trước ngày bắt đầu";
            }
            
        } catch (DateTimeParseException e) {
            // Date format errors are caught by individual date validations
            return "";
        }
        
        return "";
    }
    
    /**
     * Converts string date to LocalDate
     * @param dateStr Date string in format dd/MM/yyyy
     * @return LocalDate object or null if parsing fails
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Validates experience years (must be >= 0)
     * @param experienceYearsStr Experience years as string
     * @return Error message or empty string if valid
     */
    public static String validateExperienceYears(String experienceYearsStr) {
        if (experienceYearsStr == null || experienceYearsStr.trim().isEmpty()) {
            return "Số năm kinh nghiệm không được để trống";
        }
        
        try {
            float experienceYears = Float.parseFloat(experienceYearsStr);
            if (experienceYears < 0) {
                return "Số năm kinh nghiệm không thể nhỏ hơn 0";
            }
        } catch (NumberFormatException e) {
            return "Số năm kinh nghiệm phải là số";
        }
        
        return "";
    }
    
    /**
     * Validates base salary (must be >= 0)
     * @param baseSalaryStr Base salary as string
     * @return Error message or empty string if valid
     */
    public static String validateBaseSalary(String baseSalaryStr) {
        if (baseSalaryStr == null || baseSalaryStr.trim().isEmpty()) {
            return "Lương cơ bản không được để trống";
        }
        
        try {
            BigDecimal baseSalary = new BigDecimal(baseSalaryStr);
            if (baseSalary.compareTo(BigDecimal.ZERO) < 0) {
                return "Lương cơ bản không thể nhỏ hơn 0";
            }
        } catch (NumberFormatException e) {
            return "Lương cơ bản phải là số";
        }
        
        return "";
    }
    
    /**
     * Validates a new contract against an existing contract
     * @param signingDateStr Signing date of the new contract
     * @param startDateStr Start date of the new contract
     * @param latestContractEndDate End date of the latest contract
     * @return Error message or empty string if valid
     */
    public static String validateNewContractDates(String signingDateStr, String startDateStr, LocalDate latestContractEndDate) {
        if (latestContractEndDate == null) {
            return ""; // No previous contract or previous contract has no end date
        }

        try {
            // Parse dates
            LocalDate signingDate = LocalDate.parse(signingDateStr, DATE_FORMATTER);
            LocalDate startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);

            // Calculate the date that is 10 days before the latest contract's end date
            LocalDate tenDaysBeforeEnd = latestContractEndDate.minusDays(10);

            // Check if signing date is at least 10 days before the end date or later
            if (signingDate.isBefore(tenDaysBeforeEnd)) {
                return "Ngày ký hợp đồng mới phải từ 10 ngày trước ngày kết thúc hợp đồng cũ hoặc sau đó";
            }

            // Check if the start date of the new contract is after the end date of the latest contract
            if (!startDate.isAfter(latestContractEndDate)) {
                return "Ngày bắt đầu hợp đồng mới phải sau ngày kết thúc hợp đồng cũ";
            }
        } catch (DateTimeParseException e) {
            // Date format errors are caught by individual date validations
            return "";
        }

        return "";
    }
}
