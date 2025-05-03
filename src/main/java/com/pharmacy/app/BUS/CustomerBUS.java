package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.CustomerDAO;
import com.pharmacy.app.DTO.CustomerDTO;
import java.util.ArrayList;

/**
 *
 * @author BOI QUAN
 */
public class CustomerBUS {
    private CustomerDAO customerDAO;
    private ArrayList<CustomerDTO> customerList;
    public CustomerBUS(){
        customerDAO = new CustomerDAO();
        customerList = new ArrayList<>();
    }
    public ArrayList<CustomerDTO> getCustomerList() {
        return customerList;
    }
    
    public void loadCustomerList() {
        customerList = customerDAO.selectAll();
    }
    
    public CustomerDTO getCustomerByID(String customerID) {
        for (CustomerDTO customer : customerList) {
            if (customer.getId().equals(customerID)) {
                return customer;
            }
        }
        return customerDAO.selectByID(customerID);
    }
    
    public String generateNextId(){
        return customerDAO.generateNextId();
    }
    
    public ArrayList<CustomerDTO> getAllCustomers(){
        return this.customerList;
    }
    
    public boolean addCustomer(CustomerDTO customer){
        boolean check = customerDAO.insert(customer);
        if (check){
            this.customerList.add(customer);
        }
        return check;
    }
    
    public boolean updateCustomer(CustomerDTO customer){
        boolean check = customerDAO.update(customer);
        if (check){
            this.customerList.set(getIndexByCustomerId(customer.getId()), customer);
        }
        return check;
    }
    
    public boolean deleteCustomer(CustomerDTO customer){
        boolean check = customerDAO.delete(customer.getId());
        int index = getIndexByCustomerId(customer.getId());
        if (check){
            this.customerList.remove(index);
        }
        return check;
    }
    
    public ArrayList<CustomerDTO> search(String txt){
        return customerDAO.search(txt);
    }
    
    public int getIndexByCustomerId(String id) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    
    public CustomerDTO findCustomerByPhone(String phone) {
        return customerDAO.findCustomerByPhone(phone);
    }
    
}
