/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.itextpdf.text.pdf.BidiOrder;
import com.pharmacy.app.DAO.ProductBatchDAO;
import com.pharmacy.app.DTO.ProductBatchDTO;
import com.pharmacy.app.GUI.Product.MedicalProducts;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author LENOVO
 */
public class ProductBatchBUS {
    private ProductBatchDAO batchDAO = new ProductBatchDAO();
    private ProductBatchDTO batchDTO = new ProductBatchDTO();
    private MedicalProductsBUS productBUS = new MedicalProductsBUS();
    public ArrayList<ProductBatchDTO> getAllBatches(){
        SuplierInvoiceDetailsBUS supInvDeBUS = new SuplierInvoiceDetailsBUS();
        try {
            ArrayList<ProductBatchDTO> list = batchDAO.selectAll();
            for(ProductBatchDTO batch : list){
                String medName = productBUS.getMedicineNameByID(batch.getMedicineID());
                batch.setMedicineName(medName);
                String supName = batchDAO.getSupplierNameByBatchID(batch.getBatchID());
                batch.setSupplierName(supName);
            }
            return list;
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
    

    public boolean saleInventoryQuantity(String batchId, String productId, int quantity) {
        if (batchId == null || productId == null || quantity <= 0) {
            return false;
        }
        quantity = - quantity;
        return batchDAO.updateBatchQuantity(batchId, productId, quantity);
    }
    
    public boolean importInventoryQuantity(String batchId, String productId, int quantity, LocalDate manuDate, LocalDate expDate, double sellprice) {
        if (batchId == null || productId == null || quantity <= 0) {
            return false;
        }
            // Kiểm tra batch đã tồn tại chưa
        ProductBatchDTO existingBatch = getProductBatchByProductID(batchId);
        if (existingBatch == null) {
            System.out.println("🔄 Tạo mới lô hàng " + batchId);

            ProductBatchDTO newBatch = new ProductBatchDTO();
            newBatch.setBatchID(batchId);
            newBatch.setMedicineID(productId);
            newBatch.setQuantityReceived(quantity);
            newBatch.setQuantityInStock(quantity);
            newBatch.setExpirationDate(expDate);
            newBatch.setManufacturingDate(manuDate);
            newBatch.setSellPrice(sellprice);

            if (!batchDAO.insert(newBatch)) {
                System.err.println("❌ Không thể tạo lô hàng mới");
                return false;
            }
            return true;
        }
        return batchDAO.updateBatchQuantity(batchId, productId, quantity);
    }
    
    
   public ProductBatchDTO getProductBatchByProductID(String ID){
       ProductBatchDTO pd = batchDAO.selectByID(ID);
       return pd; 
   }
   
   public boolean addBatch(ProductBatchDTO batchDTO){
        if (!batchDAO.insert(batchDTO)){
            return false;
        }
       return true;
   }
   
   public ArrayList<ProductBatchDTO> searchAll(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(batchDAO.selectAll());
        }

        String lowerKeyword = keyword.toLowerCase();
        return batchDAO.selectAll().stream()
            .filter(batch ->
                matchesKeyword(batch.getBatchID(), lowerKeyword) ||
                matchesKeyword(batch.getMedicineID(), lowerKeyword) ||
                matchesKeyword(batch.getSupplierName(), lowerKeyword)
            )
            .collect(Collectors.toCollection(ArrayList::new));
    }
    private boolean matchesKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword.toLowerCase());
    }
    
   
}
