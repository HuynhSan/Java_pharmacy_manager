/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.pharmacy.app.GUI.Employee;

import com.pharmacy.app.BUS.ContractBUS;
import com.pharmacy.app.DTO.ContractDTO;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.Utils.ContractValidation;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author phong
 */
public class AddContract extends javax.swing.JDialog {
    private ContractBUS contractBUS;
    private String employeeID;

    /**
     * Creates new form AddContract
     * @param parent
     * @param modal
     */
    public AddContract(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    private boolean validateForm() {
        // Get all form values
        String degree = txtDegree.getText();
        String experienceYearsStr = txtExperienceYears.getText();
        String signingDateStr = txtSigningDate.getText();
        String position = txtPosition.getText();
        String startDateStr = txtStartDate.getText();
        String endDateStr = txtEndDate.getText();
        String description = txtDescription.getText();
        String baseSalaryStr = txtBaseSalary.getText();

        // Validate required fields
        String degreeError = ContractValidation.validateRequired(degree, "Bằng cấp");
        if (!degreeError.isEmpty()) {
            JOptionPane.showMessageDialog(this, degreeError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtDegree.requestFocus();
            return false;
        }

        String positionError = ContractValidation.validateRequired(position, "Chức vụ");
        if (!positionError.isEmpty()) {
            JOptionPane.showMessageDialog(this, positionError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtPosition.requestFocus();
            return false;
        }

        String descriptionError = ContractValidation.validateRequired(description, "Mô tả công việc");
        if (!descriptionError.isEmpty()) {
            JOptionPane.showMessageDialog(this, descriptionError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtDescription.requestFocus();
            return false;
        }

        // Validate experience years
        String experienceYearsError = ContractValidation.validateExperienceYears(experienceYearsStr);
        if (!experienceYearsError.isEmpty()) {
            JOptionPane.showMessageDialog(this, experienceYearsError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtExperienceYears.requestFocus();
            return false;
        }

        // Validate date fields
        String signingDateError = ContractValidation.validateDate(signingDateStr, "Ngày ký kết");
        if (!signingDateError.isEmpty()) {
            JOptionPane.showMessageDialog(this, signingDateError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSigningDate.requestFocus();
            return false;
        }

        String startDateError = ContractValidation.validateDate(startDateStr, "Ngày bắt đầu");
        if (!startDateError.isEmpty()) {
            JOptionPane.showMessageDialog(this, startDateError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtStartDate.requestFocus();
            return false;
        }

        String endDateError = ContractValidation.validateDate(endDateStr, "Ngày kết thúc");
        if (!endDateError.isEmpty()) {
            JOptionPane.showMessageDialog(this, endDateError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtEndDate.requestFocus();
            return false;
        }

        // Validate date relationships
        String dateRelationshipError = ContractValidation.validateDateRelationships(signingDateStr, startDateStr, endDateStr);
        if (!dateRelationshipError.isEmpty()) {
            JOptionPane.showMessageDialog(this, dateRelationshipError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSigningDate.requestFocus();
            return false;
        }

        // Validate base salary
        String baseSalaryError = ContractValidation.validateBaseSalary(baseSalaryStr);
        if (!baseSalaryError.isEmpty()) {
            JOptionPane.showMessageDialog(this, baseSalaryError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtBaseSalary.requestFocus();
            return false;
        }
        
        // Check if employee already has contracts
        if (contractBUS == null) {
            contractBUS = new ContractBUS();
        }

        ContractDTO latestContract = contractBUS.getLatestEmployeeContract(employeeID);
        if (latestContract != null && latestContract.getEndDate() != null) {
            // Validate new contract dates against the latest contract
            String contractDateError = ContractValidation.validateNewContractDates(
                signingDateStr, 
                startDateStr, 
                latestContract.getEndDate()
            );

            if (!contractDateError.isEmpty()) {
                JOptionPane.showMessageDialog(this, contractDateError, "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSigningDate.requestFocus();
                return false;
            }
        }

        // All validations passed
        return true;
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

        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        pnlAddContract = new javax.swing.JPanel();
        lblAddContract = new javax.swing.JLabel();
        pnlEmployeeInfo = new javax.swing.JPanel();
        lblEmployeeName = new javax.swing.JLabel();
        lblDegree = new javax.swing.JLabel();
        txtDegree = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtExperienceYears = new javax.swing.JTextField();
        txtEmployeeName = new javax.swing.JTextField();
        lblEmployeeID = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        pnlContractInfo = new javax.swing.JPanel();
        lblDescription = new javax.swing.JLabel();
        txtEndDate = new javax.swing.JTextField();
        lblSigningDate = new javax.swing.JLabel();
        txtSigningDate = new javax.swing.JTextField();
        lblPosition = new javax.swing.JLabel();
        txtPosition = new javax.swing.JTextField();
        lblStartDate = new javax.swing.JLabel();
        txtStartDate = new javax.swing.JTextField();
        lblEndDate = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        pnlSalaryTerms = new javax.swing.JPanel();
        lblBaseSalary = new javax.swing.JLabel();
        txtBaseSalary = new javax.swing.JTextField();
        lblBaseWorkDays = new javax.swing.JLabel();
        txtBaseWorkDays = new javax.swing.JTextField();
        pnlContractButton = new javax.swing.JPanel();
        btnAddContract = new javax.swing.JButton();
        btnCancelContract = new javax.swing.JButton();

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel6.setText("jLabel6");

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnlAddContract.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        lblAddContract.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAddContract.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddContract.setText("TẠO HỢP ĐỒNG");
        pnlAddContract.add(lblAddContract);

        pnlEmployeeInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlEmployeeInfo.setLayout(new java.awt.GridBagLayout());

        lblEmployeeName.setText("Tên nhân viên:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        pnlEmployeeInfo.add(lblEmployeeName, gridBagConstraints);

        lblDegree.setText("Bằng cấp:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 20);
        pnlEmployeeInfo.add(lblDegree, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 30);
        pnlEmployeeInfo.add(txtDegree, gridBagConstraints);

        jLabel3.setText("Số năm kinh nghiệm:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlEmployeeInfo.add(jLabel3, gridBagConstraints);

        txtExperienceYears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExperienceYearsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        pnlEmployeeInfo.add(txtExperienceYears, gridBagConstraints);

        txtEmployeeName.setEditable(false);
        txtEmployeeName.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 10);
        pnlEmployeeInfo.add(txtEmployeeName, gridBagConstraints);

        lblEmployeeID.setText("Mã nhân viên:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        pnlEmployeeInfo.add(lblEmployeeID, gridBagConstraints);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 30);
        pnlEmployeeInfo.add(txtEmployeeID, gridBagConstraints);

        pnlContractInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hợp đồng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlContractInfo.setLayout(new java.awt.GridBagLayout());

        lblDescription.setText("Mô tả công việc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 20);
        pnlContractInfo.add(lblDescription, gridBagConstraints);

        txtEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEndDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        pnlContractInfo.add(txtEndDate, gridBagConstraints);

        lblSigningDate.setText("Ngày ký kết:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        pnlContractInfo.add(lblSigningDate, gridBagConstraints);

        txtSigningDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSigningDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 30);
        pnlContractInfo.add(txtSigningDate, gridBagConstraints);

        lblPosition.setText("Chức vụ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        pnlContractInfo.add(lblPosition, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 10);
        pnlContractInfo.add(txtPosition, gridBagConstraints);

        lblStartDate.setText("Ngày bắt đầu:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 20);
        pnlContractInfo.add(lblStartDate, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 30);
        pnlContractInfo.add(txtStartDate, gridBagConstraints);

        lblEndDate.setText("Ngày kết thúc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 20);
        pnlContractInfo.add(lblEndDate, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        pnlContractInfo.add(txtDescription, gridBagConstraints);

        pnlSalaryTerms.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điều khoản lương", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlSalaryTerms.setLayout(new java.awt.GridBagLayout());

        lblBaseSalary.setText("Lương cơ bản:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        pnlSalaryTerms.add(lblBaseSalary, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 30);
        pnlSalaryTerms.add(txtBaseSalary, gridBagConstraints);

        lblBaseWorkDays.setText("Số ngày làm việc cơ bản:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 20);
        pnlSalaryTerms.add(lblBaseWorkDays, gridBagConstraints);

        txtBaseWorkDays.setEditable(false);
        txtBaseWorkDays.setText("26");
        txtBaseWorkDays.setEnabled(false);
        txtBaseWorkDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBaseWorkDaysActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 10);
        pnlSalaryTerms.add(txtBaseWorkDays, gridBagConstraints);

        pnlContractButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 0));

        btnAddContract.setBackground(new java.awt.Color(0, 204, 51));
        btnAddContract.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddContract.setForeground(new java.awt.Color(255, 255, 255));
        btnAddContract.setText("Tạo");
        btnAddContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddContractActionPerformed(evt);
            }
        });
        pnlContractButton.add(btnAddContract);

        btnCancelContract.setBackground(new java.awt.Color(153, 153, 153));
        btnCancelContract.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelContract.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelContract.setText("Hủy");
        btnCancelContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelContractActionPerformed(evt);
            }
        });
        pnlContractButton.add(btnCancelContract);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlEmployeeInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContractInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSalaryTerms, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(pnlContractButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAddContract, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlAddContract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(pnlEmployeeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlContractInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSalaryTerms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(pnlContractButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndDateActionPerformed

    private void txtExperienceYearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExperienceYearsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExperienceYearsActionPerformed

    private void txtSigningDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSigningDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSigningDateActionPerformed

    private void btnCancelContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelContractActionPerformed
        this.dispose();
        ChooseEmployee chooseEmDialog = new ChooseEmployee((JFrame) SwingUtilities.getWindowAncestor(this), true);
        chooseEmDialog.setLocationRelativeTo(null);
        chooseEmDialog.setVisible(true);
    }//GEN-LAST:event_btnCancelContractActionPerformed

    private void txtBaseWorkDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBaseWorkDaysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBaseWorkDaysActionPerformed

    private void btnAddContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContractActionPerformed
        // Validate all input fields
        if (!validateForm()) {
            return;
        }
        
        try {
            // Get the contract BUS if not initialized
            if (contractBUS == null) {
                contractBUS = new ContractBUS();
            }
            
            // Generate a new contract ID
            String contractID = contractBUS.generateNewContractID();
            
            // Parse date
            String signingDateStr = txtSigningDate.getText().trim();
            LocalDate signingDate = ContractValidation.parseDate(signingDateStr);
            
            String startDateStr = txtStartDate.getText().trim();
            LocalDate startDate = ContractValidation.parseDate(startDateStr);
            
            String endDateStr = txtEndDate.getText().trim();
            LocalDate endDate = ContractValidation.parseDate(endDateStr);
            
            // Parse float and BigDecimal from input
            String experienceYearsStr = txtExperienceYears.getText().trim();
            float experienceYears = Float.parseFloat(experienceYearsStr);

            String baseSalaryStr = txtBaseSalary.getText().trim();
            BigDecimal baseSalary = new BigDecimal(baseSalaryStr);

            
            String degree = txtDegree.getText().trim();
            String position = txtPosition.getText().trim();
            String workDescription = txtDescription.getText().trim();
            
            // Create contract object (not deleted by default)
            ContractDTO contract = new ContractDTO(
                    contractID,
                    employeeID,
                    signingDate,
                    startDate,
                    endDate,
                    degree,
                    experienceYears,
                    position,
                    workDescription,
                    baseSalary,
                    false
            );
            
            // Add the contract
            boolean success = contractBUS.addContract(contract);
            
            if (success) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Tạo hợp đồng thành công!\nMã hợp đồng: " + contractID, 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                this.dispose();
                ChooseEmployee chooseEmDialog = new ChooseEmployee((JFrame) SwingUtilities.getWindowAncestor(this), true);
                chooseEmDialog.setLocationRelativeTo(null);
                chooseEmDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                    this, 
                    "Tạo hợp đồng thất bại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, 
                "Lỗi: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnAddContractActionPerformed

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
            java.util.logging.Logger.getLogger(AddContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddContract dialog = new AddContract(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelContract;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblAddContract;
    private javax.swing.JLabel lblBaseSalary;
    private javax.swing.JLabel lblBaseWorkDays;
    private javax.swing.JLabel lblDegree;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblEmployeeName;
    private javax.swing.JLabel lblEndDate;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblSigningDate;
    private javax.swing.JLabel lblStartDate;
    private javax.swing.JPanel pnlAddContract;
    private javax.swing.JPanel pnlContractButton;
    private javax.swing.JPanel pnlContractInfo;
    private javax.swing.JPanel pnlEmployeeInfo;
    private javax.swing.JPanel pnlSalaryTerms;
    private javax.swing.JTextField txtBaseSalary;
    private javax.swing.JTextField txtBaseWorkDays;
    private javax.swing.JTextField txtDegree;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtEndDate;
    private javax.swing.JTextField txtExperienceYears;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JTextField txtSigningDate;
    private javax.swing.JTextField txtStartDate;
    // End of variables declaration//GEN-END:variables
}
