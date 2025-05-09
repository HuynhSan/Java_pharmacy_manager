/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DAO.MyConnection;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phong
 */
public class ProductReportDAO {
    private final MyConnection myconnect = new MyConnection();
    
    /**
     * Get best selling products for a specific month and year
     * @param month Month (1-12)
     * @param year Year (e.g. 2025)
     * @param limit Maximum number of products to return
     * @return List of maps containing product_id, product_name, and quantity_sold
     */
    public List<Map<String, Object>> getBestSellingProductsByMonth(int month, int year, int limit) {
        List<Map<String, Object>> productData = new ArrayList<>();
        
        if (myconnect.openConnection()) {
            String sql = "SELECT p.product_id, p.name AS product_name, SUM(sd.quantity) AS quantity_sold " +
                         "FROM medical_products p " +
                         "JOIN sales_invoice_details sd ON p.product_id = sd.product_id " +
                         "JOIN sales_invoices si ON sd.sales_invoice_id = si.sales_invoice_id " +
                         "WHERE MONTH(si.sale_date) = ? AND YEAR(si.sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY p.product_id, p.name " +
                         "ORDER BY sale_quantity DESC";
            
            if (limit > 0) {
                sql += " LIMIT " + limit;
            }
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, month, year);
                while (rs != null && rs.next()) {
                    Map<String, Object> product = new HashMap<>();
                    product.put("product_id", rs.getString("product_id"));
                    product.put("product_name", rs.getString("product_name"));
                    product.put("quantity_sold", rs.getInt("quantity_sold"));
                    productData.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return productData;
    }
    
    /**
     * Get best selling products for a specific year
     * @param year Year (e.g. 2025)
     * @param limit Maximum number of products to return
     * @return List of maps containing product_id, product_name, and quantity_sold
     */
    public List<Map<String, Object>> getBestSellingProductsByYear(int year, int limit) {
        List<Map<String, Object>> productData = new ArrayList<>();
        
        if (myconnect.openConnection()) {
            String sql = "SELECT TOP 5 p.product_id, p.name AS product_name, SUM(sd.quantity) AS sale_quantity" +
                         "FROM medical_products p" +
                         "JOIN sales_invoice_details sd ON p.product_id = sd.product_id" +
                         "JOIN sales_invoices si ON sd.sales_invoice_id = si.sales_invoice_id" +
                         "WHERE YEAR(si.sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY p.product_id, p.name" +
                         "ORDER BY sale_quantity DESC";
            
            if (limit > 0) {
                sql += " LIMIT " + limit;
            }
            
            try {
                ResultSet rs = myconnect.prepareQuery(sql, year);
                while (rs != null && rs.next()) {
                    Map<String, Object> product = new HashMap<>();
                    product.put("product_id", rs.getString("product_id"));
                    product.put("product_name", rs.getString("product_name"));
                    product.put("quantity_sold", rs.getInt("quantity_sold"));
                    productData.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return productData;
    }
    
    /**
     * Get total quantity of products sold in a specific period
     * @param startDate Start date
     * @param endDate End date
     * @return Total quantity of products sold for the period
     */
//    public int getTotalProductQuantity(Date startDate, Date endDate) {
//        int totalQuantity = 0;
//        
//        if (myconnect.openConnection()) {
//            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
//            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
//            
//            String sql = "SELECT SUM(sd.quantity) as total_quantity " +
//                         "FROM sales_invoice_details sd " +
//                         "JOIN sales_invoices si ON sd.sales_invoice_id = si.sales_invoice_id " +
//                         "WHERE CONVERT(date, si.sale_date) BETWEEN ? AND ? " +
//                         "AND si.is_deleted = 0";
//            
//            try {
//                ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
//                if (rs != null && rs.next()) {
//                    totalQuantity = rs.getInt("total_quantity");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                myconnect.closeConnection();
//            }
//        }
//        
//        return totalQuantity;
//    }
}
