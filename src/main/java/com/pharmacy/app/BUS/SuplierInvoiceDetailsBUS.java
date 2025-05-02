/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SupplierInvoiceDetailsDAO;
import com.pharmacy.app.DTO.SuplierInvoiceDetailsDTO;
import java.util.ArrayList;

/**
 *
 * @author BOI QUAN
 */
public class SuplierInvoiceDetailsBUS {
    private SupplierInvoiceDetailsDAO supInvoiceDetailDAO;
    
    public SuplierInvoiceDetailsBUS(){
        supInvoiceDetailDAO = new SupplierInvoiceDetailsDAO();
    }
    
    public ArrayList<SuplierInvoiceDetailsDTO> getHistoryByID (String invoiceID){
        return supInvoiceDetailDAO.selectInvoiceDetailByID(invoiceID);
    }
}
