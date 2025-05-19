/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.RequestDAO;
import com.pharmacy.app.DTO.RequestDTO;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class RequestBUS {
    private RequestDAO reqDAO = new RequestDAO();
    
    public ArrayList<RequestDTO> getAllRequest(){
        return reqDAO.selectAll();
    }
    public ArrayList<RequestDTO> getAllRequestByID(String ID){
        String emID = reqDAO.getEmployeeIDbyUserID(ID);
        return reqDAO.selectAllByID(emID);
    }
    
    public String getEmployeeIDbyUserID(String ID){
        return reqDAO.getEmployeeIDbyUserID(ID);
    }
    
    public String generateNextProductID() {
        String lastID = reqDAO.getLatestProductID();  // Có thể là null

        if (lastID == null || lastID.isEmpty()) {
            return "REQ001"; // Mặc định nếu bảng rỗng
        }

        String numericPart = lastID.substring(3); // Bỏ "MP", lấy số
        int lastNumber = Integer.parseInt(numericPart);
        int nextNumber = lastNumber + 1;

        return "REQ" + String.format("%03d", nextNumber); // VD: MP013
    }
    
    public boolean addRequest(RequestDTO req){
        return reqDAO.insert(req);
    }
    
    public RequestDTO getRequestByID(String ID){
        for (RequestDTO req : getAllRequest()) {
            if (req.getRequestId().equals(ID.trim())) {
                return req;
            }
        }
        return reqDAO.selectByID(ID);
    }
    
    public boolean updateRequest(RequestDTO req){
        return reqDAO.update(req);
    }
    
    public int getCountRequestNonSalary(String emID){
        return reqDAO.quantityRequestNonSalary(emID);
    }
}
