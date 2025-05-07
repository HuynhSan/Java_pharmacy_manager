/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SuplierInvoiceDTO;
import com.pharmacy.app.DTO.SuplierInvoiceDetailsDTO;
import java.sql.Date;
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
            String sql = "SELECT si.supplier_invoice_id, SUM(sid.quantity), SUM(sid.quantity * unit_price) AS total_price, si.supplier_id, si.purchase_date, manager_user_id "
                    + " FROM supplier_invoices si"
                    + " INNER JOIN purchase_orders po ON po.po_id = si.po_id"
                    + " INNER JOIN supplier_invoice_details sid ON sid.supplier_invoice_id = si.supplier_invoice_id"
                    + " WHERE si.is_deleted = 0"
                    + " GROUP BY si.supplier_invoice_id, si.supplier_id, si.purchase_date, manager_user_id ";
            ResultSet rs = myconnect.runQuery(sql);

            try {
                while (rs.next()) {
                    SuplierInvoiceDTO supInvoice = new SuplierInvoiceDTO();
                    supInvoice.setInvoiceID(rs.getString(1));
                    supInvoice.setTotalQuantity(rs.getInt(2));
                    supInvoice.setTotalPrice(rs.getBigDecimal(3));
                    supInvoice.setSupplierID(rs.getString(4));
                    LocalDate purchaseDate = rs.getDate(5).toLocalDate();
                    supInvoice.setPurchaseDate(purchaseDate);
                    supInvoice.setManagerID(rs.getString(6));

                    supInvoices.add(supInvoice);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        } else {
            System.out.println("Khong the ket noi voi csdl");
        }
        return supInvoices;
    }

    
    @Override
    public SuplierInvoiceDTO selectByID(String t) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        SuplierInvoiceDTO supInvoice = null;
        if (myconnect.openConnection()){
            String query = "SELECT si.supplier_invoice_id, sp.name, si.purchase_date, manager_user_id, u.username "
                    + " FROM supplier_invoices si"
                    + " INNER JOIN suppliers sp ON si.supplier_id = sp.supplier_id"
                    + " INNER JOIN purchase_orders po ON po.po_id = si.po_id"
                    + " INNER JOIN users u ON u.user_id = po.manager_user_id"
                    + " WHERE si.supplier_invoice_id = ?";
            ResultSet rs = myconnect.prepareQuery(query, t);
            try {
                while(rs.next()){
                    supInvoice = new SuplierInvoiceDTO();
                    supInvoice.setInvoiceID(rs.getString(1));
                    supInvoice.setSupplierName(rs.getString(2));
                    LocalDate purchaseDate = rs.getDate(3).toLocalDate();
                    supInvoice.setPurchaseDate(purchaseDate);
                    supInvoice.setManagerID(rs.getString(4));
                    supInvoice.setManagerName(rs.getString(5));

                    
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

    public ArrayList<SuplierInvoiceDTO> selectBySupplierID(String t) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<SuplierInvoiceDTO> supInvoices = new ArrayList<>();
        if (myconnect.openConnection()){
            String query = "SELECT si.supplier_invoice_id, SUM(sids.quantity), si.purchase_date"
                    + " FROM supplier_invoices si"
                    + " INNER JOIN suppliers sp ON si.supplier_id = sp.supplier_id"
                    + " INNER JOIN supplier_invoice_details sids ON sids.supplier_invoice_id = si.supplier_invoice_id"
                    + " WHERE si.supplier_id = ?"
                    + " GROUP BY si.supplier_invoice_id, si.purchase_date";
            ResultSet rs = myconnect.prepareQuery(query, t);
            try {
                while(rs.next()){
                    SuplierInvoiceDTO supInvoice = new SuplierInvoiceDTO();
                    supInvoice.setInvoiceID(rs.getString(1));
                    supInvoice.setTotalQuantity(rs.getInt(2));
                    LocalDate purchaseDate = rs.getDate(3).toLocalDate();
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
    @Override
    public ArrayList<SuplierInvoiceDTO> search(String t) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ArrayList<SuplierInvoiceDTO> supInvoices = new ArrayList<>();
        if (myconnect.openConnection()){
            String query = "SELECT si.supplier_invoice_id, SUM(sids.quantity), total_price, si.supplier_id, purchase_date, po.manager_user_id "
                    + " FROM supplier_invoices si"
                    + " INNER JOIN supplier_invoice_details sids ON sids.supplier_invoice_id = si.supplier_invoice_id"
                    + " INNER JOIN purchase_orders po ON po.po_id = si.po_id"
                    + " INNER JOIN users u ON u.user_id = po.manager_user_id"
                    + " WHERE LOWER(si.supplier_invoice_id) LIKE ? "
                    + " OR LOWER(si.supplier_id) LIKE ? "
                    + " OR LOWER(po.manager_user_id) LIKE ?"
                    + " GROUP BY si.supplier_invoice_id, total_price, si.supplier_id, purchase_date, po.manager_user_id";
            ResultSet rs = myconnect.prepareQuery(query, "%" + t.toLowerCase() + "%", "%" + t.toLowerCase() + "%", "%" + t.toLowerCase() + "%");
            
            try {
                while(rs.next()){
                    SuplierInvoiceDTO supInvoice = new SuplierInvoiceDTO();
                    supInvoice.setInvoiceID(rs.getString(1));
                    supInvoice.setTotalQuantity(rs.getInt(2));
                    supInvoice.setTotalPrice(rs.getBigDecimal(3));
                    supInvoice.setSupplierID(rs.getString(4));
                    LocalDate purchaseDate = rs.getDate(5).toLocalDate();
                    supInvoice.setPurchaseDate(purchaseDate);
                    supInvoice.setManagerID(rs.getString(6));
                    
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
    
//    public ArrayList<SuplierInvoiceDTO> filterByDate(LocalDate date) {
//        ArrayList<SuplierInvoiceDTO> result = new ArrayList<>();
//        if (myconnect.openConnection()) {
//            String query = "SELECT si.supplier_invoice_id, SUM(sids.quantity), total_price, si.supplier_id, purchase_date, po.manager_user_id "
//                         + " FROM supplier_invoices si"
//                         + " INNER JOIN supplier_invoice_details sids ON sids.supplier_invoice_id = si.supplier_invoice_id"
//                         + " INNER JOIN purchase_orders po ON po.po_id = si.po_id"
//                         + " WHERE purchase_date >= ? AND purchase_date < ?"
//                         + " GROUP BY si.supplier_invoice_id, total_price, si.supplier_id, purchase_date, po.manager_user_id";
//
//            try {
//                // Truyền vào khoảng thời gian trong ngày
//                LocalDate nextDay = date.plusDays(1);
//                ResultSet rs = myconnect.prepareQuery(query, Date.valueOf(date), Date.valueOf(nextDay));
//
//                while (rs.next()) {
//                    SuplierInvoiceDTO invoice = new SuplierInvoiceDTO();
//                    invoice.setInvoiceID(rs.getString(1));
//                    invoice.setTotalQuantity(rs.getInt(2));
//                    invoice.setTotalPrice(rs.getDouble(3));
//                    invoice.setSupplierID(rs.getString(4));
//                    invoice.setPurchaseDate(rs.getDate(5).toLocalDate());
//                    invoice.setManagerID(rs.getString(6));
//                    result.add(invoice);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } finally {
//                myconnect.closeConnection();
//            }
//        }
//        return result;
//    }
}