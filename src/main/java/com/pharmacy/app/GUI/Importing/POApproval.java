/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.GUI.Importing;

import javax.swing.*;

/**
 *
 * @author LENOVO
 */
public class POApproval extends JOptionPane {
    public static boolean showCustomConfirmDialog(String message, String title, String yesText, String noText) {
        String[] options = { "Xác nhận", "Hủy bỏ" };
        int choice = JOptionPane.showOptionDialog(
            null, message, title, 
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
            null, options, options[0]
        );
        
        // Trả về true nếu chọn "Yes" (nút đầu tiên), false nếu chọn "No"
        return choice == 0;
    }

    public static void main(String[] args) {
        showCustomConfirmDialog("Bạn có chắc chắn không ?", "Xác nhận", "Xác nhận", "Hủy bỏ");
    }
}
