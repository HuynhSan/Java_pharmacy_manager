/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.WorkShiftDAO;
import com.pharmacy.app.DTO.AttendanceDTO;
import com.pharmacy.app.DTO.WorkShiftDTO;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkShiftBUS {
    WorkShiftDAO dao = new WorkShiftDAO();
    
    public ArrayList<WorkShiftDTO> selectAll() {
        return dao.selectAll();
    }
    
    public boolean checkIn(AttendanceDTO attendance){
        return dao.checkIn(attendance);
    }
    
    public boolean checkOut(String employeeID){
        return dao.checkOut(employeeID);
    }
    
    public boolean isCheckIn(String employeeID){
        return dao.isCheckIn(employeeID);
    }
    public boolean isCheckOut(String employeeID){
        return dao.isCheckOut(employeeID);
    }
}
