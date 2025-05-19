/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.PayrollDAO;
import com.pharmacy.app.DTO.PayrollDTO;

/**
 *
 * @author BOI QUAN
 */
public class PayrollBUS {
    PayrollDAO dao = new PayrollDAO();
    PayrollDTO dto;
    
    public PayrollDTO getPayrollByID(String employeeID){
        return dao.selectByID(employeeID);
    }
    
}
