/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DAO.MyConnection;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author phong
 */
public class RevenueReportDAO {
    private final MyConnection myconnect = new MyConnection();
    
    /**
     * Get revenue data for a specific day
     * @param date The date to get revenue for
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByDay(Date date) {
        Map<String, BigDecimal> revenueData = new HashMap<>();
        
        if (myconnect.openConnection()) {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                         "SUM(total_amount) as total_revenue " +
                         "FROM sales_invoices " +
                         "WHERE CONVERT(date, sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY CONVERT(VARCHAR(10), sale_date, 103)";
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, sqlDate);
                while (rs != null && rs.next()) {
                    String saleDate = rs.getString("sale_date");
                    BigDecimal revenue = rs.getBigDecimal("total_revenue");
                    revenueData.put(saleDate, revenue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return revenueData;
    }
    
    /**
     * Get daily revenue data for the past N days
     * @param days Number of days to fetch
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueForLastNDays(int days) {
        Map<String, BigDecimal> revenueData = new HashMap<>();
        
        if (myconnect.openConnection()) {
            String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                         "SUM(total_amount) as total_revenue " +
                         "FROM sales_invoices " +
                         "WHERE sale_date >= DATEADD(day, -?, GETDATE()) AND is_deleted = 0 " +
                         "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                         "ORDER BY MIN(sale_date) DESC";
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, days);
                while (rs != null && rs.next()) {
                    String saleDate = rs.getString("sale_date");
                    BigDecimal revenue = rs.getBigDecimal("total_revenue");
                    revenueData.put(saleDate, revenue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return revenueData;
    }
    
    /**
     * Get revenue data for a specific month and year
     * @param month Month (1-12)
     * @param year Year (e.g. 2025)
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByMonth(int month, int year) {
        Map<String, BigDecimal> revenueData = new HashMap<>();
        
        if (myconnect.openConnection()) {
            String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                         "SUM(total_amount) as total_revenue " +
                         "FROM sales_invoices " +
                         "WHERE MONTH(sale_date) = ? AND YEAR(sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                         "ORDER BY MIN(sale_date)";
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, month, year);
                while (rs != null && rs.next()) {
                    String saleDate = rs.getString("sale_date");
                    BigDecimal revenue = rs.getBigDecimal("total_revenue");
                    revenueData.put(saleDate, revenue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return revenueData;
    }
    
    /**
     * Get monthly revenue data for a specific year
     * @param year Year (e.g. 2025)
     * @return Map with month as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByYear(int year) {
        Map<String, BigDecimal> revenueData = new HashMap<>();
        
        if (myconnect.openConnection()) {
            String sql = "SELECT MONTH(sale_date) as month, " +
                         "SUM(total_amount) as total_revenue " +
                         "FROM sales_invoices " +
                         "WHERE YEAR(sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY MONTH(sale_date) " +
                         "ORDER BY month";
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, year);
                while (rs != null && rs.next()) {
                    int month = rs.getInt("month");
                    BigDecimal revenue = rs.getBigDecimal("total_revenue");
                    revenueData.put(getMonthName(month), revenue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return revenueData;
    }
    
    /**
     * Get revenue data for a custom date range
     * @param startDate Start date
     * @param endDate End date
     * @return Map with date as key and total revenue as value
     */
    public Map<String, BigDecimal> getRevenueByDateRange(Date startDate, Date endDate) {
        Map<String, BigDecimal> revenueData = new HashMap<>();
        
        if (myconnect.openConnection()) {
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
            
            String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                         "SUM(total_amount) as total_revenue " +
                         "FROM sales_invoices " +
                         "WHERE CONVERT(date, sale_date) BETWEEN ? AND ? AND is_deleted = 0 " +
                         "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                         "ORDER BY MIN(sale_date)";
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
                while (rs != null && rs.next()) {
                    String saleDate = rs.getString("sale_date");
                    BigDecimal revenue = rs.getBigDecimal("total_revenue");
                    revenueData.put(saleDate, revenue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return revenueData;
    }
    
    /**
     * Get total revenue for a date range
     * @param startDate Start date
     * @param endDate End date
     * @return Total revenue for the date range
     */
    public BigDecimal getTotalRevenueForDateRange(Date startDate, Date endDate) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        
        if (myconnect.openConnection()) {
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
            
            String sql = "SELECT SUM(total_amount) as total_revenue " +
                         "FROM sales_invoices " +
                         "WHERE CONVERT(date, sale_date) BETWEEN ? AND ? AND is_deleted = 0";
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
                if (rs != null && rs.next()) {
                    totalRevenue = rs.getBigDecimal("total_revenue");
                    if (totalRevenue == null) {
                        totalRevenue = BigDecimal.ZERO;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return totalRevenue;
    }
    
    /**
     * Helper method to get month name from month number
     * @param month Month number (1-12)
     * @return Month name (e.g. "Tháng 1")
     */
    private String getMonthName(int month) {
        return "Tháng " + month;
    }
}
