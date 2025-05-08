/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SupplierDAO;
import com.pharmacy.app.DTO.SupplierDTO;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public final class SupplierBUS {
    private final SupplierDAO supplierDAO;
    private ArrayList<SupplierDTO> supplierList;
    
    public SupplierBUS(){
        supplierDAO = new SupplierDAO();
        supplierList = new ArrayList<>();
        loadSupplierList();
    }
    
    public void loadSupplierList() {
        supplierList = supplierDAO.selectAll();
    }
    
    public ArrayList<SupplierDTO> getSupplierList() {
        return supplierList;
    }
        
    public SupplierDTO getSupplierByID(String supplierID) {
        for (SupplierDTO supplier : supplierList) {
            if (supplier.getId().equals(supplierID)) {
                return supplier;
            }
        }
        return supplierDAO.selectByID(supplierID);
    }

    public String generateNextId(){
        return supplierDAO.generateNextSupplierId();
    }
    
    public ArrayList<SupplierDTO> getAllSuppliers(){
        return this.supplierList;
    }
    
    public boolean addSupplier(SupplierDTO supplier){
        boolean check = supplierDAO.insert(supplier);
        if (check){
            loadSupplierList();
        }
        return check;
    }
    
    public boolean updateSupplier(SupplierDTO supplier){
        boolean check = supplierDAO.update(supplier);
        if (check){
            loadSupplierList();
        }
        return check;
    }
    
    public boolean deleteSupplier(SupplierDTO supplier){
        boolean check = supplierDAO.delete(supplier.getId());
        if (check){
            loadSupplierList();
        }
        return check;
    }
    
    public ArrayList<SupplierDTO> search(String keyword){
        return supplierDAO.search(keyword);
    }
    
    public int getIndexBySupplierId(String id) {
        for (int i = 0; i < supplierList.size(); i++) {
            if (supplierList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    
    public ArrayList<SupplierDTO> getSupList() {
        try{
            return supplierDAO.selectAll();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public SupplierDTO getSupplierByName(String name) {
        for (SupplierDTO sup : getSupList()) {
            if (sup.getName().equalsIgnoreCase(name)) {
                return sup;
            }
        }
        return null; // Không tìm thấy
    }

}
