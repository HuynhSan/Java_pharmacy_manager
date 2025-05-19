/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.pharmacy.app.GUI.Payroll;

import com.pharmacy.app.BUS.PayrollBUS;
import com.pharmacy.app.BUS.PayrollDetailsBUS;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.DTO.PayrollDTO;
import com.pharmacy.app.DTO.PayrollDetailsDTO;
import com.pharmacy.app.GUI.WorkSchedule.SalaryCalculationService;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class AddPayroll extends javax.swing.JDialog {
    private String employeeID;
    private SalaryCalculationService salaryService;
    private PayrollBUS payrollBUS;
    private PayrollDetailsBUS payrollDetailsBUS;
    private List<PayrollDetailsDTO> currentPayrollDetails;
    private YearMonth currentMonth;

    /**
     * Creates new form PayrollDetails
     */
    public AddPayroll(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.salaryService = new SalaryCalculationService();
        this.payrollBUS = new PayrollBUS();
        this.payrollDetailsBUS = new PayrollDetailsBUS();
        this.currentMonth = YearMonth.now(); // or get from user input
        setupTable();
    }
    
    /**
     * Sets employee data in the contract form fields
     * @param employee The selected employee
     */
    public void setEmployeeData(EmployeeDTO employee) {
        if (employee != null) {
            this.employeeID = employee.getEmployeeID();
            txtEmployeeID.setText(employee.getEmployeeID());
            txtEmployeeName.setText(employee.getName());
            calculateAndDisplaySalary();
        }
    }
    
    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Thành phần lương", "Ngày/Giờ", "Số tiền"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing only the performance bonus (SC06)
                return column == 1 && row == 5; // SC06 row
            }
        };
        tblPayrollComponent.setModel(model);

        // Add listener for performance bonus changes
        model.addTableModelListener(e -> {
            if (e.getColumn() == 1 && e.getFirstRow() == 5) {
                updatePerformanceBonus();
            }
        });
    }
    
    private void calculateAndDisplaySalary() {
        try {
            // Generate new payroll ID
            String payrollID = payrollBUS.generateNewPayrollID();

            // Calculate salary details
            currentPayrollDetails = salaryService.calculateSalary(employeeID, payrollID, currentMonth);

            // Display in table
            updateTable();

            // Calculate and display total
            updateTotalSalary();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tính lương: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) tblPayrollComponent.getModel();
        model.setRowCount(0);

        // Component names mapping
        Map<String, String> componentNames = Map.of(
            "SC01", "Lương cơ bản thực tế",
            "SC02", "Phụ cấp OT ngày thường",
            "SC03", "Phụ cấp OT cuối tuần", 
            "SC04", "Phụ cấp OT ngày lễ",
            "SC05", "Phụ cấp đi lại",
            "SC06", "Thưởng hiệu suất",
            "SC07", "Trừ nghỉ không phép",
            "SC08", "Trừ đi muộn",
            "SC09", "Thuế thu nhập cá nhân"
        );

        for (PayrollDetailsDTO detail : currentPayrollDetails) {
            String componentName = componentNames.get(detail.getComponentID());
            String unit = detail.getComponentID().equals("SC05") ? "Tháng" : 
                         detail.getComponentID().startsWith("SC0") && 
                         (detail.getComponentID().equals("SC02") || 
                          detail.getComponentID().equals("SC03") || 
                          detail.getComponentID().equals("SC04")) ? "Giờ" : "Ngày";

            model.addRow(new Object[]{
                componentName,
                detail.getValue() + " " + unit,
                String.format("%,.0f VNĐ", detail.getAmount())
            });
        }
    }
    
    private void updateTotalSalary() {
        BigDecimal total = currentPayrollDetails.stream()
                                              .map(PayrollDetailsDTO::getAmount)
                                              .reduce(BigDecimal.ZERO, BigDecimal::add);
        txtTotal.setText(String.format("%,.0f VNĐ", total));
    }
    
    private void updatePerformanceBonus() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblPayrollComponent.getModel();
            String valueStr = model.getValueAt(5, 1).toString().replaceAll("[^0-9]", "");
            BigDecimal bonusAmount = new BigDecimal(valueStr);

            // Update the payroll detail
            for (PayrollDetailsDTO detail : currentPayrollDetails) {
                if (detail.getComponentID().equals("SC06")) {
                    detail.setAmount(bonusAmount);
                    detail.setValue(bonusAmount.compareTo(BigDecimal.ZERO) > 0 ? 1 : 0);
                    break;
                }
            }

            // Update table display
            model.setValueAt(String.format("%,.0f VNĐ", bonusAmount), 5, 2);

            // Update total
            updateTotalSalary();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Số tiền không hợp lệ", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
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

        pnlTotal = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        pnlPayrollComponent = new javax.swing.JPanel();
        spPayrollComponent = new javax.swing.JScrollPane();
        tblPayrollComponent = new javax.swing.JTable();
        pnlPayroll = new javax.swing.JPanel();
        lblPayroll = new javax.swing.JLabel();
        pnlButton = new javax.swing.JPanel();
        btnAddPayroll = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        pnlEmployeeInfo = new javax.swing.JPanel();
        lblEmployeeID = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        lblEmployeeName = new javax.swing.JLabel();
        txtEmployeeName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnlTotal.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        lblTotal.setText("Lương thực nhận:");
        pnlTotal.add(lblTotal);

        txtTotal.setEditable(false);
        txtTotal.setFocusable(false);
        txtTotal.setMinimumSize(new java.awt.Dimension(383, 22));
        txtTotal.setPreferredSize(new java.awt.Dimension(380, 22));
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        pnlTotal.add(txtTotal);

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
        spPayrollComponent.setViewportView(tblPayrollComponent);

        pnlPayrollComponent.add(spPayrollComponent, java.awt.BorderLayout.CENTER);

        lblPayroll.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPayroll.setText("BẢNG LƯƠNG");
        pnlPayroll.add(lblPayroll);

        pnlButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        btnAddPayroll.setBackground(new java.awt.Color(0, 204, 51));
        btnAddPayroll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddPayroll.setForeground(new java.awt.Color(255, 255, 255));
        btnAddPayroll.setText("Xác nhận");
        btnAddPayroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPayrollActionPerformed(evt);
            }
        });
        pnlButton.add(btnAddPayroll);

        btnCancel.setBackground(new java.awt.Color(153, 153, 153));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Hủy");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlButton.add(btnCancel);

        pnlEmployeeInfo.setLayout(new java.awt.GridBagLayout());

        lblEmployeeID.setText("Mã nhân viên:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        pnlEmployeeInfo.add(lblEmployeeID, gridBagConstraints);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setFocusable(false);
        txtEmployeeID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeIDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        pnlEmployeeInfo.add(txtEmployeeID, gridBagConstraints);

        lblEmployeeName.setText("Tên nhân viên:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 20);
        pnlEmployeeInfo.add(lblEmployeeName, gridBagConstraints);

        txtEmployeeName.setEditable(false);
        txtEmployeeName.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        pnlEmployeeInfo.add(txtEmployeeName, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlPayroll, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pnlPayrollComponent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlEmployeeInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlPayroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlEmployeeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlPayrollComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtEmployeeIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeIDActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
        EmployeePayroll emPayrollDialog = new EmployeePayroll((JFrame) SwingUtilities.getWindowAncestor(this), true);
        emPayrollDialog.setLocationRelativeTo(null);
        emPayrollDialog.setVisible(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddPayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPayrollActionPerformed
        try {
            // Calculate total salary
            BigDecimal totalSalary = currentPayrollDetails.stream()
                                                        .map(PayrollDetailsDTO::getAmount)
                                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Create payroll
            PayrollDTO payroll = new PayrollDTO(
                currentPayrollDetails.get(0).getPayrollID(),
                employeeID,
                totalSalary,
                false, // Not paid yet
                null,  // Pay date will be set when payment is made
                false  // Not deleted
            );

            // Save payroll
            boolean payrollSaved = payrollBUS.addPayroll(payroll);

            if (payrollSaved) {
                // Save payroll details
                boolean allDetailsSaved = true;
                for (PayrollDetailsDTO detail : currentPayrollDetails) {
                    if (!payrollDetailsBUS.addPayrollDetail(detail)) {
                        allDetailsSaved = false;
                        break;
                    }
                }

                if (allDetailsSaved) {
                    JOptionPane.showMessageDialog(this,
                        "Tạo bảng lương thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                } else {
                    // Rollback payroll if details failed
                    payrollBUS.deletePayroll(payroll.getPayrollID());
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi lưu chi tiết bảng lương!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi tạo bảng lương!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddPayrollActionPerformed

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
            java.util.logging.Logger.getLogger(AddPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddPayroll dialog = new AddPayroll(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddPayroll;
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblEmployeeName;
    private javax.swing.JLabel lblPayroll;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlEmployeeInfo;
    private javax.swing.JPanel pnlPayroll;
    private javax.swing.JPanel pnlPayrollComponent;
    private javax.swing.JPanel pnlTotal;
    private javax.swing.JScrollPane spPayrollComponent;
    private javax.swing.JTable tblPayrollComponent;
    private javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
