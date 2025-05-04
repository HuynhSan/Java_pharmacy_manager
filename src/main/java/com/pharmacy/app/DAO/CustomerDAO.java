/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.CustomerDTO;
import com.pharmacy.app.DAO.MyConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.Objects;
/**
 *
 * @author BOI QUAN
 */
public class CustomerDAO implements DAOinterface<CustomerDTO>{
    MyConnection myconnect = new MyConnection();
    
    public String generateNextId() {
        String nextId = ""; // Mặc định nếu bảng chưa có dữ liệu
        if (myconnect.openConnection()){
            try {
                String sql = "SELECT MAX(customer_id) AS max_id FROM customers";
                ResultSet rs = myconnect.runQuery(sql);
                String lastId = "CUS000";
                
                if (rs.next()) {
                    lastId = rs.getString("max_id"); // Ví dụ SUP012
                }
                
                String numericPart = lastId.substring(3);
                int lastNumber = Integer.parseInt(numericPart); // Lấy phần số: 12
                lastNumber++; // Tăng lên: 13
                nextId = "CUS" + String.format("%03d", lastNumber); // Kết quả: SUP013
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        return nextId;
    }
        
    @Override
    public boolean insert(CustomerDTO t) {
        boolean result = false;
        if (myconnect.openConnection()){
            String newId = generateNextId();
            String sql = "INSERT INTO customers (customer_id, customer_name, phone_number, point) VALUES (?, ?, ?, ?)";
            int rowsAffected = myconnect.prepareUpdate(
                sql,
                newId,
                t.getName(),
                t.getPhone(),
                t.getPoint()                 
            );
            result = rowsAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }

//    update thong tin
    @Override
    public boolean update(CustomerDTO t) {
        boolean result = false;
        if (myconnect.openConnection()){
            String sql = "UPDATE customers SET customer_name=?, phone_number=? WHERE customer_id=?";
            int rowsAffected = myconnect.prepareUpdate(
                    sql,
                    t.getName(),
                    t.getPhone(),
                    t.getId() // WHERE dieu kien
            );
            result = rowsAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }

//    update diem
    @Override
    public boolean delete(String t) {
        boolean result = false;
        if (myconnect.openConnection()){
            String sql = "UPDATE customers SET is_deleted=1 WHERE customer_id=?";
            int rowsAffected = myconnect.prepareUpdate(sql, t);
            result = rowsAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<CustomerDTO> selectAll() {
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        if (myconnect.openConnection()) {
            String sql = "SELECT * FROM customers WHERE is_deleted = 0";
            ResultSet rs = myconnect.runQuery(sql);
            try {
                while (rs.next()) {
                    CustomerDTO customer = new CustomerDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getFloat(4) // point
                    );
                    customers.add(customer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return customers;
    }

    @Override
    public CustomerDTO selectByID(String t) {
        CustomerDTO customer = null;
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM customers WHERE customer_id = ?";
            ResultSet rs = myconnect.prepareQuery(sql, t);
            try {
                while (rs.next()){
                    customer = new CustomerDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getFloat(4) // point
                    );
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return customer;
    }
    

    @Override
    public ArrayList<CustomerDTO> search(String t) {
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM customers WHERE( "
                    + "LOWER(customer_id) LIKE '%" + t.toLowerCase() + "%' OR "
                    + "LOWER(customer_name) LIKE '%" + t.toLowerCase() + "%' OR "
                    + "phone_number LIKE '%" + t.toLowerCase() + "%')"
                    + "AND is_deleted = 0";
            ResultSet rs = myconnect.runQuery(sql);
            try {
                while (rs.next()) {
                    CustomerDTO customer = new CustomerDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getFloat(4) // point
                    );
                    customers.add(customer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return customers;
    }

    public CustomerDTO findCustomerByPhone(String phone) {
        CustomerDTO customer = null;
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM customers WHERE phone_number = ?";
            ResultSet rs = myconnect.prepareQuery(sql, phone);
            try {
                while (rs != null && rs.next()){
                    customer = new CustomerDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getFloat(4) // point
                    );
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return customer;
    }

    public void updatePoints(String customerId, float newTotalPoints) {
        if (myconnect.openConnection()){
            String sql = "UPDATE customers SET point = ? WHERE customer_id=?";
            int rowsAffected = myconnect.prepareUpdate(
                    sql,
                    newTotalPoints,
                    customerId // WHERE dieu kien
            );
            myconnect.closeConnection();
        }
    }

    public String getCustomerNameById(String id) {
        String name = "";
        if (myconnect.openConnection()){
            String sql = "SELECT customer_name FROM customers WHERE customer_id = ?";
            ResultSet rs = myconnect.prepareQuery(sql, id);
            try {
                while (rs != null && rs.next()){
                    name = rs.getString(1);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return name;
    }
    
    
}
