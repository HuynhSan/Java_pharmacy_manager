/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.ContractDAO;
import com.pharmacy.app.DTO.ContractDTO;
import java.util.ArrayList;

/**
 *
 * @author phong
 */
public class ContractBUS {
    public ContractDAO contractDAO;
    private ArrayList<ContractDTO> contractList;
    
    public ContractBUS() {
        contractDAO = new ContractDAO();
        contractList = new ArrayList<>();
    }
    
    public ArrayList<ContractDTO> getContractList() {
        return contractList;
    }
    
    public void loadContractList() {
        contractList = contractDAO.selectAll();
    }
    
    public boolean addContract(ContractDTO contract) {
        boolean result = contractDAO.insert(contract);
        if (result) {
            contractList.add(contract);
        }
        return result;
    }
    
    public boolean updateContract(ContractDTO contract) {
        boolean result = contractDAO.update(contract);
        if (result) {
            for (int i = 0; i < contractList.size(); i++) {
                if (contractList.get(i).getContractID().equals(contract.getContractID())) {
                    contractList.set(i, contract);
                    break;
                }
            }
        }
        return result;
    }
    
    public boolean deleteContract(String contractID) {
        boolean result = contractDAO.delete(contractID);
        if (result) {
            for (int i = 0; i < contractList.size(); i++) {
                if (contractList.get(i).getContractID().equals(contractID)) {
                    contractList.remove(i);
                    break;
                }
            }
        }
        return result;
    }
    
    public ContractDTO getContractByID(String contractID) {
        for (ContractDTO contract : contractList) {
            if (contract.getContractID().equals(contractID)) {
                return contract;
            }
        }
        return contractDAO.selectByID(contractID);
    }
    
    public ArrayList<ContractDTO> searchContracts(String keyword) {
        return contractDAO.search(keyword);
    }
    
    public String generateNewContractID() {
        // Get the highest contract ID from the entire database (including deleted contracts)
        int maxID = contractDAO.getHighestContractIDNumber();
        
        // Increment and format the new ID
        int newID = maxID + 1;
        return String.format("CT%03d", newID);
    }
    
    /**
     * Gets the latest contract for an employee
     * @param employeeID The employee ID to find the latest contract for
     * @return The latest contract or null if no contracts exist
     */
    public ContractDTO getLatestEmployeeContract(String employeeID) {
        try {
            ArrayList<ContractDTO> employeeContracts = contractDAO.getContractsByEmployeeID(employeeID);
            if (employeeContracts == null || employeeContracts.isEmpty()) {
                return null;
            }

            // Sort contracts by end date in descending order (latest first)
            employeeContracts.sort((c1, c2) -> {
                // Handle null end dates (indefinite contracts)
                if (c1.getEndDate() == null) return -1;
                if (c2.getEndDate() == null) return 1;
                return c2.getEndDate().compareTo(c1.getEndDate());
            });

            // Return the latest contract
            return employeeContracts.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
