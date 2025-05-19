/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.SalaryComponentsDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class SalaryComponentsDAO {
    private MyConnection myConnection;
    
    public SalaryComponentsDAO() {
        myConnection = new MyConnection();
    }
    
    /**
     * Retrieves all salary components that are not marked as deleted
     * @return ArrayList of all active salary components
     */
    public ArrayList<SalaryComponentsDTO> selectAll() {
        ArrayList<SalaryComponentsDTO> componentsList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM salary_components WHERE is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                SalaryComponentsDTO component = extractComponentFromResultSet(rs);
                componentsList.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return componentsList;
    }
    
    /**
     * Retrieves a specific salary component by its ID
     * @param componentID The ID of the salary component to retrieve
     * @return The salary component if found, null otherwise
     */
    public SalaryComponentsDTO selectByID(String componentID) {
        SalaryComponentsDTO component = null;
        myConnection.openConnection();
        String query = "SELECT * FROM salary_components WHERE component_id = ? AND is_deleted = 0";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, componentID);
            if (rs.next()) {
                component = extractComponentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return component;
    }
    
    /**
     * Extract salary component information from a ResultSet
     * @param rs The ResultSet containing salary component data
     * @return A SalaryComponentsDTO object populated with data from the ResultSet
     * @throws SQLException If there's an error accessing the ResultSet
     */
    private SalaryComponentsDTO extractComponentFromResultSet(ResultSet rs) throws SQLException {
        String componentID = rs.getString("component_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        boolean isDeleted = rs.getBoolean("is_deleted");
        
        return new SalaryComponentsDTO(componentID, name, description, isDeleted);
    }
}
