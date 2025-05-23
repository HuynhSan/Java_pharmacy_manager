/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.User;

import com.pharmacy.app.BUS.RoleBUS;
import com.pharmacy.app.BUS.UserBUS;
import com.pharmacy.app.DAO.UserDAO;
import com.pharmacy.app.DTO.RoleDTO;
import com.pharmacy.app.DTO.UserDTO;
import com.pharmacy.app.GUI.Sales.PaymentDialog;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class UserManagement extends javax.swing.JPanel {
    private UserBUS userBUS;
    private UserDAO userDAO = new UserDAO();
    private UserDTO userDTO;
    private RoleBUS roleBUS;

    /**
     * Creates new form UserMangement
     */
    public UserManagement() {
        initComponents();
        initBUS();
        setupListeners();
        filterUsersByStatus();
        centerTableContent(tblUsers);
    }

    private void initBUS() {
        userBUS = new UserBUS();
        userBUS.loadUserList();
        roleBUS = new RoleBUS();
        roleBUS.loadRoleList();
    }

    private void setupListeners() {
        // Setup search text field focus listener
        txtSearchUser.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearchUser.getText().equals("Tìm kiếm")) {
                    txtSearchUser.setText("");
                    txtSearchUser.setFont(new java.awt.Font("Segoe UI", 0, 12));
                    txtSearchUser.setForeground(new java.awt.Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearchUser.getText().isEmpty()) {
                    txtSearchUser.setText("Tìm kiếm");
                    txtSearchUser.setFont(new java.awt.Font("Segoe UI", 2, 12));
                    txtSearchUser.setForeground(new java.awt.Color(153, 153, 153));
                }
            }
        });

        // Setup search text field key listener
        txtSearchUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearchUser.getText();
                if (!keyword.equals("Tìm kiếm")) {
                    searchUsers(keyword);
                }
            }
        });

        // Setup combobox listener
        cbUser.addActionListener((e) -> {
            filterUsersByStatus();
        });
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

    /**
     * Load all user data into the table
     */
    private void loadUserData() {
        ArrayList<UserDTO> users = userBUS.getUserList();
        displayUsers(users);
    }

    /**
     * Search users based on keyword
     */
    private void searchUsers(String keyword) {
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm")) {
            loadUserData();
            return;
        }

        ArrayList<UserDTO> searchResults = userBUS.searchUsers(keyword);
        displayUsers(searchResults);
    }

    /**
     * Filter users by status
     */
    private void filterUsersByStatus() {
        String selectedOption = (String) cbUser.getSelectedItem();

        if (selectedOption.equals("Tất cả")) {
            loadUserData();
        } else if (selectedOption.equals("Hoạt động")) {
            ArrayList<UserDTO> activeUsers = userBUS.filterUsersByStatus(true);
            displayUsers(activeUsers);
        } else if (selectedOption.equals("Ngừng hoạt động")) {
            ArrayList<UserDTO> inactiveUsers = userBUS.filterUsersByStatus(false);
            displayUsers(inactiveUsers);
        }
    }

    /**
     * Display employees in the table
     */
    private void displayUsers(ArrayList<UserDTO> users) {
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        model.setRowCount(0); // Clear current data

        for (UserDTO user : users) {
            // Look up the role name based on role ID
            String roleID = user.getRoleID();
            RoleDTO role = roleBUS.getRoleByID(roleID);
            String roleName = (role != null) ? role.getRoleName() : "Unknown";

            Object[] row = {
                    user.getUserID(),
                    user.getUsername(),
                    roleName,
                    user.getStatus() ? "Hoạt động" : "Ngừng hoạt động"
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlUser1 = new javax.swing.JPanel();
        txtSearchUser = new javax.swing.JTextField();
        cbUser = new javax.swing.JComboBox<>();
        pnlUserButton = new javax.swing.JPanel();
        btnAddUser = new javax.swing.JButton();
        btnRefeshUser = new javax.swing.JButton();
        btnPdf = new javax.swing.JButton();
        pnlUser2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        pnlUser1.setBackground(new java.awt.Color(255, 255, 255));
        pnlUser1.setToolTipText("");

        pnlUser1.setPreferredSize(new java.awt.Dimension(1180, 40));

        pnlUser1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        txtSearchUser.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearchUser.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchUser.setText("Tìm kiếm");
        txtSearchUser.setHighlighter(null);
        txtSearchUser.setMinimumSize(new java.awt.Dimension(250, 22));
        txtSearchUser.setPreferredSize(new java.awt.Dimension(250, 22));
        txtSearchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchUserActionPerformed(evt);
            }
        });
        pnlUser1.add(txtSearchUser);

        cbUser.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoạt động", "Ngừng hoạt động", "Tất cả" }));
        cbUser.setFocusable(false);
        cbUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUserActionPerformed(evt);
            }
        });
        pnlUser1.add(cbUser);

        pnlUserButton.setBackground(new java.awt.Color(255, 255, 255));
        pnlUserButton.setPreferredSize(new java.awt.Dimension(310, 35));
        pnlUserButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        btnAddUser.setText("Tạo");
        btnAddUser.setFocusable(false);
        btnAddUser.setMaximumSize(new java.awt.Dimension(72, 22));
        btnAddUser.setMinimumSize(new java.awt.Dimension(72, 22));
        btnAddUser.setPreferredSize(new java.awt.Dimension(72, 22));
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });
        pnlUserButton.add(btnAddUser);

        btnRefeshUser.setText("Tải lại");
        btnRefeshUser.setFocusable(false);
        btnRefeshUser.setMaximumSize(new java.awt.Dimension(72, 22));
        btnRefeshUser.setMinimumSize(new java.awt.Dimension(72, 22));
        btnRefeshUser.setPreferredSize(new java.awt.Dimension(72, 22));
        btnRefeshUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshUserActionPerformed(evt);
            }
        });
        pnlUserButton.add(btnRefeshUser);

        btnPdf.setText("In PDF");
        btnPdf.setFocusable(false);
        btnPdf.setMaximumSize(new java.awt.Dimension(72, 22));
        btnPdf.setMinimumSize(new java.awt.Dimension(72, 22));
        btnPdf.setPreferredSize(new java.awt.Dimension(72, 22));
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });
        pnlUserButton.add(btnPdf);

        pnlUser1.add(pnlUserButton);

        add(pnlUser1, java.awt.BorderLayout.NORTH);

        pnlUser2.setBackground(new java.awt.Color(255, 255, 255));
        pnlUser2.setPreferredSize(new java.awt.Dimension(1180, 700));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1180, 650));

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Mã người dùng", "Tên người dùng", "Vai trò", "Trạng thái"
                }));
        tblUsers.setFocusable(false);
        tblUsers.setMinimumSize(new java.awt.Dimension(500, 80));

        tblUsers.setPreferredSize(new java.awt.Dimension(1180, 700));

        tblUsers.setRowHeight(30);
        tblUsers.setShowGrid(true);
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsers);

        javax.swing.GroupLayout pnlUser2Layout = new javax.swing.GroupLayout(pnlUser2);
        pnlUser2.setLayout(pnlUser2Layout);
        pnlUser2Layout.setHorizontalGroup(
                pnlUser2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pnlUser2Layout.setVerticalGroup(
                pnlUser2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlUser2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

        );

        add(pnlUser2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cbUserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbUserActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbUserActionPerformed

    private void txtSearchUserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchUserActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtSearchUserActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddUserActionPerformed
        AddUser addDialog = new AddUser((JFrame) SwingUtilities.getWindowAncestor(this), true);
        addDialog.setLocationRelativeTo(null);
        addDialog.setVisible(true);
    }// GEN-LAST:event_btnAddUserActionPerformed

    private void btnRefeshUserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRefeshUserActionPerformed
        // Reset the search field
        txtSearchUser.setText("Tìm kiếm");
        txtSearchUser.setFont(new java.awt.Font("Segoe UI", 2, 12));
        txtSearchUser.setForeground(new java.awt.Color(153, 153, 153));

        // Reset the status filter combobox
        cbUser.setSelectedIndex(0);

        // Reload the user data from the database
        userBUS.loadUserList();

        // Display the refreshed user data
        filterUsersByStatus();
    }// GEN-LAST:event_btnRefeshUserActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPdfActionPerformed
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();

            // Use the PDFExporter utility class to export employee data
            com.pharmacy.app.Utils.PDFExporter.exportUsersToPDF(this, model);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lỗi khi xuất PDF: " + e.getMessage(),
                    "Lỗi",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_btnPdfActionPerformed

    private void tblUsersMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblUsersMouseClicked
        int selectedRow = tblUsers.getSelectedRow();
        if (selectedRow != -1) {
            // Get id of selected row
            String id = tblUsers.getValueAt(selectedRow, 0).toString();
            // Create DTO object
            UserDTO selectedUser = userBUS.getUserByID(id);
            UpdateUser detailDialog = new UpdateUser((JFrame) SwingUtilities.getWindowAncestor(this), true,
                    selectedUser);
            detailDialog.setLocationRelativeTo(null);
            detailDialog.setVisible(true);
        }
    }// GEN-LAST:event_tblUsersMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnPdf;
    private javax.swing.JButton btnRefeshUser;
    private javax.swing.JComboBox<String> cbUser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlUser1;
    private javax.swing.JPanel pnlUser2;
    private javax.swing.JPanel pnlUserButton;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtSearchUser;
    // End of variables declaration//GEN-END:variables
}
