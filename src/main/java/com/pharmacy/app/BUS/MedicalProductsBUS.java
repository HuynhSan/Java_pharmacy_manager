/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.MedicalProductsDAO;
import com.pharmacy.app.DTO.MedicalProductsDTO;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author LENOVO
 */
public class MedicalProductsBUS {
    private MedicalProductsDAO productDAO = new MedicalProductsDAO();
    
    public ArrayList<MedicalProductsDTO> getAllProducts (){
        try{
            return productDAO.selectAll();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public String getMedicineNameByID(String medicineID) {
        try {
            return productDAO.selectByID(medicineID).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    
}
    public MedicalProductsDTO getProductByID(String productID) {
        for (MedicalProductsDTO product : getAllProducts()) {
            if (product.getMedicineID().equals(productID)) {
                return product;
            }
        }
        return productDAO.selectByID(productID);
    }
    
    public void saleQuantity(int quan, String ID){
        MedicalProductsDTO pd = productDAO.selectByID(ID);
        pd.setQuantity(pd.getQuantity()- quan);
        int newQuantity = pd.getQuantity();
        productDAO.updateSumQuantity(ID, newQuantity);
    }
    
    public boolean updateProduct(MedicalProductsDTO product){
        return productDAO.update(product);
    }
    
    public String generateNextProductID() {
        String lastID = productDAO.getLatestProductID();  // Có thể là null

        if (lastID == null || lastID.isEmpty()) {
            return "MP001"; // Mặc định nếu bảng rỗng
        }

        String numericPart = lastID.substring(2); // Bỏ "MP", lấy số
        int lastNumber = Integer.parseInt(numericPart);
        int nextNumber = lastNumber + 1;

        return "MP" + String.format("%03d", nextNumber); // VD: MP013
    }
    
    public boolean addProduct(MedicalProductsDTO product){
        return productDAO.insert(product);
    }
    
    public boolean deleteProduct(String ID){
        return productDAO.delete(ID);
    }
    
    private boolean matchesKeyword(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword.toLowerCase());
    }
    
    public ArrayList<MedicalProductsDTO> searchAll(String keyword){
            if (keyword == null || keyword.trim().isEmpty()) {
                return new ArrayList<>(productDAO.selectAll());
                }
            String lowerKeyword = keyword.toLowerCase();
            return productDAO.selectAll().stream()
                .filter(p -> 
                    matchesKeyword(p.getMedicineID(), lowerKeyword) ||
                    matchesKeyword(p.getName(), lowerKeyword) ||
                    matchesKeyword(p.getCategory(), lowerKeyword) ||
                    matchesKeyword(p.getStatus(), lowerKeyword)
                )
                .collect(Collectors.toCollection(ArrayList::new));
        }
    }
