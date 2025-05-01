/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.MedicalProductsDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class MedicalProductsDAO implements DAOinterface<MedicalProductsDTO>{
    MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(MedicalProductsDTO product) {
        boolean result = false;
        
        if(myconnect.openConnection()){
            try{
            String sql = "INSERT INTO medical_products(product_id, name, category_id, description, unit, quantity, packing_specification) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = (PreparedStatement) myconnect.runQuery(sql);
            
            ps.setString(1, product.getMedicineID());
            ps.setString(2, product.getName());
            ps.setString(3, product.getCategory());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getUnit());
            ps.setInt(6, product.getQuantity());
            ps.setString(7, product.getPackingSpecification());
            
            if(ps.executeUpdate()>0){
                result = true;
            }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                myconnect.closeConnection();
            }
        }
        
        return result;
    }

    @Override
    public boolean update(MedicalProductsDTO product) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<MedicalProductsDTO> selectAll() {
        ArrayList<MedicalProductsDTO> medicineList = new ArrayList<MedicalProductsDTO>();
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM medical_products WHERE is_deleted = 0";
            ResultSet rs = myconnect.runQuery(sql);
            
            try {
                while(rs.next()){
                       MedicalProductsDTO product = new MedicalProductsDTO(
                       rs.getString(1),
                       rs.getString(2),
                       rs.getString(3),
                       rs.getString(4),
                       rs.getString(5),
                       rs.getInt(6),
                       rs.getString(7),
                       rs.getBoolean(8)
                    );
                       medicineList.add(product);
                }
            } catch (SQLException ex) {
                System.out.println("SQLException occurred: " + ex.getMessage());
                ex.printStackTrace();
            }finally{
                myconnect.closeConnection();
                    }
        }
        
        return medicineList;
    }

    @Override
    public MedicalProductsDTO selectByID(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<MedicalProductsDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
        
    public MedicalProductsDTO getProductbyID(String product_ID){
        if(myconnect.openConnection()){
            try {
                String sql = "SELECT p.*, c.category_name FROM medical_products p " + 
                             "JOIN categories c ON p.category_id = c.category_id " + 
                             "WHERE p.product_id = ?";

                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, product_ID);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    MedicalProductsDTO product = new MedicalProductsDTO();

                    product.setMedicineID(rs.getString("product_id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category_name")); 
                    product.setDescription(rs.getString("description"));
                    product.setPackingSpecification(rs.getString("packing_specification"));
                    product.setUnit(rs.getString("unit"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setStatus(rs.getBoolean("is_deleted"));

                    return product;
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

    
}
