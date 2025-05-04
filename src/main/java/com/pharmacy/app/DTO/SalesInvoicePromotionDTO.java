/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DTO;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoicePromotionDTO {
    private String invoiceId;
    private String promoId;

    public SalesInvoicePromotionDTO(String invoiceId, String promoId) {
        this.invoiceId = invoiceId;
        this.promoId = promoId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }
    
    
}
