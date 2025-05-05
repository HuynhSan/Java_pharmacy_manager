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
    public boolean insert(PurchaseOrderDetailsDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
                String sql = "SELECT * FROM purchase_order_details WHERE po_id = ?";
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
