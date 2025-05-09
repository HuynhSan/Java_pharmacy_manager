/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SaleItemDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesDAO implements DAOinterface{
    MyConnection myconnect = new MyConnection();


    @Override
    public boolean insert(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    public ArrayList<SaleItemDTO> selectAllSaleItems() {
        ArrayList<SaleItemDTO> saleItems = new ArrayList<>();
        
        if (myconnect.openConnection()) {
            String sql = "SELECT * FROM v_product_batch_discount_summary";
            
            ResultSet rs = myconnect.runQuery(sql);
            
            try {
                while (rs != null && rs.next()) {
                    SaleItemDTO saleItem = new SaleItemDTO(
                        rs.getString(1),  // Lấy giá trị batch_id từ cột trong kết quả
                        rs.getString(2), // Lấy giá trị product_id
                        rs.getString(3),    // Lấy tên sản phẩm
                        rs.getString(4),    // Lấy đơn vị sản phẩm
                        rs.getBigDecimal(5),  // Lấy giá bán
                        rs.getDate(6).toLocalDate(),  // Lấy ngày hết hạn
                        rs.getInt(7),  // Lấy số lượng tồn kho
                        rs.getFloat(8), // Lấy tỷ lệ giảm giá
                        rs.getBigDecimal(9),  // Lấy số tiền giảm giá
                        rs.getBigDecimal(10),   // Lấy giá cuối sau giảm giá
                        rs.getString(11)
                    );
                    saleItems.add(saleItem); // Thêm vào danh sách
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection(); // Đảm bảo đóng kết nối
            }
        }
        return saleItems; // Trả về danh sách các sản phẩm
    }
    
    
}
