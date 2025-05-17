/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.RequestDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class RequestDAO implements DAOinterface<RequestDTO>{
    MyConnection myconnect = new MyConnection();

    @Override
    public boolean insert(RequestDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(RequestDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<RequestDTO> selectAll() {
        ArrayList<RequestDTO> reqList = new ArrayList<>();
        try {
            if(myconnect.openConnection()){
                String sql = "SELECT * FROM requests WHERE is_deleted = 0";
                PreparedStatement ps = myconnect.con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
             
            while(rs.next()){
                       RequestDTO req = new RequestDTO();
                       req.setRequestId(rs.getString("request_id"));
                       req.setRequestType(rs.getString("request_type"));
                       req.setEmployeeId(rs.getString("employee_id"));
                       req.setRequestDate(rs.getDate("request_date").toLocalDate());
                       req.setLeaveStartDate(rs.getDate("leave_start_date").toLocalDate());
                       if(rs.getDate("leave_end_date") != null){
                           req.setLeaveEndDate(rs.getDate("leave_end_date").toLocalDate());
                       }else{
                           req.setLeaveEndDate(null);
                       }
                       if(rs.getString("manager_id") != null){
                           req.setManagerId(rs.getString("manager_id"));
                       }else{
                           req.setManagerId(null);
                       }
                       if(rs.getDate("response_date") != null){
                           req.setResponseDate(rs.getDate("response_date").toLocalDate());
                       }else{
                           req.setResponseDate(null);
                       }
                       req.setReason(rs.getString("reason"));
                       req.setStatus(rs.getString("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            myconnect.closeConnection();
        }
        return reqList;
    }

    @Override
    public RequestDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<RequestDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
