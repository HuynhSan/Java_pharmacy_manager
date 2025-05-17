/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.MedicalProductsDTO;
import com.pharmacy.app.DTO.PurchaseOrderDetailsDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class PurchaseOrderDetailsDAO implements DAOinterface<PurchaseOrderDetailsDTO>{
    MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(PurchaseOrderDetailsDTO poDeDTO) {
        boolean result = false;

        if (myconnect.openConnection()) {
            try {
                String sql = "INSERT INTO purchase_order_details(po_id, product_id, quantity) VALUES (?, ?, ?)";
                    // Enhanced logging before execution
                System.out.println("Executing SQL: " + sql);
                System.out.println("With parameters: ");
                System.out.println(" - PoID: " + poDeDTO.getPoID());
                System.out.println(" - ProductID: " + poDeDTO.getProductID());
                System.out.println(" - quantity: " + poDeDTO.getQuantity());

                // Corrected parameter order
                int rowsAffected = myconnect.prepareUpdate(sql, 
                    poDeDTO.getPoID(),
                    poDeDTO.getProductID(),
                    poDeDTO.getQuantity()  
                );

                System.out.println("Rows affected: " + rowsAffected);
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
    public boolean update(PurchaseOrderDetailsDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    @Override
    public ArrayList<PurchaseOrderDetailsDTO> selectAll(){
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<PurchaseOrderDetailsDTO> selectAllByID(String ID) {
       ArrayList<PurchaseOrderDetailsDTO> poDeList = new ArrayList<PurchaseOrderDetailsDTO>();
        if (myconnect.openConnection()){
            try {
                String sql = "SELECT * FROM purchase_order_details pod JOIN medical_products mp ON pod.product_id = mp.product_id  WHERE po_id = ? ";
                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, ID);
                ResultSet rs = ps.executeQuery();
                
                try {
                    while(rs.next()){
                        PurchaseOrderDetailsDTO po = new PurchaseOrderDetailsDTO(
                                rs.getString("po_id"),
                                rs.getString("product_id"),
                                rs.getInt("quantity")
                        );
                        po.setProductName(rs.getString("name"));
                        poDeList.add(po);
                    }
                } catch (SQLException ex) {
                    System.out.println("SQLException occurred: " + ex.getMessage());
                    ex.printStackTrace();
                }finally{
                    myconnect.closeConnection();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseOrderDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return poDeList;
        
    }

    @Override
    public PurchaseOrderDetailsDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<PurchaseOrderDetailsDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
