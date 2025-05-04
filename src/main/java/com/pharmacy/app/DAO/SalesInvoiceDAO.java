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
