/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;
import com.pharmacy.app.DAO.UserDAO;
import com.pharmacy.app.DTO.UserDTO;
import java.util.ArrayList;
/**
 *
 * @author phong
 */
public class UserBUS {
    private UserDAO userDAO;
    private ArrayList<UserDTO> userList;
    
    public UserBUS() {
        userDAO = new UserDAO();
        userList = new ArrayList<>();
    }
    
    public ArrayList<UserDTO> getUserList() {
        // Filter out USER001 from the returned list
        ArrayList<UserDTO> filteredList = new ArrayList<>();
        for (UserDTO user : userList) {
            if (!user.getUserID().equals("USER001")) {
                filteredList.add(user);
            }
        }
        return filteredList;
    }
    
    public void loadUserList() {
        userList = userDAO.selectAll();
    }
    
    public boolean addUser(UserDTO user) {
        boolean result = userDAO.insert(user);
        if (result) {
            userList.add(user);
        }
        return result;
    }
    
    public boolean updateUser(UserDTO user) {
        boolean result = userDAO.update(user);
        if (result) {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserID().equals(user.getUserID())) {
                    userList.set(i, user);
                    break;
                }
            }
        }
        return result;
    }
    
    public boolean deleteUser(String userID) {
        // Prevent deletion of USER001
        if (userID.equals("USER001")) {
            return false;
        }
        
        boolean result = userDAO.delete(userID);
        if (result) {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserID().equals(userID)) {
                    userList.remove(i);
                    break;
                }
            }
        }
        return result;
    }
    
    public UserDTO getUserByID(String userID) {
        for (UserDTO user : userList) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return userDAO.selectByID(userID);
    }
    
    public ArrayList<UserDTO> searchUsers(String keyword) {
        ArrayList<UserDTO> searchResults = userDAO.search(keyword);
        // Filter out USER001 from search results
        ArrayList<UserDTO> filteredResults = new ArrayList<>();
        for (UserDTO user : searchResults) {
            if (!user.getUserID().equals("USER001")) {
                filteredResults.add(user);
            }
        }
        return filteredResults;
    }
    
    public ArrayList<UserDTO> filterUsersByStatus(boolean status) {
        ArrayList<UserDTO> statusResults = userDAO.filterByStatus(status);
        // Filter out USER001 from status filtered results
        ArrayList<UserDTO> filteredResults = new ArrayList<>();
        for (UserDTO user : statusResults) {
            if (!user.getUserID().equals("USER001")) {
                filteredResults.add(user);
            }
        }
        return filteredResults;
    }
    
    public String generateNewUserID() {
        // Get the highest user ID from the entire database (including deleted users)
        int maxID = userDAO.getHighestUserIDNumber();
        
        // Increment and format the new ID
        int newID = maxID + 1;
        return String.format("USER%03d", newID);
    }
    
    /**
     * Authenticates a user with the provided username and password
     * @param username The username to check
     * @param password The password to check
     * @return UserDTO object if authentication is successful, null otherwise
     */
    public UserDTO checkLogin(String username, String password) {
        // First check if user is in the loaded list
        for (UserDTO user : userList) {
            if (user.getUsername().equals(username) && 
                user.getPassword().equals(password) && 
                user.getStatus()) {
                return user;
            }
        }
        // If not found in the list, query the database
        return userDAO.checkLogin(username, password);
    }

    public String getUserNameById(String userId) {
        return userDAO.getUserNameById(userId);
    }
}
