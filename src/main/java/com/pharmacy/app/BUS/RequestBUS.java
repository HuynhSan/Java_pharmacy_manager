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
    
    
}
