/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;
import com.pharmacy.app.DTO.SupplierDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giai Cuu Li San
 */
public class SupplierDAO {
    public List<SupplierDTO> getAllSuppliers() throws SQLException {
        List<SupplierDTO> suppliers = new ArrayList<>();

        MyConnection myconnect = new MyConnection();
        System.out.println("connect thanh cong");

        if (myconnect.openConnection()) {
            String sql = "SELECT * FROM suppliers";
            ResultSet rs = myconnect.runQuery(sql);

            try {
                while (rs != null && rs.next()) {
                    SupplierDTO supplier = new SupplierDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getString(4), // email
                        rs.getString(5) // address
                    );
                    suppliers.add(supplier);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return suppliers;
    }
    
}
