/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import com.pharmacy.app.DTO.AttendanceDTO;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.DTO.WorkShiftDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Giai Cuu Li San
 */
public class ScheduleTableHelper {

    public static void buildScheduleTable(
        JTable table,
        ArrayList<EmployeeDTO> employees,
        List<LocalDate> weekDates,
        Map<String, Map<LocalDate, String>> scheduleMap,
        List<String> shiftId
    ) {
        // Tạo tiêu đề cột: "Nhân viên", Thứ 2 đến CN
        String[] columns = new String[weekDates.size() + 1];
        columns[0] = "Nhân viên";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        for (int i = 0; i < weekDates.size(); i++) {
            columns[i + 1] = weekDates.get(i).format(formatter);
        }

        // Tạo dữ liệu bảng
        Object[][] data = new Object[employees.size()][columns.length];

        for (int row = 0; row < employees.size(); row++) {
            EmployeeDTO emp = employees.get(row);
            data[row][0] = emp.getName();

            for (int col = 1; col < columns.length; col++) {
                LocalDate date = weekDates.get(col - 1);
                // Lấy ca làm nếu có, nếu không thì mặc định "Nghỉ"
                String shift = scheduleMap.getOrDefault(emp.getEmployeeID(), new HashMap<>())
                        .getOrDefault(date, "OFF");
                data[row][col] = shift;
            }
        }

        // Tạo model cho table
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép chỉnh sửa tất cả cột ngoài cột tên nhân viên
                return column != 0;
            }
        };

        table.setModel(model);

        String[] shiftsArray = shiftId.toArray(new String[0]);

        
        // Set custom cell editor: JComboBox cho các cột ca làm việc
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 1; i < columns.length; i++) {
            TableColumn col = columnModel.getColumn(i);
            JComboBox<String> comboBox = new JComboBox<>(shiftsArray);
            col.setCellEditor(new DefaultCellEditor(comboBox));
        }

        // Một số tuỳ chỉnh (tuỳ bạn thêm nếu muốn)
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
    }
    
    public static void buildAttendanceTable(
        JTable table,
        ArrayList<EmployeeDTO> employees,
        List<LocalDate> weekDates,
        Map<String, Map<LocalDate, AttendanceDTO>> attendanceMap
    ) {
        // Tạo tiêu đề cột: "Nhân viên", Thứ 2 đến CN
        String[] columns = new String[weekDates.size() + 1];
        columns[0] = "Nhân viên";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        for (int i = 0; i < weekDates.size(); i++) {
            columns[i + 1] = weekDates.get(i).format(formatter);
        }

        // Tạo dữ liệu bảng
        Object[][] data = new Object[employees.size()][columns.length];

        for (int row = 0; row < employees.size(); row++) {
            EmployeeDTO emp = employees.get(row);
            data[row][0] = emp.getName();

            for (int col = 1; col < columns.length; col++) {
                LocalDate date = weekDates.get(col - 1);
                
                AttendanceDTO attendance = null;
                
                if (attendanceMap.containsKey(emp.getEmployeeID())) {
                    attendance = attendanceMap.get(emp.getEmployeeID()).get(date);
                }

                if (attendance != null && attendance.getCheckIn() != null) {
                    String timeRange = attendance.getCheckIn().toString();
                    if (attendance.getCheckOut() != null){
                        timeRange += " - " + attendance.getCheckOut().toString();
                    }
                    data[row][col] = timeRange;
                } else {
                    data[row][col] = ""; // Chưa chấm công
                }
                
            }
        }

        // Tạo model cho table
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép chỉnh sửa tất cả cột ngoài cột tên nhân viên
                return false;
            }
        };

        table.setModel(model);
    }
}
