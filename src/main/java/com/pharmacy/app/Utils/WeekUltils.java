/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JComboBox;

/**
 *
 * @author Giai Cuu Li San
 */
public class WeekUltils {
        // Hàm để điền danh sách tuần vào ComboBox
    public static void populateWeeksComboBox(JComboBox<String> comboBox, int numberOfWeeks, LocalDate startDate) {
        comboBox.removeAllItems(); // Xoá các item cũ nếu có

        // Tìm thứ 2 gần nhất từ ngày bắt đầu
        LocalDate startOfWeek = startDate.with(DayOfWeek.MONDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < numberOfWeeks; i++) {
            LocalDate start = startOfWeek.plusWeeks(i);
            LocalDate end = start.plusDays(6); // Chủ nhật
            String weekRange = formatter.format(start) + " - " + formatter.format(end);
            comboBox.addItem(weekRange);
        }
        
        // Tự động chọn tuần hiện tại
        LocalDate today = LocalDate.now();
        for (int i = 0; i < numberOfWeeks; i++) {
            LocalDate start = startOfWeek.plusWeeks(i);
            LocalDate end = start.plusDays(6);

            if ((today.isEqual(start) || today.isAfter(start)) && (today.isEqual(end) || today.isBefore(end))) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }
}
