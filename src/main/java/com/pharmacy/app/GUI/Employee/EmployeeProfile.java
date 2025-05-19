/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.Employee;

import com.pharmacy.app.BUS.ContractBUS;
import com.pharmacy.app.BUS.EmployeeBUS;
import com.pharmacy.app.BUS.PayrollBUS;
import com.pharmacy.app.BUS.PayrollDetailsBUS;
import com.pharmacy.app.BUS.SalaryComponentsBUS;
import com.pharmacy.app.BUS.WorkSchedulesBUS;
import com.pharmacy.app.BUS.WorkShiftBUS;
import com.pharmacy.app.DTO.ContractDTO;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.DTO.PayrollDTO;
import com.pharmacy.app.DTO.PayrollDetailsDTO;
import com.pharmacy.app.DTO.SalaryComponentsDTO;
import com.pharmacy.app.DTO.SessionDTO;
import com.pharmacy.app.DTO.UserDTO;
import com.pharmacy.app.DTO.WorkShiftDTO;
import com.pharmacy.app.Utils.WeekUltils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.pharmacy.app.Utils.ScheduleTableHelper;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class EmployeeProfile extends javax.swing.JPanel {
    private List<LocalDate> weekDates;
    private final EmployeeBUS emBUS;
    private final EmployeeDTO emDTO;
    private final EmployeeDTO employee_login;
    private ContractBUS contractBUS;
    private PayrollBUS payrollBUS;
    private PayrollDetailsBUS payrollDetailsBUS;
    private String employeeId;
    private Map<String, WorkShiftDTO> shiftMap;
    private final UserDTO currentUser = SessionDTO.getCurrentUser();
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    WorkShiftBUS shiftBus = new WorkShiftBUS();
    WorkSchedulesBUS scheduleBus = new WorkSchedulesBUS();
    /**
     * Creates new form EmployeeProfile
     */
    public EmployeeProfile() {
        initComponents();
        emBUS = new EmployeeBUS();
        contractBUS = new ContractBUS();
        emDTO = new EmployeeDTO();
        payrollBUS = new PayrollBUS();
        payrollDetailsBUS = new PayrollDetailsBUS();
        setData(currentUser.getUserID());
        
        // Hiển thị cbx theo tuần
        WeekUltils.populateWeeksComboBox(cbWeekPicker1, 48, startDate);
        
        // Lấy nhân viên
        employee_login = emBUS.getEmployeeByUserID(currentUser.getUserID());
        employeeId = employee_login.getEmployeeID();
        
        // Lấy tuần hiện tại của cbx
        String selectedWeek = cbWeekPicker1.getSelectedItem().toString();
        weekDates = getWeekDatesFromComboBox(selectedWeek);
        
        // Lấy ca làm và giờ làm 
        shiftMap = shiftBus.getShiftMap();
        setDataPayroll(employeeId);
        displayPayrollDetails(employeeId);
        setDataContract(employeeId);
        showScheduleEmployee();
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
    public final void setData(String userID){
        EmployeeDTO employee = emBUS.getEmployeeByUserID(userID);
        txtEmployeeID.setText(employee.getEmployeeID());
        txtName.setText(employee.getName());
        txtPhone.setText(employee.getPhone());
        txtAddress.setText(employee.getAddress());
        txtGender.setText(employee.getGender() ? "Nam" : "Nữ");
        txtDOB.setText(employee.getDob().format(DATE_FORMAT));
        txtEmail.setText(employee.getEmail());
    }
    
    public void setDataContract(String employeeID){
        ContractDTO contract = contractBUS.getLatestEmployeeContract(employeeID);
        txtContractID1.setText(contract.getContractID());
        txtEmployeeID2.setText(contract.getEmployeeID());
        txtDegree1.setText(contract.getDegree());
        txtExperienceYears1.setText(String.valueOf(contract.getExperienceYears()));
        txtSigningDate1.setText(contract.getSigningDate().format(DATE_FORMAT));
        txtPosition1.setText(contract.getPosition());
        txtStartDate1.setText(contract.getStartDate().format(DATE_FORMAT));
        txtEndDate1.setText(contract.getEndDate().format(DATE_FORMAT));
        txtDescription1.setText(contract.getWorkDescription());
        txtBaseSalary1.setText(String.valueOf(contract.getBaseSalary()));
        txtBaseWorkDays1.setText("26");
    }
    
    public void setDataPayroll(String employeeID){
        PayrollDTO payroll = payrollBUS.getPayrollByEmpID(employeeID);
        txtEmployeeID3.setText(employeeID);
        txtTotal.setText(String.valueOf(payroll.getTotalSalary()));
        txtStatus.setText(payroll.getStatus() ? "Đã nhận" : "Chưa nhận");
        txtDate.setText(payroll.getPayDate().format(DATE_FORMAT));
    }
    public void displayPayrollDetails(String employeeID) {
        // Initialize SalaryComponentsBUS and load data
        SalaryComponentsBUS componentsBUS = new SalaryComponentsBUS();
        PayrollDTO payroll = payrollBUS.getPayrollByEmpID(employeeID);
        componentsBUS.loadComponentsList();

        // Get payroll details
        ArrayList<PayrollDetailsDTO> payrollDetails = payrollDetailsBUS.getPayrollDetailsByPayrollID(payroll.getPayrollID());

        // Set up table model
        DefaultTableModel model = (DefaultTableModel) tblPayrollComponent.getModel();
        model.setRowCount(0); // Clear existing rows

        // Format currency
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Add data to table
        for (PayrollDetailsDTO detail : payrollDetails) {
            Object[] row = new Object[3];

            // Get component name instead of ID
            SalaryComponentsDTO component = componentsBUS.getComponentByID(detail.getComponentID());
            row[0] = (component != null) ? component.getName() : detail.getComponentID();

            // Value (could be days/hours)
            row[1] = detail.getValue();

            // Amount
            row[2] = currencyFormat.format(detail.getAmount());

            model.addRow(row);
        }

        // Auto-resize columns
        tblPayrollComponent.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    public void showScheduleEmployee(){
        LocalDate start = weekDates.get(0);
        LocalDate end = weekDates.get(6);
        Map<String, Map<LocalDate, String>> scheduleMap = scheduleBus.getScheduleForWeek(start, end);
        ScheduleTableHelper.buildScheduleTableForEmployee(tblWeekSchedule,employeeId,weekDates,scheduleMap,shiftMap);
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
        pnlProfile = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pnlEmployeeProfile = new javax.swing.JPanel();
        lblEmployeeProfile = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pnlProfileFields = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblDOB = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDOB = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        lblEmpoyeeID = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txtGender = new javax.swing.JTextField();
        pnlUpdateProfile = new javax.swing.JPanel();
        btnUpdateProfile = new javax.swing.JButton();
        pnlWorkSchedule = new javax.swing.JPanel();
        pnlWeekSchedule = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblWeekSchedule = new javax.swing.JTable();
        pnlEmployeeAttendance = new javax.swing.JPanel();
        lblWeekPicker1 = new javax.swing.JLabel();
        cbWeekPicker1 = new javax.swing.JComboBox<>();
        pnlSalary = new javax.swing.JPanel();
        pnlPayrollComponent = new javax.swing.JPanel();
        spPayrollComponent = new javax.swing.JScrollPane();
        tblPayrollComponent = new javax.swing.JTable();
        pnlEmployeeInfo1 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        lblBankAccount = new javax.swing.JLabel();
        txtEmployeeID3 = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        pnlFilter = new javax.swing.JPanel();
        lblMonth = new javax.swing.JLabel();
        txtMonth = new javax.swing.JTextField();
        lblYear = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        pnlContract1 = new javax.swing.JPanel();
        pnlEmployeeContract1 = new javax.swing.JPanel();
        lblEmployeeContract1 = new javax.swing.JLabel();
        pnlEmployeeInfo2 = new javax.swing.JPanel();
        lblEmployeeID1 = new javax.swing.JLabel();
        lblDegree1 = new javax.swing.JLabel();
        txtDegree1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtExperienceYears1 = new javax.swing.JTextField();
        lblContractID1 = new javax.swing.JLabel();
        txtContractID1 = new javax.swing.JTextField();
        txtEmployeeID2 = new javax.swing.JTextField();
        pnlContractInfo1 = new javax.swing.JPanel();
        lblDescription1 = new javax.swing.JLabel();
        txtEndDate1 = new javax.swing.JTextField();
        lblSigningDate1 = new javax.swing.JLabel();
        txtSigningDate1 = new javax.swing.JTextField();
        lblPosition1 = new javax.swing.JLabel();
        txtPosition1 = new javax.swing.JTextField();
        lblStartDate1 = new javax.swing.JLabel();
        txtStartDate1 = new javax.swing.JTextField();
        lblEndDate1 = new javax.swing.JLabel();
        txtDescription1 = new javax.swing.JTextField();
        pnlSalaryTerms1 = new javax.swing.JPanel();
        lblBaseSalary1 = new javax.swing.JLabel();
        txtBaseSalary1 = new javax.swing.JTextField();
        lblBaseWorkDays1 = new javax.swing.JLabel();
        txtBaseWorkDays1 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        pnlProfile.setBackground(new java.awt.Color(255, 255, 255));
        pnlProfile.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlEmployeeProfile.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployeeProfile.setLayout(new java.awt.GridBagLayout());

        lblEmployeeProfile.setBackground(new java.awt.Color(255, 255, 255));
        lblEmployeeProfile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmployeeProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmployeeProfile.setText("THÔNG TIN NHÂN VIÊN");
        lblEmployeeProfile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblEmployeeProfile.setMaximumSize(new java.awt.Dimension(326589, 326589));
        lblEmployeeProfile.setName(""); // NOI18N
        lblEmployeeProfile.setPreferredSize(new java.awt.Dimension(500, 30));
        pnlEmployeeProfile.add(lblEmployeeProfile, new java.awt.GridBagConstraints());

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Làm mới");
        jButton1.setPreferredSize(new java.awt.Dimension(80, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        pnlEmployeeProfile.add(jButton1, gridBagConstraints);

        pnlProfileFields.setBackground(new java.awt.Color(255, 255, 255));
        pnlProfileFields.setPreferredSize(new java.awt.Dimension(516, 88));
        pnlProfileFields.setLayout(new java.awt.GridBagLayout());

        lblName.setText("Họ tên:");
        lblName.setMinimumSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(lblName, gridBagConstraints);

        lblGender.setText("Giới tính:");
        lblGender.setMinimumSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(lblGender, gridBagConstraints);

        lblEmail.setText("Email:");
        lblEmail.setMinimumSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(lblEmail, gridBagConstraints);

        lblDOB.setText("Ngày sinh:");
        lblDOB.setMinimumSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(lblDOB, gridBagConstraints);

        lblPhone.setText("Số điện thoại:");
        lblPhone.setMinimumSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(lblPhone, gridBagConstraints);

        lblAddress.setText("Địa chỉ:");
        lblAddress.setMinimumSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(lblAddress, gridBagConstraints);

        txtName.setEditable(false);
        txtName.setText("Trần Uyên Phương");
        txtName.setFocusable(false);
        txtName.setPreferredSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(txtName, gridBagConstraints);

        txtEmail.setEditable(false);
        txtEmail.setText("tranuyenphuong23022003@gmail.com");
        txtEmail.setFocusable(false);
        txtEmail.setPreferredSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(txtEmail, gridBagConstraints);

        txtDOB.setEditable(false);
        txtDOB.setText("23/02/2003");
        txtDOB.setFocusable(false);
        txtDOB.setPreferredSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(txtDOB, gridBagConstraints);

        txtPhone.setEditable(false);
        txtPhone.setText("0946279238");
        txtPhone.setFocusable(false);
        txtPhone.setPreferredSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(txtPhone, gridBagConstraints);

        txtAddress.setEditable(false);
        txtAddress.setText("Chung cư C2 Lý Thường Kiệt đường Vĩnh Viễn, phường 7, quận 11, TPHCM");
        txtAddress.setFocusable(false);
        txtAddress.setPreferredSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(txtAddress, gridBagConstraints);

        lblEmpoyeeID.setText("Mã nhân viên:");
        lblEmpoyeeID.setMinimumSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(lblEmpoyeeID, gridBagConstraints);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setText("NV01");
        txtEmployeeID.setFocusable(false);
        txtEmployeeID.setPreferredSize(new java.awt.Dimension(64, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(txtEmployeeID, gridBagConstraints);

        txtGender.setEditable(false);
        txtGender.setText("Nữ");
        txtGender.setFocusable(false);
        txtGender.setPreferredSize(new java.awt.Dimension(0, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 16, 14, 14);
        pnlProfileFields.add(txtGender, gridBagConstraints);

        pnlUpdateProfile.setBackground(new java.awt.Color(255, 255, 255));

        btnUpdateProfile.setBackground(new java.awt.Color(0, 204, 51));
        btnUpdateProfile.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateProfile.setText("Cập nhật thông tin");
        btnUpdateProfile.setPreferredSize(new java.awt.Dimension(135, 30));
        btnUpdateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProfileActionPerformed(evt);
            }
        });
        pnlUpdateProfile.add(btnUpdateProfile);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUpdateProfile, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
            .addComponent(pnlEmployeeProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlProfileFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(pnlEmployeeProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlProfileFields, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlUpdateProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        pnlProfile.add(jPanel1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Thông tin", pnlProfile);

        pnlWorkSchedule.setBackground(new java.awt.Color(255, 255, 255));
        pnlWorkSchedule.setLayout(new java.awt.BorderLayout());

        pnlWeekSchedule.setPreferredSize(new java.awt.Dimension(650, 450));
        pnlWeekSchedule.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(550, 600));

        tblWeekSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Thứ 2", "Ca 1"},
                {"Thứ 3", null},
                {"Thứ 4", "Ca 2"},
                {"Thứ 5", null},
                {"Thứ 6", null},
                {"Thứ 7", null},
                {"Chủ nhật", null}
            },
            new String [] {
                "Ngày làm việc", "Ca làm việc"
            }
        ));
        tblWeekSchedule.setPreferredSize(new java.awt.Dimension(690, 700));
        tblWeekSchedule.setRowHeight(30);
        tblWeekSchedule.setShowGrid(true);
        jScrollPane2.setViewportView(tblWeekSchedule);

        pnlWeekSchedule.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnlWorkSchedule.add(pnlWeekSchedule, java.awt.BorderLayout.CENTER);

        pnlEmployeeAttendance.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployeeAttendance.setPreferredSize(new java.awt.Dimension(240, 60));
        pnlEmployeeAttendance.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        pnlEmployeeAttendance.add(cbWeekPicker1, gridBagConstraints);

        pnlWorkSchedule.add(pnlEmployeeAttendance, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Lịch làm việc", pnlWorkSchedule);

        pnlSalary.setBackground(new java.awt.Color(255, 255, 255));
        pnlSalary.setMinimumSize(new java.awt.Dimension(0, 550));
        pnlSalary.setPreferredSize(new java.awt.Dimension(568, 550));

        pnlPayrollComponent.setBackground(new java.awt.Color(255, 255, 255));
        pnlPayrollComponent.setPreferredSize(new java.awt.Dimension(500, 439));
        pnlPayrollComponent.setLayout(new java.awt.BorderLayout());

        spPayrollComponent.setMinimumSize(new java.awt.Dimension(452, 20));

        tblPayrollComponent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Lương cơ bản", "26 ngày", "5,000,000"},
                {"Phụ cấp ngoài giờ/OT", "10 giờ", "1,000,000"},
                {"...", null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Thành phần lương", "Ngày/Giờ", "Số tiền"
            }
        ));
        tblPayrollComponent.setEnabled(false);
        tblPayrollComponent.setFocusable(false);
        tblPayrollComponent.setRowHeight(30);
        spPayrollComponent.setViewportView(tblPayrollComponent);

        pnlPayrollComponent.add(spPayrollComponent, java.awt.BorderLayout.CENTER);

        pnlEmployeeInfo1.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployeeInfo1.setLayout(new java.awt.GridBagLayout());

        lblTotal.setText("Lương thực nhận:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 20, 20);
        pnlEmployeeInfo1.add(lblTotal, gridBagConstraints);

        txtTotal.setEditable(false);
        txtTotal.setFocusable(false);
        txtTotal.setMinimumSize(new java.awt.Dimension(72, 22));
        txtTotal.setPreferredSize(new java.awt.Dimension(72, 22));
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        pnlEmployeeInfo1.add(txtTotal, gridBagConstraints);

        lblBankAccount.setText("Mã nhân viên");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlEmployeeInfo1.add(lblBankAccount, gridBagConstraints);

        txtEmployeeID3.setEditable(false);
        txtEmployeeID3.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        pnlEmployeeInfo1.add(txtEmployeeID3, gridBagConstraints);

        lblStatus.setText("Trạng thái:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        pnlEmployeeInfo1.add(lblStatus, gridBagConstraints);

        txtStatus.setEditable(false);
        txtStatus.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        pnlEmployeeInfo1.add(txtStatus, gridBagConstraints);

        lblDate.setText("Ngày trả lương:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 20);
        pnlEmployeeInfo1.add(lblDate, gridBagConstraints);

        txtDate.setEditable(false);
        txtDate.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        pnlEmployeeInfo1.add(txtDate, gridBagConstraints);

        pnlFilter.setBackground(new java.awt.Color(255, 255, 255));
        pnlFilter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        lblMonth.setText("Tháng:");
        pnlFilter.add(lblMonth);

        txtMonth.setMaximumSize(new java.awt.Dimension(64, 22));
        pnlFilter.add(txtMonth);

        lblYear.setText("Năm:");
        pnlFilter.add(lblYear);

        txtYear.setMaximumSize(new java.awt.Dimension(64, 22));
        pnlFilter.add(txtYear);

        btnFilter.setText("Lọc");
        pnlFilter.add(btnFilter);

        javax.swing.GroupLayout pnlSalaryLayout = new javax.swing.GroupLayout(pnlSalary);
        pnlSalary.setLayout(pnlSalaryLayout);
        pnlSalaryLayout.setHorizontalGroup(
            pnlSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSalaryLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPayrollComponent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addComponent(pnlEmployeeInfo1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        pnlSalaryLayout.setVerticalGroup(
            pnlSalaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSalaryLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlEmployeeInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlPayrollComponent, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Lương", pnlSalary);

        pnlContract1.setBackground(new java.awt.Color(255, 255, 255));

        pnlEmployeeContract1.setBackground(new java.awt.Color(255, 255, 255));

        lblEmployeeContract1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEmployeeContract1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmployeeContract1.setText("HỢP ĐỒNG LAO ĐỘNG");
        pnlEmployeeContract1.add(lblEmployeeContract1);

        pnlEmployeeInfo2.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployeeInfo2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlEmployeeInfo2.setLayout(new java.awt.GridBagLayout());

        lblEmployeeID1.setText("Mã nhân viên:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        pnlEmployeeInfo2.add(lblEmployeeID1, gridBagConstraints);

        lblDegree1.setText("Bằng cấp:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 20);
        pnlEmployeeInfo2.add(lblDegree1, gridBagConstraints);

        txtDegree1.setEditable(false);
        txtDegree1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 30);
        pnlEmployeeInfo2.add(txtDegree1, gridBagConstraints);

        jLabel4.setText("Số năm kinh nghiệm:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlEmployeeInfo2.add(jLabel4, gridBagConstraints);

        txtExperienceYears1.setEditable(false);
        txtExperienceYears1.setFocusable(false);
        txtExperienceYears1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExperienceYears1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        pnlEmployeeInfo2.add(txtExperienceYears1, gridBagConstraints);

        lblContractID1.setText("Mã hợp đồng:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        pnlEmployeeInfo2.add(lblContractID1, gridBagConstraints);

        txtContractID1.setEditable(false);
        txtContractID1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 30);
        pnlEmployeeInfo2.add(txtContractID1, gridBagConstraints);

        txtEmployeeID2.setEditable(false);
        txtEmployeeID2.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 10);
        pnlEmployeeInfo2.add(txtEmployeeID2, gridBagConstraints);

        pnlContractInfo1.setBackground(new java.awt.Color(255, 255, 255));
        pnlContractInfo1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hợp đồng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlContractInfo1.setLayout(new java.awt.GridBagLayout());

        lblDescription1.setText("Mô tả công việc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 20);
        pnlContractInfo1.add(lblDescription1, gridBagConstraints);

        txtEndDate1.setEditable(false);
        txtEndDate1.setFocusable(false);
        txtEndDate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEndDate1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        pnlContractInfo1.add(txtEndDate1, gridBagConstraints);

        lblSigningDate1.setText("Ngày ký kết:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        pnlContractInfo1.add(lblSigningDate1, gridBagConstraints);

        txtSigningDate1.setEditable(false);
        txtSigningDate1.setFocusable(false);
        txtSigningDate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSigningDate1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 30);
        pnlContractInfo1.add(txtSigningDate1, gridBagConstraints);

        lblPosition1.setText("Chức vụ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        pnlContractInfo1.add(lblPosition1, gridBagConstraints);

        txtPosition1.setEditable(false);
        txtPosition1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 10);
        pnlContractInfo1.add(txtPosition1, gridBagConstraints);

        lblStartDate1.setText("Ngày bắt đầu:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 20);
        pnlContractInfo1.add(lblStartDate1, gridBagConstraints);

        txtStartDate1.setEditable(false);
        txtStartDate1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 30);
        pnlContractInfo1.add(txtStartDate1, gridBagConstraints);

        lblEndDate1.setText("Ngày kết thúc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlContractInfo1.add(lblEndDate1, gridBagConstraints);

        txtDescription1.setEditable(false);
        txtDescription1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        pnlContractInfo1.add(txtDescription1, gridBagConstraints);

        pnlSalaryTerms1.setBackground(new java.awt.Color(255, 255, 255));
        pnlSalaryTerms1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điều khoản lương", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlSalaryTerms1.setLayout(new java.awt.GridBagLayout());

        lblBaseSalary1.setText("Lương cơ bản:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        pnlSalaryTerms1.add(lblBaseSalary1, gridBagConstraints);

        txtBaseSalary1.setEditable(false);
        txtBaseSalary1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 30);
        pnlSalaryTerms1.add(txtBaseSalary1, gridBagConstraints);

        lblBaseWorkDays1.setText("Số ngày làm việc cơ bản:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        pnlSalaryTerms1.add(lblBaseWorkDays1, gridBagConstraints);

        txtBaseWorkDays1.setEditable(false);
        txtBaseWorkDays1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 10);
        pnlSalaryTerms1.add(txtBaseWorkDays1, gridBagConstraints);

        javax.swing.GroupLayout pnlContract1Layout = new javax.swing.GroupLayout(pnlContract1);
        pnlContract1.setLayout(pnlContract1Layout);
        pnlContract1Layout.setHorizontalGroup(
            pnlContract1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContract1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlContract1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEmployeeContract1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addComponent(pnlEmployeeInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContractInfo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSalaryTerms1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );
        pnlContract1Layout.setVerticalGroup(
            pnlContract1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContract1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(pnlEmployeeContract1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlEmployeeInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlContractInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSalaryTerms1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(pnlContract1);

        jTabbedPane1.addTab("Hợp đồng", jScrollPane3);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSigningDate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSigningDate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSigningDate1ActionPerformed

    private void txtEndDate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndDate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndDate1ActionPerformed

    private void txtExperienceYears1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExperienceYears1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExperienceYears1ActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void cbWeekPicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbWeekPicker1ActionPerformed
        String selectedWeek = (String) cbWeekPicker1.getSelectedItem();
        if (selectedWeek == null) return;

        weekDates = getWeekDatesFromComboBox(selectedWeek);
        showScheduleEmployee();
    }//GEN-LAST:event_cbWeekPicker1ActionPerformed

    private void btnUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProfileActionPerformed
        EmployeeDTO employee = emBUS.getEmployeeByID(txtEmployeeID.getText());
        UpdateEmployee updateEmployeeDialog = new UpdateEmployee((JFrame) SwingUtilities.getWindowAncestor(this), true, employee);
        updateEmployeeDialog.setLocationRelativeTo(null);
        updateEmployeeDialog.setVisible(true);
    }//GEN-LAST:event_btnUpdateProfileActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setData(currentUser.getUserID());
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnUpdateProfile;
    private javax.swing.JComboBox<String> cbWeekPicker1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBankAccount;
    private javax.swing.JLabel lblBaseSalary1;
    private javax.swing.JLabel lblBaseWorkDays1;
    private javax.swing.JLabel lblContractID1;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDegree1;
    private javax.swing.JLabel lblDescription1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmployeeContract1;
    private javax.swing.JLabel lblEmployeeID1;
    private javax.swing.JLabel lblEmployeeProfile;
    private javax.swing.JLabel lblEmpoyeeID;
    private javax.swing.JLabel lblEndDate1;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPosition1;
    private javax.swing.JLabel lblSigningDate1;
    private javax.swing.JLabel lblStartDate1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblWeekPicker1;
    private javax.swing.JLabel lblYear;
    private javax.swing.JPanel pnlContract1;
    private javax.swing.JPanel pnlContractInfo1;
    private javax.swing.JPanel pnlEmployeeAttendance;
    private javax.swing.JPanel pnlEmployeeContract1;
    private javax.swing.JPanel pnlEmployeeInfo1;
    private javax.swing.JPanel pnlEmployeeInfo2;
    private javax.swing.JPanel pnlEmployeeProfile;
    private javax.swing.JPanel pnlFilter;
    private javax.swing.JPanel pnlPayrollComponent;
    private javax.swing.JPanel pnlProfile;
    private javax.swing.JPanel pnlProfileFields;
    private javax.swing.JPanel pnlSalary;
    private javax.swing.JPanel pnlSalaryTerms1;
    private javax.swing.JPanel pnlUpdateProfile;
    private javax.swing.JPanel pnlWeekSchedule;
    private javax.swing.JPanel pnlWorkSchedule;
    private javax.swing.JScrollPane spPayrollComponent;
    private javax.swing.JTable tblPayrollComponent;
    private javax.swing.JTable tblWeekSchedule;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtBaseSalary1;
    private javax.swing.JTextField txtBaseWorkDays1;
    private javax.swing.JTextField txtContractID1;
    private javax.swing.JTextField txtDOB;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDegree1;
    private javax.swing.JTextField txtDescription1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtEmployeeID2;
    private javax.swing.JTextField txtEmployeeID3;
    private javax.swing.JTextField txtEndDate1;
    private javax.swing.JTextField txtExperienceYears1;
    private javax.swing.JTextField txtGender;
    private javax.swing.JTextField txtMonth;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPosition1;
    private javax.swing.JTextField txtSigningDate1;
    private javax.swing.JTextField txtStartDate1;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
