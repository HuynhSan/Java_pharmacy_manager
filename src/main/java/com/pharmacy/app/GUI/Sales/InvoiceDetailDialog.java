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
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Format thời gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Panel thông tin chung hóa đơn
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        infoPanel.add(new JLabel("Mã hóa đơn: " + invoice.getInvoiceId()));
        infoPanel.add(new JLabel("Khách hàng: " + customerBus.getCustomerNameById(invoice.getCustomerId())));
        infoPanel.add(new JLabel("Người lập: " + userBus.getUserNameById(invoice.getUserId())));
        infoPanel.add(new JLabel("Số lượng: " + invoice.getTotalQuantity()));
        infoPanel.add(new JLabel("Tổng tiền: " + invoice.getTotalAmount() + " VNĐ"));
        infoPanel.add(new JLabel("Giảm giá: " + invoice.getTotalDiscount()+ " VNĐ"));
        infoPanel.add(new JLabel("Thành tiền: " + invoice.getFinalTotal() + " VNĐ"));
        infoPanel.add(new JLabel("Ngày tạo: " + invoice.getCreateDate().format(formatter)));

        add(infoPanel, BorderLayout.NORTH);

        // Bảng chi tiết sản phẩm
        String[] columnNames = {"Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (SalesInvoiceDetailDTO detail : detailList) {
            model.addRow(new Object[]{
                detail.getInvoiceId(),
                medicalBus.getMedicineNameByID(detail.getProductId()),
                detail.getQuantity(),
                detail.getTotalPrice()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        add(scrollPane, BorderLayout.CENTER);

        // Nút đóng
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
