/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SuplierInvoiceDetailsDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BOI QUAN
 */
public class SupplierInvoiceDetailsDAO implements DAOinterface<SuplierInvoiceDetailsDTO> {
    MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(SuplierInvoiceDetailsDTO supInvDe) {
        boolean result = false;

        if (myconnect.openConnection()) {
            try {
                String sql = "INSERT INTO supplier_invoice_details(supplier_invoice_id, batch_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
                    // Enhanced logging before execution
                System.out.println("Executing SQL: " + sql);
                System.out.println("With parameters: ");
                System.out.println(" - invoiceID: " + supInvDe.getInvoiceID());
                System.out.println(" - batchID: " + supInvDe.getBatchID());
                System.out.println(" - quantity: " + supInvDe.getQuantity());
                System.out.println(" - unitPrice: " + supInvDe.getUnitPrice());

                // Corrected parameter order
                int rowsAffected = myconnect.prepareUpdate(sql, 
                    supInvDe.getInvoiceID(),
                    supInvDe.getBatchID(),
                    supInvDe.getQuantity(),     // Now matches quantity in SQL
                    supInvDe.getUnitPrice()     // Now matches unit_price in SQL
                );

                System.out.println("Rows affected: " + rowsAffected);
                if (rowsAffected > 0) {
                    result = true;
                }
            } catch (Exception e) {
                System.err.println("SQL Insert Error:");
                e.printStackTrace();
                System.err.println("Full error details: " + e);

                // Log SQL state if available (helps identify constraint violations)
                if (e instanceof SQLException) {
                    SQLException sqlEx = (SQLException)e;
                    System.err.println("SQL State: " + sqlEx.getSQLState());
                    System.err.println("Error Code: " + sqlEx.getErrorCode());
                }
            } finally {
                myconnect.closeConnection();
            }
        }

        return result;
    }

    @Override
    public boolean update(SuplierInvoiceDetailsDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SuplierInvoiceDetailsDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
//    Lay thong tin cua phieu nhap
    public ArrayList<SuplierInvoiceDetailsDTO> selectInvoiceDetailByID(String t) {
        ArrayList<SuplierInvoiceDetailsDTO> supInvoiceDetails = new ArrayList<>();
        if(myconnect.openConnection()){
            String query = "SELECT pb.batch_id, mp.product_id, name, unit_price, sid.quantity, (sid.quantity * unit_price) AS total_price"
                    + " FROM supplier_invoice_details sid"
                    + " INNER JOIN product_batches pb ON sid.batch_id = pb.batch_id"
                    + " INNER JOIN medical_products mp ON pb.product_id = mp.product_id"
                    + " WHERE sid.supplier_invoice_id = ?";
            ResultSet rs = myconnect.prepareQuery(query, t);
            try {
                while(rs != null && rs.next()){
                    SuplierInvoiceDetailsDTO supInvoiceDetail = new SuplierInvoiceDetailsDTO();
                    supInvoiceDetail.setBatchID(rs.getString(1));
                    supInvoiceDetail.setProductID(rs.getString(2));
                    supInvoiceDetail.setName(rs.getString(3));
                    supInvoiceDetail.setUnitPrice(rs.getBigDecimal(4));
                    supInvoiceDetail.setQuantity(rs.getInt(5));
                    supInvoiceDetail.setTotalPrice(rs.getBigDecimal(6));
                    
                    supInvoiceDetails.add(supInvoiceDetail);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return supInvoiceDetails;
    }
    @Override
    public SuplierInvoiceDetailsDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SuplierInvoiceDetailsDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
