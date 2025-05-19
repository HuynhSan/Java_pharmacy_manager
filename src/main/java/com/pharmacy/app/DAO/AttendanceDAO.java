/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.AttendanceDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Giai Cuu Li San
 */
public class AttendanceDAO implements DAOinterface<AttendanceDTO>{
    MyConnection myconnect = new MyConnection();
    
    public Map<String, Map<LocalDate, AttendanceDTO>> getAttendanceForWeek(LocalDate start, LocalDate end) {
        Map<String, Map<LocalDate, AttendanceDTO>> result = new HashMap<>();

        if (myconnect.openConnection()) {
            String sql = "SELECT * FROM attendance WHERE work_date BETWEEN ? AND ?";

            try {
                ResultSet rs = myconnect.runPreparedQuery(sql, Date.valueOf(start), Date.valueOf(end));

                while (rs.next()) {
                    String employeeId = rs.getString("employee_id");
                    LocalDate workDate = rs.getDate("work_date").toLocalDate();
                    Time checkIn = rs.getTime("check_in");
                    Time checkOut = rs.getTime("check_out");
                    String id = rs.getString("attendance_id");

                    AttendanceDTO dto = new AttendanceDTO(id, employeeId, workDate, checkIn, checkOut);

                    result.putIfAbsent(employeeId, new HashMap<>());
                    result.get(employeeId).put(workDate, dto);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            myconnect.closeConnection();
        }

        return result;
    }

    @Override
    public boolean insert(AttendanceDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(AttendanceDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<AttendanceDTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AttendanceDTO selectByID(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<AttendanceDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<AttendanceDTO> getAttendanceByEmployeeAndMonth(String employeeID, YearMonth month) {
        ArrayList<AttendanceDTO> attendanceList = new ArrayList<>();

        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        String sql = "SELECT * FROM attendance WHERE employee_id = ? " +
                     "AND work_date >= ? AND work_date <= ? AND is_deleted = 0";

        if (myconnect.openConnection()) {
            try {
                ResultSet rs = myconnect.runPreparedQuery(
                    sql,
                    employeeID,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate)
                );

                while (rs.next()) {
                    String id = rs.getString("attendance_id");
                    LocalDate workDate = rs.getDate("work_date").toLocalDate();
                    Time checkIn = rs.getTime("check_in");
                    Time checkOut = rs.getTime("check_out");

                    AttendanceDTO dto = new AttendanceDTO(id, employeeID, workDate, checkIn, checkOut);
                    attendanceList.add(dto);
                }

                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return attendanceList;
    }
   

}
