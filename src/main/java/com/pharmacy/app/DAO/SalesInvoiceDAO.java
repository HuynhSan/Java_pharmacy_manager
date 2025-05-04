/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SalesInvoiceDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceDAO implements DAOinterface<SalesInvoiceDTO>{
    MyConnection myconnect = new MyConnection();

    
    public String generateNewSalesInvoiceId() {
        String newId = "SALE001"; // mặc định nếu chưa có khuyến mãi nào
        if (myconnect.openConnection()) {
            String sql = "SELECT TOP 1 sales_invoice_id FROM sales_invoices ORDER BY sales_invoice_id DESC";
            try {
                ResultSet rs = myconnect.runQuery(sql);
                if (rs != null && rs.next()) {
                    String lastId = rs.getString("sales_invoice_id"); 
                    int num = Integer.parseInt(lastId.substring(4)) + 1;
                    newId = String.format("SALE%03d", num);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return newId;
    }

    @Override
    public boolean insert(SalesInvoiceDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public boolean update(SalesInvoiceDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SalesInvoiceDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SalesInvoiceDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SalesInvoiceDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
