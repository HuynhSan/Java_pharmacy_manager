/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SupplierInvoicesDAO;
import com.pharmacy.app.DTO.SuplierInvoiceDTO;
import com.pharmacy.app.DTO.SuplierInvoiceDetailsDTO;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author BOI QUAN
 */
public class SuplierInvoicesBUS {
    private SupplierInvoicesDAO supInvoiceDAO;
    private ArrayList<SuplierInvoiceDTO> supInvoicesList;
    private SuplierInvoiceDetailsBUS supInvDeBUS = new SuplierInvoiceDetailsBUS();

    public SuplierInvoicesBUS() {
        supInvoiceDAO = new SupplierInvoicesDAO();
        supInvoicesList = new ArrayList<>();
        loadSupInvoiceList();
    }

    public void getSearchFiled(String field) {
        supInvoiceDAO.setSearchField(field);
    }

    public ArrayList<SuplierInvoiceDTO> getCustomerList() {
        return supInvoicesList;
    }

    public void loadSupInvoiceList() {
        supInvoicesList = supInvoiceDAO.selectAll();
    }

    public ArrayList<SuplierInvoiceDTO> getAllSuplierInvoice() {
        return this.supInvoicesList;
    }

    public SuplierInvoiceDTO getSupInvoiceByID(String invoiceID) {
        return supInvoiceDAO.selectByID(invoiceID);
    }

    public String generateNextProductID() {
        String lastID = supInvoiceDAO.getLatestProductID(); // Có thể là null

        if (lastID == null || lastID.isEmpty()) {
            return "INV001"; // Mặc định nếu bảng rỗng
        }

        String numericPart = lastID.substring(3); // Bỏ "INV", lấy số
        int lastNumber = Integer.parseInt(numericPart);
        int nextNumber = lastNumber + 1;

        return "INV" + String.format("%03d", nextNumber);
    }

    public ArrayList<SuplierInvoiceDTO> getSupInvoiceBySupplierID(String supplierID) {
        return supInvoiceDAO.selectBySupplierID(supplierID);
    }

    public ArrayList<SuplierInvoiceDTO> search(String keyword) {
        return supInvoiceDAO.search(keyword);
    }

    public ArrayList<SuplierInvoiceDTO> filterByDate(LocalDate date) {
        return supInvoiceDAO.filterByDate(date);
    }

    public boolean addSupInv(SuplierInvoiceDTO supInvDTO) {
        if (!supInvoiceDAO.insert(supInvDTO)) {
            return false;
        }

        // thêm ctpn
        for (SuplierInvoiceDetailsDTO detail : supInvDTO.getDetails()) {
            System.out.println("Chi tiết đơn nhập: " + supInvDTO.getDetails());
            System.out.println(
                    "Số dòng chi tiết: " + (supInvDTO.getDetails() == null ? "null" : supInvDTO.getDetails().size()));

            if (!supInvDeBUS.addSupInvDe(detail)) {
                return false;
            }
        }
        return true;
    }
}
