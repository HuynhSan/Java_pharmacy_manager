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
    public boolean insert(ProductBatchDTO batch) {
boolean result = false;

        if (myconnect.openConnection()) {
            try {
                String sql = "INSERT INTO product_batches( batch_id, product_id, manufacturing_date, expiration_date, recieved_quantity, inventory_quantity, sell_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
                // Sử dụng phương thức prepareUpdate để thực thi câu lệnh SQL
                int rowsAffected = myconnect.prepareUpdate(sql, 
                    batch.getBatchID(),
                    batch.getMedicineID(),
                    batch.getManufacturingDate(),
                    batch.getExpirationDate(),
                    batch.getQuantityReceived(),
                    batch.getQuantityInStock(),
                    batch.getSellPrice()
                );

                if (rowsAffected > 0) {
                    result = true;
                }
            }catch (Exception e) {
                System.err.println("SQL Error: " + e.getMessage()); // In lỗi cụ thể
                e.printStackTrace();
                return false;
            } finally {
                myconnect.closeConnection();
            }
        }

        return result;
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
                       
                       product.setBatchID(rs.getString("batch_id"));
                       product.setMedicineID(rs.getString("product_id"));
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
    public ProductBatchDTO selectByID(String ID) {
        if(myconnect.openConnection()){
            try {
                String sql = "SELECT pb.*, si.supplier_id FROM product_batches pb " + 
                            "JOIN supplier_invoice_details sid ON pb.batch_id = sid.batch_id " + 
                            "JOIN supplier_invoices si ON sid.supplier_invoice_id = si.supplier_invoice_id " +
                            "WHERE pb.product_id = ?";


                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, ID);

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
    public void updateSumQuantity(String productId, int quantity) {
    if (myconnect.openConnection()) {
        try {
            String sql = "UPDATE medical_products SET quantity = ? WHERE product_id = ?";
            myconnect.prepareUpdate(sql, quantity, productId);
            
        } finally {
            myconnect.closeConnection();
        }
    }
    }

    @Override
    public ArrayList<ProductBatchDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean updateBatchQuantity(String batchId, String productId, int quantity) {
        boolean isSuccess = false;
        if (myconnect.openConnection()) {
        String sql = "UPDATE product_batches SET inventory_quantity = inventory_quantity +  ? WHERE batch_id = ? AND product_id = ?";
        int result = myconnect.prepareUpdate(sql, quantity, batchId, productId);
            
        if (result > 0) {
                isSuccess = true; // thành công
            }
            myconnect.closeConnection(); // Đảm bảo đóng kết nối sau khi xong
        }
        return isSuccess;
    }
    
    public String getSupplierNameByBatchID(String batchID) {
        String supplierName = null;
        try {
            myconnect.openConnection();
            String sql = """
                SELECT s.name, s.supplier_id
                                FROM supplier_invoice_details sid
                                JOIN supplier_invoices si ON sid.supplier_invoice_id = si.supplier_invoice_id
                                JOIN suppliers s ON s.supplier_id = si.supplier_id 
                                WHERE sid.batch_id = ?
            """;

            PreparedStatement ps = myconnect.con.prepareStatement(sql);
            ps.setString(1, batchID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                supplierName = rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
        }

        return supplierName;
    }

}



