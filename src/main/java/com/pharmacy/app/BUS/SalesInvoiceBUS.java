/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalesInvoiceDAO;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceBUS {
    SalesInvoiceDAO salesinvoiceDAO = new SalesInvoiceDAO();
    public String generateNewId(){
        return salesinvoiceDAO.generateNewSalesInvoiceId();
    }
}
