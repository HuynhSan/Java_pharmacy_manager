/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.Importing;
import com.pharmacy.app.BUS.SuplierInvoiceDetailsBUS;
import com.pharmacy.app.BUS.SuplierInvoicesBUS;
import com.pharmacy.app.DAO.SupplierInvoicesDAO;
import com.pharmacy.app.DTO.SuplierInvoiceDTO;
import com.pharmacy.app.DTO.SuplierInvoiceDetailsDTO;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
//import java.util.List;
import java.util.ArrayList;
import java.util.Locale;


import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LENOVO
 */
public final class Invoices extends javax.swing.JPanel {
    private final SupplierInvoicesDAO supInvoiceDAO = new SupplierInvoicesDAO();
    private SuplierInvoicesBUS supInvoiceBUS;
    private SuplierInvoiceDetailsBUS supInvoiceDetailsBUS;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat currencyFomat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    JDateChooser dateChooser = new JDateChooser();

    /**
     * Creates new form Invoices
     */
    public Invoices() {
        initComponents();
        initBUS();
        loadSupInvoiceData();
        setupListeners();
    }
    private void initBUS(){
        supInvoiceBUS = new SuplierInvoicesBUS();
        supInvoiceDetailsBUS = new SuplierInvoiceDetailsBUS();
        supInvoiceBUS.loadSupInvoiceList();
    }
    private void setupListeners(){
        txtSearch.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Tìm kiếm")) {
                    txtSearch.setText("");
                    txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 12));
                    txtSearch.setForeground(new java.awt.Color(0, 0, 0));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Tìm kiếm");
                    txtSearch.setFont(new java.awt.Font("Segoe UI", 2, 12));
                    txtSearch.setForeground(new java.awt.Color(153, 153, 153));
                }
            }
        });
        // Setup search text field key listener
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearch.getText().trim();
                if (!keyword.equals("Tìm kiếm")) {
                    searchInvoice(keyword);
                }
            }
        });
    }
    
    private void displayList(ArrayList<SuplierInvoiceDTO> supInvoiceList) {
        DefaultTableModel model = (DefaultTableModel) tbInvoiceHistory.getModel();
        model.setRowCount(0);

        for (SuplierInvoiceDTO si: supInvoiceList){
            model.addRow(new Object[]{
                si.getInvoiceID(),
                si.getTotalQuantity(),
                si.getTotalPrice(),
                si.getSupplierID(),
                si.getPurchaseDate().format(DATE_FORMAT),
                si.getManagerID()
            });
        }
    }

    public void loadSupInvoiceData(){
        ArrayList<SuplierInvoiceDTO> supInvoiceList = supInvoiceBUS.getAllSuplierInvoice();
        displayList(supInvoiceList);
    }
    
    public void loadSupInvoiceDetail(String supInvoiceID){
        // Lay thong tin chi tiet cua supplier invoice
        SuplierInvoiceDTO infoSupInvoice = supInvoiceBUS.getSupInvoiceByID(supInvoiceID);
        txtInvoiceID.setText(infoSupInvoice.getInvoiceID());
        txtSupplier.setText(infoSupInvoice.getSupplierName());
        txtPurchaseDate.setText(infoSupInvoice.getPurchaseDate().format(DATE_FORMAT));
        txtManagerName.setText(infoSupInvoice.getManagerName());

        // Lay noi dung cua supplier invoice
        ArrayList<SuplierInvoiceDetailsDTO> supInvoiceDetails = supInvoiceDetailsBUS.getHistoryByID(supInvoiceID);

        DefaultTableModel model = (DefaultTableModel) tbSupInvoiceDetail.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (SuplierInvoiceDetailsDTO sid: supInvoiceDetails){
            model.addRow(new Object[]{
                stt++,
                sid.getBatchID(),
                sid.getProductID(),
                sid.getName(),
                sid.getUnitPrice(),
                sid.getQuantity(),
                sid.getTotalPrice()
            });
        }
    }
    
    public double calculateTotalAmount() {
        DefaultTableModel model = (DefaultTableModel) tbSupInvoiceDetail.getModel();
        Double total = 0.0;

        for (int i = 0; i < model.getRowCount(); i++) {
            Object value = model.getValueAt(i, 6);
            if (value != null) {
                try {
                    total += Double.parseDouble(value.toString());
                } catch (NumberFormatException e) {
                    System.out.println("Không thể chuyển thành tiền dòng " + i + ": " + value);
                }
            }
        }
        return total;
    }
    
    private void searchInvoice(String keyword){
        if (keyword.isEmpty() || keyword.equals("Tìm kiếm")) {
            loadSupInvoiceData(); // Hiển thị lại toàn bộ nếu người dùng xóa từ khóa
            return;
        }
        ArrayList<SuplierInvoiceDTO> searchResult = supInvoiceBUS.search(keyword);
        displayList(searchResult);
    }


//    private void filterByDate(LocalDate date){
//        ArrayList<SuplierInvoiceDTO> result = supInvoiceBUS.filterByDate(date);
//        displayList(result);
//    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        importBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbInvoiceHistory = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbSupInvoiceDetail = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtPurchaseDate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtInvoiceID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtManagerName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSupplier = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(480, 500));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TẠO PHIẾU NHẬP");
        jPanel8.add(jLabel2, java.awt.BorderLayout.NORTH);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách đơn đặt hàng đã duyệt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã đơn", "Ngày tạo", "Người tạo", "Tổng thành tiền", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Float.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(30);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable2);

        jComboBox5.setEditable(true);
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ngày tạo đơn", "Tên người tạo", "Nhà cung cấp" }));

        jLabel10.setText("Tìm theo: ");

        jTextField4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(204, 204, 204));
        jTextField4.setText("Nhập....");

        jButton4.setText("TÌM KIẾM");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 734, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(60, 60, 60))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );

        jPanel8.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(309, 30));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        importBtn.setBackground(new java.awt.Color(0, 204, 51));
        importBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        importBtn.setForeground(new java.awt.Color(255, 255, 255));
        importBtn.setText("NHẬP KHO");
        importBtn.setAlignmentX(0.5F);
        importBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importBtnMouseClicked(evt);
            }
        });
        importBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel11.add(importBtn, gridBagConstraints);

        jPanel8.add(jPanel11, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("Tạo phiếu nhập", jPanel8);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(480, 500));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 518));

        txtSearch.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(153, 153, 153));
        txtSearch.setText("Tìm kiếm");
        txtSearch.setPreferredSize(new java.awt.Dimension(64, 30));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 400));

        tbInvoiceHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu nhập", "Tổng số lượng", "Tổng tiền", "Nhà cung cấp", "Ngày", "Người lập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbInvoiceHistory.setPreferredSize(new java.awt.Dimension(450, 400));
        tbInvoiceHistory.setRowHeight(30);
        tbInvoiceHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbInvoiceHistoryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbInvoiceHistory);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("PHIẾU NHẬP");
        jPanel3.add(jLabel9, java.awt.BorderLayout.NORTH);

        jPanel2.add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(650, 529));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CHI TIẾT PHIẾU NHẬP");
        jPanel5.add(jLabel3, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N

        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 400));

        tbSupInvoiceDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Lô thuốc", "Mã thuốc", "Tên thuốc", "Giá nhập", "Số lượng", "Thành tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
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
        tbSupInvoiceDetail.setPreferredSize(new java.awt.Dimension(400, 400));
        tbSupInvoiceDetail.setRowHeight(30);
        jScrollPane2.setViewportView(tbSupInvoiceDetail);
        if (tbSupInvoiceDetail.getColumnModel().getColumnCount() > 0) {
            tbSupInvoiceDetail.getColumnModel().getColumn(0).setPreferredWidth(1);
            tbSupInvoiceDetail.getColumnModel().getColumn(1).setPreferredWidth(5);
            tbSupInvoiceDetail.getColumnModel().getColumn(2).setPreferredWidth(5);
            tbSupInvoiceDetail.getColumnModel().getColumn(3).setPreferredWidth(100);
            tbSupInvoiceDetail.getColumnModel().getColumn(4).setPreferredWidth(10);
            tbSupInvoiceDetail.getColumnModel().getColumn(5).setPreferredWidth(10);
            tbSupInvoiceDetail.getColumnModel().getColumn(6).setPreferredWidth(10);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Ngày lập:");
        jLabel1.setPreferredSize(new java.awt.Dimension(80, 30));

        txtPurchaseDate.setEditable(false);
        txtPurchaseDate.setBackground(new java.awt.Color(255, 255, 255));
        txtPurchaseDate.setBorder(null);
        txtPurchaseDate.setFocusable(false);
        txtPurchaseDate.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Mã đơn:");
        jLabel4.setMaximumSize(new java.awt.Dimension(46, 30));
        jLabel4.setPreferredSize(new java.awt.Dimension(46, 30));

        txtInvoiceID.setEditable(false);
        txtInvoiceID.setBackground(new java.awt.Color(255, 255, 255));
        txtInvoiceID.setBorder(null);
        txtInvoiceID.setFocusable(false);
        txtInvoiceID.setPreferredSize(new java.awt.Dimension(80, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Nhà cung cấp:");
        jLabel5.setPreferredSize(new java.awt.Dimension(80, 30));

        txtManagerName.setEditable(false);
        txtManagerName.setBackground(new java.awt.Color(255, 255, 255));
        txtManagerName.setBorder(null);
        txtManagerName.setFocusable(false);
        txtManagerName.setPreferredSize(new java.awt.Dimension(80, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Người lập:");
        jLabel7.setPreferredSize(new java.awt.Dimension(57, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel8.setText("TỔNG THÀNH TIỀN:");
        jLabel8.setPreferredSize(new java.awt.Dimension(114, 30));

        txtSupplier.setEditable(false);
        txtSupplier.setBackground(new java.awt.Color(255, 255, 255));
        txtSupplier.setBorder(null);
        txtSupplier.setFocusable(false);
        txtSupplier.setPreferredSize(new java.awt.Dimension(64, 30));

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pdf.png"))); // NOI18N
        jButton8.setText("Xuất PDF");
        jButton8.setBorder(null);
        jButton8.setPreferredSize(new java.awt.Dimension(100, 30));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0 VND");
        txtTotal.setBorder(null);
        txtTotal.setFocusable(false);
        txtTotal.setPreferredSize(new java.awt.Dimension(80, 30));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtInvoiceID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtManagerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPurchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvoiceID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtManagerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPurchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Lịch sử nhập hàng", jPanel2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tbSupInvoiceDetail.getModel();
            String invoiceID = txtInvoiceID.getText();
            String supplier = txtSupplier.getText();
            String managerName = txtManagerName.getText();
            String purchaseDate = txtPurchaseDate.getText();
            // Use the PDFExporter utility class to export employee data
            com.pharmacy.app.Utils.PDFExporter.exportSupInvoiceToPDF(this, model, invoiceID, supplier, managerName, purchaseDate);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lỗi khi xuất PDF: " + e.getMessage(),
                    "Lỗi",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void importBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_importBtnActionPerformed

    private void importBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importBtnMouseClicked
        ConfirmStockIn confirmDialog = new ConfirmStockIn((Frame)SwingUtilities.getWindowAncestor(importBtn), true);
        confirmDialog.setLocationRelativeTo(null);
        confirmDialog.setVisible(true);
    }//GEN-LAST:event_importBtnMouseClicked

    private void tbInvoiceHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbInvoiceHistoryMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbInvoiceHistory.getSelectedRow();
        if (selectedRow != -1){
            // Lấy dữ liệu từ các cột trong dòng được chọn
            String id = tbInvoiceHistory.getValueAt(selectedRow, 0).toString();
            
            loadSupInvoiceDetail(id);
            SwingUtilities.invokeLater(() -> {
                txtTotal.setText(String.format("%,.0f VND", calculateTotalAmount()));
            });
            // Tong tien
//            txtTotal.setText(String.format("%,.0f VND", calculateTotalAmount()));
        }
    }//GEN-LAST:event_tbInvoiceHistoryMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton importBtn;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTable tbInvoiceHistory;
    private javax.swing.JTable tbSupInvoiceDetail;
    private javax.swing.JTextField txtInvoiceID;
    private javax.swing.JTextField txtManagerName;
    private javax.swing.JTextField txtPurchaseDate;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSupplier;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
