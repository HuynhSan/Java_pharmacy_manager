/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.WorkSchedule;

import com.pharmacy.app.BUS.AttendanceBUS;
import com.pharmacy.app.BUS.EmployeeBUS;
import com.pharmacy.app.BUS.WorkSchedulesBUS;
import com.pharmacy.app.BUS.WorkShiftBUS;
import com.pharmacy.app.DTO.AttendanceDTO;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.DTO.WorkScheduleDTO;
import com.pharmacy.app.DTO.WorkShiftDTO;
import com.pharmacy.app.Utils.ScheduleTableHelper;
import com.pharmacy.app.Utils.WeekUltils;
import java.awt.Frame;
import java.awt.Window;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author phong
 */
public class WorkScheduleManagement extends javax.swing.JPanel {
    private List<LocalDate> weekDates;  // Biến toàn cục lưu tuần hiện tại được chọn
    private List<LocalDate> weekDatesAttendance;
    private Set<LocalDate> holidays = new HashSet<>();


    EmployeeBUS employeeBus = new EmployeeBUS(); 
    WorkShiftBUS shiftBUS = new WorkShiftBUS();
    WorkSchedulesBUS scheduleBUS = new WorkSchedulesBUS();
    AttendanceBUS attendanceBUS = new AttendanceBUS();
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    List<String> shiftID = shiftBUS.getAllShiftIds();
    ArrayList<EmployeeDTO> employees = employeeBus.getAll();

    /**
     * Creates new form WorkScheduleManagement
     */
    public WorkScheduleManagement() {
        initComponents();
        showWorkShift();
        centerTableContent(tblShiftList);
        WeekUltils.populateWeeksComboBox(cbWeekPicker, 48, startDate);
        WeekUltils.populateWeeksComboBox(cbWeekPicker1, 48, startDate);

        String selectedWeek = cbWeekPicker.getSelectedItem().toString();
        String selectedWeekAttendance = cbWeekPicker1.getSelectedItem().toString();

        weekDates = getWeekDatesFromComboBox(selectedWeek);
        weekDatesAttendance = getWeekDatesFromComboBox(selectedWeekAttendance);
        
        reloadScheduleTable();
        reloadAttendanceTable();
    }
    
    

    public List<LocalDate> getWeekDatesFromComboBox(String selectedWeek) {
        // selectedWeek ví dụ: "07/04/2025 - 13/04/2025"
        String[] parts = selectedWeek.split(" - ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate start = LocalDate.parse(parts[0], formatter); // thứ 2
        // tạo list ngày từ thứ 2 -> CN
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(start.plusDays(i));
        }
        return dates;
    }


    private void centerTableContent(JTable table) {
        // Căn giữa tiêu đề
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Căn giữa nội dung
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    private void showWorkShift(){
        ArrayList<WorkShiftDTO> list = shiftBUS.selectAll();

        DefaultTableModel model = (DefaultTableModel) tblShiftList.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (WorkShiftDTO shift : list) {
            Object[] row = new Object[] {
                shift.getShiftId(),
                shift.getStartTime(),
                shift.getEndTime()
            };
            model.addRow(row);
        }
    }
    
    public boolean saveSchedule(
        JTable table,
        ArrayList<EmployeeDTO> employees,
        List<LocalDate> weekDates,
        Set<LocalDate> holidays // ngày nghỉ lễ
    ) {
        boolean success = true;

        for (int row = 0; row < table.getRowCount(); row++) {
            String employeeId = employees.get(row).getEmployeeID();
            System.out.println(employeeId);

            for (int col = 1; col < table.getColumnCount(); col++) {
                LocalDate workDate = weekDates.get(col - 1);
                Object cellValue = table.getValueAt(row, col);
                if (cellValue == null) continue;

                String shiftId = cellValue.toString();
                if (shiftId.equalsIgnoreCase("OFF")) {
                    shiftId = "OFF"; // hoặc giá trị đặc biệt bạn định nghĩa
                }

                boolean isWeekend = (workDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                                     workDate.getDayOfWeek() == DayOfWeek.SUNDAY);
                boolean isHoliday = holidays.contains(workDate);
                
                
                boolean exists = scheduleBUS.existsSchedule(employeeId, workDate);

                if (exists) {
                    System.out.println("Cập nhật: "+ employeeId + workDate + shiftId);
                    success &= scheduleBUS.updateShift(employeeId, workDate, shiftId);
                } else {
                    WorkScheduleDTO ws = new WorkScheduleDTO(
                        scheduleBUS.generateNewId(),
                        employeeId,
                        shiftId,
                        workDate,
                        isWeekend,
                        isHoliday,
                        false
                    );
                    System.out.println("Thêm mới: "+ ws.getEmployeeId() + ws.getShiftId());
                    success &= scheduleBUS.add(ws);
                }

            }
        }

        return success;
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlSchedule = new javax.swing.JPanel();
        pnlCreateSchedule = new javax.swing.JPanel();
        lblWeekPicker = new javax.swing.JLabel();
        cbWeekPicker = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        pnlWeekSchedule = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblWeekSchedule = new javax.swing.JTable();
        pnlAttendance = new javax.swing.JPanel();
        pnlEmployeeAttendance = new javax.swing.JPanel();
        lblWeekPicker1 = new javax.swing.JLabel();
        cbWeekPicker1 = new javax.swing.JComboBox<>();
        pnlWeekAttendance = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblWeekAttendance = new javax.swing.JTable();
        pnlWorkShift = new javax.swing.JPanel();
        pnlButton = new javax.swing.JPanel();
        btnAddPermission = new javax.swing.JButton();
        btnRefeshRole1 = new javax.swing.JButton();
        pnlShiftList = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblShiftList = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        pnlSchedule.setBackground(new java.awt.Color(255, 255, 255));
        pnlSchedule.setLayout(new java.awt.BorderLayout());

        pnlCreateSchedule.setBackground(new java.awt.Color(255, 255, 255));
        pnlCreateSchedule.setPreferredSize(new java.awt.Dimension(464, 60));
        pnlCreateSchedule.setLayout(new java.awt.GridBagLayout());

        lblWeekPicker.setText("Chọn tuần:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        pnlCreateSchedule.add(lblWeekPicker, gridBagConstraints);

        cbWeekPicker.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "07/04/2025 - 13/04/2025", "14/04/2025 - 20/04/2025" }));
        cbWeekPicker.setMinimumSize(new java.awt.Dimension(161, 30));
        cbWeekPicker.setPreferredSize(new java.awt.Dimension(450, 30));
        cbWeekPicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbWeekPickerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 3.0;
        pnlCreateSchedule.add(cbWeekPicker, gridBagConstraints);

        btnSave.setText("Lưu lịch");
        btnSave.setMaximumSize(new java.awt.Dimension(72, 30));
        btnSave.setMinimumSize(new java.awt.Dimension(72, 30));
        btnSave.setPreferredSize(new java.awt.Dimension(72, 30));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 20);
        pnlCreateSchedule.add(btnSave, gridBagConstraints);

        btnDelete.setText("Xóa");
        btnDelete.setMaximumSize(new java.awt.Dimension(72, 30));
        btnDelete.setMinimumSize(new java.awt.Dimension(72, 30));
        btnDelete.setPreferredSize(new java.awt.Dimension(72, 30));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlCreateSchedule.add(btnDelete, new java.awt.GridBagConstraints());

        btnRefresh.setText("Tải lại");
        btnRefresh.setMaximumSize(new java.awt.Dimension(72, 30));
        btnRefresh.setMinimumSize(new java.awt.Dimension(72, 30));
        btnRefresh.setPreferredSize(new java.awt.Dimension(72, 30));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 30);
        pnlCreateSchedule.add(btnRefresh, gridBagConstraints);

        pnlSchedule.add(pnlCreateSchedule, java.awt.BorderLayout.NORTH);

        pnlWeekSchedule.setBackground(new java.awt.Color(255, 255, 255));
        pnlWeekSchedule.setPreferredSize(new java.awt.Dimension(650, 450));
        pnlWeekSchedule.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(550, 600));

        tblWeekSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"UP", "Ca 1", null, "Ca 2", null, null, null, null},
                {"ABC", "Ca 2", "Ca 1", null, null, null, null, null},
                {"Cho cbbox chọn ca", null, null, null, null, null, null, null},
                {"hoặc cho ghi giờ ", null, null, null, null, null, null, null}
            },
            new String [] {
                "Nhân viên", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"
            }
        ));
        tblWeekSchedule.setColumnSelectionAllowed(true);
        tblWeekSchedule.setPreferredSize(new java.awt.Dimension(690, 700));
        tblWeekSchedule.setRowHeight(30);
        tblWeekSchedule.setShowGrid(true);
        jScrollPane1.setViewportView(tblWeekSchedule);
        if (tblWeekSchedule.getColumnModel().getColumnCount() > 0) {
            tblWeekSchedule.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        pnlWeekSchedule.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlSchedule.add(pnlWeekSchedule, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Lịch làm việc", pnlSchedule);

        pnlAttendance.setBackground(new java.awt.Color(255, 255, 255));
        pnlAttendance.setLayout(new java.awt.BorderLayout());

        pnlEmployeeAttendance.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployeeAttendance.setPreferredSize(new java.awt.Dimension(240, 60));
        pnlEmployeeAttendance.setLayout(new java.awt.GridBagLayout());

        lblWeekPicker1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblWeekPicker1.setText("Chọn tuần:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        pnlEmployeeAttendance.add(lblWeekPicker1, gridBagConstraints);

        cbWeekPicker1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "07/04/2025 - 13/04/2025", "14/04/2025 - 20/04/2025" }));
        cbWeekPicker1.setPreferredSize(new java.awt.Dimension(161, 30));
        cbWeekPicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbWeekPicker1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        pnlEmployeeAttendance.add(cbWeekPicker1, gridBagConstraints);

        pnlAttendance.add(pnlEmployeeAttendance, java.awt.BorderLayout.NORTH);

        pnlWeekAttendance.setBackground(new java.awt.Color(255, 255, 255));
        pnlWeekAttendance.setPreferredSize(new java.awt.Dimension(650, 450));
        pnlWeekAttendance.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(550, 600));

        tblWeekAttendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"UP", "6:00 - 12:01", null, "13:21 - 18:00", null, null, null, null}
            },
            new String [] {
                "Nhân viên", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"
            }
        ));
        tblWeekAttendance.setPreferredSize(new java.awt.Dimension(690, 700));
        tblWeekAttendance.setRowHeight(30);
        tblWeekAttendance.setShowGrid(true);
        jScrollPane2.setViewportView(tblWeekAttendance);
        if (tblWeekAttendance.getColumnModel().getColumnCount() > 0) {
            tblWeekAttendance.getColumnModel().getColumn(0).setPreferredWidth(150);
        }

        pnlWeekAttendance.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnlAttendance.add(pnlWeekAttendance, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Chấm công", pnlAttendance);

        pnlWorkShift.setBackground(new java.awt.Color(255, 255, 255));
        pnlWorkShift.setLayout(new java.awt.BorderLayout());

        pnlButton.setBackground(new java.awt.Color(255, 255, 255));
        pnlButton.setPreferredSize(new java.awt.Dimension(320, 50));
        pnlButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));

        btnAddPermission.setText("Thêm");
        btnAddPermission.setFocusable(false);
        btnAddPermission.setMaximumSize(new java.awt.Dimension(72, 22));
        btnAddPermission.setMinimumSize(new java.awt.Dimension(72, 22));
        btnAddPermission.setPreferredSize(new java.awt.Dimension(72, 30));
        btnAddPermission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPermissionActionPerformed(evt);
            }
        });
        pnlButton.add(btnAddPermission);

        btnRefeshRole1.setText("Tải lại");
        btnRefeshRole1.setFocusable(false);
        btnRefeshRole1.setMaximumSize(new java.awt.Dimension(72, 22));
        btnRefeshRole1.setMinimumSize(new java.awt.Dimension(72, 22));
        btnRefeshRole1.setPreferredSize(new java.awt.Dimension(72, 30));
        btnRefeshRole1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshRole1ActionPerformed(evt);
            }
        });
        pnlButton.add(btnRefeshRole1);

        pnlWorkShift.add(pnlButton, java.awt.BorderLayout.NORTH);

        pnlShiftList.setBackground(new java.awt.Color(255, 255, 255));
        pnlShiftList.setPreferredSize(new java.awt.Dimension(600, 439));

        jScrollPane3.setPreferredSize(new java.awt.Dimension(580, 402));

        tblShiftList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã ca làm việc", "Giờ bắt đầu", "Giờ kết thúc"
            }
        ));
        tblShiftList.setFocusable(false);
        tblShiftList.setMinimumSize(new java.awt.Dimension(500, 80));
        tblShiftList.setPreferredSize(new java.awt.Dimension(500, 300));
        tblShiftList.setRowHeight(30);
        tblShiftList.setShowGrid(true);
        jScrollPane3.setViewportView(tblShiftList);

        javax.swing.GroupLayout pnlShiftListLayout = new javax.swing.GroupLayout(pnlShiftList);
        pnlShiftList.setLayout(pnlShiftListLayout);
        pnlShiftListLayout.setHorizontalGroup(
            pnlShiftListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
        );
        pnlShiftListLayout.setVerticalGroup(
            pnlShiftListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );

        pnlWorkShift.add(pnlShiftList, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Ca làm việc", pnlWorkShift);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cbWeekPickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbWeekPickerActionPerformed
        String selectedWeek = (String) cbWeekPicker.getSelectedItem();
        if (selectedWeek == null) return;

        weekDates = getWeekDatesFromComboBox(selectedWeek);
        reloadScheduleTable();
//        LocalDate start = weekDates.get(0);
//        LocalDate end = weekDates.get(6);
//
//        // Lấy lịch từ DB
//        Map<String, Map<LocalDate, String>> scheduleMap = scheduleBUS.getScheduleForWeek(start, end);
//
//        // Hiển thị bảng lịch
//        ScheduleTableHelper.buildScheduleTable(tblWeekSchedule, employees, weekDates, scheduleMap, shiftID);

    }//GEN-LAST:event_cbWeekPickerActionPerformed

    private void cbWeekPicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbWeekPicker1ActionPerformed
        String selectedWeek = (String) cbWeekPicker1.getSelectedItem();
        if (selectedWeek == null) return;

        weekDatesAttendance = getWeekDatesFromComboBox(selectedWeek);
        reloadAttendanceTable();
    }//GEN-LAST:event_cbWeekPicker1ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean result = saveSchedule(tblWeekSchedule, employees, weekDates, holidays);

        if (result) {
            JOptionPane.showMessageDialog(null, "Lưu lịch làm việc thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Lưu lịch làm việc thất bại, vui lòng thử lại.");
        }
        reloadScheduleTable();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        LocalDate start = weekDates.get(0);
        LocalDate end = weekDates.get(6);

        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Bạn có chắc muốn xóa toàn bộ lịch làm việc trong tuần này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = scheduleBUS.deleteSchedulesInWeek(start, end);
            if (deleted) {
                JOptionPane.showMessageDialog(null, "Đã xóa lịch làm việc tuần này.");
                // Cập nhật lại bảng sau khi xóa
                reloadScheduleTable();
            } else {
                JOptionPane.showMessageDialog(null, "Xóa lịch thất bại.");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefeshRole1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshRole1ActionPerformed
        showWorkShift();
        tblShiftList.clearSelection();
    }//GEN-LAST:event_btnRefeshRole1ActionPerformed

    private void btnAddPermissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPermissionActionPerformed
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        AddShiftDialog dialog = new AddShiftDialog((Frame) parentWindow, (shiftId, start, end) -> {

            System.out.println("Đã thêm: " + shiftId + ", " + start + " - " + end);
            boolean success = shiftBUS.addShift(new WorkShiftDTO(shiftId, start, end));
            if (success){
                JOptionPane.showMessageDialog(this, "Thêm ca làm việc thành công!");
                showWorkShift();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm ca làm việc thất bại!","Lỗi", JOptionPane.ERROR_MESSAGE);

            }
        });
        dialog.setVisible(true);

    }//GEN-LAST:event_btnAddPermissionActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        reloadScheduleTable();
        tblWeekSchedule.clearSelection();
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPermission;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefeshRole1;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbWeekPicker;
    private javax.swing.JComboBox<String> cbWeekPicker1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblWeekPicker;
    private javax.swing.JLabel lblWeekPicker1;
    private javax.swing.JPanel pnlAttendance;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlCreateSchedule;
    private javax.swing.JPanel pnlEmployeeAttendance;
    private javax.swing.JPanel pnlSchedule;
    private javax.swing.JPanel pnlShiftList;
    private javax.swing.JPanel pnlWeekAttendance;
    private javax.swing.JPanel pnlWeekSchedule;
    private javax.swing.JPanel pnlWorkShift;
    private javax.swing.JTable tblShiftList;
    private javax.swing.JTable tblWeekAttendance;
    private javax.swing.JTable tblWeekSchedule;
    // End of variables declaration//GEN-END:variables

    private void reloadScheduleTable() {
        shiftID = shiftBUS.getAllShiftIds();
        LocalDate start = weekDates.get(0);
        LocalDate end = weekDates.get(6);
        Map<String, Map<LocalDate, String>> scheduleMap = scheduleBUS.getScheduleForWeek(start, end);

        ScheduleTableHelper.buildScheduleTable(tblWeekSchedule, employees, weekDates, scheduleMap, shiftID);
        centerTableContent(tblWeekSchedule);
        showWorkShift();
        tblWeekSchedule.clearSelection();
    }
    
    private void reloadAttendanceTable() {
        LocalDate start = weekDatesAttendance.get(0);
        LocalDate end = weekDatesAttendance.get(6);
        Map<String, Map<LocalDate, AttendanceDTO>> attendanceMap = attendanceBUS.getAttendanceForWeek(start, end);

        ScheduleTableHelper.buildAttendanceTable(tblWeekAttendance, employees, weekDatesAttendance, attendanceMap);
        centerTableContent(tblWeekAttendance);
        tblWeekAttendance.clearSelection();

    }
    

}
