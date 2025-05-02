/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.Employee;

import com.pharmacy.app.BUS.EmployeeBUS;
import com.pharmacy.app.DTO.EmployeeDTO;
import com.pharmacy.app.DAO.EmployeeDAO;
import com.pharmacy.app.BUS.ContractBUS;
import com.pharmacy.app.DTO.ContractDTO;
import com.pharmacy.app.DAO.ContractDAO;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phong
 */
public class EmployeeManagement extends javax.swing.JPanel {
    private EmployeeBUS employeeBUS;
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private EmployeeDTO employeeDTO;
    private ContractBUS contractBUS;
    private ContractDAO contractDAO = new ContractDAO();
    private ContractDTO contractDTO;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Creates new form EmployeeManagement
     */
    public EmployeeManagement() {
        initComponents();
        setupListeners();
        initBUS();
        loadEmployeeData();
        loadContractData();
    }
    
    private void initBUS() {
        employeeBUS = new EmployeeBUS();
        employeeBUS.loadEmployeeList();
        contractBUS = new ContractBUS();
        contractBUS.loadContractList();
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
        
        txtSearchContract.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearchContract.getText().equals("Tìm kiếm")) {
                    txtSearchContract.setText("");
                    txtSearchContract.setFont(new java.awt.Font("Segoe UI", 0, 12));
                    txtSearchContract.setForeground(new java.awt.Color(0, 0, 0));
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearchContract.getText().isEmpty()) {
                    txtSearchContract.setText("Tìm kiếm");
                    txtSearchContract.setFont(new java.awt.Font("Segoe UI", 2, 12));
                    txtSearchContract.setForeground(new java.awt.Color(153, 153, 153));
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
        
        txtSearchContract.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearchContract.getText();
                if (!keyword.equals("Tìm kiếm")) {
                    searchContracts(keyword);
                }
            }
        });
        
        // Setup combobox listener
        cbEmployee.addActionListener((e) -> {
            filterEmployeesByGender();
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
     * Filter employees by gender
     */
    private void filterEmployeesByGender() {
        String selectedOption = (String) cbEmployee.getSelectedItem();
        
        if (selectedOption.equals("Tất cả")) {
            loadEmployeeData();
        } else if (selectedOption.equals("Nam")) {
            ArrayList<EmployeeDTO> maleEmployees = employeeBUS.filterEmployeesByGender(true);
            displayEmployees(maleEmployees);
        } else if (selectedOption.equals("Nữ")) {
            ArrayList<EmployeeDTO> femaleEmployees = employeeBUS.filterEmployeesByGender(false);
            displayEmployees(femaleEmployees);
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
                employee.getGender() ? "Nam" : "Nữ",
                employee.getEmail(),
                employee.getPhone(),
                employee.getAddress()
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load all contract data into the table
     */
    private void loadContractData() {
        ArrayList<ContractDTO> contracts = contractBUS.getContractList();
        displayContracts(contracts);
    }
    
    /**
     * Search contracts based on keyword
     */
    private void searchContracts(String keyword) {
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm")) {
            loadContractData();
            return;
        }
        
        ArrayList<ContractDTO> searchResults = contractBUS.searchContracts(keyword);
        displayContracts(searchResults);
    }
    
    /**
     * Display contracts in the table
     */
    private void displayContracts(ArrayList<ContractDTO> contracts) {
        DefaultTableModel model = (DefaultTableModel) tblContracts.getModel();
        model.setRowCount(0); // Clear current data

        for (ContractDTO contract : contracts) {
            // Look up the employee name based on employee ID
            String employeeID = contract.getEmployeeID();
            EmployeeDTO employee = employeeBUS.getEmployeeByID(employeeID);
            String employeeName = (employee != null) ? employee.getName() : "Unknown";

            Object[] row = {
                contract.getContractID(),
                employeeName, // Display employee name instead of ID
                contract.getSigningDate().format(DATE_FORMAT),
                contract.getStartDate().format(DATE_FORMAT),
                contract.getEndDate().format(DATE_FORMAT),
                contract.getPosition()
            };
            model.addRow(row);
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

        tpEmployeeManagement = new javax.swing.JTabbedPane();
        pnlEmployees = new javax.swing.JPanel();
        pnlEmployee1 = new javax.swing.JPanel();
        txtSearchEmployee = new javax.swing.JTextField();
        cbEmployee = new javax.swing.JComboBox<>();
        btnAddEmployee = new javax.swing.JButton();
        btnRefeshEmployee = new javax.swing.JButton();
        btnPdfEmployee = new javax.swing.JButton();
        pnlEmployee2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();
        pnlContracts = new javax.swing.JPanel();
        pnlContract1 = new javax.swing.JPanel();
        txtSearchContract = new javax.swing.JTextField();
        btnAddContract = new javax.swing.JButton();
        btnRefeshContract = new javax.swing.JButton();
        btnPdfContract = new javax.swing.JButton();
        pnlContract2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblContracts = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(900, 508));
        setLayout(new java.awt.BorderLayout());

        tpEmployeeManagement.setBackground(new java.awt.Color(255, 255, 255));
        tpEmployeeManagement.setPreferredSize(new java.awt.Dimension(900, 529));

        pnlEmployees.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployees.setPreferredSize(new java.awt.Dimension(900, 700));
        pnlEmployees.setLayout(new java.awt.BorderLayout());

        pnlEmployee1.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployee1.setToolTipText("");
        pnlEmployee1.setPreferredSize(new java.awt.Dimension(1180, 70));

        txtSearchEmployee.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearchEmployee.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchEmployee.setText("Tìm kiếm");
        txtSearchEmployee.setHighlighter(null);
        txtSearchEmployee.setMinimumSize(new java.awt.Dimension(250, 22));
        txtSearchEmployee.setPreferredSize(new java.awt.Dimension(300, 35));
        pnlEmployee1.add(txtSearchEmployee);

        cbEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Nam", "Nữ" }));
        cbEmployee.setFocusable(false);
        cbEmployee.setMinimumSize(new java.awt.Dimension(80, 22));
        cbEmployee.setPreferredSize(new java.awt.Dimension(80, 35));
        pnlEmployee1.add(cbEmployee);

        btnAddEmployee.setBackground(new java.awt.Color(0, 204, 51));
        btnAddEmployee.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEmployee.setText("Thêm");
        btnAddEmployee.setFocusable(false);
        btnAddEmployee.setMaximumSize(new java.awt.Dimension(72, 22));
        btnAddEmployee.setMinimumSize(new java.awt.Dimension(72, 22));
        btnAddEmployee.setPreferredSize(new java.awt.Dimension(80, 35));
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });
        pnlEmployee1.add(btnAddEmployee);

        btnRefeshEmployee.setText("Tải lại");
        btnRefeshEmployee.setFocusable(false);
        btnRefeshEmployee.setMaximumSize(new java.awt.Dimension(72, 22));
        btnRefeshEmployee.setMinimumSize(new java.awt.Dimension(72, 22));
        btnRefeshEmployee.setPreferredSize(new java.awt.Dimension(80, 35));
        btnRefeshEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshEmployeeActionPerformed(evt);
            }
        });
        pnlEmployee1.add(btnRefeshEmployee);

        btnPdfEmployee.setText("In PDF");
        btnPdfEmployee.setFocusable(false);
        btnPdfEmployee.setMaximumSize(new java.awt.Dimension(72, 22));
        btnPdfEmployee.setMinimumSize(new java.awt.Dimension(72, 22));
        btnPdfEmployee.setPreferredSize(new java.awt.Dimension(80, 35));
        btnPdfEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfEmployeeActionPerformed(evt);
            }
        });
        pnlEmployee1.add(btnPdfEmployee);

        pnlEmployees.add(pnlEmployee1, java.awt.BorderLayout.NORTH);

        pnlEmployee2.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmployee2.setPreferredSize(new java.awt.Dimension(600, 439));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1180, 600));

        tblEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính", "Email", "SĐT", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployees.setFocusable(false);
        tblEmployees.setMinimumSize(new java.awt.Dimension(500, 80));
        tblEmployees.setPreferredSize(new java.awt.Dimension(1180, 600));
        tblEmployees.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmployees.setShowGrid(true);
        tblEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmployees);
        tblEmployees.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout pnlEmployee2Layout = new javax.swing.GroupLayout(pnlEmployee2);
        pnlEmployee2.setLayout(pnlEmployee2Layout);
        pnlEmployee2Layout.setHorizontalGroup(
            pnlEmployee2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        pnlEmployee2Layout.setVerticalGroup(
            pnlEmployee2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmployee2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlEmployees.add(pnlEmployee2, java.awt.BorderLayout.CENTER);

        tpEmployeeManagement.addTab("Nhân viên", pnlEmployees);

        pnlContracts.setBackground(new java.awt.Color(255, 255, 255));
        pnlContracts.setPreferredSize(new java.awt.Dimension(900, 500));
        pnlContracts.setLayout(new java.awt.BorderLayout());

        pnlContract1.setBackground(new java.awt.Color(255, 255, 255));
        pnlContract1.setToolTipText("");
        pnlContract1.setPreferredSize(new java.awt.Dimension(1200, 70));

        txtSearchContract.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearchContract.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchContract.setText("Tìm kiếm");
        txtSearchContract.setHighlighter(null);
        txtSearchContract.setMinimumSize(new java.awt.Dimension(250, 22));
        txtSearchContract.setPreferredSize(new java.awt.Dimension(300, 35));
        pnlContract1.add(txtSearchContract);

        btnAddContract.setBackground(new java.awt.Color(0, 204, 51));
        btnAddContract.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddContract.setForeground(new java.awt.Color(255, 255, 255));
        btnAddContract.setText("Thêm");
        btnAddContract.setFocusable(false);
        btnAddContract.setMaximumSize(new java.awt.Dimension(72, 22));
        btnAddContract.setMinimumSize(new java.awt.Dimension(80, 35));
        btnAddContract.setPreferredSize(new java.awt.Dimension(80, 35));
        btnAddContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddContractActionPerformed(evt);
            }
        });
        pnlContract1.add(btnAddContract);

        btnRefeshContract.setText("Tải lại");
        btnRefeshContract.setFocusable(false);
        btnRefeshContract.setMaximumSize(new java.awt.Dimension(72, 22));
        btnRefeshContract.setMinimumSize(new java.awt.Dimension(80, 35));
        btnRefeshContract.setPreferredSize(new java.awt.Dimension(80, 35));
        btnRefeshContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshContractActionPerformed(evt);
            }
        });
        pnlContract1.add(btnRefeshContract);

        btnPdfContract.setText("In PDF");
        btnPdfContract.setFocusable(false);
        btnPdfContract.setMaximumSize(new java.awt.Dimension(72, 22));
        btnPdfContract.setMinimumSize(new java.awt.Dimension(80, 35));
        btnPdfContract.setPreferredSize(new java.awt.Dimension(80, 35));
        btnPdfContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfContractActionPerformed(evt);
            }
        });
        pnlContract1.add(btnPdfContract);

        pnlContracts.add(pnlContract1, java.awt.BorderLayout.NORTH);

        pnlContract2.setBackground(new java.awt.Color(255, 255, 255));
        pnlContract2.setPreferredSize(new java.awt.Dimension(1180, 500));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1180, 500));

        tblContracts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã hợp đồng", "Tên nhân viên", "Ngày ký kết", "Ngày bắt đầu", "Ngày kết thúc", "Chức vụ"
            }
        ));
        tblContracts.setFocusable(false);
        tblContracts.setMinimumSize(new java.awt.Dimension(500, 80));
        tblContracts.setPreferredSize(new java.awt.Dimension(1180, 500));
        tblContracts.setRowHeight(30);
        tblContracts.setShowGrid(true);
        tblContracts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblContractsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblContracts);

        javax.swing.GroupLayout pnlContract2Layout = new javax.swing.GroupLayout(pnlContract2);
        pnlContract2.setLayout(pnlContract2Layout);
        pnlContract2Layout.setHorizontalGroup(
            pnlContract2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        pnlContract2Layout.setVerticalGroup(
            pnlContract2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContract2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlContracts.add(pnlContract2, java.awt.BorderLayout.CENTER);

        tpEmployeeManagement.addTab("Hợp đồng", pnlContracts);

        add(tpEmployeeManagement, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefeshEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshEmployeeActionPerformed
        // Reset the search field
        txtSearchEmployee.setText("Tìm kiếm");
        txtSearchEmployee.setFont(new java.awt.Font("Segoe UI", 2, 12));
        txtSearchEmployee.setForeground(new java.awt.Color(153, 153, 153));

        // Reset the gender filter combobox
        cbEmployee.setSelectedIndex(0);

        // Reload the employee data from the database
        employeeBUS.loadEmployeeList();

        // Display the refreshed employee data
        loadEmployeeData();
    }//GEN-LAST:event_btnRefeshEmployeeActionPerformed

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed
        AddEmployee addEmDialog = new AddEmployee((JFrame) SwingUtilities.getWindowAncestor(this), true);
        addEmDialog.setLocationRelativeTo(null);
        addEmDialog.setVisible(true);
    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void btnAddContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContractActionPerformed
        ChooseEmployee chooseEmDialog = new ChooseEmployee((JFrame) SwingUtilities.getWindowAncestor(this), true);
        chooseEmDialog.setLocationRelativeTo(null);
        chooseEmDialog.setVisible(true);
    }//GEN-LAST:event_btnAddContractActionPerformed

    private void tblEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeesMouseClicked
        int selectedRow = tblEmployees.getSelectedRow();
        if (selectedRow != -1){
            // Get id of selected row
            String id = tblEmployees.getValueAt(selectedRow, 0).toString();
            // Create DTO object
            EmployeeDTO selectedEmployee = employeeBUS.getEmployeeByID(id);
            UpdateEmployee detailDialog = new UpdateEmployee((JFrame) SwingUtilities.getWindowAncestor(this), true, selectedEmployee);
            detailDialog.setLocationRelativeTo(null);
            detailDialog.setVisible(true);
        }
    }//GEN-LAST:event_tblEmployeesMouseClicked

    private void btnPdfEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfEmployeeActionPerformed
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tblEmployees.getModel();

            // Use the PDFExporter utility class to export employee data
            com.pharmacy.app.Utils.PDFExporter.exportEmployeesToPDF(this, model, "DANH SÁCH NHAN VIÊN");

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất PDF: " + e.getMessage(),
                "Lỗi",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPdfEmployeeActionPerformed

    private void btnRefeshContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshContractActionPerformed
        // Reset the search field
        txtSearchContract.setText("Tìm kiếm");
        txtSearchContract.setFont(new java.awt.Font("Segoe UI", 2, 12));
        txtSearchContract.setForeground(new java.awt.Color(153, 153, 153));

        // Reload the contract data from the database
        contractBUS.loadContractList();

        // Display the refreshed contract data
        loadContractData();
    }//GEN-LAST:event_btnRefeshContractActionPerformed

    private void tblContractsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblContractsMouseClicked
        int selectedRow = tblContracts.getSelectedRow();
        if (selectedRow != -1){
            // Get id of selected row
            String id = tblContracts.getValueAt(selectedRow, 0).toString();
            // Create DTO object
            ContractDTO selectedContract = contractBUS.getContractByID(id);
            UpdateContract detailDialog = new UpdateContract((JFrame) SwingUtilities.getWindowAncestor(this), true, selectedContract);
            detailDialog.setLocationRelativeTo(null);
            detailDialog.setVisible(true);
        }
    }//GEN-LAST:event_tblContractsMouseClicked

    private void btnPdfContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfContractActionPerformed
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tblContracts.getModel();

            // Use the PDFExporter utility class to export contract data
            com.pharmacy.app.Utils.PDFExporter.exportContractsToPDF(this, model);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất PDF: " + e.getMessage(),
                "Lỗi",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPdfContractActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddContract;
    private javax.swing.JButton btnAddEmployee;
    private javax.swing.JButton btnPdfContract;
    private javax.swing.JButton btnPdfEmployee;
    private javax.swing.JButton btnRefeshContract;
    private javax.swing.JButton btnRefeshEmployee;
    private javax.swing.JComboBox<String> cbEmployee;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlContract1;
    private javax.swing.JPanel pnlContract2;
    private javax.swing.JPanel pnlContracts;
    private javax.swing.JPanel pnlEmployee1;
    private javax.swing.JPanel pnlEmployee2;
    private javax.swing.JPanel pnlEmployees;
    private javax.swing.JTable tblContracts;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JTabbedPane tpEmployeeManagement;
    private javax.swing.JTextField txtSearchContract;
    private javax.swing.JTextField txtSearchEmployee;
    // End of variables declaration//GEN-END:variables
}
