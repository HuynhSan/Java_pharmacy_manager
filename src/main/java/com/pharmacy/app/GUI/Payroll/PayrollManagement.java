/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.Payroll;

import com.pharmacy.app.BUS.EmployeeBUS;
import com.pharmacy.app.BUS.PayrollBUS;
import com.pharmacy.app.DAO.PayrollDAO;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.DTO.PayrollDTO;
import com.pharmacy.app.DTO.SessionDTO;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author phong
 */
public class PayrollManagement extends javax.swing.JPanel {
    private PayrollBUS payrollBUS;
    private EmployeeBUS employeeBUS;
    private PayrollDAO payrollDAO = new PayrollDAO();
    private PayrollDTO payrollDTO;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Creates new form PayrollManagement
     */
    public PayrollManagement() {
        initComponents();
        initBUS();
        loadPayrollData();
        setupListeners();
        centerTableContent(tblPayrolls);
    }
    
    private void initBUS() {
        payrollBUS = new PayrollBUS();
        
        // Get current user role
        String roleID = SessionDTO.getCurrentUser().getRoleID();

        // Load different data based on role
        if ("ROLE001".equals(roleID)) {
            // Admin sees all payrolls
            payrollBUS.loadPayrollList();
        } else if ("ROLE002".equals(roleID)) {
            // Manager sees only sales employee payrolls
            payrollBUS.loadSalesEmployeesPayrollsList();
        } else {
            // Default behavior (for other roles)
            payrollBUS.loadPayrollList();
        }
        
        employeeBUS = new EmployeeBUS();
    }
    
    /**
     * Load all payroll data into the table
     */
    private void loadPayrollData() {
        ArrayList<PayrollDTO> payrolls;
    
        // Get current user role
        String roleID = SessionDTO.getCurrentUser().getRoleID();

        // Load different data based on role
        if ("ROLE001".equals(roleID)) {
            // Admin sees all payrolls
            payrolls = payrollBUS.getPayrollList();
        } else if ("ROLE002".equals(roleID)) {
            // Manager sees only sales employee payrolls
            payrolls = payrollBUS.getSalesEmployeesPayrollsList();
        } else {
            // Default behavior (for other roles)
            payrolls = payrollBUS.getPayrollList();
        }

        displayPayrolls(payrolls);
    }
    
    /**
     * Display payrolls in the table
     */
    private void displayPayrolls(ArrayList<PayrollDTO> payrolls) {
        DefaultTableModel model = (DefaultTableModel) tblPayrolls.getModel();
        model.setRowCount(0); // Clear current data

        for (PayrollDTO payroll : payrolls) {
            // Look up the employee name based on employee ID
            String employeeID = payroll.getEmployeeID();
            EmployeeDTO employee = employeeBUS.getEmployeeByID(employeeID);
            String employeeName = (employee != null) ? employee.getName() : "Unknown";

            // Check payDate for null
            String payDateStr = (payroll.getPayDate() != null)
                ? payroll.getPayDate().format(DATE_FORMAT)
                : "Chưa có";

            Object[] row = {
                payroll.getPayrollID(),
                employeeName,
                formatMoney(payroll.getTotalSalary()),
                payroll.getStatus() ? "Đã thanh toán" : "Chưa thanh toán",
                payDateStr
            };
            model.addRow(row);
        }
    }
    
    private String formatMoney(BigDecimal amount) {
        return String.format("%,.0f VNĐ", amount);
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
    
    private void setupListeners() {
        // Setup search text field focus listener
        txtSearchPayroll.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearchPayroll.getText().equals("Tìm kiếm")) {
                    txtSearchPayroll.setText("");
                    txtSearchPayroll.setFont(new java.awt.Font("Segoe UI", 0, 12));
                    txtSearchPayroll.setForeground(new java.awt.Color(0, 0, 0));
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearchPayroll.getText().isEmpty()) {
                    txtSearchPayroll.setText("Tìm kiếm");
                    txtSearchPayroll.setFont(new java.awt.Font("Segoe UI", 2, 12));
                    txtSearchPayroll.setForeground(new java.awt.Color(153, 153, 153));
                }
            }
        });
        
        // Setup search text field key listener
        txtSearchPayroll.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearchPayroll.getText();
                if (!keyword.equals("Tìm kiếm")) {
                    searchPayrolls(keyword);
                }
            }
        });
        
        // Setup combobox listener
        cbFilter.addActionListener((e) -> {
            filterPayrollsByStatus();
        });
        
        btnPay.addActionListener((evt) -> {
            handlePayAllUnpaidPayrolls();
        });

    }
    
    /**
     * Search employees based on keyword
     */
    private void searchPayrolls(String keyword) {
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm")) {
            loadPayrollData();
            return;
        }

        ArrayList<PayrollDTO> searchResults = payrollBUS.searchPayrollsByEmployeeName(keyword);

        // Get current user role
        String roleID = SessionDTO.getCurrentUser().getRoleID();

        // Filter search results based on role
        if ("ROLE002".equals(roleID)) {
            // For managers, filter the search results to only include sales employees
            ArrayList<PayrollDTO> salesEmployeeResults = new ArrayList<>();
            ArrayList<PayrollDTO> salesEmployeePayrolls = payrollBUS.getPayrollsOfSalesEmployees();

            // Only keep payrolls that are in both lists (search results and sales employee)
            for (PayrollDTO payroll : searchResults) {
                for (PayrollDTO salesPayroll : salesEmployeePayrolls) {
                    if (payroll.getPayrollID().equals(salesPayroll.getPayrollID())) {
                        salesEmployeeResults.add(payroll);
                        break;
                    }
                }
            }
            searchResults = salesEmployeeResults;
        }

        displayPayrolls(searchResults);
    }
    
    /**
     * Filter employees by gender
     */
    private void filterPayrollsByStatus() {
        String selectedOption = (String) cbFilter.getSelectedItem();

        // Get current user role
        String roleID = SessionDTO.getCurrentUser().getRoleID();

        ArrayList<PayrollDTO> filteredPayrolls;

        if (selectedOption.equals("Tất cả")) {
            if ("ROLE001".equals(roleID)) {
                // Admin sees all payrolls
                filteredPayrolls = payrollBUS.getPayrollList();
            } else if ("ROLE002".equals(roleID)) {
                // Manager sees only sales employee payrolls
                filteredPayrolls = payrollBUS.getSalesEmployeesPayrollsList();
            } else {
                // Default behavior
                filteredPayrolls = payrollBUS.getPayrollList();
            }
        } else if (selectedOption.equals("Đã thanh toán")) {
            ArrayList<PayrollDTO> paid = payrollBUS.filterPayrollsByStatus(true);

            if ("ROLE002".equals(roleID)) {
                // For managers, filter the paid list to only include sales employees
                ArrayList<PayrollDTO> salesEmployeePaid = new ArrayList<>();
                ArrayList<PayrollDTO> salesEmployeePayrolls = payrollBUS.getPayrollsOfSalesEmployees();

                // Only keep payrolls that are in both lists (paid and sales employee)
                for (PayrollDTO payroll : paid) {
                    for (PayrollDTO salesPayroll : salesEmployeePayrolls) {
                        if (payroll.getPayrollID().equals(salesPayroll.getPayrollID())) {
                            salesEmployeePaid.add(payroll);
                            break;
                        }
                    }
                }
                filteredPayrolls = salesEmployeePaid;
            } else {
                filteredPayrolls = paid;
            }
        } else if (selectedOption.equals("Chưa thanh toán")) {
            ArrayList<PayrollDTO> unpaid = payrollBUS.filterPayrollsByStatus(false);

            if ("ROLE002".equals(roleID)) {
                // For managers, filter the unpaid list to only include sales employees
                ArrayList<PayrollDTO> salesEmployeeUnpaid = new ArrayList<>();
                ArrayList<PayrollDTO> salesEmployeePayrolls = payrollBUS.getPayrollsOfSalesEmployees();

                // Only keep payrolls that are in both lists (unpaid and sales employee)
                for (PayrollDTO payroll : unpaid) {
                    for (PayrollDTO salesPayroll : salesEmployeePayrolls) {
                        if (payroll.getPayrollID().equals(salesPayroll.getPayrollID())) {
                            salesEmployeeUnpaid.add(payroll);
                            break;
                        }
                    }
                }
                filteredPayrolls = salesEmployeeUnpaid;
            } else {
                filteredPayrolls = unpaid;
            }
        } else {
            // Default case
            filteredPayrolls = payrollBUS.getPayrollList();
        }

        displayPayrolls(filteredPayrolls);
    }
    
    /**
     * Handles the payment of all unpaid payrolls
     * Updates all payrolls with status = 0 to status = 1 and refreshes the table
     */
    private void handlePayAllUnpaidPayrolls() {
        // Get all unpaid payrolls
        ArrayList<PayrollDTO> unpaidPayrolls = payrollBUS.filterPayrollsByStatus(false);

        if (unpaidPayrolls.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Không có khoản lương nào cần thanh toán!", 
                    "Thông báo", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Confirm with user
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn thanh toán " + unpaidPayrolls.size() + " khoản lương chưa được thanh toán?",
                "Xác nhận thanh toán",
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        // Update each payroll
        int updatedCount = 0;
        for (PayrollDTO payroll : unpaidPayrolls) {
            payroll.setStatus(true);
            payroll.setPayDate(java.time.LocalDateTime.now()); // Set the payment date to current time
            if (payrollBUS.updatePayroll(payroll)) {
                updatedCount++;
            }
        }

        // Show results
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Đã thanh toán thành công " + updatedCount + "/" + unpaidPayrolls.size() + " khoản lương!",
                "Thanh toán lương",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Refresh the display
        loadPayrollData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPayrollList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPayrolls = new javax.swing.JTable();
        pnlPayrollFilter = new javax.swing.JPanel();
        txtSearchPayroll = new javax.swing.JTextField();
        cbFilter = new javax.swing.JComboBox<>();
        pnlPayrollButton = new javax.swing.JPanel();
        btnPayroll = new javax.swing.JButton();
        btnPay = new javax.swing.JButton();
        btnRefesh = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout(0, 20));

        pnlPayrollList.setBackground(new java.awt.Color(255, 255, 255));
        pnlPayrollList.setPreferredSize(new java.awt.Dimension(600, 439));
        pnlPayrollList.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(670, 402));

        tblPayrolls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã lương", "Tên nhân viên", "Tổng lương", "Trạng thái", "Ngày trả"
            }
        ));
        tblPayrolls.setFocusable(false);
        tblPayrolls.setMinimumSize(new java.awt.Dimension(500, 80));
        tblPayrolls.setPreferredSize(new java.awt.Dimension(500, 80));
        tblPayrolls.setShowGrid(true);
        tblPayrolls.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPayrollsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPayrolls);

        pnlPayrollList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(pnlPayrollList, java.awt.BorderLayout.CENTER);

        pnlPayrollFilter.setBackground(new java.awt.Color(255, 255, 255));
        pnlPayrollFilter.setToolTipText("");
        pnlPayrollFilter.setPreferredSize(new java.awt.Dimension(607, 40));
        pnlPayrollFilter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        txtSearchPayroll.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearchPayroll.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchPayroll.setText("Tìm kiếm");
        txtSearchPayroll.setHighlighter(null);
        txtSearchPayroll.setMinimumSize(new java.awt.Dimension(250, 22));
        txtSearchPayroll.setPreferredSize(new java.awt.Dimension(250, 30));
        txtSearchPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchPayrollActionPerformed(evt);
            }
        });
        pnlPayrollFilter.add(txtSearchPayroll);

        cbFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã thanh toán", "Chưa thanh toán" }));
        cbFilter.setFocusable(false);
        cbFilter.setMinimumSize(new java.awt.Dimension(80, 22));
        cbFilter.setPreferredSize(new java.awt.Dimension(105, 23));
        pnlPayrollFilter.add(cbFilter);

        pnlPayrollButton.setBackground(new java.awt.Color(255, 255, 255));
        pnlPayrollButton.setPreferredSize(new java.awt.Dimension(300, 35));
        pnlPayrollButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        btnPayroll.setText("Tính lương");
        btnPayroll.setFocusable(false);
        btnPayroll.setMaximumSize(new java.awt.Dimension(72, 23));
        btnPayroll.setMinimumSize(new java.awt.Dimension(72, 23));
        btnPayroll.setPreferredSize(new java.awt.Dimension(90, 23));
        btnPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayrollActionPerformed(evt);
            }
        });
        pnlPayrollButton.add(btnPayroll);

        btnPay.setText("Thanh toán");
        btnPay.setFocusable(false);
        btnPay.setMaximumSize(new java.awt.Dimension(72, 22));
        btnPay.setMinimumSize(new java.awt.Dimension(72, 22));
        pnlPayrollButton.add(btnPay);

        btnRefesh.setText("Tải lại");
        btnRefesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshActionPerformed(evt);
            }
        });
        pnlPayrollButton.add(btnRefesh);

        pnlPayrollFilter.add(pnlPayrollButton);

        add(pnlPayrollFilter, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchPayrollActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchPayrollActionPerformed

    private void btnPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayrollActionPerformed
        EmployeePayroll emPayrollDialog = new EmployeePayroll((JFrame) SwingUtilities.getWindowAncestor(this), true);
        emPayrollDialog.setLocationRelativeTo(null);
        emPayrollDialog.setVisible(true);
    }//GEN-LAST:event_btnPayrollActionPerformed

    private void btnRefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshActionPerformed
        // Reset the search field
        txtSearchPayroll.setText("Tìm kiếm");
        txtSearchPayroll.setFont(new java.awt.Font("Segoe UI", 2, 12));
        txtSearchPayroll.setForeground(new java.awt.Color(153, 153, 153));

        // Reset the filter combobox
        cbFilter.setSelectedIndex(0);

        // Reload the employee data from the database
        employeeBUS.loadEmployeeList();

        // Display the refreshed payroll data with role-based access
        loadPayrollData();
    }//GEN-LAST:event_btnRefeshActionPerformed

    private void tblPayrollsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPayrollsMouseClicked
        int selectedRow = tblPayrolls.getSelectedRow();
        if (selectedRow != -1){
            // Get id of selected row
            String id = tblPayrolls.getValueAt(selectedRow, 0).toString();
            // Create DTO object
            PayrollDTO selectedPayroll = payrollBUS.getPayrollByID(id);
            PayrollDetails detailDialog = new PayrollDetails((JFrame) SwingUtilities.getWindowAncestor(this), true, selectedPayroll);
            detailDialog.setLocationRelativeTo(null);
            detailDialog.setVisible(true);
        }
    }//GEN-LAST:event_tblPayrollsMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnPayroll;
    private javax.swing.JButton btnRefesh;
    private javax.swing.JComboBox<String> cbFilter;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlPayrollButton;
    private javax.swing.JPanel pnlPayrollFilter;
    private javax.swing.JPanel pnlPayrollList;
    private javax.swing.JTable tblPayrolls;
    private javax.swing.JTextField txtSearchPayroll;
    // End of variables declaration//GEN-END:variables
}
