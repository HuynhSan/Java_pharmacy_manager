/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SalesInvoiceDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    
    public ArrayList<SalesInvoiceDTO> selectSaleInvoiceByCustomerID(String t) {
        ArrayList<SalesInvoiceDTO> saleInvoices = new ArrayList<>();
        if (myconnect.openConnection()){
            String sql = "SELECT sales_invoice_id, user_id, total_quantity, total_amount, sale_date "
                    + " FROM sales_invoices sai"
                    + " INNER JOIN customers c ON c.customer_id = sai.customer_id "
                    + " WHERE c.customer_id = ?";
            ResultSet rs = myconnect.prepareQuery(sql, t);
            try {
                while (rs.next()){
                    SalesInvoiceDTO saleInvoice = new SalesInvoiceDTO();
                    saleInvoice.setInvoiceId(rs.getString(1)); // id
                    saleInvoice.setUserId(rs.getString(2)); // name
                    saleInvoice.setTotalQuantity(rs.getInt(3)); // phone
                    saleInvoice.setFinalTotal(rs.getBigDecimal(4));
                    // Convert Timestamp to LocalDateTime
                    Timestamp timestamp = rs.getTimestamp(5);               // sale_date
                    if (timestamp != null) {
                        saleInvoice.setCreateDate(timestamp.toLocalDateTime());
                    }

                    saleInvoices.add(saleInvoice);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return saleInvoices;
    }
    @Override
    public boolean insert(SalesInvoiceDTO t) {
        boolean isSuccess = false;

        if (myconnect.openConnection()) {
            String sql = "INSERT INTO sales_invoices (sales_invoice_id, user_id, customer_id, total_quantity, total_amount, original_amount, discount_amount, sale_date)"
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Sử dụng prepareUpdate để thực hiện câu lệnh INSERT
            int result = myconnect.prepareUpdate(
                sql,
                t.getInvoiceId(),
                t.getUserId(),
                t.getCustomerId() != null && !t.getCustomerId().isEmpty()? t.getCustomerId() : null,
                t.getTotalQuantity(),
                t.getFinalTotal(),
                t.getTotalAmount(),
                t.getTotalDiscount(),
                Timestamp.valueOf(t.getCreateDate())   // Convert LocalDate sang java.sql.Date
                
            );

            // Kiểm tra kết quả và trả về true nếu thành công, false nếu thất bại
            if (result > 0) {
                isSuccess = true; // Insert thành công
            }

            myconnect.closeConnection(); // Đảm bảo đóng kết nối sau khi xong
        }

        return isSuccess;

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
        ArrayList<SalesInvoiceDTO> list = new ArrayList<>();
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM sales_invoices WHERE is_deleted = 0";
            
            ResultSet rs = myconnect.runQuery(sql);
            try{
                while (rs != null && rs.next()){
                    SalesInvoiceDTO invoice = new SalesInvoiceDTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getBigDecimal("original_amount"),
                        rs.getBigDecimal("discount_amount"),
                        rs.getBigDecimal("total_amount"),
                        rs.getTimestamp(8).toLocalDateTime()
                    );
                    list.add(invoice);    
                }
            } catch(Exception e){
                    e.printStackTrace();
            } finally{
                myconnect.closeConnection();
            }
        }
        return list;
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
