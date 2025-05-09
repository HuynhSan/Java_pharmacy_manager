/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.ProductReportDAO;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phong
 */
public class ProductReportBUS {
    private final ProductReportDAO productReportDAO = new ProductReportDAO();
    
    /**
     * Get best selling products for a specific month and year
     * @param month Month (1-12)
     * @param year Year (e.g. 2025)
     * @param limit Maximum number of products to return (0 for all)
     * @return List of maps containing product details
     */
    public List<Map<String, Object>> getBestSellingProductsByMonth(int month, int year, int limit) {
        return productReportDAO.getBestSellingProductsByMonth(month, year, limit);
    }
    
    /**
     * Get best selling products for a specific year
     * @param year Year (e.g. 2025)
     * @param limit Maximum number of products to return (0 for all)
     * @return List of maps containing product details
     */
    public List<Map<String, Object>> getBestSellingProductsByYear(int year, int limit) {
        return productReportDAO.getBestSellingProductsByYear(year, limit);
    }
    
    /**
     * Get total revenue from product sales in a specific period
     * @param startDate Start date
     * @param endDate End date
     * @return Total revenue for the period
     */
//    public int getTotalProductQuantity(Date startDate, Date endDate) {
//        return productReportDAO.getTotalProductQuantity(startDate, endDate);
//    }
}
