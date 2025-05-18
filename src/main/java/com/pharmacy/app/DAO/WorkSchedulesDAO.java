/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.AttendanceDTO;
import com.pharmacy.app.DTO.WorkScheduleDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkSchedulesDAO implements DAOinterface<WorkScheduleDTO>{
    MyConnection myconnect = new MyConnection();
    
    public String generateNewId() {
        String newId = "WS001"; // mặc định nếu chưa có khuyến mãi nào
        if (myconnect.openConnection()) {
            String sql = "SELECT TOP 1 schedule_id FROM work_schedules ORDER BY schedule_id DESC";
            try {
                ResultSet rs = myconnect.runQuery(sql);
                if (rs != null && rs.next()) {
                    String lastId = rs.getString("schedule_id"); // ví dụ: "KM015"
                    int num = Integer.parseInt(lastId.substring(2)) + 1;
                    newId = String.format("WS%03d", num);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return newId;
    }

    public Map<String, Map<LocalDate, String>> fetchScheduleForWeek(LocalDate startOfWeek, LocalDate endOfWeek) {
        Map<String, Map<LocalDate, String>> scheduleMap = new HashMap<>();

        String sql = "SELECT employee_id, work_date, shift_id FROM work_schedules " +
                     "WHERE work_date BETWEEN ? AND ? AND is_deleted = 0";

        try {
            if (myconnect.openConnection()) {
                ResultSet rs = myconnect.runPreparedQuery(sql, Date.valueOf(startOfWeek), Date.valueOf(endOfWeek));

                while (rs.next()) {
                    String empId = rs.getString("employee_id");
                    LocalDate date = rs.getDate("work_date").toLocalDate();
                    String shift = rs.getString("shift_id");

                    scheduleMap.putIfAbsent(empId, new HashMap<>());
                    scheduleMap.get(empId).put(date, shift);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scheduleMap;
    }

    @Override
    public boolean insert(WorkScheduleDTO ws) {
        boolean success = false;
        if (myconnect.openConnection()) {
            String sql = "INSERT INTO work_schedules(schedule_id, shift_id, employee_id, work_date, is_weekend, is_holiday, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
            int rows = myconnect.prepareUpdate(
                    sql,
                    ws.getScheduleId(),
                    ws.getShiftId(),
                    ws.getEmployeeId(),
                    Date.valueOf(ws.getWorkDate()),
                    ws.isIsWeekend()? 1 : 0,
                    ws.isIsHoliday()? 1 : 0,
                    ws.isIsDeleted()? 1 : 0
            );
            success = rows > 0;
            
            if (rows > 0) {
                success = true;
            } else {
                System.err.println("Insert work schedule failed for employee: " + ws.getEmployeeId() + " on date " + ws.getWorkDate());
            }
                myconnect.closeConnection();   
        }
        return success;
    }

    @Override
    public boolean update(WorkScheduleDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<WorkScheduleDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public WorkScheduleDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<WorkScheduleDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean exists(String employeeId, LocalDate workDate) {
        boolean exists = false;

        if (myconnect.openConnection()) {
            String sql = "SELECT * FROM work_schedules WHERE employee_id = ? AND work_date = ? AND is_deleted = 0";
            try {
                ResultSet rs = myconnect.runPreparedQuery(sql, employeeId, Date.valueOf(workDate));
                if (rs.next()) {
                    exists = true;
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace(); // hoặc log lỗi
            }
            myconnect.closeConnection();
        }

        return exists;
    }


    public boolean updateShift(String employeeId, LocalDate workDate, String shiftId) {
        boolean success = false;

        if (myconnect.openConnection()) {
            String sql = "UPDATE work_schedules SET shift_id = ? WHERE employee_id = ? AND work_date = ? AND is_deleted = 0";

            int result = myconnect.prepareUpdate(
                    sql,
                    shiftId,
                    employeeId,
                    Date.valueOf(workDate)
            );
            success = result > 0;
            myconnect.closeConnection();
        }

        return success;
    }

    
    public boolean deleteSchedulesInWeek(LocalDate startDate, LocalDate endDate) {
        boolean isSuccess = false;
        if (myconnect.openConnection()) {
            String sql = "UPDATE work_schedules SET is_deleted = 1 WHERE work_date BETWEEN ? AND ?";

            try {
                int result = myconnect.prepareUpdate(
                    sql,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate)
                );
                isSuccess = result > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            myconnect.closeConnection();
        }
        return isSuccess;
    }

    
    
}
