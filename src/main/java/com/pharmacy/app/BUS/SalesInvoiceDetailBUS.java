/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalesInvoiceDetailDAO;
import com.pharmacy.app.DTO.SalesInvoiceDetailDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesInvoiceDetailBUS {
    SalesInvoiceDetailDAO dao = new SalesInvoiceDetailDAO();
        
    
    public boolean addInvoiceDetail(SalesInvoiceDetailDTO detail) {
        return dao.insert(detail);
    }

    public ArrayList<SalesInvoiceDetailDTO> getByInvoiceId(String invoiceId) {
        return dao.selectInvoiceByID(invoiceId);
    }
    
}
