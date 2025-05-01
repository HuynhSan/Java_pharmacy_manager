/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SupplierDAO;
import com.pharmacy.app.DTO.SupplierDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Giai Cuu Li San
 */
public class SupplierBUS {
        private SupplierDAO supplier = new SupplierDAO();
    
    public List<SupplierDTO> getAllSuppliers(){
        try{
            return supplier.getAllSuppliers();
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
