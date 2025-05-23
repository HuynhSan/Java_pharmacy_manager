/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class UserDAO implements DAOinterface<UserDTO> {
    private MyConnection myConnection;
    
    public UserDAO() {
        myConnection = new MyConnection();
    }

    @Override
    public boolean insert(UserDTO user) {
        myConnection.openConnection();
        String query = "INSERT INTO users(user_id, username, password, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        int result = myConnection.prepareUpdate(query, 
                user.getUserID(),
                user.getUsername(),
                user.getPassword(),
                user.getRoleID(),
                user.getStatus());
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public boolean update(UserDTO user) {
        myConnection.openConnection();
        String query = "UPDATE users SET username = ?, password = ?, role_id = ?, status = ? WHERE user_id = ?";
        int result = myConnection.prepareUpdate(query, 
                user.getUsername(),
                user.getPassword(),
                user.getRoleID(),
                user.getStatus(),
                user.getUserID());
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public boolean delete(String userID) {
        // Soft delete by setting status = true
        myConnection.openConnection();
        String query = "UPDATE users SET status = 0 WHERE user_id = ?";
        int result = myConnection.prepareUpdate(query, userID);
        myConnection.closeConnection();
        return result > 0;
    }

    @Override
    public ArrayList<UserDTO> selectAll() {
        ArrayList<UserDTO> userList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM users";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                UserDTO user = extractUserFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return userList;
    }

    @Override
    public UserDTO selectByID(String userID) {
        UserDTO user = null;
        myConnection.openConnection();
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            ResultSet rs = myConnection.runPreparedQuery(query, userID);
            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return user;
    }

    @Override
    public ArrayList<UserDTO> search(String keyword) {
        ArrayList<UserDTO> userList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT u.* FROM users u " +
                   "JOIN roles r ON u.role_id = r.role_id " +
                   "WHERE u.user_id LIKE '%" + keyword + "%' OR " +
                   "u.username LIKE '%" + keyword + "%' OR " +
                   "r.role_name LIKE '%" + keyword + "%'";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                UserDTO user = extractUserFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return userList;
    }
    
    public ArrayList<UserDTO> filterByStatus(boolean status) {
        ArrayList<UserDTO> userList = new ArrayList<>();
        myConnection.openConnection();
        String query = "SELECT * FROM users WHERE status = " + (status ? "1" : "0");
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                UserDTO user = extractUserFromResultSet(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }
        return userList;
    }
    
    public int getHighestUserIDNumber() {
        int maxID = 0;
        myConnection.openConnection();
        // Query to select all user IDs, including deleted ones
        String query = "SELECT user_id FROM users";
        ResultSet rs = myConnection.runQuery(query);
        try {
            while (rs.next()) {
                String id = rs.getString("user_id");
                if (id != null && id.startsWith("USER")) {
                    try {
                        int idNum = Integer.parseInt(id.substring(4));
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
        System.out.println("Max ID: "+maxID);
        return maxID;
    }
    
    public boolean isUsernameExists(String username) {
        myConnection.openConnection();
        String query = "SELECT COUNT(*) as count FROM users WHERE username = '" + username + "'";
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
     * Authenticates a user with the provided username and password
     * @param username The username to check
     * @param password The password to check
     * @return UserDTO object if credentials are valid, null otherwise
     */
    public UserDTO checkLogin(String username, String password) {
        UserDTO user = null;
        myConnection.openConnection();

        try {
            // Escape any single quotes in the username and password to prevent SQL injection
            String safeUsername = username.replace("'", "''");
            String safePassword = password.replace("'", "''");

            String query = "SELECT * FROM users WHERE username = '" + safeUsername + 
                          "' AND password = '" + safePassword + "' AND status = 1";

            ResultSet rs = myConnection.runQuery(query);

            if (rs != null && rs.next()) {
                // User found with matching credentials
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myConnection.closeConnection();
        }

        return user;
    }
    
    private UserDTO extractUserFromResultSet(ResultSet rs) throws SQLException {
        String userID = rs.getString("user_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String roleID = rs.getString("role_id");
        boolean status = rs.getBoolean("status");
        
        return new UserDTO(userID, username, password, roleID, status);
    }

    public String getUserNameById(String userId) {
        String name = "";
        if (myConnection.openConnection()){
            String sql = "SELECT name FROM employees WHERE user_id = ?";
            ResultSet rs = myConnection.prepareQuery(sql, userId);
            try {
                while (rs != null && rs.next()){
                    name = rs.getString(1);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myConnection.closeConnection();
            }
        }
        return name;
    }
}
