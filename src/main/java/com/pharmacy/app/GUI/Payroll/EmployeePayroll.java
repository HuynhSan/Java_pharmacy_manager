/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.pharmacy.app.GUI.Payroll;

import com.pharmacy.app.BUS.EmployeeBUS;
import com.pharmacy.app.DAO.EmployeeDAO;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.DTO.SessionDTO;
import com.pharmacy.app.GUI.Employee.AddContract;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class EmployeePayroll extends javax.swing.JDialog {
    private EmployeeBUS employeeBUS;
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private EmployeeDTO employeeDTO;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private AddPayroll addPayrollDialog;
    private EmployeeDTO selectedEmployee;

    /**
     * Creates new form EmployeePayroll
     */
    public EmployeePayroll(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupListeners();
        initBUS();
        loadEmployeeData();
        setupTable();
        // Disable Add Contract button initially (until an employee is selected)
        btnPayroll.setEnabled(false);
    }
    
    private void initBUS() {
        employeeBUS = new EmployeeBUS();
    
        // Check current user role
        String roleID = SessionDTO.getCurrentUser().getRoleID();

        if ("ROLE001".equals(roleID)) {
            // Admin sees employees with contracts
            employeeBUS.loadContractEmployeeList();
        } else if ("ROLE002".equals(roleID)) {
            // Manager sees only sales employees
            employeeBUS.loadSalesEmployeeList();
        } else {
            // Default behavior for other roles - load all employees
            employeeBUS.loadEmployeeList();
        }
    }
    
    private void setupListeners() {
        // Setup search text field focus listener
        txtSearchEmployee.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearchEmployee.getText().equals("Tìm kiếm")) {
                    txtSearchEmployee.setText("");
                    txtSearchEmployee.setFont(new java.awt.Font("Segoe UI", 0, 12));
                    txtSearchEmployee.setForeground(new java.awt.Color(0, 0, 0));
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearchEmployee.getText().isEmpty()) {
                    txtSearchEmployee.setText("Tìm kiếm");
                    txtSearchEmployee.setFont(new java.awt.Font("Segoe UI", 2, 12));
                    txtSearchEmployee.setForeground(new java.awt.Color(153, 153, 153));
                }
            }
        });
        
        // Setup search text field key listener
        txtSearchEmployee.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearchEmployee.getText();
                if (!keyword.equals("Tìm kiếm")) {
                    searchEmployees(keyword);
                }
            }
        });
    }
    
    /**
     * Load all employee data into the table
     */
    private void loadEmployeeData() {
        ArrayList<EmployeeDTO> employees;
        String roleID = SessionDTO.getCurrentUser().getRoleID();

        if ("ROLE001".equals(roleID)) {
            // Admin sees employees with contracts
            employees = employeeBUS.getContractEmployeeList();
        } else if ("ROLE002".equals(roleID)) {
            // Manager sees only sales employees
            employees = employeeBUS.getSalesEmployeeList();
        } else {
            // Default behavior for other roles
            employees = employeeBUS.getEmployeeList();
        }

        displayEmployees(employees);
    }
    
    /**
     * Search employees based on keyword
     */
    private void searchEmployees(String keyword) {
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm")) {
            loadEmployeeData();
            return;
        }

        ArrayList<EmployeeDTO> searchResults = employeeBUS.searchEmployees(keyword);
        String roleID = SessionDTO.getCurrentUser().getRoleID();

        if ("ROLE001".equals(roleID)) {
            // Admin sees only employees with contracts
            ArrayList<EmployeeDTO> contractEmployees = employeeBUS.getContractEmployeeList();
            ArrayList<EmployeeDTO> filteredResults = new ArrayList<>();

            for (EmployeeDTO employee : searchResults) {
                // Check if this employee is in the contract employees list
                for (EmployeeDTO contractEmployee : contractEmployees) {
                    if (employee.getEmployeeID().equals(contractEmployee.getEmployeeID())) {
                        filteredResults.add(employee);
                        break;
                    }
                }
            }

            displayEmployees(filteredResults);
        } else if ("ROLE002".equals(roleID)) {
            // Manager sees only sales employees
            ArrayList<EmployeeDTO> salesEmployees = employeeBUS.getSalesEmployeeList();
            ArrayList<EmployeeDTO> filteredResults = new ArrayList<>();

            for (EmployeeDTO employee : searchResults) {
                // Check if this employee is in the sales employees list
                for (EmployeeDTO salesEmployee : salesEmployees) {
                    if (employee.getEmployeeID().equals(salesEmployee.getEmployeeID())) {
                        filteredResults.add(employee);
                        break;
                    }
                }
            }

            displayEmployees(filteredResults);
        } else {
            // For other roles, show all search results
            displayEmployees(searchResults);
        }
    }
    
    /**
     * Display employees in the table
     */
    private void displayEmployees(ArrayList<EmployeeDTO> employees) {
        DefaultTableModel model = (DefaultTableModel) tblEmployees.getModel();
        model.setRowCount(0); // Clear current data
        
        for (EmployeeDTO employee : employees) {
            Object[] row = {
                employee.getEmployeeID(),
                employee.getName(),
                employee.getDob().format(DATE_FORMAT),
                employee.getEmail()
            };
            model.addRow(row);
        }
    }
    
    private void setupTable() {
        // Set table selection mode to single selection
        tblEmployees.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // Add table row selection listener
        tblEmployees.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
                // Skip if adjusting or no row is selected
                if (event.getValueIsAdjusting() || tblEmployees.getSelectedRow() == -1) {
                    return;
                }

                // Get selected employee's ID from the table
                String employeeID = tblEmployees.getValueAt(tblEmployees.getSelectedRow(), 0).toString();

                // Retrieve full employee object from BUS
                selectedEmployee = employeeBUS.getEmployeeByID(employeeID);

                // Enable the create contract button if a row is selected
                btnPayroll.setEnabled(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSearchEmployee = new javax.swing.JPanel();
        txtSearchEmployee = new javax.swing.JTextField();
        btnPayroll = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        pnlEmployeesList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();
        pnlTitle = new javax.swing.JPanel();
        lblEmployeeList = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout(0, 20));

        pnlSearchEmployee.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        txtSearchEmployee.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearchEmployee.setForeground(new java.awt.Color(204, 204, 204));
        txtSearchEmployee.setText("Tìm kiếm");
        txtSearchEmployee.setHighlighter(null);
        txtSearchEmployee.setPreferredSize(new java.awt.Dimension(400, 22));
        txtSearchEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchEmployeeActionPerformed(evt);
            }
        });
        pnlSearchEmployee.add(txtSearchEmployee);

        btnPayroll.setText("Tính lương");
        btnPayroll.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayrollActionPerformed(evt);
            }
        });
        pnlSearchEmployee.add(btnPayroll);

        btnCancel.setText("Hủy");
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlSearchEmployee.add(btnCancel);

        getContentPane().add(pnlSearchEmployee, java.awt.BorderLayout.CENTER);

        pnlEmployeesList.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 310));

        tblEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Ngày sinh", "Email"
            }
        ));
        tblEmployees.setFocusable(false);
        tblEmployees.setPreferredSize(new java.awt.Dimension(500, 80));
        tblEmployees.setShowGrid(true);
        jScrollPane1.setViewportView(tblEmployees);

        pnlEmployeesList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlEmployeesList, java.awt.BorderLayout.SOUTH);

        pnlTitle.setLayout(new java.awt.BorderLayout());

        lblEmployeeList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmployeeList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmployeeList.setText("DANH SÁCH NHÂN VIÊN");
        lblEmployeeList.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblEmployeeList.setPreferredSize(new java.awt.Dimension(137, 30));
        lblEmployeeList.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pnlTitle.add(lblEmployeeList, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlTitle, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayrollActionPerformed
        if (selectedEmployee == null) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn một nhân viên từ danh sách.",
                "Thông báo",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Open AddContract dialog with selected employee data
        addPayrollDialog = new AddPayroll(new javax.swing.JFrame(), true);

        // Set employee data in AddContract dialog
        addPayrollDialog.setEmployeeData(selectedEmployee);

        // Close this dialog
        dispose();

        // Show the AddContract dialog
        addPayrollDialog.setLocationRelativeTo(null);
        addPayrollDialog.setVisible(true);
    }//GEN-LAST:event_btnPayrollActionPerformed

    private void txtSearchEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchEmployeeActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmployeePayroll dialog = new EmployeePayroll(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnPayroll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmployeeList;
    private javax.swing.JPanel pnlEmployeesList;
    private javax.swing.JPanel pnlSearchEmployee;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JTextField txtSearchEmployee;
    // End of variables declaration//GEN-END:variables
}
