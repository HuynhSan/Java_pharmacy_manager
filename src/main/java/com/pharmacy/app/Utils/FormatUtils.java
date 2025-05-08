/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author phong
 */
public class FormatUtils {
    private static final NumberFormat currencyFormatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    private static final DecimalFormat decimalFormatter = new DecimalFormat("#,###.##");
    
    static {
        currencyFormatter.setMaximumFractionDigits(0);
        currencyFormatter.setMinimumFractionDigits(0);
    }
    
    /**
     * Format a BigDecimal as currency (without currency symbol)
     * @param amount Amount to format
     * @return Formatted amount
     */
    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0";
        }
        return currencyFormatter.format(amount);
    }
    
    /**
     * Format a double as currency (without currency symbol)
     * @param amount Amount to format
     * @return Formatted amount
     */
    public static String formatCurrency(double amount) {
        return currencyFormatter.format(amount);
    }
    
    /**
     * Parse a formatted currency string back to BigDecimal
     * @param formattedAmount Formatted amount (e.g. "12,345,678")
     * @return BigDecimal value
     * @throws Exception If parsing fails
     */
    public static BigDecimal parseCurrency(String formattedAmount) throws Exception {
        if (formattedAmount == null || formattedAmount.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        // Remove any non-numeric characters except decimal point
        String cleaned = formattedAmount.replaceAll("[^\\d.]", "");
        if (cleaned.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return new BigDecimal(cleaned);
    }
}
