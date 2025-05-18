/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.EmployeeDAO;
import com.pharmacy.app.DTO.EmployeeDTO;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class EmployeeBUS {
    public EmployeeDAO employeeDAO;
    private ArrayList<EmployeeDTO> employeeList;
    private ArrayList<EmployeeDTO> noUserIDList;
    
    public EmployeeBUS() {
        employeeDAO = new EmployeeDAO();
        employeeList = new ArrayList<>();
        noUserIDList = new ArrayList<>();
    }
    
    public ArrayList<EmployeeDTO> getEmployeeList() {
        return employeeList;
    }
    
    public ArrayList<EmployeeDTO> getAll() {
        return employeeDAO.selectAll();
    }
    
    public void loadEmployeeList() {
        employeeList = employeeDAO.selectAll();
    }
    
    public ArrayList<EmployeeDTO> getNoUserIDList() {
        return noUserIDList;
    }
    
    public void loadNoUserIDList() {
        noUserIDList = employeeDAO.selectNoUserID();
    }
    
    public boolean addEmployee(EmployeeDTO employee) {
        boolean result = employeeDAO.insert(employee);
        if (result) {
            employeeList.add(employee);
        }
        return result;
    }
    
    public boolean updateEmployee(EmployeeDTO employee) {
        boolean result = employeeDAO.update(employee);
        if (result) {
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getEmployeeID().equals(employee.getEmployeeID())) {
                    employeeList.set(i, employee);
                    break;
                }
            }
        }
        return result;
    }
    
    public boolean deleteEmployee(String employeeID) {
        boolean result = employeeDAO.delete(employeeID);
        if (result) {
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getEmployeeID().equals(employeeID)) {
                    employeeList.remove(i);
                    break;
                }
            }
        }
        return result;
    }
    
    public EmployeeDTO getEmployeeByID(String employeeID) {
        for (EmployeeDTO employee : employeeList) {
            if (employee.getEmployeeID().equals(employeeID)) {
                return employee;
            }
        }
        return employeeDAO.selectByID(employeeID);
    }
    
    public EmployeeDTO getEmployeeByUserID(String userID) {
        for (EmployeeDTO employee : employeeList) {
            if (employee.getUserID().equals(userID)) {
                return employee;
            }
        }
        return employeeDAO.selectByUserID(userID);
    }
    public ArrayList<EmployeeDTO> searchEmployees(String keyword) {
        return employeeDAO.search(keyword);
    }
    
    public ArrayList<EmployeeDTO> filterEmployeesByGender(boolean gender) {
        return employeeDAO.filterByGender(gender);
    }
    
    public String generateNewEmployeeID() {
        // Get the highest employee ID from the entire database (including deleted employees)
        int maxID = employeeDAO.getHighestEmployeeIDNumber();
        
        // Increment and format the new ID
        int newID = maxID + 1;
        return String.format("EM%03d", newID);
    }
}
