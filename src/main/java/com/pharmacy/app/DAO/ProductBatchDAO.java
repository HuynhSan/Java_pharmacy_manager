/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.BUS.MedicalProductsBUS;
import com.pharmacy.app.DTO.ProductBatchDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ProductBatchDAO implements DAOinterface<ProductBatchDTO>{
    MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(ProductBatchDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(ProductBatchDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductBatchDTO> selectAll() {
        ArrayList<ProductBatchDTO> batchList = new ArrayList<>();
        try {
            myconnect.openConnection();
            
            String sql = "SELECT * FROM product_batches WHERE is_deleted = 0";
            PreparedStatement ps = myconnect.con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
             
            while(rs.next()){
                       ProductBatchDTO product = new ProductBatchDTO();
                       MedicalProductsBUS pd_name = new MedicalProductsBUS();
                       String name = pd_name.getMedicineNameByID(rs.getString("product_id"));
                       
                       product.setBatchID(rs.getString("batch_id"));
                       product.setMedicineID(name);
                       product.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                       product.setManufacturingDate(rs.getDate("manufacturing_date").toLocalDate());
                       product.setQuantityInStock(rs.getInt("inventory_quantity"));
                       product.setQuantityReceived(rs.getInt("recieved_quantity"));
                       product.setSellPrice(rs.getDouble("sell_price"));
                       product.setStatus(rs.getBoolean("is_deleted"));
                       
                       batchList.add(product);
                }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }finally{
            myconnect.closeConnection();
        }
        return batchList;
    }

    @Override
    public ProductBatchDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductBatchDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void updateBatchQuantity(String productId, int quantity) {
    if (myconnect.openConnection()) {
        try {
            String sql = "UPDATE product_batches SET inventory_quantity = ? WHERE product_id = ?";
            myconnect.prepareUpdate(sql, quantity, productId);
            
        } finally {
            myconnect.closeConnection();
        }
    }
}

}


