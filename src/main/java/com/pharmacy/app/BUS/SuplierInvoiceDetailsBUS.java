/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SupplierInvoiceDetailsDAO;
import com.pharmacy.app.DTO.SuplierInvoiceDTO;
import com.pharmacy.app.DTO.SuplierInvoiceDetailsDTO;
import java.util.ArrayList;

/**
 *
 * @author BOI QUAN
 */
public class SuplierInvoiceDetailsBUS {
    private SupplierInvoiceDetailsDAO supInvoiceDetailDAO;
    private ProductBatchBUS pbBUS = new ProductBatchBUS();
    public SuplierInvoiceDetailsBUS(){
        supInvoiceDetailDAO = new SupplierInvoiceDetailsDAO();
    }
    
    public ArrayList<SuplierInvoiceDetailsDTO> getHistoryByID (String invoiceID){
        return supInvoiceDetailDAO.selectInvoiceDetailByID(invoiceID);
    }
    
//    public boolean addSupInvDe(SuplierInvoiceDetailsDTO supInvDe){     
//        if(!supInvoiceDetailDAO.insert(supInvDe)){
//            return false;
//        }
//        System.out.println("addSupInvDe");
//        // cập nhật batch mới
//        boolean isStockUpdate = pbBUS.updateInventoryQuantity(
//                supInvDe.getBatchID(),
//                supInvDe.getProductID(),
//                supInvDe.getQuantity(),
//                true);
//        return isStockUpdate;
//    }
    public boolean addSupInvDe(SuplierInvoiceDetailsDTO supInvDe){     
        System.out.println("➡️ Thêm chi tiết: " + supInvDe.getBatchID() + " - " + supInvDe.getProductID());
        
        boolean isStockUpdate = pbBUS.importInventoryQuantity(
                supInvDe.getBatchID(),
                supInvDe.getProductID(),
                supInvDe.getQuantity(),
                supInvDe.getManuDate(),
                supInvDe.getExpDate(),
                supInvDe.getSellPrice()
        );

        if (!isStockUpdate) {
            System.err.println("❌ Cập nhật kho thất bại!");
        }
        
        if (!supInvoiceDetailDAO.insert(supInvDe)) {
            System.err.println("❌ Không insert được chi tiết phiếu nhập!");
            return false;
        }

        System.out.println("✅ Insert chi tiết thành công");


        return isStockUpdate;
    }
    

}
