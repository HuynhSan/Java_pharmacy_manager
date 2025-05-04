/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalesDAO;
import com.pharmacy.app.DTO.SaleItemDTO;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesBUS {
    private SalesDAO dao = new SalesDAO();

    public ArrayList<SaleItemDTO> selectSaleItems(){
        return dao.selectAllSaleItems();
    }
    
    public ArrayList<SaleItemDTO> searchProduct(String keyword){
        return dao.selectAllSaleItems().stream()
            .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toCollection(ArrayList::new));

    }
}
