/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.PayrollDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BOI QUAN
 */
public class PayrollDAO implements DAOinterface<PayrollDTO>{
    MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(PayrollDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(PayrollDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<PayrollDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PayrollDTO selectByID(String employeeID) {
        PayrollDTO payroll = null;
        if(myconnect.openConnection()){
            String query = "SELECT payroll_id, total_salary, pay_date, status "
                    + "FROM payrolls "
                    + "WHERE employee_id = ?";
            ResultSet rs = myconnect.prepareQuery(query, employeeID);
            try {
                while(rs.next()){
                    payroll = new PayrollDTO();
                    payroll.setPayrollID(rs.getString("payroll_id"));
                    payroll.setPayDate(rs.getDate("pay_date").toLocalDate());
                    payroll.setTotalSalary(rs.getBigDecimal("total_salary"));
                    payroll.setStatus(rs.getBoolean("status"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally{
                myconnect.closeConnection();
            }
        }
        return payroll;
    }

    @Override
    public ArrayList<PayrollDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
