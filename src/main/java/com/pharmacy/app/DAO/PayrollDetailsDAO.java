/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.PayrollDetailsDTO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class PayrollDetailsDAO {
    private MyConnection myConnection;

    public PayrollDetailsDAO() {
        myConnection = new MyConnection();
    }
    
    public boolean insert(PayrollDetailsDTO payrollDetail) {
        myConnection.openConnection();
        String query = "INSERT INTO payroll_details(payroll_id, component_id, value, amount) "
                    + "VALUES (?, ?, ?, ?)";
        int result = myConnection.prepareUpdate(query, 
                payrollDetail.getPayrollID(),
                payrollDetail.getComponentID(),
                payrollDetail.getValue(),
                payrollDetail.getAmount()
        );
        myConnection.closeConnection();
        return result > 0;
    }
    
    public ArrayList<PayrollDetailsDTO> selectAll() {
        ArrayList<PayrollDetailsDTO> payrollDetailList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM payroll_details";
        try {
            ResultSet rs = myConnection.runQuery(query);
            while (rs.next()) {
                PayrollDetailsDTO payrollDetail = extractPayrollDetailFromResultSet(rs);
                payrollDetailList.add(payrollDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payrollDetailList;
    }
    
    public ArrayList<PayrollDetailsDTO> selectAllByPayrollID(String payrollID) {
        ArrayList<PayrollDetailsDTO> payrollDetailList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM payroll_details WHERE payroll_id = ?";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, payrollID);
            while (rs.next()) {
                PayrollDetailsDTO payrollDetail = extractPayrollDetailFromResultSet(rs);
                payrollDetailList.add(payrollDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return payrollDetailList;
    }
    
    private PayrollDetailsDTO extractPayrollDetailFromResultSet(ResultSet rs) throws SQLException {
        String payrollID = rs.getString("payroll_id");
        String componentID = rs.getString("component_id");
        int value = rs.getInt("value");
        BigDecimal amount = rs.getBigDecimal("amount");
        
        return new PayrollDetailsDTO(payrollID, componentID, value, amount);
    }
}
