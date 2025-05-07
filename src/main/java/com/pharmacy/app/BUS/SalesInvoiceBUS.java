/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalesInvoiceDAO;
import com.pharmacy.app.DTO.CustomerDTO;
import com.pharmacy.app.DTO.SalesInvoiceDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import java.util.stream.Collectors;


/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceBUS {
    SalesInvoiceDAO dao = new SalesInvoiceDAO();
    CustomerBUS customerBus = new CustomerBUS();
    
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
        return dao.search(keyword);
    }
    public ArrayList<SalesInvoiceDTO> getSelectSaleInvoiceByCustomerID(String customerID){
        return dao.selectSaleInvoiceByCustomerID(customerID);
    }

    public SalesInvoiceDTO getInvoiceById(String invoiceId) {
        return dao.selectByID(invoiceId);
    }
    
    public ArrayList<SalesInvoiceDTO> filterByDateRange(LocalDate from, LocalDate to) {
        return dao.selectAll().stream()
            .filter(invoice -> {
                LocalDate d = invoice.getCreateDate().toLocalDate();
                return (d.isEqual(from) || d.isAfter(from)) &&
                       (d.isEqual(to) || d.isBefore(to));
            })
            .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<SalesInvoiceDTO> filterByDateRangeMix(ArrayList<SalesInvoiceDTO> salesInvoice, LocalDate from, LocalDate to) {
        return salesInvoice.stream()
            .filter(invoice -> {
                LocalDate d = invoice.getCreateDate().toLocalDate();
                return (d.isEqual(from) || d.isAfter(from)) &&
                       (d.isEqual(to) || d.isBefore(to));
            })
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
