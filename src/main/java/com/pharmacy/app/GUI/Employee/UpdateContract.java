/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.pharmacy.app.GUI.Employee;

import com.pharmacy.app.BUS.ContractBUS;
import com.pharmacy.app.BUS.EmployeeBUS;
import com.pharmacy.app.DTO.ContractDTO;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.Utils.ContractValidation;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author phong
 */
public class UpdateContract extends javax.swing.JDialog {
    private ContractDTO contractDTO;
    private ContractBUS contractBUS = new ContractBUS();
    private EmployeeBUS employeeBUS = new EmployeeBUS();
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Creates new form UpdateContract
     * @param parent
     * @param modal
     * @param contract
     */
    public UpdateContract(java.awt.Frame parent, boolean modal, ContractDTO contract) {
        super(parent, modal);
        initComponents();
        this.contractDTO = contract;
        setData(contract);
    }

    private UpdateContract(JFrame jFrame, boolean b) {
        super(jFrame, b);
        initComponents();
    }
    
    private void setData(ContractDTO contract) {
        txtContractID.setText(contract.getContractID());
        
        // Look up the employee name based on employee ID
        String employeeID = contract.getEmployeeID();
        EmployeeDTO employee = employeeBUS.getEmployeeByID(employeeID);
        String employeeName = (employee != null) ? employee.getName() : "Unknown";
        
        txtEmployeeName.setText(employeeName);
        txtDegree.setText(contract.getDegree());
        txtExperienceYears.setText(String.valueOf(contract.getExperienceYears()));
        txtSigningDate.setText(contract.getSigningDate().format(DATE_FORMAT));
        txtPosition.setText(contract.getPosition());
        txtStartDate.setText(contract.getStartDate().format(DATE_FORMAT));
        txtEndDate.setText(contract.getEndDate().format(DATE_FORMAT));
        txtDescription.setText(contract.getWorkDescription());
        txtBaseSalary.setText(String.valueOf(contract.getBaseSalary()));
    }
    
    /**
     * Validates the form inputs using ContractValidation class
     * @return Error message or empty string if all inputs are valid
     */
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

        // All validations passed
        return true;
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

        pnlContractButton = new javax.swing.JPanel();
        btnUpdateContract = new javax.swing.JButton();
        btnDeleteContract = new javax.swing.JButton();
        btnCancelContract = new javax.swing.JButton();
        pnlUpdateContract = new javax.swing.JPanel();
        lblUpdateContract = new javax.swing.JLabel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        pnlSalaryTerms = new javax.swing.JPanel();
        lblBaseSalary = new javax.swing.JLabel();
        txtBaseSalary = new javax.swing.JTextField();
        lblBaseWorkDays = new javax.swing.JLabel();
        txtBaseWorkDays = new javax.swing.JTextField();
        pnlEmployeeInfo = new javax.swing.JPanel();
        lblEmployeeName = new javax.swing.JLabel();
        lblDegree = new javax.swing.JLabel();
        txtDegree = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtExperienceYears = new javax.swing.JTextField();
        lblContractID = new javax.swing.JLabel();
        txtContractID = new javax.swing.JTextField();
        txtEmployeeName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(520, 730));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        pnlContractButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 0));

        btnUpdateContract.setBackground(new java.awt.Color(0, 204, 51));
        btnUpdateContract.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateContract.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateContract.setText("Cập nhật");
        btnUpdateContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateContractActionPerformed(evt);
            }
        });
        pnlContractButton.add(btnUpdateContract);

        btnDeleteContract.setBackground(new java.awt.Color(255, 0, 0));
        btnDeleteContract.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteContract.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteContract.setText("Xóa");
        btnDeleteContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteContractActionPerformed(evt);
            }
        });
        pnlContractButton.add(btnDeleteContract);

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

        pnlUpdateContract.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        lblUpdateContract.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUpdateContract.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdateContract.setText("HỢP ĐỒNG LAO ĐỘNG");
        pnlUpdateContract.add(lblUpdateContract);

        pnlContractInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hợp đồng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlContractInfo.setLayout(new java.awt.GridBagLayout());

        lblDescription.setText("Mô tả công việc:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
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

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 10);
        pnlContractInfo.add(jScrollPane1, gridBagConstraints);

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        lblContractID.setText("Mã hợp đồng:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 20);
        pnlEmployeeInfo.add(lblContractID, gridBagConstraints);

        txtContractID.setEditable(false);
        txtContractID.setEnabled(false);
        txtContractID.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 30);
        pnlEmployeeInfo.add(txtContractID, gridBagConstraints);

        txtEmployeeName.setEditable(false);
        txtEmployeeName.setEnabled(false);
        txtEmployeeName.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 20, 10);
        pnlEmployeeInfo.add(txtEmployeeName, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlEmployeeInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContractInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSalaryTerms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUpdateContract, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContractButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlUpdateContract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlEmployeeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlContractInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSalaryTerms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlContractButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelContractActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelContractActionPerformed

    private void txtEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndDateActionPerformed

    private void txtSigningDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSigningDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSigningDateActionPerformed

    private void txtExperienceYearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExperienceYearsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExperienceYearsActionPerformed

    private void txtBaseWorkDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBaseWorkDaysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBaseWorkDaysActionPerformed

    private void btnDeleteContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteContractActionPerformed
        // Confirm before deletion
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa hợp đồng này?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String contractID = txtContractID.getText();
            boolean success = contractBUS.deleteContract(contractID);

            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa hợp đồng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close dialog after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "Xóa hợp đồng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteContractActionPerformed

    private void btnUpdateContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateContractActionPerformed
        // Validate form
        if (!validateForm()) {
            return;
        }
        
        try {
            // Update contract DTO with form values
            contractDTO.setDegree(txtDegree.getText());
            contractDTO.setExperienceYears(Float.parseFloat(txtExperienceYears.getText()));
            contractDTO.setSigningDate(ContractValidation.parseDate(txtSigningDate.getText()));
            contractDTO.setPosition(txtPosition.getText());
            contractDTO.setStartDate(ContractValidation.parseDate(txtStartDate.getText()));
            contractDTO.setEndDate(ContractValidation.parseDate(txtEndDate.getText()));
            contractDTO.setWorkDescription(txtDescription.getText());
            contractDTO.setBaseSalary(new java.math.BigDecimal(txtBaseSalary.getText()));

            // Update contract in database
            boolean success = contractBUS.updateContract(contractDTO);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật hợp đồng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close dialog after successful update
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hợp đồng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateContractActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateContract.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UpdateContract dialog = new UpdateContract(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelContract;
    private javax.swing.JButton btnDeleteContract;
    private javax.swing.JButton btnUpdateContract;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBaseSalary;
    private javax.swing.JLabel lblBaseWorkDays;
    private javax.swing.JLabel lblContractID;
    private javax.swing.JLabel lblDegree;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblEmployeeName;
    private javax.swing.JLabel lblEndDate;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblSigningDate;
    private javax.swing.JLabel lblStartDate;
    private javax.swing.JLabel lblUpdateContract;
    private javax.swing.JPanel pnlContractButton;
    private javax.swing.JPanel pnlContractInfo;
    private javax.swing.JPanel pnlEmployeeInfo;
    private javax.swing.JPanel pnlSalaryTerms;
    private javax.swing.JPanel pnlUpdateContract;
    private javax.swing.JTextField txtBaseSalary;
    private javax.swing.JTextField txtBaseWorkDays;
    private javax.swing.JTextField txtContractID;
    private javax.swing.JTextField txtDegree;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtEndDate;
    private javax.swing.JTextField txtExperienceYears;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JTextField txtSigningDate;
    private javax.swing.JTextField txtStartDate;
    // End of variables declaration//GEN-END:variables
}
