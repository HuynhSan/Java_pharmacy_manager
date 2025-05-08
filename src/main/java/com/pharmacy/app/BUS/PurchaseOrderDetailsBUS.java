/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.PurchaseOrderDetailsDAO;
import com.pharmacy.app.DTO.PurchaseOrderDetailsDTO;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class PurchaseOrderDetailsBUS {
    PurchaseOrderDetailsDAO poDetailsDAO = new PurchaseOrderDetailsDAO();
    
    public ArrayList<PurchaseOrderDetailsDTO> getAllPOdetails(String ID){
        try{
            return poDetailsDAO.selectAllByID(ID);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean addPOde(PurchaseOrderDetailsDTO newpoDe){
        System.out.println("➡️ Thêm chi tiết: " + newpoDe.getPoID()+ " - " + newpoDe.getProductID());
        if (!poDetailsDAO.insert(newpoDe)) {
            System.err.println("❌ Không insert được chi tiết phiếu nhập!");
            return false;
        }
        System.out.println("✅ Insert chi tiết thành công");
        return true;
    }
    
}
