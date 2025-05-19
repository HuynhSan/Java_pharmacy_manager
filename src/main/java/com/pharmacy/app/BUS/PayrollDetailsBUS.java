/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.PayrollDetailsDAO;
import com.pharmacy.app.DTO.PayrollDetailsDTO;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class PayrollDetailsBUS {
    public PayrollDetailsDAO payrollDetailsDAO;
    private ArrayList<PayrollDetailsDTO> payrollDetailList;
    
    public PayrollDetailsBUS() {
        payrollDetailsDAO = new PayrollDetailsDAO();
        payrollDetailList = new ArrayList<>();
        loadData();
    }
    
    public void loadData() {
        payrollDetailList = payrollDetailsDAO.selectAll();
    }
    
    public ArrayList<PayrollDetailsDTO> getPayrollDetailsByPayrollID(String payrollID) {
        return payrollDetailsDAO.selectAllByPayrollID(payrollID);
    }
    
    public ArrayList<PayrollDetailsDTO> getPayrollDetailList() {
        return payrollDetailList;
    }
    
    public boolean addPayrollDetail(PayrollDetailsDTO payrollDetail) {
        boolean result = payrollDetailsDAO.insert(payrollDetail);
        if (result) {
            payrollDetailList.add(payrollDetail);
        }
        return result;
    }
//    
//    public ArrayList<PayrollDetailsDTO> getPayrollDetailsByPayrollID(String payrollID) {
//        ArrayList<PayrollDetailsDTO> result = new ArrayList<>();
//        for (PayrollDetailsDTO detail : payrollDetailList) {
//            if (detail.getPayrollID().equals(payrollID)) {
//                result.add(detail);
//            }
//        }
//        return result;
//    }
}
