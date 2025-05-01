/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.ProductBatchDAO;
import com.pharmacy.app.DTO.ProductBatchDTO;
import com.pharmacy.app.GUI.Product.MedicalProducts;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ProductBatchBUS {
    private ProductBatchDAO batchDAO = new ProductBatchDAO();
    private ProductBatchDTO batchDTO = new ProductBatchDTO();
    private MedicalProductsBUS productBUS = new MedicalProductsBUS();
    public ArrayList<ProductBatchDTO> getAllBatches(){
        try {
            return batchDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getDateforBatch(LocalDate exp){
        StringBuilder daysLeft = new StringBuilder();
        if (exp.isAfter(LocalDate.now())){
            Period p = Period.between(LocalDate.now(), exp);
                if (p.getYears() > 0) {
                daysLeft.append(p.getYears()).append(" năm ");
            }

            if (p.getMonths() > 0) {
                daysLeft.append(p.getMonths()).append(" tháng ");
            }

            if (p.getDays() > 0) {
                daysLeft.append(p.getDays()).append(" ngày");
            }
            return daysLeft.length() > 0 ? daysLeft.toString() : "0 ngày";
        }else return "Hết hạn sử dụng";
    }
    
    public boolean saleBatchQuantity(int quan){
        if(quan <= batchDTO.getQuantityInStock()){
            
            batchDTO.setQuantityInStock(batchDTO.getQuantityInStock() - quan);
            int newQuantity = batchDTO.getQuantityInStock();
            
            productBUS.saleQuantity(quan, batchDTO.getMedicineID());
            batchDAO.updateBatchQuantity(batchDTO.getMedicineID(), newQuantity);
            
            return true;
        }else return false;
    }
}
