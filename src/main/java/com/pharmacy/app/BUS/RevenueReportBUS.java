/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.RevenueReportDAO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author phong
 */
public class RevenueReportBUS {
    RevenueReportDAO dao = new RevenueReportDAO();
    /**
     * Get revenue data for a specific day
     * @param date The date to get revenue for
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByDay(Date date) {
        return dao.getRevenueByDay(date);
    }
    
    /**
     * Get daily revenue data for the past N days
     * @param days Number of days to fetch
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueForLastNDays(int days) {
        return dao.getRevenueForLastNDays(days);
    }
    
    /**
     * Get revenue data for a specific month and year
     * @param month Month (1-12)
     * @param year Year (e.g. 2025)
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByMonth(int month, int year) {
        return dao.getRevenueByMonth(month, year);
    }
    
    /**
     * Get monthly revenue data for a specific year
     * @param year Year (e.g. 2025)
     * @return Map with month as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByYear(int year) {
        return dao.getRevenueByYear(year);
    }
    
    /**
     * Get revenue data for a custom date range
     * @param startDate Start date
     * @param endDate End date
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByDateRange(Date startDate, Date endDate) {
        return dao.getRevenueByDateRange(startDate, endDate);
    }
    
    /**
     * Get total revenue for a date range
     * @param startDate Start date
     * @param endDate End date
     * @return Total revenue for the date range
     */
    public BigDecimal getTotalRevenueForDateRange(Date startDate, Date endDate) {
        return dao.getTotalRevenueForDateRange(startDate, endDate);
    }
}
