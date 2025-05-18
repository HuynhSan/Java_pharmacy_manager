/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.ContractDTO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class ContractDAO implements DAOinterface<ContractDTO> {
    private MyConnection myConnection;
    
    public ContractDAO() {
        myConnection = new MyConnection();
    }

    @Override
    public boolean insert(ContractDTO contract) {
        myConnection.openConnection();
        String query = "INSERT INTO contracts(contract_id, employee_id, signing_date, start_date, end_date, "
                + "degree, experience_years, position, work_description, base_salary, is_deleted)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = myConnection.prepareUpdate(query, 
                contract.getContractID(),
                contract.getEmployeeID(),
                java.sql.Date.valueOf(contract.getSigningDate()),
                java.sql.Date.valueOf(contract.getStartDate()),
                java.sql.Date.valueOf(contract.getEndDate()),
                contract.getDegree(),
                contract.getExperienceYears(),
                contract.getPosition(),
                contract.getWorkDescription(),
                contract.getBaseSalary(),
                contract.isDeleted());
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public boolean update(ContractDTO contract) {
        myConnection.openConnection();
        String query = "UPDATE contracts SET employee_id = ?, signing_date = ?, start_date = ?, end_date = ?, "
                + "degree = ?, experience_years = ?, position = ?, work_description = ?, base_salary = ?, is_deleted = ? WHERE contract_id = ?";
        int result = myConnection.prepareUpdate(query, 
                contract.getEmployeeID(),
                java.sql.Date.valueOf(contract.getSigningDate()),
                java.sql.Date.valueOf(contract.getStartDate()),
                java.sql.Date.valueOf(contract.getEndDate()),
                contract.getDegree(),
                contract.getExperienceYears(),
                contract.getPosition(),
                contract.getWorkDescription(),
                contract.getBaseSalary(),
                contract.isDeleted(),
                contract.getContractID());
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public boolean delete(String contractID) {
        // Soft delete by setting isDeleted = true
        myConnection.openConnection();
        String query = "UPDATE contracts SET is_deleted = 1 WHERE contract_id = ?";
        int result = myConnection.prepareUpdate(query, contractID);
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public ArrayList<ContractDTO> selectAll() {
        ArrayList<ContractDTO> contractList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM contracts WHERE is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                ContractDTO contract = extractContractFromResultSet(rs);
                contractList.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return contractList;
    }

    @Override
    public ContractDTO selectByID(String contractID) {
        ContractDTO contract = null;
        myConnection.openConnection();
        String query = "SELECT * FROM contracts WHERE contract_id = ? AND is_deleted = 0";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, contractID);
            if (rs.next()) {
                contract = extractContractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return contract;
    }

    @Override
    public ArrayList<ContractDTO> search(String keyword) {
        ArrayList<ContractDTO> contractList = new ArrayList<>();
        myConnection.openConnection();

        // Modified query to join contracts with employees table to search by employee name
        String query = "SELECT c.* FROM contracts c " +
                      "JOIN employees e ON c.employee_id = e.employee_id " +
                      "WHERE (c.contract_id LIKE ? OR " +
                      "e.name LIKE ? OR " +
                      "c.position LIKE ?) " +
                      "AND c.is_deleted = 0";

        String searchPattern = "%" + keyword + "%";

        try {
            ResultSet rs = myConnection.runPreparedQuery(query, searchPattern, searchPattern, searchPattern);

            while (rs != null && rs.next()) {
                ContractDTO contract = extractContractFromResultSet(rs);
                contractList.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }

        return contractList;
    }
    
    /**
     * Get the highest contract ID number from all contracts (including deleted ones)
     * @return The highest ID number used in the database
     */
    public int getHighestContractIDNumber() {
        int maxID = 0;
        myConnection.openConnection();
        // Query to select all contract IDs, including deleted ones
        String query = "SELECT contract_id FROM contracts";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                String id = rs.getString("contract_id");
                if (id != null && id.startsWith("CT")) {
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
     * Gets all contracts for a specific employee
     * @param employeeID The employee ID to find contracts for
     * @return List of contracts for the employee or empty list if none found
     */
    public ArrayList<ContractDTO> getContractsByEmployeeID(String employeeID) {
        ArrayList<ContractDTO> contractList = new ArrayList<>();
        myConnection.openConnection();

        // Query to select all non-deleted contracts for the specified employee, ordered by end date (newest first)
        String query = "SELECT * FROM contracts WHERE employee_id = ? AND is_deleted = 0 ORDER BY end_date DESC";

        try {
            ResultSet rs = myConnection.runPreparedQuery(query, employeeID);

            while (rs != null && rs.next()) {
                ContractDTO contract = extractContractFromResultSet(rs);
                contractList.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }

        return contractList;
    }
    
    public ContractDTO getCurrentContractByEmployeeID(String employeeID) {
        ContractDTO currentContract = null;
        myConnection.openConnection();
        
        String sql = "SELECT * FROM contracts WHERE employee_id = ? AND is_deleted = 0 " +
                    "AND start_date <= GETDATE() AND end_date >= GETDATE()";
        
        try {
            ResultSet rs = myConnection.runPreparedQuery(sql, employeeID);
            if (rs != null && rs.next()) {
                currentContract = extractContractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }

        return currentContract;
    }
    
    private ContractDTO extractContractFromResultSet(ResultSet rs) throws SQLException {
        String contractID = rs.getString("contract_id");
        String employeeID = rs.getString("employee_id");
        LocalDate signingDate = rs.getDate("signing_date").toLocalDate();
        LocalDate startDate = rs.getDate("start_date").toLocalDate();
        LocalDate endDate = rs.getDate("end_date").toLocalDate();
        String degree = rs.getString("degree");
        float experienceYears = rs.getFloat("experience_years");
        String position = rs.getString("position");
        String workDescription = rs.getString("work_description");
        BigDecimal baseSalary = rs.getBigDecimal("base_salary");
        boolean isDeleted = rs.getBoolean("is_deleted");
        
        return new ContractDTO(contractID, employeeID, signingDate, startDate, endDate, degree, experienceYears, position, workDescription, baseSalary, isDeleted);
    }
}
