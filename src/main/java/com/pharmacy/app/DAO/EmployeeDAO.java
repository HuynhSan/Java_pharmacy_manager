/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;
import com.pharmacy.app.DTO.EmployeeDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class EmployeeDAO implements DAOinterface<EmployeeDTO> {
    private MyConnection myConnection;
    
    public EmployeeDAO() {
        myConnection = new MyConnection();
    }

    @Override
    public boolean insert(EmployeeDTO employee) {
        myConnection.openConnection();
        String query = "INSERT INTO employees(employee_id, user_id, name, dob, gender, email, phone_number, address, is_deleted) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = myConnection.prepareUpdate(query, 
                employee.getEmployeeID(),
                employee.getUserID(),
                employee.getName(),
                java.sql.Date.valueOf(employee.getDob()),
                employee.getGender(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getAddress(),
                employee.isDeleted());
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public boolean update(EmployeeDTO employee) {
        myConnection.openConnection();
        String query = "UPDATE employees SET user_id = ?, name = ?, dob = ?, gender = ?, "
                + "email = ?, phone_number = ?, address = ?, is_deleted = ? WHERE employee_id = ?";
        int result = myConnection.prepareUpdate(query, 
                employee.getUserID(),
                employee.getName(),
                java.sql.Date.valueOf(employee.getDob()),
                employee.getGender(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getAddress(),
                employee.isDeleted(),
                employee.getEmployeeID());
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public boolean delete(String employeeID) {
        // Soft delete by setting isDeleted = true
        myConnection.openConnection();
        String query = "UPDATE employees SET is_deleted = 1 WHERE employee_id = ?";
        int result = myConnection.prepareUpdate(query, employeeID);
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public ArrayList<EmployeeDTO> selectAll() {
        ArrayList<EmployeeDTO> employeeList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM employees WHERE is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                EmployeeDTO employee = extractEmployeeFromResultSet(rs);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return employeeList;
    }

    @Override
    public EmployeeDTO selectByID(String employeeID) {
        EmployeeDTO employee = null;
        myConnection.openConnection();
        String query = "SELECT * FROM employees WHERE employee_id = ? AND is_deleted = 0";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, employeeID);
            if (rs.next()) {
                employee = extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return employee;
    }

    @Override
    public ArrayList<EmployeeDTO> search(String keyword) {
        ArrayList<EmployeeDTO> employeeList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM employees WHERE (employee_id LIKE '%" + keyword + "%' OR "
                + "name LIKE '%" + keyword + "%' OR "
                + "email LIKE '%" + keyword + "%' OR "
                + "phone_number LIKE '%" + keyword + "%' OR "
                + "address LIKE '%" + keyword + "%') "
                + "AND is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                EmployeeDTO employee = extractEmployeeFromResultSet(rs);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return employeeList;
    }
    
    public ArrayList<EmployeeDTO> filterByGender(boolean gender) {
        ArrayList<EmployeeDTO> employeeList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM employees WHERE gender = " + (gender ? "1" : "0") + " AND is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                EmployeeDTO employee = extractEmployeeFromResultSet(rs);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return employeeList;
    }
    
    /**
     * Get the highest employee ID number from all employees (including deleted ones)
     * @return The highest ID number used in the database
     */
    public int getHighestEmployeeIDNumber() {
        int maxID = 0;
        myConnection.openConnection();
        // Query to select all employee IDs, including deleted ones
        String query = "SELECT employee_id FROM employees";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                String id = rs.getString("employee_id");
                if (id != null && id.startsWith("EM")) {
                    try {
                        int idNum = Integer.parseInt(id.substring(2));
                        if (idNum > maxID) {
                            maxID = idNum;
                        }
                    } catch (NumberFormatException e) {
                        // Skip if ID format is different or can't be parsed
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return maxID;
    }
    
    /**
     * Checks if an email already exists in the database
     * @param email The email to check
     * @return true if the email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        myConnection.openConnection();
        String query = "SELECT COUNT(*) as count FROM employees WHERE email = '" + email + "' AND is_deleted = 0";
        boolean exists = false;

        try {
            ResultSet rs = myConnection.runQuery(query);
            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }

        return exists;
    }

    /**
     * Checks if a phone number already exists in the database
     * @param phone The phone number to check
     * @return true if the phone number exists, false otherwise
     */
    public boolean isPhoneExists(String phone) {
        myConnection.openConnection();
        String query = "SELECT COUNT(*) as count FROM employees WHERE phone_number = '" + phone + "' AND is_deleted = 0";
        boolean exists = false;

        try {
            ResultSet rs = myConnection.runQuery(query);
            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }

        return exists;
    }
    
    private EmployeeDTO extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        String employeeID = rs.getString("employee_id");
        String userID = rs.getString("user_id");
        String name = rs.getString("name");
        LocalDate dob = rs.getDate("dob").toLocalDate();
        boolean gender = rs.getBoolean("gender");
        String email = rs.getString("email");
        String phone = rs.getString("phone_number");
        String address = rs.getString("address");
        boolean isDeleted = rs.getBoolean("is_deleted");
        
        return new EmployeeDTO(employeeID, userID, name, dob, gender, email, phone, address, isDeleted);
    }
}
