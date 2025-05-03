/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SupplierInvoicesDAO;
import com.pharmacy.app.DTO.SuplierInvoiceDTO;
import java.util.ArrayList;

/**
 *
 * @author BOI QUAN
 */
public class SuplierInvoicesBUS {
    private final SupplierInvoicesDAO supInvoiceDAO;
    private ArrayList<SuplierInvoiceDTO> supInvoicesList;
    public SuplierInvoicesBUS(){
        supInvoiceDAO = new SupplierInvoicesDAO();
        supInvoicesList = new ArrayList<>();
    }
    
    public void getSearchFiled(String field){
        supInvoiceDAO.setSearchField(field);
    }
    
    public ArrayList<SuplierInvoiceDTO> getCustomerList() {
        return supInvoicesList;
    }
    
    public void loadSupInvoiceList() {
        supInvoicesList = supInvoiceDAO.selectAll();
    }
    
    public ArrayList<SuplierInvoiceDTO> getAllSuplierInvoice(){
        return this.supInvoicesList;
    }
    
    public SuplierInvoiceDTO getSupInvoiceByID(String invoiceID){
//        for (SuplierInvoiceDTO supInvoice : supInvoicesList) {
//            if (supInvoice.getInvoiceID().equals(invoiceID)) {
//                return supInvoice;
//            }
//        }
        return supInvoiceDAO.selectByID(invoiceID);
    }
    
    public ArrayList<SuplierInvoiceDTO> search(String keyword){
        return supInvoiceDAO.search(keyword);
    }
    
}
