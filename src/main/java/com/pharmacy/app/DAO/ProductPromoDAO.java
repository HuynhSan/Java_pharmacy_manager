/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.ProductPromoDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ProductPromoDAO implements DAOinterface<ProductPromoDTO>{
    MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(ProductPromoDTO ppDTO) {
        boolean result = false;

        if (myconnect.openConnection()) {
            try {
                String sql = "INSERT INTO product_promotion(product_id, promo_id) VALUES (?, ?)";

                // Corrected parameter order
                int rowsAffected = myconnect.prepareUpdate(sql, 
                    ppDTO.getProductID(),
                    ppDTO.getPromoID()
                );

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
    public boolean update(ProductPromoDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductPromoDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductPromoDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProductPromoDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ProductPromoDTO selectPromoByProductID(String productID){
        if(myconnect.openConnection()){
            try {
                String sql = "SELECT * FROM product_promotion WHERE productID = ? ";

                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, productID);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    ProductBatchDTO pdBatch = new ProductBatchDTO();

                    pdBatch.setMedicineID(rs.getString("product_id"));
                    pdBatch.setBatchID(rs.getString("batch_id"));
                    pdBatch.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                    pdBatch.setManufacturingDate(rs.getDate("manufacturing_date").toLocalDate());
                    pdBatch.setQuantityInStock(rs.getInt("inventory_quantity"));
                    pdBatch.setQuantityReceived(rs.getInt("recieved_quantity"));
                    pdBatch.setSellPrice(rs.getDouble("sell_price"));
                    pdBatch.setStatus(rs.getBoolean("is_deleted"));

                    return pdBatch;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return null; 
    }
    
    public ProductPromoDTO selectProductByPromoID(String promoID){
        return null;
    }
    
}

