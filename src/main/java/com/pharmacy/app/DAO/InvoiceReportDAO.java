/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class InvoiceReportDAO {
    private final MyConnection myconnect = new MyConnection();
       public Map<String, Integer> getInvoicesByDay(Date date) {
           Map<String, Integer> invoiceData = new HashMap<>();

           if (myconnect.openConnection()) {
               java.sql.Date sqlDate = new java.sql.Date(date.getTime());
               String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                            "COUNT(*) as invoice_count " +
                            "FROM sales_invoices " +
                            "WHERE CONVERT(date, sale_date) = ? AND is_deleted = 0 " +
                            "GROUP BY CONVERT(VARCHAR(10), sale_date, 103)";

               try {
                   ResultSet rs = myconnect.prepareQuery(sql, sqlDate);
                   while (rs != null && rs.next()) {
                       String saleDate = rs.getString("sale_date");
                       int count = rs.getInt("invoice_count");
                       invoiceData.put(saleDate, count);
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               } finally {
                   myconnect.closeConnection();
               }
           }

           return invoiceData;
       }

       /**
        * Get invoice count data for a specific month and year
        * @param month Month (1-12)
        * @param year Year (e.g. 2025)
        * @return Map with date as key and invoice count as value
        */
       public Map<String, Integer> getInvoicesByMonth(int month, int year) {
           Map<String, Integer> invoiceData = new HashMap<>();

           if (myconnect.openConnection()) {
               String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                            "COUNT(*) as invoice_count " +
                            "FROM sales_invoices " +
                            "WHERE MONTH(sale_date) = ? AND YEAR(sale_date) = ? AND is_deleted = 0 " +
                            "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                            "ORDER BY MIN(sale_date)";

               try {
                   ResultSet rs = myconnect.prepareQuery(sql, month, year);
                   while (rs != null && rs.next()) {
                       String saleDate = rs.getString("sale_date");
                       int count = rs.getInt("invoice_count");
                       invoiceData.put(saleDate, count);
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               } finally {
                   myconnect.closeConnection();
               }
           }

           return invoiceData;
       }

       /**
        * Get monthly invoice count data for a specific year
        * @param year Year (e.g. 2025)
        * @return Map with month as key and invoice count as value
        */
       public Map<String, Integer> getInvoicesByYear(int year) {
           Map<String, Integer> invoiceData = new HashMap<>();

           if (myconnect.openConnection()) {
               String sql = "SELECT MONTH(sale_date) as month, " +
                            "COUNT(*) as invoice_count " +
                            "FROM sales_invoices " +
                            "WHERE YEAR(sale_date) = ? AND is_deleted = 0 " +
                            "GROUP BY MONTH(sale_date) " +
                            "ORDER BY month";

               try {
                   ResultSet rs = myconnect.prepareQuery(sql, year);
                   while (rs != null && rs.next()) {
                       int month = rs.getInt("month");
                       int count = rs.getInt("invoice_count");
                       invoiceData.put(getMonthName(month), count);
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               } finally {
                   myconnect.closeConnection();
               }
           }

           return invoiceData;
       }

       /**
        * Get invoice count data for a custom date range
        * @param startDate Start date
        * @param endDate End date
        * @return Map with date as key and invoice count as value
        */
       public Map<String, Integer> getInvoicesByDateRange(Date startDate, Date endDate) {
           Map<String, Integer> invoiceData = new HashMap<>();

           if (myconnect.openConnection()) {
               java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
               java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

               String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                            "COUNT(*) as invoice_count " +
                            "FROM sales_invoices " +
                            "WHERE CONVERT(date, sale_date) BETWEEN ? AND ? AND is_deleted = 0 " +
                            "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                            "ORDER BY MIN(sale_date)";

               try {
                   ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
                   while (rs != null && rs.next()) {
                       String saleDate = rs.getString("sale_date");
                       int count = rs.getInt("invoice_count");
                       invoiceData.put(saleDate, count);
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               } finally {
                   myconnect.closeConnection();
               }
           }

           return invoiceData;
       }

       /**
        * Get total invoice count for a date range
        * @param startDate Start date
        * @param endDate End date
        * @return Total invoice count for the date range
        */
       public int getTotalInvoicesForDateRange(Date startDate, Date endDate) {
           int totalInvoices = 0;

           if (myconnect.openConnection()) {
               java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
               java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

               String sql = "SELECT COUNT(*) as total_invoices " +
                            "FROM sales_invoices " +
                            "WHERE CONVERT(date, sale_date) BETWEEN ? AND ? AND is_deleted = 0";

               try {
                   ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
                   if (rs != null && rs.next()) {
                       totalInvoices = rs.getInt("total_invoices");
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               } finally {
                   myconnect.closeConnection();
               }
           }

           return totalInvoices;
       }

       /**
        * Get invoice statistics including count and total amount for a date range
        * @param startDate Start date
        * @param endDate End date
        * @return Map with date as key and object array (count, total amount) as value
        */
       public Map<String, Object[]> getInvoiceStatisticsByDateRange(Date startDate, Date endDate) {
           Map<String, Object[]> invoiceStats = new HashMap<>();

           if (myconnect.openConnection()) {
               java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
               java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

               String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                            "COUNT(*) as invoice_count, " +
                            "SUM(total_amount) as total_amount " +
                            "FROM sales_invoices " +
                            "WHERE CONVERT(date, sale_date) BETWEEN ? AND ? AND is_deleted = 0 " +
                            "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                            "ORDER BY MIN(sale_date)";

               try {
                   ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
                   while (rs != null && rs.next()) {
                       String saleDate = rs.getString("sale_date");
                       int count = rs.getInt("invoice_count");
                       BigDecimal amount = rs.getBigDecimal("total_amount");
                       if (amount == null) {
                           amount = BigDecimal.ZERO;
                       }
                       invoiceStats.put(saleDate, new Object[]{count, amount});
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               } finally {
                   myconnect.closeConnection();
               }
           }

           return invoiceStats;
       }
       private String getMonthName(int month) {
            return "Tháng " + month;
        }
    public Map<String, Object[]> getInvoicesByDayWithAmount(Date date) {
        Map<String, Object[]> invoiceData = new HashMap<>();

        if (myconnect.openConnection()) {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                         "COUNT(*) as invoice_count, " +
                         "SUM(total_amount) as total_amount " +
                         "FROM sales_invoices " +
                         "WHERE CONVERT(date, sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY CONVERT(VARCHAR(10), sale_date, 103)";

            try {
                ResultSet rs = myconnect.prepareQuery(sql, sqlDate);
                while (rs != null && rs.next()) {
                    String saleDate = rs.getString("sale_date");
                    int count = rs.getInt("invoice_count");
                    BigDecimal amount = rs.getBigDecimal("total_amount");
                    if (amount == null) {
                        amount = BigDecimal.ZERO;
                    }
                    invoiceData.put(saleDate, new Object[]{count, amount});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return invoiceData;
    }

    public Map<String, Object[]> getInvoicesByMonthWithAmount(int month, int year) {
        Map<String, Object[]> invoiceData = new HashMap<>();

        if (myconnect.openConnection()) {
            String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                         "COUNT(*) as invoice_count, " +
                         "SUM(total_amount) as total_amount " +
                         "FROM sales_invoices " +
                         "WHERE MONTH(sale_date) = ? AND YEAR(sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                         "ORDER BY MIN(sale_date)";

            try {
                ResultSet rs = myconnect.prepareQuery(sql, month, year);
                while (rs != null && rs.next()) {
                    String saleDate = rs.getString("sale_date");
                    int count = rs.getInt("invoice_count");
                    BigDecimal amount = rs.getBigDecimal("total_amount");
                    if (amount == null) {
                        amount = BigDecimal.ZERO;
                    }
                    invoiceData.put(saleDate, new Object[]{count, amount});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return invoiceData;
    }

    public Map<String, Object[]> getInvoicesByYearWithAmount(int year) {
        Map<String, Object[]> invoiceData = new HashMap<>();

        if (myconnect.openConnection()) {
            String sql = "SELECT MONTH(sale_date) as month, " +
                         "COUNT(*) as invoice_count, " +
                         "SUM(total_amount) as total_amount " +
                         "FROM sales_invoices " +
                         "WHERE YEAR(sale_date) = ? AND is_deleted = 0 " +
                         "GROUP BY MONTH(sale_date) " +
                         "ORDER BY month";

            try {
                ResultSet rs = myconnect.prepareQuery(sql, year);
                while (rs != null && rs.next()) {
                    int month = rs.getInt("month");
                    int count = rs.getInt("invoice_count");
                    BigDecimal amount = rs.getBigDecimal("total_amount");
                    if (amount == null) {
                        amount = BigDecimal.ZERO;
                    }
                    invoiceData.put(getMonthName(month), new Object[]{count, amount});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return invoiceData;
    }

    public Map<String, Object[]> getInvoicesByDateRangeWithAmount(Date startDate, Date endDate) {
        Map<String, Object[]> invoiceData = new HashMap<>();

        if (myconnect.openConnection()) {
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

            String sql = "SELECT CONVERT(VARCHAR(10), sale_date, 103) as sale_date, " +
                         "COUNT(*) as invoice_count, " +
                         "SUM(total_amount) as total_amount " +
                         "FROM sales_invoices " +
                         "WHERE CONVERT(date, sale_date) BETWEEN ? AND ? AND is_deleted = 0 " +
                         "GROUP BY CONVERT(VARCHAR(10), sale_date, 103) " +
                         "ORDER BY MIN(sale_date)";

            try {
                ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
                while (rs != null && rs.next()) {
                    String saleDate = rs.getString("sale_date");
                    int count = rs.getInt("invoice_count");
                    BigDecimal amount = rs.getBigDecimal("total_amount");
                    if (amount == null) {
                        amount = BigDecimal.ZERO;
                    }
                    invoiceData.put(saleDate, new Object[]{count, amount});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return invoiceData;
    }

    public BigDecimal getTotalAmountForDateRange(Date startDate, Date endDate) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        if (myconnect.openConnection()) {
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

            String sql = "SELECT SUM(total_amount) as total_amount " +
                         "FROM sales_invoices " +
                         "WHERE CONVERT(date, sale_date) BETWEEN ? AND ? AND is_deleted = 0";

            try {
                ResultSet rs = myconnect.prepareQuery(sql, sqlStartDate, sqlEndDate);
                if (rs != null && rs.next()) {
                    totalAmount = rs.getBigDecimal("total_amount");
                    if (totalAmount == null) {
                        totalAmount = BigDecimal.ZERO;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return totalAmount;
    }

}
