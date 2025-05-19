/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.PayrollDAO;
import com.pharmacy.app.DTO.PayrollDTO;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class PayrollBUS {
    public PayrollDAO payrollDAO;
    private ArrayList<PayrollDTO> payrollList;
    
    public PayrollBUS() {
        payrollDAO = new PayrollDAO();
        payrollList = new ArrayList<>();
    }
    
    public ArrayList<PayrollDTO> getPayrollList() {
        return payrollList;
    }
    
    public void loadPayrollList() {
        payrollList = payrollDAO.selectAll();
    }
    
    public boolean addPayroll(PayrollDTO payroll) {
        boolean result = payrollDAO.insert(payroll);
        if (result) {
            payrollList.add(payroll);
        }
        return result;
    }
    
    public boolean updatePayroll(PayrollDTO payroll) {
        boolean result = payrollDAO.update(payroll);
        if (result) {
            for (int i = 0; i < payrollList.size(); i++) {
                if (payrollList.get(i).getPayrollID().equals(payroll.getPayrollID())) {
                    payrollList.set(i, payroll);
                    break;
                }
            }
        }
        return result;
    }
    
    public boolean deletePayroll(String payrollID) {
        boolean result = payrollDAO.delete(payrollID);
        if (result) {
            for (int i = 0; i < payrollList.size(); i++) {
                if (payrollList.get(i).getPayrollID().equals(payrollID)) {
                    payrollList.remove(i);
                    break;
                }
            }
        }
        return result;
    }
    
    public PayrollDTO getPayrollByID(String payrollID) {
        for (PayrollDTO payroll : payrollList) {
            if (payroll.getPayrollID().equals(payrollID)) {
                return payroll;
            }
        }
        return payrollDAO.selectByID(payrollID);
    }
    public PayrollDTO getPayrollByEmpID(String employeeID) {
        for (PayrollDTO payroll : payrollList) {
            if (payroll.getEmployeeID().equals(employeeID)) {
                return payroll;
            }
        }
        return payrollDAO.selectByEmpID(employeeID);
    }    
    public ArrayList<PayrollDTO> searchPayrollsByEmployeeName(String keyword) {
        return payrollDAO.search(keyword);
    }
    
    public ArrayList<PayrollDTO> filterPayrollsByStatus(boolean status) {
        return payrollDAO.filterByStatus(status);
    }
    
    public ArrayList<PayrollDTO> getPayrollsByEmployeeID(String employeeID) {
        return payrollDAO.findByEmployeeID(employeeID);
    }
    
    /**
     * Get payrolls for employees with position "Nhân viên bán hàng"
     * @return List of payrolls for employees with position "Nhân viên bán hàng"
     */
    public ArrayList<PayrollDTO> getPayrollsOfSalesEmployees() {
        return payrollDAO.getPayrollByEmployeesPosition("Nhân viên bán hàng");
    }
    
    /**
     * Loads payrolls of sales employees into a dedicated list
     */
    private ArrayList<PayrollDTO> salesEmployeesPayrollsList;

    public ArrayList<PayrollDTO> getSalesEmployeesPayrollsList() {
        return salesEmployeesPayrollsList;
    }

    public void loadSalesEmployeesPayrollsList() {
        salesEmployeesPayrollsList = payrollDAO.getPayrollByEmployeesPosition("Nhân viên bán hàng");
    }
    
    public String generateNewPayrollID() {
        // Get the highest payroll ID from the entire database (including deleted payrolls)
        int maxID = payrollDAO.getHighestPayrollIDNumber();
        
        // Increment and format the new ID
        int newID = maxID + 1;
        return String.format("PR%03d", newID);
    }
    
    public void updatePaymentStatus(String payrollID, boolean status) {
        PayrollDTO payroll = getPayrollByID(payrollID);
        if (payroll != null) {
            payroll.setStatus(status);
            updatePayroll(payroll);
        }
    }
}
