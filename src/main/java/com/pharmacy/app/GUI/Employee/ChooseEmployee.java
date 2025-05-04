/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.pharmacy.app.GUI.Employee;

import com.pharmacy.app.BUS.EmployeeBUS;
import com.pharmacy.app.DAO.EmployeeDAO;
import com.pharmacy.app.DTO.EmployeeDTO;
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
public class ChooseEmployee extends javax.swing.JDialog {
    private EmployeeBUS employeeBUS;
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private EmployeeDTO employeeDTO;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private AddContract addContractDialog;
    private EmployeeDTO selectedEmployee;

    /**
     * Creates new form ChooseEmployee
     * @param parent
     * @param modal
     */
    public ChooseEmployee(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupListeners();
        initBUS();
        loadEmployeeData();
        setupTable();
        // Disable Add Contract button initially (until an employee is selected)
        btnAddContract.setEnabled(false);
    }
    
    private void initBUS() {
        employeeBUS = new EmployeeBUS();
        employeeBUS.loadEmployeeList();
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
        ArrayList<EmployeeDTO> employees = employeeBUS.getEmployeeList();
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
        displayEmployees(searchResults);
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
                btnAddContract.setEnabled(true);
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

        jLabel2 = new javax.swing.JLabel();
        pnlSearchEmployee = new javax.swing.JPanel();
        txtSearchEmployee = new javax.swing.JTextField();
        pnlEmployeesList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();
        pnlChooseEmployee = new javax.swing.JPanel();
        lblChooseEmployee = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAddContract = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlSearchEmployee.setLayout(new java.awt.BorderLayout());

        txtSearchEmployee.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearchEmployee.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchEmployee.setText("Tìm kiếm");
        txtSearchEmployee.setHighlighter(null);
        txtSearchEmployee.setPreferredSize(new java.awt.Dimension(400, 22));
        txtSearchEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchEmployeeActionPerformed(evt);
            }
        });
        pnlSearchEmployee.add(txtSearchEmployee, java.awt.BorderLayout.CENTER);

        pnlEmployeesList.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 310));

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
        tblEmployees.setColumnSelectionAllowed(true);
        tblEmployees.setFocusable(false);
        tblEmployees.setPreferredSize(new java.awt.Dimension(300, 310));
        tblEmployees.setRowHeight(30);
        tblEmployees.setShowGrid(true);
        tblEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmployees);
        tblEmployees.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnlEmployeesList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        lblChooseEmployee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblChooseEmployee.setText("CHỌN NHÂN VIÊN");
        pnlChooseEmployee.add(lblChooseEmployee);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        btnAddContract.setBackground(new java.awt.Color(0, 204, 51));
        btnAddContract.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddContract.setForeground(new java.awt.Color(255, 255, 255));
        btnAddContract.setText("Tạo hợp đồng");
        btnAddContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddContractActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddContract);

        btnClose.setBackground(new java.awt.Color(153, 153, 153));
        btnClose.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Đóng");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSearchEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlEmployeesList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlChooseEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlChooseEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlSearchEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlEmployeesList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchEmployeeActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnAddContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContractActionPerformed
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
        addContractDialog = new AddContract(new javax.swing.JFrame(), true);

        // Set employee data in AddContract dialog
        addContractDialog.setEmployeeData(selectedEmployee);

        // Close this dialog
        dispose();

        // Show the AddContract dialog
        addContractDialog.setLocationRelativeTo(null);
        addContractDialog.setVisible(true);
    }//GEN-LAST:event_btnAddContractActionPerformed

    private void tblEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEmployeesMouseClicked

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
            java.util.logging.Logger.getLogger(ChooseEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChooseEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChooseEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChooseEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChooseEmployee dialog = new ChooseEmployee(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddContract;
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChooseEmployee;
    private javax.swing.JPanel pnlChooseEmployee;
    private javax.swing.JPanel pnlEmployeesList;
    private javax.swing.JPanel pnlSearchEmployee;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JTextField txtSearchEmployee;
    // End of variables declaration//GEN-END:variables
}
