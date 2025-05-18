/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.WorkSchedulesDAO;
import com.pharmacy.app.DTO.AttendanceDTO;
import com.pharmacy.app.DTO.WorkScheduleDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkSchedulesBUS {
    WorkSchedulesDAO dao = new WorkSchedulesDAO();
    
    public String generateNewId() {
        return dao.generateNewId();
    }
    
    public Map<String, Map<LocalDate, String>> getScheduleForWeek(LocalDate startOfWeek, LocalDate endOfWeek) {
        return dao.fetchScheduleForWeek(startOfWeek, endOfWeek);
    }
    

    public boolean add(WorkScheduleDTO ws) {
        return dao.insert(ws);
    }

    public boolean updateShift(String employeeId, LocalDate workDate, String shiftId) {
        return dao.updateShift(employeeId, workDate, shiftId);
    }
    
    public boolean existsSchedule(String employeeId, LocalDate workDate) {
        return dao.exists(employeeId, workDate);
    }

    public boolean deleteSchedulesInWeek(LocalDate startDate, LocalDate endDate) {
        return dao.deleteSchedulesInWeek(startDate, endDate);
    }
    
}
