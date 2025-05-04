/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SuplierInvoiceDTO;
import com.pharmacy.app.DTO.SuplierInvoiceDetailsDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author BOI QUAN
 */
public class SupplierInvoicesDAO implements DAOinterface<SuplierInvoiceDTO> {
    MyConnection myconnect = new MyConnection();
    private String searchField = "all";
    
    public void setSearchField(String field){
        this.searchField = searchField;
    }
    
    @Override
    public boolean insert(SuplierInvoiceDTO t){
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(SuplierInvoiceDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SuplierInvoiceDTO> selectAll() {
        ArrayList<SuplierInvoiceDTO> supInvoices = new ArrayList<>();
        if (myconnect.openConnection()){
            String sql = "SELECT supplier_invoice_id, total_quantity, total_price, supplier_id, purchase_date FROM supplier_invoices WHERE is_deleted = 0";
            ResultSet rs = myconnect.runQuery(sql);
            try {
                while (rs != null && rs.next()) {
                    SuplierInvoiceDTO supInvoice = new SuplierInvoiceDTO();
                    supInvoice.setInvoiceID(rs.getString(1));
                    supInvoice.setTotalQuantity(rs.getInt(2));
                    supInvoice.setTotalPrice(rs.getDouble(3));
                    supInvoice.setSupplierID(rs.getString(4));
                    LocalDate purchaseDate = rs.getDate(5).toLocalDate();
                    supInvoice.setPurchaseDate(purchaseDate);

                    supInvoices.add(supInvoice);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return supInvoices;
    }

    
    @Override
    public SuplierInvoiceDTO selectByID(String t) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        SuplierInvoiceDTO supInvoice = null;
        if (myconnect.openConnection()){
            String query = "SELECT si.supplier_invoice_id, sp.name, si.purchase_date "
                    + " FROM supplier_invoices si"
                    + " INNER JOIN suppliers sp ON si.supplier_id = sp.supplier_id"
                    + " WHERE si.supplier_invoice_id = ?";
            ResultSet rs = myconnect.prepareQuery(query, t);
            try {
                while(rs.next()){
                    supInvoice = new SuplierInvoiceDTO();
                    supInvoice.setInvoiceID(rs.getString(1));
                    supInvoice.setSupplierName(rs.getString(2));
                    LocalDate purchaseDate = rs.getDate(3).toLocalDate();
                    supInvoice.setPurchaseDate(purchaseDate);
                    
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return supInvoice;
    }
    
    public String getLatestProductID(){
        String latestID = null;
        if (myconnect.openConnection()) {
            try {
                String sql = "SELECT MAX(supplier_invoice_id) AS max_id FROM supplier_invoices";
                ResultSet rs = myconnect.runQuery(sql);
                if (rs.next()) {
                    latestID = rs.getString("max_id"); // Có thể là null nếu chưa có sản phẩm
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return latestID;
    }
    
    @Override
    public ArrayList<SuplierInvoiceDTO> search(String t) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<SuplierInvoiceDTO> supInvoices = new ArrayList<>();
        if (myconnect.openConnection()){
            String query = "SELECT supplier_invoice_id, total_quantity, total_price, supplier_id, purchase_date FROM supplier_invoices ";
            ResultSet rs = null;
            
            try {
                switch(searchField) {
                    case "siId":
                        query += "WHERE LOWER(supplier_invoice_id) LIKE ?";
                        rs = myconnect.prepareQuery(query, "%" + t + "%");
                        break;
                    case "date":
                        query += "WHERE purchase_date LIKE ?";
                        rs = myconnect.prepareQuery(query, "%" + t + "%");
                        break;
                    case "sup":
                        query += "WHERE LOWER(supplier_id) LIKE ?";
                        rs = myconnect.prepareQuery(query, "%" + t + "%");
                        break;
                    default:
                        query += "WHERE LOWER(supplier_invoice_id) LIKE ? OR purchase_date LIKE ? OR LOWER(supplier_id) LIKE ?";
                        rs = myconnect.prepareQuery(query, "%" + t + "%", "%" + t + "%", "%" + t + "%");
                        break;
                }
                while(rs.next()){
                    SuplierInvoiceDTO supInvoice = new SuplierInvoiceDTO();
                    supInvoice.setInvoiceID(rs.getString(1));
                    supInvoice.setTotalQuantity(rs.getInt(2));
                    supInvoice.setTotalPrice(rs.getDouble(3));
                    supInvoice.setSupplierID(rs.getString(4));
                    LocalDate purchaseDate = rs.getDate(5).toLocalDate();
                    supInvoice.setPurchaseDate(purchaseDate);
                    
                    supInvoices.add(supInvoice);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return supInvoices;
    }
}