/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.AttendanceDTO;
import com.pharmacy.app.DTO.WorkShiftDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkShiftDAO implements DAOinterface<WorkShiftDTO>{
    MyConnection myconnect = new MyConnection();

    public String generateShiftId() {
        String nextId = "S1"; // Mặc định nếu bảng chưa có dữ liệu
        if (myconnect.openConnection()) {
            try {
                String sql = "select top 1 shift_id from work_shifts order by shift_id DESC";
                ResultSet rs = myconnect.runQuery(sql);

                if (rs.next()) {
                    String lastId = rs.getString("shift_id"); // Ví dụ: ATT012

                    if (lastId != null) {
                        String numericPart = lastId.substring(1); // Lấy phần số: "012"
                        int lastNumber = Integer.parseInt(numericPart); // Chuyển thành int: 12
                        nextId = "S" + (lastNumber + 1); // Tạo ID mới: ATT013
                    }
                }

                rs.close(); // Đóng ResultSet sau khi dùng
            } catch (SQLException e) {
                e.printStackTrace(); // Log lỗi nếu có
            } finally {
                myconnect.closeConnection();
            }
        }
        return nextId;
    }
    
    @Override
    public boolean insert(WorkShiftDTO shift) {
        if (myconnect.openConnection()) {
            String sql = "INSERT INTO work_shifts (shift_id, start_time, end_time, is_deleted) VALUES (?, ?, ?, 0)";
            try {
                int result = myconnect.prepareUpdate(
                    sql,
                    shift.getShiftId(),
                    Time.valueOf(shift.getStartTime()), // LocalTime -> Time
                    Time.valueOf(shift.getEndTime())
                );
                return result > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return false;

    }

    @Override
    public boolean update(WorkShiftDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<WorkShiftDTO> selectAll() {
        ArrayList<WorkShiftDTO> list = new ArrayList<>();
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM work_shifts WHERE is_deleted=0";        
            ResultSet rs = myconnect.runQuery(sql);
            
            try {
                while (rs != null && rs.next()){
                    WorkShiftDTO shift = new WorkShiftDTO(
                            rs.getString(1),
                            rs.getObject(2, LocalTime.class),
                            rs.getObject(3, LocalTime.class)
                    );
                    list.add(shift);
                }
            } catch(Exception e){
                    e.printStackTrace();
            } finally{
                myconnect.closeConnection();
            }
        }
        return list;
    }

    @Override
    public WorkShiftDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<WorkShiftDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String generateNextId() {
        String nextId = "ATT001"; // Mặc định nếu bảng chưa có dữ liệu
        if (myconnect.openConnection()) {
            try {
                String sql = "SELECT MAX(attendance_id) AS max_id FROM attendance";
                ResultSet rs = myconnect.runQuery(sql);

                if (rs.next()) {
                    String lastId = rs.getString("max_id"); // Ví dụ: ATT012

                    if (lastId != null) {
                        String numericPart = lastId.substring(3); // Lấy phần số: "012"
                        int lastNumber = Integer.parseInt(numericPart); // Chuyển thành int: 12
                        nextId = "ATT" + String.format("%03d", lastNumber + 1); // Tạo ID mới: ATT013
                    }
                }

                rs.close(); // Đóng ResultSet sau khi dùng
            } catch (SQLException e) {
                e.printStackTrace(); // Log lỗi nếu có
            }
        }
        return nextId;
    }

    public boolean isCheckIn(String employeeID){
        boolean result = false;
        if (myconnect.openConnection()){
            String query = "SELECT COUNT(*) FROM attendance "
                    + "WHERE employee_id = ? "
                    + "AND check_in IS NOT NULL "
                    + "AND work_date = CAST(GETDATE() AS DATE)";
            ResultSet rs = myconnect.prepareQuery(query, employeeID);
            try {
                if(rs.next()){
                    result = rs.getInt(1) > 0;
                }
                rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return result; 
    }
    public boolean isCheckOut(String employeeID){
        boolean result = false;
        if (myconnect.openConnection()){
            String query = "SELECT COUNT(*) FROM attendance "
                    + "WHERE employee_id = ? "
                    + "AND check_in IS NOT NULL AND check_out IS NOT NULL "
                    + "AND work_date = CAST(GETDATE() AS DATE)";
            ResultSet rs = myconnect.prepareQuery(query, employeeID);
            try {
                if(rs.next()){
                    result = rs.getInt(1) > 0;
                }
                rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return result; 
    }
    public boolean hasAttendanceStatus(String employeeID, String type) {
        boolean result = false;
        if (myconnect.openConnection()) {
            String query = "";
            if ("checkin".equalsIgnoreCase(type)) {
                query = "SELECT COUNT(*) FROM attendance "
                      + "WHERE employee_id = ? "
                      + "AND check_in IS NOT NULL "
                      + "AND work_date = CAST(GETDATE() AS DATE)";
            } else if ("checkout".equalsIgnoreCase(type)) {
                query = "SELECT COUNT(*) FROM attendance "
                      + "WHERE employee_id = ? "
                      + "AND check_in IS NOT NULL AND check_out IS NOT NULL "
                      + "AND work_date = CAST(GETDATE() AS DATE)";
            } else {
                System.out.println("Invalid attendance check type: " + type);
                return false;
            }

            ResultSet rs = myconnect.prepareQuery(query, employeeID);
            try {
                if (rs.next()) {
                    result = rs.getInt(1) > 0;
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return result;
    }

    public boolean checkIn(AttendanceDTO t){
        boolean result = false;
        if (myconnect.openConnection()){
            String newId = generateNextId();
            t.setId(newId);
            System.out.println(newId);
            String query = "INSERT INTO attendance (attendance_id, employee_id, work_date, check_in, check_out) "
                    + "VALUES (?, ?, ?, ?, ?)";
            int rowsAffected = myconnect.prepareUpdate(
                query, 
                t.getId(),
                t.getEmployeeId(),
                Date.valueOf(LocalDate.now()),
                Time.valueOf(LocalTime.now()),
                null
//                t.isIsDeleted() ? 1 : 0  // Include is_deleted field
            );
            result = rowsAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }
    
    public boolean checkOut(String employeeID){
        boolean result = false;
        if (myconnect.openConnection()){
            String query = "UPDATE A "
                    + "SET A.check_out = ? "
                    + "FROM attendance A "
                    + "INNER JOIN ( "
                    + "SELECT TOP 1 attendance_id "
                    + "FROM attendance "
                    + "WHERE employee_id = ? "
                    + "AND check_out IS NULL "
                    + "AND work_date = ? "
                    + "ORDER BY check_in DESC "
                    + ") T ON A.attendance_id = T.attendance_id";

            int rowAffected = myconnect.prepareUpdate(
                    query,
                    Time.valueOf(LocalTime.now()),
                    employeeID,
                    Date.valueOf(LocalDate.now())
            );
            result = rowAffected > 0;
            myconnect.closeConnection();
        }
        return result;
    }
}
