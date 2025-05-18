/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.SalaryComponentsDAO;
import com.pharmacy.app.DTO.SalaryComponentsDTO;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class SalaryComponentsBUS {
    public SalaryComponentsDAO salaryComponentsDAO;
    private ArrayList<SalaryComponentsDTO> componentsList;
    
    public SalaryComponentsBUS() {
        salaryComponentsDAO = new SalaryComponentsDAO();
        componentsList = new ArrayList<>();
    }
    
    public ArrayList<SalaryComponentsDTO> getComponentsList() {
        return componentsList;
    }
    
    public void loadComponentsList() {
        componentsList = salaryComponentsDAO.selectAll();
    }
    
    public SalaryComponentsDTO getComponentByID(String componentID) {
        for (SalaryComponentsDTO component : componentsList) {
            if (component.getComponentID().equals(componentID)) {
                return component;
            }
        }
        return salaryComponentsDAO.selectByID(componentID);
    }
}
