/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.WorkShiftDTO;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class WorkShiftDAO implements DAOinterface<WorkShiftDTO>{
    MyConnection myconnect = new MyConnection();

    @Override
    public boolean insert(WorkShiftDTO t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
}
