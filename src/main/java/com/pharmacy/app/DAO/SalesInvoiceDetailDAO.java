/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SalesInvoiceDetailDTO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<SalesInvoiceDetailDTO> selectInvoiceByID(String invoiceId) {
        ArrayList<SalesInvoiceDetailDTO> list = new ArrayList<>();
        
        if (myconnect.openConnection()){
            String sql = "SELECT *FROM sales_invoice_details WHERE sales_invoice_id = ?";
            ResultSet rs = myconnect.runPreparedQuery(sql, invoiceId);
            try {
                while (rs.next()){
                    SalesInvoiceDetailDTO detail = new SalesInvoiceDetailDTO(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getBigDecimal(4)
                    );
                    list.add(detail);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return list;
    }
    
}
