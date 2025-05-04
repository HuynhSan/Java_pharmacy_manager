/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SalesInvoiceDetailDTO;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceDetailDAO implements DAOinterface<SalesInvoiceDetailDTO>{
    MyConnection myconnect = new MyConnection();

    @Override
    public boolean insert(SalesInvoiceDetailDTO t) {
        boolean isSuccess = false;

        if (myconnect.openConnection()) {
            String sql = "INSERT INTO sales_invoice_details (sales_invoice_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
            int result = myconnect.prepareUpdate(
                sql,
                t.getInvoiceId(),
                t.getProductId(),
                t.getQuantity(),
                t.getTotalPrice()
            );

            isSuccess = result > 0;
            myconnect.closeConnection();
        }

        return isSuccess;
    }

    @Override
    public boolean update(SalesInvoiceDetailDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SalesInvoiceDetailDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SalesInvoiceDetailDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SalesInvoiceDetailDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
