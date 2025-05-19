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
    public boolean insert(RequestDTO req) {
        boolean result = false;

        if (myconnect.openConnection()) {
            try {
                String sql = "INSERT INTO requests(request_id, request_type, employee_id, request_date, leave_start_date, leave_end_date, reason, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                // Chuyển LocalDate thành java.sql.Date
                java.sql.Date requestDate = java.sql.Date.valueOf(req.getRequestDate());
                java.sql.Date leaveStartDate = java.sql.Date.valueOf(req.getLeaveStartDate());

                // leave_end_date có thể null
                java.sql.Date leaveEndDate = null;
                if (req.getLeaveEndDate() != null) {
                    leaveEndDate = java.sql.Date.valueOf(req.getLeaveEndDate());
                }

                int rowsAffected = myconnect.prepareUpdate(sql, 
                    req.getRequestId(),
                    req.getRequestType(),
                    req.getEmployeeId(),
                    requestDate,
                    leaveStartDate,
                    leaveEndDate, // Có thể null, xử lý bên trên
                    req.getReason(),
                    req.getStatus()
                );

                if (rowsAffected > 0) {
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return result;
    }

    @Override
    public boolean update(RequestDTO req) {
        boolean result = false;

        try {
            myconnect.openConnection();

            String sql = "UPDATE requests SET request_type = ?, leave_start_date = ?, leave_end_date = ?, reason = ?, status = ?, response_date = ?, comment = ? WHERE request_id = ?";

            int rowsAffected = myconnect.prepareUpdate(sql, 
                req.getRequestType(),
                req.getLeaveStartDate(),
                req.getLeaveEndDate(),
                req.getReason(),
                req.getStatus(),
                req.getResponseDate(),
                req.getComment(),
                req.getRequestId()
            );
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
        }

        return result;
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
                       
                       reqList.add(req);
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
    public RequestDTO selectByID(String ID) {
        if(myconnect.openConnection()){
            try {
                String sql = "SELECT * FROM requests WHERE request_id = ? AND is_deleted = 0";

                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, ID);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
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
                    return req;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return null;
    }

    @Override
    public ArrayList<RequestDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<RequestDTO> selectAllByID(String emID){
        ArrayList<RequestDTO> reqList = new ArrayList<>();
        try {
            System.out.println("Mở kết nối tới cơ sở dữ liệu...");
            if(myconnect.openConnection()){
                System.out.println("Kết nối thành công.");
                String sql = "SELECT * FROM requests WHERE employee_id = ? AND is_deleted = 0";
                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, emID);
                System.out.println("Thực hiện truy vấn với employee_id = " + emID);
                ResultSet rs = ps.executeQuery();

                boolean hasData = false;
                while(rs.next()){
                    hasData = true;
                    RequestDTO req = new RequestDTO();
                    req.setRequestId(rs.getString("request_id"));
                    req.setRequestType(rs.getString("request_type"));
                    req.setEmployeeId(rs.getString("employee_id"));
                    req.setRequestDate(rs.getDate("request_date").toLocalDate());
                    req.setLeaveStartDate(rs.getDate("leave_start_date").toLocalDate());
                    if(rs.getDate("leave_end_date") != null){
                        req.setLeaveEndDate(rs.getDate("leave_end_date").toLocalDate());
                    } else {
                        req.setLeaveEndDate(null);
                    }
                    if(rs.getString("manager_id") != null){
                        req.setManagerId(rs.getString("manager_id"));
                    } else {
                        req.setManagerId(null);
                    }
                    if(rs.getDate("response_date") != null){
                        req.setResponseDate(rs.getDate("response_date").toLocalDate());
                    } else {
                        req.setResponseDate(null);
                    }
                    req.setReason(rs.getString("reason"));
                    req.setStatus(rs.getString("status"));

                    reqList.add(req);
                    System.out.println("Đã thêm request: " + req.getRequestId());
                }

                if (!hasData) {
                    System.out.println("Không có bản ghi nào phù hợp với employee_id = " + emID);
                }
            } else {
                System.out.println("Kết nối thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
            System.out.println("Đã đóng kết nối.");
        }
        return reqList;
    }

        public String getLatestProductID() {
            String latestID = null;
            if (myconnect.openConnection()) {
                try {
                    String sql = "SELECT MAX(request_id) AS max_id FROM requests";
                    ResultSet rs = myconnect.runQuery(sql);
                    if (rs.next()) {
                        latestID = rs.getString("max_id"); // Có thể là null nếu chưa có sản phẩm
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return latestID;
    }
    
    public String getEmployeeIDbyUserID(String ID) {
        String emID = null;
        try {
            if (myconnect.openConnection()) {
                String sql = "SELECT employee_id FROM employees WHERE user_id = ? AND is_deleted = 0";
                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, ID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    emID = rs.getString("employee_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
        }
        return emID;
    }
    
    public int quantityRequestNonSalary(String emID){
        int count = 0;
        try {
            if (myconnect.openConnection()) {
                String sql = "select count(*) as total_requests from requests where request_type = N'Đơn xin nghỉ phép' and status = N'Đã duyệt'  and employee_id = ? ";
                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, emID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("total_requests");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
        }
        return count;
    }
    
}

