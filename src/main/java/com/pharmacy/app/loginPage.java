/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.pharmacy.app;

import com.pharmacy.app.DTO.SessionDTO;
import com.pharmacy.app.GUI.User.UpdateUserPassword;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author phong
 */
public class loginPage extends javax.swing.JFrame {

    /**
     * Creates new form loginPage
     */
    public loginPage() {
        initComponents();
        this.setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnLogin);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblLogin = new javax.swing.JLabel();
        pnlLoginFields = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        jPasswordField = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        txtForgetPassword = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng nhập");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(400, 280));
        setResizable(false);
        getContentPane().setLayout(new java.awt.BorderLayout(0, 10));

        pnlLogin.setBackground(new java.awt.Color(255, 255, 255));
        pnlLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlLogin.setMaximumSize(new java.awt.Dimension(330, 330));
        pnlLogin.setMinimumSize(new java.awt.Dimension(330, 330));
        pnlLogin.setPreferredSize(new java.awt.Dimension(330, 330));
        pnlLogin.setVerifyInputWhenFocusTarget(false);
        pnlLogin.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));

        lblLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(255, 255, 255));
        lblLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogin.setText("ĐĂNG NHẬP");
        lblLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 156, Short.MAX_VALUE)
                    .addComponent(lblLogin)
                    .addGap(0, 156, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 15, Short.MAX_VALUE)
                    .addComponent(lblLogin)
                    .addGap(0, 15, Short.MAX_VALUE)))
        );

        pnlLogin.add(jPanel1);

        pnlLoginFields.setBackground(new java.awt.Color(255, 255, 255));
        pnlLoginFields.setMinimumSize(new java.awt.Dimension(300, 170));
        pnlLoginFields.setPreferredSize(new java.awt.Dimension(300, 170));
        pnlLoginFields.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 10));

        lblUsername.setBackground(new java.awt.Color(255, 255, 255));
        lblUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUsername.setText("Tên đăng nhập:");
        lblUsername.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblUsername.setPreferredSize(new java.awt.Dimension(98, 40));
        pnlLoginFields.add(lblUsername);

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.setPreferredSize(new java.awt.Dimension(300, 32));
        pnlLoginFields.add(txtUsername);

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassword.setText("Mật khẩu:");
        pnlLoginFields.add(lblPassword);

        jPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPasswordField.setPreferredSize(new java.awt.Dimension(300, 30));
        jPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordFieldActionPerformed(evt);
            }
        });
        pnlLoginFields.add(jPasswordField);

        pnlLogin.add(pnlLoginFields);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        txtForgetPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtForgetPassword.setText("Quên mật khẩu ?");
        txtForgetPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtForgetPasswordMouseClicked(evt);
            }
        });
        jPanel2.add(txtForgetPassword);

        pnlLogin.add(jPanel2);

        btnLogin.setBackground(new java.awt.Color(0, 102, 155));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.setPreferredSize(new java.awt.Dimension(300, 40));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        pnlLogin.add(btnLogin);

        getContentPane().add(pnlLogin, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // Get username and password from input fields
        String username = txtUsername.getText().trim();
        String password = new String(jPasswordField.getPassword()).trim();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu", 
                    "Lỗi đăng nhập", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create UserBUS to check login
        com.pharmacy.app.BUS.UserBUS userBUS = new com.pharmacy.app.BUS.UserBUS();
        userBUS.loadUserList();

        // Attempt to login
        com.pharmacy.app.DTO.UserDTO loggedInUser = userBUS.checkLogin(username, password);

        if (loggedInUser != null) {
            // Login successful
            SessionDTO.setCurrentUser(loggedInUser);
            // Redirect based on role
            String roleID = loggedInUser.getRoleID();

            this.dispose(); // Close the login window

            switch (roleID) {
                case "ROLE001":
                    // Admin role
                    java.awt.EventQueue.invokeLater(() -> {
                        new adminView().setVisible(true);
                    });
                    break;

                case "ROLE002":
                    // Manager role
                    java.awt.EventQueue.invokeLater(() -> {
                        new managerView().setVisible(true);
                    });
                    break;

                case "ROLE003":
                    // Regular user role
                    java.awt.EventQueue.invokeLater(() -> {
                        new homepage().setVisible(true);
                    });
                    break;

                default:
                    // Unknown role
                    javax.swing.JOptionPane.showMessageDialog(null, 
                            "Không thể xác định quyền hạn của tài khoản. Vui lòng liên hệ quản trị viên.", 
                            "Lỗi phân quyền", 
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    // Reopen login page
                    java.awt.EventQueue.invokeLater(() -> {
                        new loginPage().setVisible(true);
                    });
                    break;
            }
        } else {
            // Login failed
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "Tên đăng nhập hoặc mật khẩu không đúng", 
                    "Lỗi đăng nhập", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);

            // Clear password field for security
            jPasswordField.setText("");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void jPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordFieldActionPerformed

    private void txtForgetPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtForgetPasswordMouseClicked
        UpdateUserPassword  passwordDialog = new UpdateUserPassword((JFrame) SwingUtilities.getWindowAncestor(this), true, true);
        passwordDialog.setLocationRelativeTo(null);
        passwordDialog.setVisible(true);
    }//GEN-LAST:event_txtForgetPasswordMouseClicked

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
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPanel pnlLoginFields;
    private javax.swing.JLabel txtForgetPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
