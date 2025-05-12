/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.ProductPromoDAO;
import com.pharmacy.app.DTO.ProductPromoDTO;

/**
 *
 * @author LENOVO
 */
public class ProductPromoBUS {
    private ProductPromoDAO ppDAO = new ProductPromoDAO();
    
    public boolean addProductPromo(ProductPromoDTO ppDTO){
        return ppDAO.insert(ppDTO);
    }
    
    public boolean deletePPByID (String ID){
        return true;
    }
    
    public ProductPromoDTO getProductByPromoID(String promoID){
        ProductPromoDTO ppDTO = ppDAO.selectProductByPromoID(promoID);
        return ppDTO;
    }
    
    public ProductPromoDTO getPromoByProductID(String productID){
        ProductPromoDTO ppDTO = ppDAO.selectPromoByProductID(productID);
        return ppDTO;
    }
}
