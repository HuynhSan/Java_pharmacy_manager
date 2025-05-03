/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalesDAO;
import com.pharmacy.app.DTO.SaleItemDTO;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class SalesBUS {
    private SalesDAO salesDAO = new SalesDAO();

    public ArrayList<SaleItemDTO> selectSaleItems(){
        return salesDAO.selectAllSaleItems();
    }
}
