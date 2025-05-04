/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalesInvoiceDAO;
import com.pharmacy.app.DTO.SalesInvoiceDTO;
import java.util.ArrayList;

import java.util.stream.Collectors;


/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceBUS {
    SalesInvoiceDAO dao = new SalesInvoiceDAO();
    
    public String generateNewId(){
        return dao.generateNewSalesInvoiceId();
    }
    
    public ArrayList<SalesInvoiceDTO> selectAll(){
        return dao.selectAll();
    }
    
    public boolean insertInvoice(SalesInvoiceDTO t) {
        return dao.insert(t);
    }
    
    public ArrayList<SalesInvoiceDTO> searchInvoice(String keyword) {
//        return dao.selectAll().stream()
//            .filter(p -> p.getInvoiceId().toLowerCase().contains(keyword.toLowerCase()) ||
//                         p.getCustomerId().toLowerCase().contains(keyword.toLowerCase()) ||
//                         p.getUserId().toLowerCase().contains(keyword.toLowerCase()) ||
//                         String.valueOf(p.getFinalTotal()).contains(keyword.toLowerCase())) ||
//                         p.getCreateDate().toString().contains(keyword.toLowerCase()))
//            .collect(Collectors.toCollection(ArrayList::new));
//    }
        return null;
//        return dao.selectAll().stream()
//            .filter(p -> p.getInvoiceId().toLowerCase().contains(keyword.toLowerCase()) ||
//                         p.getCustomerId().toLowerCase().contains(keyword.toLowerCase()) ||
//                         p.getUserId().toLowerCase().contains(keyword.toLowerCase()) ||
//                         String.valueOf(p.getFinalTotal()).contains(keyword.toLowerCase())) ||
//                         p.getCreateDate().toString().contains(keyword.toLowerCase()))
//            .collect(Collectors.toCollection(ArrayList::new));
//    }
    }
    public ArrayList<SalesInvoiceDTO> getSelectSaleInvoiceByCustomerID(String customerID){
        return salesinvoiceDAO.selectSaleInvoiceByCustomerID(customerID);
    }
}
