/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.InvoiceReportDAO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class InvoiceReportBUS {
    InvoiceReportDAO invoiceDAO = new InvoiceReportDAO();
    public Map<String, Integer> getInvoicesByDay(Date date) {
        return invoiceDAO.getInvoicesByDay(date);
    }

    public Map<String, Integer> getInvoicesByMonth(int month, int year) {
        return invoiceDAO.getInvoicesByMonth(month, year);
    }
    
    public Map<String, Integer> getInvoicesByYear( int year) {
        return invoiceDAO.getInvoicesByYear(year);
    }
    
    public int getTotalInvoicesForDateRange(Date startDate, Date endDate) {
        return invoiceDAO.getTotalInvoicesForDateRange(startDate, endDate);
    }
    
    public Map<String, Object[]> getInvoiceStatisticsByDateRange(Date startDate, Date endDate) {
        return invoiceDAO.getInvoiceStatisticsByDateRange(startDate, endDate);
    }
    
    public Map<String, Object[]> getInvoiceStatisticsForTable(Date startDate, Date endDate) {
        // Có thể thêm logic xử lý bổ sung nếu cần
        return invoiceDAO.getInvoiceStatisticsByDateRange(startDate, endDate);
    }
    
    public Map<String, Integer> getInvoicesByDateRange(Date startDate, Date endDate) {
        // Có thể thêm logic xử lý dữ liệu phù hợp cho biểu đồ
        return invoiceDAO.getInvoicesByDateRange(startDate, endDate);
    }
    
    public Map<String, Object[]> getInvoicesWithAmountByDay(Date date) {
        return invoiceDAO.getInvoicesByDayWithAmount(date);
    }
    
    /**
     * Get invoice count and total amount data for a specific month
     * @param month Month (1-12)
     * @param year Year (e.g. 2025)
     * @return Map with date as key and object array (count, total amount) as value
     */
    public Map<String, Object[]> getInvoicesWithAmountByMonth(int month, int year) {
        return invoiceDAO.getInvoicesByMonthWithAmount(month, year);
    }
    
    /**
     * Get invoice count and total amount data for a specific year
     * @param year Year (e.g. 2025)
     * @return Map with month as key and object array (count, total amount) as value
     */
    public Map<String, Object[]> getInvoicesWithAmountByYear(int year) {
        return invoiceDAO.getInvoicesByYearWithAmount(year);
    }
    
    /**
     * Get invoice count and total amount data for a custom date range
     * @param startDate Start date
     * @param endDate End date
     * @return Map with date as key and object array (count, total amount) as value
     */
    public Map<String, Object[]> getInvoicesWithAmountByDateRange(Date startDate, Date endDate) {
        return invoiceDAO.getInvoicesByDateRangeWithAmount(startDate, endDate);
    }
    
    /**
     * Get total amount for a specific date range
     * @param startDate Start date
     * @param endDate End date
     * @return Total amount for the date range
     */
    public BigDecimal getTotalAmountForDateRange(Date startDate, Date endDate) {
        return invoiceDAO.getTotalAmountForDateRange(startDate, endDate);
    }
}
