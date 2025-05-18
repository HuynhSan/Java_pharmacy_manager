/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.GUI.WorkSchedule;

import com.pharmacy.app.BUS.WorkShiftBUS;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author Giai Cuu Li San
 */
public class AddShiftDialog extends JDialog{
    private JTextField txtShiftId;
    private JSpinner spinnerStart, spinnerEnd;
    private JButton btnAdd, btnCancel;
    WorkShiftBUS bus = new WorkShiftBUS();

    public interface ShiftAddedListener {
        void onShiftAdded(String shiftId, LocalTime startTime, LocalTime endTime);
    }

    private ShiftAddedListener listener;

    public AddShiftDialog(Frame owner, ShiftAddedListener listener) {
        super(owner, "Thêm ca làm việc", true);
        this.listener = listener;
        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblShiftId = new JLabel("Mã ca:");
        JLabel lblStart = new JLabel("Giờ bắt đầu:");
        JLabel lblEnd = new JLabel("Giờ kết thúc:");

        txtShiftId = new JTextField(15);
        txtShiftId.setEditable(false);
        txtShiftId.setEnabled(false);
        
        txtShiftId.setText(bus.generateShiftId());

        spinnerStart = new JSpinner(new SpinnerDateModel());
        spinnerEnd = new JSpinner(new SpinnerDateModel());

        // Định dạng giờ: phút
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(spinnerStart, "HH:mm");
        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(spinnerEnd, "HH:mm");
        spinnerStart.setEditor(startEditor);
        spinnerEnd.setEditor(endEditor);

        btnAdd = new JButton("Thêm");
        btnCancel = new JButton("Hủy");

        // Layout
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0; add(lblShiftId, gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(txtShiftId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(lblStart, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(spinnerStart, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(lblEnd, gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(spinnerEnd, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Hành động nút
        btnAdd.addActionListener(e -> addShift());
        btnCancel.addActionListener(e -> dispose());
    }

    private void addShift() {
        String shiftId = txtShiftId.getText().trim();
        Date startDate = (Date) spinnerStart.getValue();
        Date endDate = (Date) spinnerEnd.getValue();

        LocalTime startTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
        LocalTime endTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);

        if (startTime.isAfter(endTime)) {
            JOptionPane.showMessageDialog(this, "Giờ bắt đầu phải trước giờ kết thúc.");
            return;
        }

        if (listener != null) {
            listener.onShiftAdded(shiftId, startTime, endTime);
        }

        dispose();
    }
}
