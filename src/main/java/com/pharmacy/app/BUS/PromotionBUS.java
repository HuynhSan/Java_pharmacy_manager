/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;
import com.pharmacy.app.DAO.PromotionDAO;
import com.pharmacy.app.DTO.PromotionDTO;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Giai Cuu Li San
 */
public class PromotionBUS {
    private PromotionDAO promoDAO = new PromotionDAO();
   
    
    public String generateNewPromoID(){
        return promoDAO.generateNewPromoId();
    }

    
    public ArrayList<PromotionDTO> getAllPromos(){
        return promoDAO.selectAll();
    }
    
    public boolean addPromo(PromotionDTO promo){
        return promoDAO.insert(promo);
    }
    
    public boolean updatePromo(PromotionDTO promo){
        return promoDAO.update(promo);
    }
    
    public boolean deletePromo(String promoId){
        return promoDAO.delete(promoId);
    }
    
    public ArrayList<PromotionDTO> getPromosByType(String promoType){
        return promoDAO.getPromotionsByType(promoType);
    }

    public ArrayList<PromotionDTO> searchAll(String keyword) {
        return promoDAO.selectAll().stream()
            .filter(p -> p.getPromotionId().toLowerCase().contains(keyword.toLowerCase()) ||
                         p.getProgramName().toLowerCase().contains(keyword.toLowerCase()) ||
                         p.getPromotionType().toLowerCase().contains(keyword.toLowerCase()) ||
                         (p.getDiscountPercent() != null && String.valueOf(p.getDiscountPercent()).contains(keyword.toLowerCase())) ||
                         (p.getMinAccumulatedPoints() != null && String.valueOf(p.getMinAccumulatedPoints()).contains(keyword.toLowerCase())) ||
                         (p.getDiscountAmount() != null && String.valueOf(p.getDiscountAmount()).contains(keyword.toLowerCase())) ||
                         p.getStartDate().toString().contains(keyword.toLowerCase()) ||
                         p.getEndDate().toString().contains(keyword.toLowerCase()))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public PromotionDTO selectById(String promoId) {
        return promoDAO.selectByID(promoId);
    }
}
