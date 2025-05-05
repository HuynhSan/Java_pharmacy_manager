/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.CategoryDAO;
import com.pharmacy.app.DTO.CategoryDTO;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class CategoryBUS {
    private CategoryDAO cateDAO = new CategoryDAO();

    public List<CategoryDTO> getAllCategories() {
        return cateDAO.getAllCategories();
    }
    

    public String getCategoryIDByName(String categoryName) {
        List<CategoryDTO> list = cateDAO.getAllCategories();
        for (CategoryDTO cat : list) {
            if (cat.getCategoryName().equalsIgnoreCase(categoryName)) {
                return cat.getCategoryID();
            }
        }
        return null;
    }

    
}
