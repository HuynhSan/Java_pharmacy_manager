/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.PurchaseOrderDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class PurchaseOrderDAO implements DAOinterface<PurchaseOrderDTO>{
     MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(PurchaseOrderDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(PurchaseOrderDTO po) {
        boolean result = false;

        try {
            myconnect.openConnection();

            String sql = "UPDATE purchase_orders SET status = ? WHERE po_id = ?";

            int rowsAffected = myconnect.prepareUpdate(sql, 
                po.getStatus(),
                po.getPoID()
            );
            if (rowsAffected > 0) {
                result = true;
            } else {
                System.err.println("Không có dòng nào được cập nhật. Có thể product_id không đúng hoặc không thay đổi dữ liệu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
        }

        return result;
    }

    @Override
    public boolean delete(String ID) {
        boolean result = false;

            try {
                myconnect.openConnection();

                String sql = "DELETE FROM purchase_orders WHERE po_id = ?";

                int rowsAffected = myconnect.prepareUpdate(sql, ID);

                if (rowsAffected > 0) {
                    result = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Đảm bảo đóng kết nối
                myconnect.closeConnection();
            }

            return result;
    }

    @Override
    public ArrayList<PurchaseOrderDTO> selectAll() {
        ArrayList<PurchaseOrderDTO> poList = new ArrayList<PurchaseOrderDTO>();
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM purchase_orders WHERE is_deleted = 0";
            ResultSet rs = myconnect.runQuery(sql);
            
            try {
                while(rs.next()){
                       PurchaseOrderDTO po = new PurchaseOrderDTO(
                       rs.getString(1),
                       rs.getString("manager_user_id"),
                       rs.getString("supplier_id"),
                       rs.getDate("order_date").toLocalDate(),
                       rs.getString("status"),
                       rs.getString("admin_id")
                    );
                       poList.add(po);
                }
            } catch (SQLException ex) {
                System.out.println("SQLException occurred: " + ex.getMessage());
                ex.printStackTrace();
            }finally{
                myconnect.closeConnection();
                    }
        }
        
        return poList;
    }

    @Override
    public PurchaseOrderDTO selectByID(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<PurchaseOrderDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String getLatestProductID() {
        String latestID = null;
        if (myconnect.openConnection()) {
            try {
                String sql = "SELECT MAX(po_id) AS max_id FROM purchase_orders";
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
    
}
