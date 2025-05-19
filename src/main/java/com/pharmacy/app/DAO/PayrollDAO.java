/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.PayrollDTO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class PayrollDAO {
    private MyConnection myConnection;

    public PayrollDAO() {
        myConnection = new MyConnection();
    }
    
    public boolean insert(PayrollDTO payroll) {
        myConnection.openConnection();
        String query = "INSERT INTO payrolls(payroll_id, employee_id, total_salary, status, pay_date, is_deleted) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        int result = myConnection.prepareUpdate(query, 
                payroll.getPayrollID(),
                payroll.getEmployeeID(),
                payroll.getTotalSalary(),
                payroll.getStatus(),
                payroll.getPayDate() != null ? Timestamp.valueOf(payroll.getPayDate()) : null,
                payroll.isDeleted());
        myConnection.closeConnection();
        return result > 0;
    }

    public boolean update(PayrollDTO payroll) {
        myConnection.openConnection();
        String query = "UPDATE payrolls SET employee_id = ?, total_salary = ?, status = ?, "
                + "pay_date = ?, is_deleted = ? WHERE payroll_id = ?";
        int result = myConnection.prepareUpdate(query, 
                payroll.getEmployeeID(),
                payroll.getTotalSalary(),
                payroll.getStatus(),
                payroll.getPayDate() != null ? Timestamp.valueOf(payroll.getPayDate()) : null,
                payroll.isDeleted(),
                payroll.getPayrollID());
        myConnection.closeConnection();
        return result > 0;
    }

    public boolean delete(String payrollID) {
        // Soft delete by setting isDeleted = true
        myConnection.openConnection();
        String query = "UPDATE payrolls SET is_deleted = 1 WHERE payroll_id = ?";
        int result = myConnection.prepareUpdate(query, payrollID);
        myConnection.closeConnection();
        return result > 0;
    }

    public ArrayList<PayrollDTO> selectAll() {
        ArrayList<PayrollDTO> payrollList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM payrolls WHERE is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                PayrollDTO payroll = extractPayrollFromResultSet(rs);
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payrollList;
    }

    public PayrollDTO selectByID(String payrollID) {
        PayrollDTO payroll = null;
        myConnection.openConnection();
        String query = "SELECT * FROM payrolls WHERE payroll_id = ? AND is_deleted = 0";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, payrollID);
            if (rs.next()) {
                payroll = extractPayrollFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payroll;
    }
    public PayrollDTO selectByEmpID(String payrollID) {
        PayrollDTO payroll = null;
        myConnection.openConnection();
        String query = "select TOP 1 * " +
                        "from payrolls " +
                        "where employee_id = ? " +
                        "ORDER BY pay_date DESC";
        try {
            ResultSet rs = myConnection.prepareQuery(query, payrollID);
            if (rs.next()) {
                payroll = extractPayrollFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payroll;
    }
    public ArrayList<PayrollDTO> search(String keyword) {
        ArrayList<PayrollDTO> payrollList = new ArrayList<>();
        myConnection.openConnection();
        
        // Search payrolls by joining with employees table to search by employee name
        String query = "SELECT p.* FROM payrolls p " +
                       "INNER JOIN employees e ON p.employee_id = e.employee_id " +
                       "WHERE e.name LIKE '%" + keyword + "%' " +
                       "AND p.is_deleted = 0";
                       
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                PayrollDTO payroll = extractPayrollFromResultSet(rs);
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payrollList;
    }
    
    public ArrayList<PayrollDTO> filterByStatus(boolean status) {
        ArrayList<PayrollDTO> payrollList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM payrolls WHERE status = " + (status ? "1" : "0") + " AND is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                PayrollDTO payroll = extractPayrollFromResultSet(rs);
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payrollList;
    }
    public PayrollDTO filterPayrollByMonthYear(String employeeID, String month, String year) {
        PayrollDTO payroll = null;
        myConnection.openConnection();
        String query = "SELECT * FROM payrolls "
                + "WHERE employee_id = ? "
                + "AND MONTH(pay_date) = ? "
                + "AND YEAR(pay_date) = ?";
        try {
            ResultSet rs = myConnection.prepareQuery(query, employeeID, Integer.parseInt(month), Integer.parseInt(year));
            while (rs.next()) {
                payroll = extractPayrollFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payroll;
    }
    /**
     * Get the highest payroll ID number from all payrolls (including deleted ones)
     * @return The highest ID number used in the database
     */
    public int getHighestPayrollIDNumber() {
        int maxID = 0;
        myConnection.openConnection();
        // Query to select all payroll IDs, including deleted ones
        String query = "SELECT payroll_id FROM payrolls";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                String id = rs.getString("payroll_id");
                if (id != null && id.startsWith("PR")) {
                    try {
                        int idNum = Integer.parseInt(id.substring(2));
                        if (idNum > maxID) {
                            maxID = idNum;
                        }
                    } catch (NumberFormatException e) {
                        // Skip if ID format is different or can't be parsed
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return maxID;
    }
    
    /**
     * Find payrolls for a specific employee
     * @param employeeID The ID of the employee
     * @return List of payrolls for the specified employee
     */
    public ArrayList<PayrollDTO> findByEmployeeID(String employeeID) {
        ArrayList<PayrollDTO> payrollList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM payrolls WHERE employee_id = ? AND is_deleted = 0";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, employeeID);
            while (rs.next()) {
                PayrollDTO payroll = extractPayrollFromResultSet(rs);
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payrollList;
    }
    
    /**
     * Find payrolls for employees with a specific position
     * @param position The position to filter by
     * @return List of payrolls for employees with the specified position
     */
    public ArrayList<PayrollDTO> getPayrollByEmployeesPosition(String position) {
        ArrayList<PayrollDTO> payrollList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT p.* FROM payrolls p " +
                       "INNER JOIN contracts c ON p.employee_id = c.employee_id " +
                       "WHERE c.position = ? AND p.is_deleted = 0";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, position);
            while (rs.next()) {
                PayrollDTO payroll = extractPayrollFromResultSet(rs);
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payrollList;
    }
    
    /**
     * Extract payroll information from a ResultSet
     * @param rs The ResultSet containing payroll data
     * @return A PayrollDTO object populated with data from the ResultSet
     * @throws SQLException If there's an error accessing the ResultSet
     */
    private PayrollDTO extractPayrollFromResultSet(ResultSet rs) throws SQLException {
        String payrollID = rs.getString("payroll_id");
        String employeeID = rs.getString("employee_id");
        BigDecimal totalSalary = rs.getBigDecimal("total_salary");
        boolean status = rs.getBoolean("status");
        
        // Handle nullable pay_date
        Timestamp payDateTimestamp = rs.getTimestamp("pay_date");
        LocalDateTime payDate = payDateTimestamp != null ? payDateTimestamp.toLocalDateTime() : null;
        
        boolean isDeleted = rs.getBoolean("is_deleted");
        
        return new PayrollDTO(payrollID, employeeID, totalSalary, status, payDate, isDeleted);
    }
}
