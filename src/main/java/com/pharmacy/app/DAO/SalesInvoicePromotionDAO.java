/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SalesInvoicePromotionDTO;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoicePromotionDAO implements DAOinterface<SalesInvoicePromotionDTO>{
    MyConnection myconnect = new MyConnection();
    
    @Override
    public boolean insert(SalesInvoicePromotionDTO t) {
        boolean isSuccess = false;
        if (myconnect.openConnection()) {
            String sql = "INSERT INTO sales_invoice_promotion (sales_invoice_id, promo_id) "
                       + "VALUES (?, ?)";
            
            // Sử dụng prepareUpdate để thực hiện câu lệnh INSERT
            int result = myconnect.prepareUpdate(
                sql,
                t.getInvoiceId(),
                t.getPromoId()// Convert LocalDate sang java.sql.Date
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
    public boolean update(SalesInvoicePromotionDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SalesInvoicePromotionDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SalesInvoicePromotionDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SalesInvoicePromotionDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
