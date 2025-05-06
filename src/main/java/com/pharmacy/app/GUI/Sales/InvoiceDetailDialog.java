/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.GUI.Sales;

import com.pharmacy.app.BUS.CustomerBUS;
import com.pharmacy.app.BUS.MedicalProductsBUS;
import com.pharmacy.app.BUS.UserBUS;
import com.pharmacy.app.DTO.SalesInvoiceDTO;
import com.pharmacy.app.DTO.SalesInvoiceDetailDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Giai Cuu Li San
 */
public class InvoiceDetailDialog extends JDialog{
    CustomerBUS customerBus = new CustomerBUS();
    MedicalProductsBUS medicalBus = new MedicalProductsBUS();
    UserBUS userBus = new UserBUS();

    public InvoiceDetailDialog(JFrame parent, SalesInvoiceDTO invoice, ArrayList<SalesInvoiceDetailDTO> detailList) {
        super(parent, "Chi tiết hóa đơn", true);
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(15, 15));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 15);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // === Thông tin hóa đơn ===
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        infoPanel.setFont(titleFont);
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Thông tin hóa đơn"));

        infoPanel.add(createStyledLabel("Mã hóa đơn: " + invoice.getInvoiceId(), labelFont));
        infoPanel.add(createStyledLabel("Khách hàng: " + customerBus.getCustomerNameById(invoice.getCustomerId()), labelFont));
        infoPanel.add(createStyledLabel("Người lập: " + userBus.getUserNameById(invoice.getUserId()), labelFont));
        infoPanel.add(createStyledLabel("Số lượng sản phẩm: " + invoice.getTotalQuantity(), labelFont));
        infoPanel.add(createStyledLabel("Tổng tiền: " + formatMoney(invoice.getTotalAmount()), labelFont));
        infoPanel.add(createStyledLabel("Giảm giá: " + formatMoney(invoice.getTotalDiscount()), labelFont));
        infoPanel.add(createStyledLabel("Thành tiền: " + formatMoney(invoice.getFinalTotal()), labelFont));
        infoPanel.add(createStyledLabel("Ngày tạo: " + invoice.getCreateDate().format(formatter), labelFont));

//        add(infoPanel, BorderLayout.NORTH);
        JPanel infoWrapper = new JPanel();
        infoWrapper.setLayout(new BoxLayout(infoWrapper, BoxLayout.Y_AXIS));
        infoWrapper.setBorder(new EmptyBorder(10, 15, 0, 15)); // Cùng padding với bảng

        infoWrapper.add(infoPanel);
        add(infoWrapper, BorderLayout.NORTH);


        // === Chi tiết sản phẩm ===
        String[] columnNames = {"STT", "Tên sản phẩm", "Số lượng", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        int stt = 1;
        for (SalesInvoiceDetailDTO detail : detailList) {
            model.addRow(new Object[]{
                stt++,
                medicalBus.getMedicineNameByID(detail.getProductId()),
                detail.getQuantity(),
                formatMoney(detail.getTotalPrice())
            });
        }

        JTable table = new JTable(model);
        table.setFont(labelFont);
        table.setRowHeight(25);
        table.getTableHeader().setFont(titleFont);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Căn giữa tiêu đề cột
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        // === Panel "Danh sách sản phẩm" + Bảng ===
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel lblTableTitle = new JLabel("Danh sách sản phẩm");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTableTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn trái
        lblTableTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn cho bảng khớp label
        tablePanel.add(lblTableTitle);
        tablePanel.add(scrollPane);

        add(tablePanel, BorderLayout.CENTER);

        // === Nút Đóng ===
        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(labelFont);
        btnClose.setPreferredSize(new Dimension(120, 40));
        btnClose.addActionListener(e -> dispose());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private String formatMoney(BigDecimal amount) {
        return String.format("%,.0f VNĐ", amount);
    }
}
