/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.AttendanceDAO;
import com.pharmacy.app.DTO.AttendanceDTO;
import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author Giai Cuu Li San
 */
public class AttendanceBUS {
    AttendanceDAO dao = new AttendanceDAO();
    
    public Map<String, Map<LocalDate, AttendanceDTO>> getAttendanceForWeek(LocalDate start, LocalDate end) {
        return dao.getAttendanceForWeek(start, end);
    }
}
