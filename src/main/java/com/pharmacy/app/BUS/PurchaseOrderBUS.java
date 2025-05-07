/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.PurchaseOrderDAO;
import com.pharmacy.app.DTO.PurchaseOrderDTO;
import com.pharmacy.app.DTO.PurchaseOrderDetailsDTO;
import com.pharmacy.app.DTO.SessionDTO;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class PurchaseOrderBUS {
    private PurchaseOrderDAO poDAO = new PurchaseOrderDAO();
    private PurchaseOrderDTO poDTO;
    private PurchaseOrderDetailsBUS POdeBUS = new PurchaseOrderDetailsBUS();
    
    public ArrayList<PurchaseOrderDTO> getAllPO(){
        try{
            return poDAO.selectAll();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean deletePO(String ID){
        return poDAO.delete(ID);
    }
    
    public void approvePO(String ID){
        for (PurchaseOrderDTO po : getAllPO()) {
            if (po.getPoID().equals(ID)) {
                poDTO = new PurchaseOrderDTO(
                        po.getPoID(),
                        po.getManagerUserID(),
                        po.getSupplierID(), 
                        po.getOrderDate(), 
                        "Đã duyệt", 
                        SessionDTO.getCurrentUser().getUserID());
            }
        }
        poDAO.update(poDTO);
    }
    
    public PurchaseOrderDTO getPOByID(String ID) {
        for (PurchaseOrderDTO po : getAllPO()) {
            if (po.getPoID().equals(ID)) {
                return po;
            }
        }
        return poDAO.selectByID(ID);
    }
    
    public String generateNextProductID() {
        String lastID = poDAO.getLatestProductID();  // Có thể là null

        if (lastID == null || lastID.isEmpty()) {
            return "PO001"; // Mặc định nếu bảng rỗng
        }

        String numericPart = lastID.substring(2); // Bỏ "PO", lấy số
        int lastNumber = Integer.parseInt(numericPart);
        int nextNumber = lastNumber + 1;

        return "PO" + String.format("%03d", nextNumber);
    }
    
     public boolean addPO(PurchaseOrderDTO newPO){
         if (!poDAO.insert(newPO)){
            return false;
        }
        
        // thêm ctpn
        for(PurchaseOrderDetailsDTO detail : newPO.getPoDetails()){
            System.out.println("Chi tiết đơn đặt: " + newPO.getPoDetails());
            System.out.println("Số dòng chi tiết: " + (newPO.getPoDetails() == null ? "null" : newPO.getPoDetails().size()));

            if (!POdeBUS.addPOde(detail)){
                return false;
            }
        }
        return true;
    }
}

