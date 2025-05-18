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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkShiftBUS {
    WorkShiftDAO dao = new WorkShiftDAO();
    
    public ArrayList<WorkShiftDTO> selectAll() {
        return dao.selectAll();
    }
    
    public Map<String, WorkShiftDTO> getShiftMap() {
        ArrayList<WorkShiftDTO> list = dao.selectAll();
        Map<String, WorkShiftDTO> map = new HashMap<>();
        for (WorkShiftDTO dto : list) {
            map.put(dto.getShiftId(), dto);
        }
        return map;
    }
    
    public String generateShiftId() {
        return dao.generateShiftId();
    }

    
    // Hàm lấy tất cả shiftId dưới dạng List<String>
    public List<String> getAllShiftIds() {
        List<WorkShiftDTO> shifts = dao.selectAll();
        List<String> shiftIds = new ArrayList<>();

        for (WorkShiftDTO shift : shifts) {
            shiftIds.add(shift.getShiftId());
        }

        return shiftIds;
    }
    
    public boolean checkIn(AttendanceDTO attendance){
        return dao.checkIn(attendance);
    }
    
    public boolean checkOut(String employeeID){
        return dao.checkOut(employeeID);
    }
    
    public boolean hasAttendanceStatus(String employeeID, String type){
        if ("checkin".equals(type)){
            return dao.hasAttendanceStatus(employeeID, type);
        } else {
            return dao.hasAttendanceStatus(employeeID, type);
        }
    }

    public boolean addShift(WorkShiftDTO workShiftDTO) {
        return dao.insert(workShiftDTO);
    }
}
