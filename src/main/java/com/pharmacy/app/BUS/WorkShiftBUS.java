/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.BUS;

import com.pharmacy.app.DAO.WorkShiftDAO;
import com.pharmacy.app.DTO.WorkShiftDTO;
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

}
