/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.pharmacy.app.GUI.User;

import com.pharmacy.app.DAO.UserDAO;
import com.pharmacy.app.DTO.SessionDTO;
import com.pharmacy.app.DTO.UserDTO;
import com.pharmacy.app.Utils.UserValidation;
import com.pharmacy.app.loginPage;
import javax.swing.JOptionPane;

/**
 *
 * @author phong
 */
public class UpdateUserPassword extends javax.swing.JDialog {

    private static boolean isForgotPasswordMode = false;

    /**
     * Creates new form UpdateUserPassword
     * @param parent
     * @param modal
     * @param isForgot
     */
    public UpdateUserPassword(java.awt.Frame parent, boolean modal, boolean isForgot) {
        super(parent, modal);
        initComponents();
        setForgotPasswordMode(isForgot);
        isForgotPasswordMode = isForgot;
    }
    public final void setForgotPasswordMode(boolean forgotPassword) {
        isForgotPasswordMode = forgotPassword;
        if (isForgotPasswordMode) {
            // Ẩn ô nhập mật khẩu hiện tại
            txtCurrentPass.setVisible(false);
            lblCurrentPass.setVisible(false);
            // Có thể đổi tiêu đề Dialog nếu muốn
            this.setTitle("Quên mật khẩu");
            lblUpdatePassword.setText("Khôi phục mật khẩu");
        } else {
            txtCurrentPass.setVisible(true);
            lblCurrentPass.setVisible(true);
            this.setTitle("Đổi mật khẩu");
        }
    }
    
    public void ForgotPassword (){
        
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

        pnlUpdatePassword = new javax.swing.JPanel();
        lblUpdatePassword = new javax.swing.JLabel();
        pnlUpdateFields = new javax.swing.JPanel();
        lblCurrentPass = new javax.swing.JLabel();
        txtCurrentPass = new javax.swing.JPasswordField();
        lblNewPass2 = new javax.swing.JLabel();
        lblNewPass = new javax.swing.JLabel();
        txtNewPass2 = new javax.swing.JPasswordField();
        txtNewPass = new javax.swing.JPasswordField();
        pnlUpdateButton = new javax.swing.JPanel();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        pnlUpdatePassword.setBackground(new java.awt.Color(255, 255, 255));
        pnlUpdatePassword.setLayout(new java.awt.BorderLayout());

        lblUpdatePassword.setBackground(new java.awt.Color(255, 255, 255));
        lblUpdatePassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUpdatePassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdatePassword.setText("CẬP NHẬT MẬT KHẨU");
        pnlUpdatePassword.add(lblUpdatePassword, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlUpdatePassword);

        pnlUpdateFields.setBackground(new java.awt.Color(255, 255, 255));
        pnlUpdateFields.setLayout(new java.awt.GridBagLayout());

        lblCurrentPass.setText("Mật khẩu hiện tại:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 30, 20);
        pnlUpdateFields.add(lblCurrentPass, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 10);
        pnlUpdateFields.add(txtCurrentPass, gridBagConstraints);

        lblNewPass2.setText("Nhập lại mật khẩu mới:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 20);
        pnlUpdateFields.add(lblNewPass2, gridBagConstraints);

        lblNewPass.setText("Mật khẩu mới:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 30, 20);
        pnlUpdateFields.add(lblNewPass, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        pnlUpdateFields.add(txtNewPass2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 10);
        pnlUpdateFields.add(txtNewPass, gridBagConstraints);

        getContentPane().add(pnlUpdateFields);

        pnlUpdateButton.setBackground(new java.awt.Color(255, 255, 255));
        pnlUpdateButton.setPreferredSize(new java.awt.Dimension(212, 50));
        pnlUpdateButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        btnUpdate.setBackground(new java.awt.Color(0, 204, 51));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Cập nhật");
        btnUpdate.setPreferredSize(new java.awt.Dimension(80, 30));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlUpdateButton.add(btnUpdate);

        btnCancel.setBackground(new java.awt.Color(153, 153, 153));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Hủy");
        btnCancel.setPreferredSize(new java.awt.Dimension(72, 30));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlUpdateButton.add(btnCancel);

        getContentPane().add(pnlUpdateButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // Get the current logged in user
        UserDTO currentUser = SessionDTO.getCurrentUser(); // Assuming you have a LoginManager class
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, 
                "Không thể xác định người dùng hiện tại!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get password inputs
        String currentPassword = new String(txtCurrentPass.getPassword());
        String newPassword = new String(txtNewPass.getPassword());
        String confirmPassword = new String(txtNewPass2.getPassword());

        // Validate inputs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng điền đầy đủ các trường thông tin!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if current password is correct
        UserDAO userDAO = new UserDAO();
        UserDTO userCheck = userDAO.checkLogin(currentUser.getUsername(), currentPassword);
        if (userCheck == null) {
            JOptionPane.showMessageDialog(this, 
                "Mật khẩu hiện tại không chính xác!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Mật khẩu mới không khớp!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate new password strength
        String passwordError = UserValidation.validatePassword(newPassword);
        if (!passwordError.isEmpty()) {
            JOptionPane.showMessageDialog(this, passwordError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update password in database
        currentUser.setPassword(newPassword);
        boolean success = userDAO.update(currentUser);

        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Cập nhật mật khẩu thành công!", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
            SessionDTO.clearUser();  // Clear the current user session
            this.dispose();  // Close the password update dialog

            // Get the parent frame and close it
            java.awt.Window parent = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (parent != null) {
                parent.dispose();  // Close the parent window (main application window)
            }

            // Launch the login page
            java.awt.EventQueue.invokeLater(() -> {
                new loginPage().setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                "Cập nhật mật khẩu thất bại!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateUserPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateUserPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateUserPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateUserPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UpdateUserPassword dialog = new UpdateUserPassword(new javax.swing.JFrame(), true, isForgotPasswordMode);
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
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel lblCurrentPass;
    private javax.swing.JLabel lblNewPass;
    private javax.swing.JLabel lblNewPass2;
    private javax.swing.JLabel lblUpdatePassword;
    private javax.swing.JPanel pnlUpdateButton;
    private javax.swing.JPanel pnlUpdateFields;
    private javax.swing.JPanel pnlUpdatePassword;
    private javax.swing.JPasswordField txtCurrentPass;
    private javax.swing.JPasswordField txtNewPass;
    private javax.swing.JPasswordField txtNewPass2;
    // End of variables declaration//GEN-END:variables
}
