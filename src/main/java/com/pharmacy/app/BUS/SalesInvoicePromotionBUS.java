/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalesInvoicePromotionDAO;
import com.pharmacy.app.DTO.SalesInvoicePromotionDTO;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoicePromotionBUS {
    SalesInvoicePromotionDAO dao = new SalesInvoicePromotionDAO();
    
    public boolean add(SalesInvoicePromotionDTO t){
        return dao.insert(t);
    }
}
