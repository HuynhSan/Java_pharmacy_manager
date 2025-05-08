/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;
import com.pharmacy.app.DTO.SupplierDTO;
import com.pharmacy.app.DAO.EmployeeDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class SupplierDAO implements DAOinterface<SupplierDTO>{
    private String searchField = "all"; // mặc định tìm tất cả

    public void setSearchField(String field) {
        this.searchField = field;
    }
    MyConnection myconnect = new MyConnection();
    
    public String generateNextSupplierId() {
        String nextId = ""; // Mặc định nếu bảng chưa có dữ liệu
        if (myconnect.openConnection()){
            try {
                String sql = "SELECT MAX(supplier_id) AS max_id FROM suppliers";
                ResultSet rs = myconnect.runQuery(sql);
                String lastId = "SUP000";
                
                if (rs.next()) {
                    lastId = rs.getString("max_id"); // Ví dụ SUP012
                }
                
                String numericPart = lastId.substring(3);
                int lastNumber = Integer.parseInt(numericPart); // Lấy phần số: 12
                lastNumber++; // Tăng lên: 13
                nextId = "SUP" + String.format("%03d", lastNumber); // Kết quả: SUP013
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        return nextId;
    }

    @Override
    public boolean insert(SupplierDTO t) {
        boolean result = false;
        if (myconnect.openConnection()){
            String newId = generateNextSupplierId();
            String sql = "INSERT INTO suppliers (supplier_id, name, phone_number, email, address) VALUES (?, ?, ?, ?, ?)";
            int rowsAffected = myconnect.prepareUpdate(
                sql,
                newId,
                t.getName(),
                t.getPhone(),
                t.getEmail(),
                t.getAddress()                    
            );
            result = rowsAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }

    @Override
    public boolean update(SupplierDTO t) {
        boolean result = false;
        if (myconnect.openConnection()){
            String sql = "UPDATE suppliers SET name=?, phone_number=?, email=?, address=? WHERE supplier_id=?";
            int rowsAffected = myconnect.prepareUpdate(
                    sql,
                    t.getName(),
                    t.getPhone(),
                    t.getEmail(),
                    t.getAddress(),
                    t.getId() // WHERE dieu kien
            );
            result = rowsAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }

    @Override
    public boolean delete(String t) {
        boolean result = false;
        if (myconnect.openConnection()){
            String sql = "UPDATE suppliers SET is_deleted=1 WHERE supplier_id=?";
            int rowsAffected = myconnect.prepareUpdate(sql, t);
            result = rowsAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<SupplierDTO> selectAll(){
        ArrayList<SupplierDTO> suppliers = new ArrayList<>();
        if (myconnect.openConnection()) {
            String sql = "SELECT supplier_id, name, phone_number, email, address FROM suppliers WHERE is_deleted = 0";
            ResultSet rs = myconnect.runQuery(sql);
            try {
                while (rs.next()) {
                    SupplierDTO supplier = new SupplierDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getString(4), // email
                        rs.getString(5) // address
                    );
                    suppliers.add(supplier);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return suppliers;
    }

    @Override
    public SupplierDTO selectByID(String t) {
        SupplierDTO supplier = null;
        if (myconnect.openConnection()){
            String sql = "SELECT supplier_id, name, phone_number, email, address FROM suppliers WHERE supplier_id = ?";
            ResultSet rs = myconnect.prepareQuery(sql, t);
            try {
                while (rs.next()){
                    supplier = new SupplierDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getString(4), // email
                        rs.getString(5) // address
                    );
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return supplier;
    }

    @Override
    public ArrayList<SupplierDTO> search(String t) {
        ArrayList<SupplierDTO> suppliers = new ArrayList<>();
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM suppliers WHERE( "
                    + "LOWER(supplier_id) LIKE '%" + t.toLowerCase() + "%' OR "
                    + "LOWER(name) LIKE '%" + t.toLowerCase() + "%' OR "
                    + "phone_number LIKE '%" + t.toLowerCase() + "%' OR "
                    + "email LIKE '%" + t.toLowerCase() + "%' OR "
                    + "LOWER(address) LIKE '%" + t.toLowerCase() + "%' ) "
                    + "AND is_deleted = 0";
            ResultSet rs = myconnect.runQuery(sql);
            try {
                while (rs.next()) {
                    SupplierDTO supplier = new SupplierDTO(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getString(3), // phone
                        rs.getString(4), // email
                        rs.getString(5) // address
                    );
                    suppliers.add(supplier);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return suppliers;
    }

    public boolean isNameExists(String name) {
        boolean exists = false;
        if (myconnect.openConnection()){
            String query = "SELECT COUNT(*) as count FROM suppliers WHERE name = ?";
            try {
                ResultSet rs = myconnect.prepareQuery(query, name);
                if (rs.next()) {
                    exists = rs.getInt("count") > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return exists;
    }
    
    /**
     * Checks if an email already exists in the database
     * @param email The email to check
     * @param supplier_id
     * @return true if the email exists, false otherwise
     */
    public boolean isUpdateEmailExists(String email, String supplier_id) {
        boolean exists = false;
        EmployeeDAO employeeDAO = new EmployeeDAO();
        if(myconnect.openConnection()){
            try{
                String query = "SELECT email FROM suppliers WHERE supplier_id = ?";
                ResultSet rs = myconnect.prepareQuery(query, supplier_id);
                if (rs.next()){
                    String currentEmail = rs.getString(1);
                    if(currentEmail.equalsIgnoreCase(email)){
                        return false;
                    }
                }
                exists = employeeDAO.isEmailExists(email);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return exists;
    }

    /**
     * Checks if a phone number already exists in the database
     * @param phone The phone number to check
     * @param supplier_id
     * @return true if the phone number exists, false otherwise
     */
    public boolean isUpdatePhoneExists(String phone, String supplier_id) {
        boolean exists = false;
        EmployeeDAO employeeDAO = new EmployeeDAO();
        if(myconnect.openConnection()){
            try{
                String query = "SELECT phone_number FROM suppliers WHERE supplier_id = ?";
                ResultSet rs = myconnect.prepareQuery(query, supplier_id);
                if (rs.next()){
                    String currentEmail = rs.getString(1);
                    if(currentEmail.equalsIgnoreCase(phone)){
                        return false;
                    }
                }
                exists = employeeDAO.isPhoneExists(phone);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        
        return exists;
    }
    
    public boolean isUpdateNameExists(String name, String supplier_id) {
        boolean exists = false;
        if(myconnect.openConnection()){
            try{
                String query = "SELECT name FROM suppliers WHERE supplier_id = ?";
                ResultSet rs = myconnect.prepareQuery(query, supplier_id);
                if (rs.next()){
                    String currentName = rs.getString(1);
                    if(currentName.equalsIgnoreCase(name)){
                        return false;
                    }
                }
                exists = isNameExists(name);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return exists;
    }
}
