/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.Product;

import com.pharmacy.app.BUS.MedicalProductsBUS;
import com.pharmacy.app.BUS.ProductBatchBUS;
import com.pharmacy.app.BUS.ProductPromoBUS;
import com.pharmacy.app.DTO.MedicalProductsDTO;
import com.pharmacy.app.DTO.ProductBatchDTO;
import com.pharmacy.app.DTO.ProductPromoDTO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author LENOVO
 */
public class MedicalProducts extends javax.swing.JPanel {
    MedicalProductsBUS medBUS = new MedicalProductsBUS();
    ProductBatchBUS pbBUS = new ProductBatchBUS();

    /**
     * Creates new form MedicinesBatch
     */
    public MedicalProducts() {
        initComponents();
        medListTbl.setDefaultEditor(Object.class, null);
        loadMedList();
        batchListTbl.setDefaultEditor(Object.class, null);
        loadBatchList();
        dateTbl.setDefaultEditor(Object.class, null);
        loadDateList();
        centerTableContent(dateTbl);
        centerTableContent(batchListTbl);
        centerTableContent(medListTbl);

        searchPdtxt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchProduct();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchProduct();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchProduct();

            }
        });
        searchPBtxt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchProductBatch();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchProductBatch();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchProductBatch();

            }
        });
    }

    private void searchProductBatch() {
        String keywordPB = searchPBtxt.getText();
        ArrayList<ProductBatchDTO> result = pbBUS.searchAll(keywordPB);
        showPBToTable(result);
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

    private void searchProduct() {
        String keywordPd = searchPdtxt.getText();
        ArrayList<MedicalProductsDTO> result = medBUS.searchAll(keywordPd);
        showProductToTable(result);
    }

    public void showPBToTable(ArrayList<ProductBatchDTO> list) {
        DefaultTableModel model = (DefaultTableModel) batchListTbl.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        int stt = 1;
        for (ProductBatchDTO batch : list) {
            model.addRow(new Object[] {
                    stt++,
                    batch.getBatchID(),
                    batch.getMedicineName(),
                    batch.getQuantityInStock(),
                    batch.getSupplierName(),
                    batch.getManufacturingDate(),
                    batch.getExpirationDate()
            });
        }
    }

    public void showProductToTable(ArrayList<MedicalProductsDTO> list) {
        DefaultTableModel model = (DefaultTableModel) medListTbl.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        int stt = 1;
        for (MedicalProductsDTO product : list) {
            model.addRow(new Object[] {
                    stt++,
                    product.getMedicineID(),
                    product.getName(),
                    product.getQuantity(),
                    product.getCategory(),
                    product.getStatus()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        medListPn = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        searchPdtxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        medListTbl = new javax.swing.JTable();
        medBatPn = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        searchPBtxt = new javax.swing.JTextField();
        refreshBtn1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        batchListTbl = new javax.swing.JTable();
        medDatePn = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        destroyBtn = new javax.swing.JButton();
        refreshBtn2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dateTbl = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1200, 800));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBackground(new java.awt.Color(231, 231, 231));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1200, 820));

        medListPn.setBackground(new java.awt.Color(255, 255, 255));
        medListPn.setPreferredSize(new java.awt.Dimension(1200, 800));
        medListPn.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(1200, 100));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("DANH SÁCH SẢN PHẨM");
        jLabel5.setPreferredSize(new java.awt.Dimension(209, 45));
        jPanel8.add(jLabel5, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));

        searchPdtxt.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        searchPdtxt.setForeground(new java.awt.Color(204, 204, 204));
        searchPdtxt.setText("Nhập tên sản phẩm");
        searchPdtxt.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel2.add(searchPdtxt);

        addBtn.setText("THÊM THUỐC MỚI");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        jPanel2.add(addBtn);

        refreshBtn.setText("TẢI LẠI");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        jPanel2.add(refreshBtn);

        jPanel8.add(jPanel2, java.awt.BorderLayout.CENTER);

        medListPn.add(jPanel8, java.awt.BorderLayout.NORTH);

        jScrollPane3.setEnabled(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(1200, 700));

        medListTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "SỐ LƯỢNG", "PHÂN LOẠI", "TRẠNG THÁI"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        medListTbl.setPreferredSize(new java.awt.Dimension(1200, 1500));
        medListTbl.setRowHeight(30);
        medListTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                medListTblMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(medListTbl);
        if (medListTbl.getColumnModel().getColumnCount() > 0) {
            medListTbl.getColumnModel().getColumn(0).setMinWidth(90);
            medListTbl.getColumnModel().getColumn(0).setPreferredWidth(100);
            medListTbl.getColumnModel().getColumn(0).setMaxWidth(110);
            medListTbl.getColumnModel().getColumn(1).setMinWidth(120);
            medListTbl.getColumnModel().getColumn(1).setMaxWidth(200);
            medListTbl.getColumnModel().getColumn(3).setMinWidth(100);
            medListTbl.getColumnModel().getColumn(3).setPreferredWidth(120);
            medListTbl.getColumnModel().getColumn(3).setMaxWidth(140);
        }

        medListPn.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Danh sách sản phẩm", medListPn);

        medBatPn.setBackground(new java.awt.Color(255, 255, 255));
        medBatPn.setPreferredSize(new java.awt.Dimension(1200, 760));
        medBatPn.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMinimumSize(new java.awt.Dimension(237, 60));
        jPanel6.setPreferredSize(new java.awt.Dimension(1200, 100));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DANH SÁCH LÔ SẢN PHẨM");
        jLabel4.setPreferredSize(new java.awt.Dimension(237, 45));
        jPanel6.add(jLabel4, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));

        searchPBtxt.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        searchPBtxt.setForeground(new java.awt.Color(204, 204, 204));
        searchPBtxt.setText("Nhập tên sản phẩm");
        searchPBtxt.setPreferredSize(new java.awt.Dimension(251, 30));
        jPanel1.add(searchPBtxt);

        refreshBtn1.setText("TẢI LẠI");
        refreshBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtn1ActionPerformed(evt);
            }
        });
        jPanel1.add(refreshBtn1);

        jPanel6.add(jPanel1, java.awt.BorderLayout.CENTER);

        medBatPn.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(1200, 650));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setEnabled(false);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1200, 670));

        batchListTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ LÔ", "TÊN SẢN PHẨM", "SỐ LƯỢNG", "NHÀ CUNG CẤP", "NSX", "HSD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        batchListTbl.setPreferredSize(new java.awt.Dimension(1200, 1500));
        batchListTbl.setRowHeight(30);
        jScrollPane2.setViewportView(batchListTbl);
        if (batchListTbl.getColumnModel().getColumnCount() > 0) {
            batchListTbl.getColumnModel().getColumn(0).setMinWidth(40);
            batchListTbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            batchListTbl.getColumnModel().getColumn(0).setMaxWidth(70);
            batchListTbl.getColumnModel().getColumn(1).setMinWidth(70);
            batchListTbl.getColumnModel().getColumn(1).setPreferredWidth(90);
            batchListTbl.getColumnModel().getColumn(1).setMaxWidth(100);
            batchListTbl.getColumnModel().getColumn(3).setMinWidth(60);
            batchListTbl.getColumnModel().getColumn(3).setPreferredWidth(70);
            batchListTbl.getColumnModel().getColumn(3).setMaxWidth(80);
            batchListTbl.getColumnModel().getColumn(4).setMinWidth(200);
            batchListTbl.getColumnModel().getColumn(4).setPreferredWidth(250);
            batchListTbl.getColumnModel().getColumn(4).setMaxWidth(300);
            batchListTbl.getColumnModel().getColumn(5).setMinWidth(90);
            batchListTbl.getColumnModel().getColumn(5).setPreferredWidth(100);
            batchListTbl.getColumnModel().getColumn(5).setMaxWidth(110);
            batchListTbl.getColumnModel().getColumn(6).setMinWidth(90);
            batchListTbl.getColumnModel().getColumn(6).setPreferredWidth(100);
            batchListTbl.getColumnModel().getColumn(6).setMaxWidth(110);
        }

        jPanel7.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        medBatPn.add(jPanel7, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Danh sách lô sản phẩm", medBatPn);

        medDatePn.setBackground(new java.awt.Color(255, 255, 255));
        medDatePn.setPreferredSize(new java.awt.Dimension(1200, 760));
        medDatePn.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1200, 70));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("QUẢN LÝ HẠN SỬ DỤNG");
        jLabel2.setPreferredSize(new java.awt.Dimension(1200, 30));
        jPanel4.add(jLabel2, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(2005, 30));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 50, 5));

        destroyBtn.setText("TIÊU HỦY");
        destroyBtn.setEnabled(false);
        destroyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destroyBtnActionPerformed(evt);
            }
        });
        jPanel3.add(destroyBtn);

        refreshBtn2.setText("TẢI LẠI");
        refreshBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtn2ActionPerformed(evt);
            }
        });
        jPanel3.add(refreshBtn2);

        jPanel4.add(jPanel3, java.awt.BorderLayout.CENTER);

        medDatePn.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1200, 700));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setEnabled(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1200, 700));

        dateTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ LÔ", "MÃ SẢN PHẨM", "SỐ LƯỢNG", "NHÀ CUNG CẤP", "NSX", "HSD", "HSD CÒN LẠI"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dateTbl.setPreferredSize(new java.awt.Dimension(1200, 1500));
        dateTbl.setRequestFocusEnabled(false);
        dateTbl.setRowHeight(30);
        dateTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dateTbl);
        if (dateTbl.getColumnModel().getColumnCount() > 0) {
            dateTbl.getColumnModel().getColumn(0).setMinWidth(60);
            dateTbl.getColumnModel().getColumn(0).setPreferredWidth(70);
            dateTbl.getColumnModel().getColumn(0).setMaxWidth(80);
            dateTbl.getColumnModel().getColumn(1).setMinWidth(100);
            dateTbl.getColumnModel().getColumn(1).setPreferredWidth(120);
            dateTbl.getColumnModel().getColumn(1).setMaxWidth(140);
            dateTbl.getColumnModel().getColumn(2).setMinWidth(140);
            dateTbl.getColumnModel().getColumn(2).setPreferredWidth(150);
            dateTbl.getColumnModel().getColumn(2).setMaxWidth(200);
            dateTbl.getColumnModel().getColumn(3).setMinWidth(60);
            dateTbl.getColumnModel().getColumn(3).setPreferredWidth(70);
            dateTbl.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        medDatePn.add(jPanel5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Quản lý hạn sử dụng", medDatePn);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_addBtnActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_addBtnMouseClicked
        AddProduct addDialog = new AddProduct((Frame) SwingUtilities.getWindowAncestor(addBtn), true);
        addDialog.setLocationRelativeTo(null);
        addDialog.setVisible(true);
    }// GEN-LAST:event_addBtnMouseClicked

    private void loadMedList() {
        ArrayList<MedicalProductsDTO> medList = medBUS.getAllProducts();
        DefaultTableModel model = (DefaultTableModel) medListTbl.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (MedicalProductsDTO med : medList) {
            model.addRow(new Object[] {
                    stt++,
                    med.getMedicineID(),
                    med.getName(),
                    med.getQuantity(),
                    med.getCategory(),
                    med.getStatus()
            });
        }
    }

    private void loadBatchList() {
        try {
            ProductBatchBUS medBUS = new ProductBatchBUS();
            ArrayList<ProductBatchDTO> batchList = medBUS.getAllBatches();
            DefaultTableModel model = (DefaultTableModel) batchListTbl.getModel();
            model.setRowCount(0);

            int stt = 1;
            for (ProductBatchDTO batch : batchList) {
                model.addRow(new Object[] {
                        stt++,
                        batch.getBatchID(),
                        batch.getMedicineName(),
                        batch.getQuantityInStock(),
                        batch.getSupplierName(),
                        batch.getManufacturingDate(),
                        batch.getExpirationDate()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    private void loadDateList() {

        try {
            ProductBatchBUS medBUS = new ProductBatchBUS();
            ArrayList<ProductBatchDTO> batchList = medBUS.getAllBatches();
            DefaultTableModel model = (DefaultTableModel) dateTbl.getModel();
            model.setRowCount(0);

            int stt = 1;
            for (ProductBatchDTO batch : batchList) {
                model.addRow(new Object[] {
                        stt++,
                        batch.getBatchID(),
                        batch.getMedicineID(),
                        batch.getQuantityInStock(),
                        batch.getSupplierName(),
                        batch.getManufacturingDate(),
                        batch.getExpirationDate(),
                        medBUS.getDateforBatch(batch.getExpirationDate())
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

//    private void setupTableRenderer() {

        // Tạo renderer tùy chỉnh: CĂN GIỮA + ĐỔI MÀU
//        DefaultTableCellRenderer centerAndColorRenderer = new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(
//                    JTable table, Object value, boolean isSelected, boolean hasFocus,
//                    int row, int column) {
//
//                // Căn giữa mọi nội dung
//                setHorizontalAlignment(SwingConstants.CENTER);
//
//                if (column == 7) {
//                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                    LocalDate hsd = (LocalDate) table.getModel().getValueAt(row, 6);
//                    long monthsLeft = Period.between(LocalDate.now(), hsd).toTotalMonths();
//                    if (monthsLeft < 6 && monthsLeft >= 3) {
//                        c.setForeground(Color.YELLOW); // Thay đổi màu chữ của ô
//                    } else if (monthsLeft < 3) {
//                        c.setForeground(Color.RED);
//                    } else {
//                        c.setForeground(Color.BLACK); // Màu mặc định (đen)
//                    }
//                    return c;
//                }
//                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            }
//        };
//        // Áp dụng renderer cho tất cả cột (căn giữa toàn bộ)
//        for (int i = 0; i < dateTbl.getColumnCount(); i++) {
//            dateTbl.getColumnModel().getColumn(i).setCellRenderer(centerAndColorRenderer);
//        }
//    }

    private void medListTblMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_medListTblMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int row = medListTbl.getSelectedRow();
            if (row >= 0) {
                String ID = medListTbl.getValueAt(row, 1).toString();
                MedicalProductsDTO selectedProduct = medBUS.getProductByID(ID);

                // Mở dialog
                Frame frame = JOptionPane.getFrameForComponent(medListTbl);
                ProductDetails dialog = new ProductDetails(frame, true, selectedProduct);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        }

    }// GEN-LAST:event_medListTblMouseClicked

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_refreshBtnActionPerformed
        loadMedList();
    }// GEN-LAST:event_refreshBtnActionPerformed

    private void refreshBtn1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_refreshBtn1ActionPerformed
        loadBatchList();
    }// GEN-LAST:event_refreshBtn1ActionPerformed

    private void refreshBtn2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_refreshBtn2ActionPerformed
        loadDateList();
        destroyBtn.setEnabled(false);
    }// GEN-LAST:event_refreshBtn2ActionPerformed

    private void dateTblMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_dateTblMouseClicked
        int selectedRow = dateTbl.getSelectedRow();
        if (selectedRow != -1) {
            destroyBtn.setEnabled(true); // Enable nút khi click vào dòng
        }
    }// GEN-LAST:event_dateTblMouseClicked

    private void destroyBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_destroyBtnActionPerformed
        int row = dateTbl.getSelectedRow();
        if (row >= 0) {
            Object expObj = dateTbl.getValueAt(row, 6);
            if (expObj instanceof LocalDate) {
                LocalDate exp = (LocalDate) expObj;
                Period p = Period.between(LocalDate.now(), exp);
                int totalDays = p.getYears() * 365 + p.getMonths() * 30 + p.getDays();

                if (totalDays <= 90 && totalDays >= 0) {
                    JOptionPane.showMessageDialog(this, "HSD còn lại dưới 3 tháng. Tiến hành tiêu huỷ...");
                    // TODO: Viết hành động tiêu hủy t đâyại
                } else if (totalDays < 0) {
                    JOptionPane.showMessageDialog(this, "Lô thuốc này đã hết hạn. Có thể tiêu huỷ.");
                    // TODO: Tiêu hủy luôn nếu muốn
                } else {
                    JOptionPane.showMessageDialog(this, "Chỉ được tiêu huỷ lô thuốc có HSD còn lại dưới 3 tháng!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ngày hết hạn không hợp lệ!");
            }

        }

    }// GEN-LAST:event_destroyBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTable batchListTbl;
    private javax.swing.JTable dateTbl;
    private javax.swing.JButton destroyBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel medBatPn;
    private javax.swing.JPanel medDatePn;
    private javax.swing.JPanel medListPn;
    private javax.swing.JTable medListTbl;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton refreshBtn1;
    private javax.swing.JButton refreshBtn2;
    private javax.swing.JTextField searchPBtxt;
    private javax.swing.JTextField searchPdtxt;
    // End of variables declaration//GEN-END:variables
}
