/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.RoleDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class RoleDAO implements DAOinterface<RoleDTO> {
    private MyConnection myConnection;
    
    public RoleDAO() {
        myConnection = new MyConnection();
    }

    @Override
    public boolean insert(RoleDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(RoleDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<RoleDTO> selectAll() {
        ArrayList<RoleDTO> roleList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM roles WHERE is_deleted = 0";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                RoleDTO role = extractRoleFromResultSet(rs);
                roleList.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return roleList;
    }

    @Override
    public RoleDTO selectByID(String roleID) {
        RoleDTO employee = null;
        myConnection.openConnection();
        String query = "SELECT * FROM roles WHERE role_id = ? AND is_deleted = 0";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, roleID);
            if (rs.next()) {
                employee = extractRoleFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return employee;
    }

    @Override
    public ArrayList<RoleDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private RoleDTO extractRoleFromResultSet(ResultSet rs) throws SQLException {
        String roleID = rs.getString("role_id");
        String roleName = rs.getString("role_name");
        String description = rs.getString("description");
        boolean isDeleted = rs.getBoolean("is_deleted");
        
        return new RoleDTO(roleID, roleName, description, isDeleted);
    }
}
