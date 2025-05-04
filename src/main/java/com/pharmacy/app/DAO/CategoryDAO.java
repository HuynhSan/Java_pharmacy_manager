/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.CategoryDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class CategoryDAO {
    MyConnection myconnect = new MyConnection();

    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> list = new ArrayList<>();

        if (myconnect.openConnection()) {
            String sql = "SELECT category_id, category_name FROM categories";
            ResultSet rs = null;

            try {
                rs = myconnect.runQuery(sql);

                while (rs != null && rs.next()) {
                    String id = rs.getString("category_id");
                    String name = rs.getString("category_name");
                    list.add(new CategoryDTO(id, name));
                }

            } catch (SQLException ex) {
                System.out.println("SQLException occurred: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                // Đóng ResultSet trước khi đóng connection
                try {
                    if (rs != null) rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                myconnect.closeConnection();
            }
        }

        return list;
    }
    
    public String getCategoryIDByName(String categoryName) {
        String categoryId = null;

        try {
            myconnect.openConnection();
            String sql = "SELECT category_id FROM categories WHERE name = ?";
            PreparedStatement ps = myconnect.con.prepareStatement(sql);
            ps.setString(1, categoryName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categoryId = rs.getString("category_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
        }

        return categoryId;
}

}


