/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.Sales;

import com.pharmacy.app.BUS.CustomerBUS;
import com.pharmacy.app.BUS.PromotionBUS;
import com.pharmacy.app.BUS.SalesBUS;
import com.pharmacy.app.BUS.SalesInvoiceBUS;
import com.pharmacy.app.BUS.SalesInvoiceDetailBUS;
import com.pharmacy.app.DAO.SalesDAO;
import com.pharmacy.app.DTO.CartItemDTO;
import com.pharmacy.app.DTO.CustomerDTO;
import com.pharmacy.app.DTO.PromotionDTO;
import com.pharmacy.app.DTO.SaleItemDTO;
import com.pharmacy.app.DTO.SalesInvoiceDTO;
import com.pharmacy.app.DTO.SalesInvoiceDetailDTO;
import com.pharmacy.app.DTO.SessionDTO;
import com.pharmacy.app.DTO.UserDTO;
//import com.pharmacy.app.GUI.Authorization.*;
//import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author phong
 */
public class HomeSales extends javax.swing.JPanel {
    UserDTO current_user = SessionDTO.getCurrentUser();
    private String user_id = current_user.getUserID();    
    private String currentCustomerId;
    
    private SalesBUS saleItemBUS = new SalesBUS();    
    private CustomerBUS customerBUS = new CustomerBUS();
    private PromotionBUS promoBUS = new PromotionBUS();    
    private SalesInvoiceBUS invoiceBUS = new SalesInvoiceBUS();    
    private SalesInvoiceDetailBUS invoicedetailsBUS = new SalesInvoiceDetailBUS();


    private ArrayList<SaleItemDTO> saleItemList = new ArrayList<>();
    private ArrayList<SalesInvoiceDTO> invoiceList = new ArrayList<>();

    private Map<String, CartItemDTO> cartItemsMap = new HashMap<>(); // Khai báo ở class để lưu các thuốc đã thêm, key là batch_id
    private Map<String, String> customerNameCache = new HashMap<>();
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Creates new form AuthorizationManagement
     */
    public HomeSales() {
        initComponents();
        loadAllData();
        setupListeners();
        centerTableContent(tblInvoice);
        centerTableContent(tblProduct);
        setupDateFilterListener();
        
        txtSearchProduct.getDocument().addDocumentListener(new DocumentListener() {
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
        
        txtPhoneCustomer.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handlePhoneInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handlePhoneInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handlePhoneInput();
            }
        });
        
    }
    
    //
    private void setupListeners(){
        // Setup search text field key listener
        txtSearchInvoice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchInvoice();
            }

            private void searchInvoice() {
                String keyword = txtSearchInvoice.getText().trim();
                Date fromDate = date_start.getDate();
                Date toDate = date_end.getDate();

                boolean hasKeyword = !keyword.isEmpty();
                boolean hasFromDate = fromDate != null;
                boolean hasToDate = toDate != null;

                ArrayList<SalesInvoiceDTO> result;

                // Nếu có cả ngày bắt đầu và kết thúc
                if (hasFromDate && hasToDate) {
                    LocalDate from = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate to = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (from.isAfter(to)) {
                        JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
                        return;
                    }

                    if (hasKeyword) {
                        ArrayList<SalesInvoiceDTO> searchResult = invoiceBUS.searchInvoice(keyword);
                        result = invoiceBUS.filterByDateRangeMix(searchResult, from, to);
                    } else {
                        result = invoiceBUS.filterByDateRange(from, to);
                    }
                    showDataToTableInvoice(result);
                }

                // Nếu chỉ có từ khoá
                else if (hasKeyword) {
                    result = invoiceBUS.searchInvoice(keyword);
                    showDataToTableInvoice(result);
                }

                // Nếu không có gì cả → load lại tất cả
                else {
                    loadAllData();
                }
            }
        });
    }
    
    private void setupDateFilterListener() {
        PropertyChangeListener dateFilterListener = evt -> {
            ArrayList<SalesInvoiceDTO> result;
            if (date_start.getDate() != null && date_end.getDate() != null) {
                Date fromDate = date_start.getDate();
                Date toDate = date_end.getDate();

                LocalDate from = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate to = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (from.isAfter(to)){
                    JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!");
                    return;
                }
                result = invoiceBUS.filterByDateRange(from, to);
                showDataToTableInvoice(result); // cập nhật lại bảng

                System.out.println("From: " + from + ", To: " + to);
                System.out.println("Result: " + result);
            }
            
        };


        date_start.getDateEditor().addPropertyChangeListener("date", dateFilterListener);
        date_end.getDateEditor().addPropertyChangeListener("date", dateFilterListener);
    }
     
    public void reload(){
        tblProduct.clearSelection();  // Bỏ mọi dòng đang được chọn
        cartItemsMap.clear();
        txtPhoneCustomer.setText("");
        cartPanel.removeAll(); // Xóa giao diện các item
        cartPanel.revalidate(); // Cập nhật lại layout
        cartPanel.repaint();
        updatePayment(); // Cập nhật lại khu vực thanh toán

    }
    
    
    public void loadAllData() {
        System.out.println("Load Danh Sach");
        saleItemList = saleItemBUS.selectSaleItems();
        invoiceList = invoiceBUS.selectAll();
        showDataToTableProduct(saleItemList);
        adjustTableHeight(tblProduct, jScrollPane2);
        showDataToTableInvoice(invoiceList);
        adjustTableHeight(tblInvoice, jScrollPane3);
        txtPromoId.setText("Không có!");
        txtDiscount.setText("0");
    }
            
    
    public void showHashMap(){
        // Sau khi thêm vào cartPanel, thêm đoạn này để debug
        System.out.println("==> Giỏ hàng hiện tại:");
        for (Map.Entry<String, CartItemDTO> entry : cartItemsMap.entrySet()) {
            CartItemDTO cartitem = entry.getValue();
            System.out.println("Batch ID: " + cartitem.getBatchId()
               + ", Product ID: " + cartitem.getProductId()
               + ", Tên: " + cartitem.getName()
               + ", SL: " + cartitem.getQuantityFromSpinner()
               + ", Giá gốc: " + cartitem.getSellPrice()
               + ", Mã khuyến mãi: " + cartitem.getPromoId()
               + ", Tiền khuyến mãi: " + cartitem.getDiscountAmount()
               + ", Giá sau KM: " + cartitem.getFinalPrice());
        }
        System.out.println("------------------------------");

    }
    
    public void adjustTableHeight(JTable table, JScrollPane scrollPane) {
        int rowCount = table.getRowCount();
        int rowHeight = table.getRowHeight();

        // Tính tổng chiều cao cần thiết cho tất cả dòng
        int totalHeight = rowCount * rowHeight;

        // Đặt kích thước preferred viewport cho bảng
        table.setPreferredScrollableViewportSize(new Dimension(
            table.getPreferredScrollableViewportSize().width,
            totalHeight
        ));

        // Làm cho bảng lấp đầy JScrollPane
        table.setFillsViewportHeight(true);

        // Gọi lại validate để cập nhật layout
        scrollPane.revalidate();
    }

       
    
    public void showDataToTableProduct(ArrayList<SaleItemDTO> list) {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (SaleItemDTO saleItem : list) {
            Object[] row = new Object[] {
                saleItem.getBatchId(),
                saleItem.getName(),
                saleItem.getUnit(),
                formatMoney_1(saleItem.getSellPrice()),
                saleItem.getInventoryQuantity(),
                saleItem.getExpirationDate(),
                saleItem.getPercentDiscount(),
                formatMoney_1(saleItem.getDiscountAmount()),
                formatMoney_1(saleItem.getFinalPrice())
            };
            model.addRow(row);
        }
    }
    
    public void showDataToTableInvoice(ArrayList<SalesInvoiceDTO> list) {
        DefaultTableModel model = (DefaultTableModel) tblInvoice.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (SalesInvoiceDTO invoice : list) {
            String customerId = invoice.getCustomerId();
            String customerName;
            
            if (customerNameCache.containsKey(customerId)){
                customerName = customerNameCache.get(customerId);
            } else {
                customerName = customerBUS.getCustomerNameById(customerId);
                customerNameCache.put(customerId, customerName);
            }
           
            Object[] row = new Object[] {
                invoice.getInvoiceId(),
                customerName,
                invoice.getCreateDate().format(formatter),
                formatMoney(invoice.getFinalTotal()),
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel19 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtSearchProduct = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnAddMedicineToCart = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        cartPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnClearCart = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lblPhoneCustomer = new javax.swing.JLabel();
        lblNameCustomer = new javax.swing.JLabel();
        lblPointCustomer = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        txtPhoneCustomer = new javax.swing.JTextField();
        txtCustomerName = new javax.swing.JTextField();
        txtLoyaltyPoints = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        lblSoLuongSP = new javax.swing.JLabel();
        lblTotalProductPrice1 = new javax.swing.JLabel();
        lblProductDiscount = new javax.swing.JLabel();
        lblVoucher = new javax.swing.JLabel();
        lblVoucherDiscount = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txtTotalProduct = new javax.swing.JTextField();
        txtTotalProductPrice = new javax.swing.JTextField();
        txtProductDiscount = new javax.swing.JTextField();
        txtPromoId = new javax.swing.JTextField();
        txtDiscount = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        btnPayment = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSearchInvoice = new javax.swing.JTextField();
        btnRefreshInvoice = new javax.swing.JButton();
        btnExportPDF = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        date_start = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        date_end = new com.toedter.calendar.JDateChooser();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(800, 600));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1250, 800));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1250, 770));
        jScrollPane1.setViewportView(jPanel19);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setMinimumSize(new java.awt.Dimension(1250, 780));
        jPanel19.setPreferredSize(new java.awt.Dimension(1250, 760));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 760));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setMinimumSize(new java.awt.Dimension(10, 57));
        jPanel4.setPreferredSize(new java.awt.Dimension(800, 70));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 10));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel9.setText("Tìm thuốc");
        jPanel8.add(jLabel9);

        txtSearchProduct.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        txtSearchProduct.setPreferredSize(new java.awt.Dimension(400, 35));
        jPanel8.add(txtSearchProduct);

        jPanel4.add(jPanel8);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(20, 37));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 40));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAddMedicineToCart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddMedicineToCart.setText("Thêm thuốc vào giỏ ");
        btnAddMedicineToCart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddMedicineToCart.setMargin(new java.awt.Insets(2, 10, 3, 0));
        btnAddMedicineToCart.setMaximumSize(new java.awt.Dimension(150, 27));
        btnAddMedicineToCart.setMinimumSize(new java.awt.Dimension(150, 27));
        btnAddMedicineToCart.setPreferredSize(new java.awt.Dimension(170, 35));
        btnAddMedicineToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMedicineToCartActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddMedicineToCart);

        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefresh.setText("Làm mới ");
        btnRefresh.setPreferredSize(new java.awt.Dimension(90, 35));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jPanel3.add(btnRefresh);

        jPanel4.add(jPanel3);

        jPanel1.add(jPanel4);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH THUỐC");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setPreferredSize(new java.awt.Dimension(800, 35));
        jLabel1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel1PropertyChange(evt);
            }
        });
        jPanel1.add(jLabel1);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(790, 250));

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "Parnadol", "vỉ", null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Lô thuốc", "Tên thuốc", "Đơn vị", "Đơn giá", "Tồn kho", "Hạn sử dụng", "Khuyến mãi (%)", "Tiền khuyến mãi", "Giá sau khuyến mãi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.setPreferredSize(new java.awt.Dimension(790, 400));
        tblProduct.setRowHeight(30);
        tblProduct.setShowGrid(true);
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProductMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblProduct);
        if (tblProduct.getColumnModel().getColumnCount() > 0) {
            tblProduct.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblProduct.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblProduct.getColumnModel().getColumn(2).setPreferredWidth(10);
            tblProduct.getColumnModel().getColumn(3).setPreferredWidth(55);
            tblProduct.getColumnModel().getColumn(4).setPreferredWidth(10);
            tblProduct.getColumnModel().getColumn(5).setPreferredWidth(60);
            tblProduct.getColumnModel().getColumn(6).setPreferredWidth(20);
        }

        jPanel1.add(jScrollPane2);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("GIỎ HÀNG");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setMaximumSize(new java.awt.Dimension(326589, 326589));
        jLabel2.setPreferredSize(new java.awt.Dimension(800, 40));
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel2);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setMinimumSize(new java.awt.Dimension(10, 45));
        jPanel11.setPreferredSize(new java.awt.Dimension(790, 30));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Tên thuốc");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setMinimumSize(new java.awt.Dimension(61, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(300, 30));
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Giá tiền");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel4.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel11.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Số lượng");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel5.setPreferredSize(new java.awt.Dimension(125, 30));
        jPanel11.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Xóa thuốc");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 30));
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel11.add(jLabel6);

        jPanel1.add(jPanel11);

        jScrollPane4.setPreferredSize(new java.awt.Dimension(790, 250));

        cartPanel.setBackground(new java.awt.Color(255, 255, 255));
        cartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cartPanel.setLayout(new javax.swing.BoxLayout(cartPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane4.setViewportView(cartPanel);

        jPanel1.add(jScrollPane4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMinimumSize(new java.awt.Dimension(10, 27));
        jPanel5.setPreferredSize(new java.awt.Dimension(790, 50));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        btnClearCart.setBackground(new java.awt.Color(255, 0, 0));
        btnClearCart.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClearCart.setForeground(new java.awt.Color(255, 255, 255));
        btnClearCart.setText("Xóa giỏ hàng");
        btnClearCart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClearCart.setMaximumSize(new java.awt.Dimension(326589, 326589));
        btnClearCart.setPreferredSize(new java.awt.Dimension(120, 30));
        btnClearCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearCartActionPerformed(evt);
            }
        });
        jPanel5.add(btnClearCart);

        jPanel1.add(jPanel5);

        jPanel19.add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName(""); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 780));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel6.setMinimumSize(new java.awt.Dimension(200, 133));
        jPanel6.setPreferredSize(new java.awt.Dimension(330, 170));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(160, 100));
        jPanel13.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        lblPhoneCustomer.setBackground(new java.awt.Color(255, 255, 255));
        lblPhoneCustomer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPhoneCustomer.setText("Số điện thoại ");
        lblPhoneCustomer.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel13.add(lblPhoneCustomer);

        lblNameCustomer.setBackground(new java.awt.Color(255, 255, 255));
        lblNameCustomer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNameCustomer.setText("Tên khách hàng");
        jPanel13.add(lblNameCustomer);

        lblPointCustomer.setBackground(new java.awt.Color(255, 255, 255));
        lblPointCustomer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPointCustomer.setText("Điểm tích lũy");
        jPanel13.add(lblPointCustomer);

        jPanel6.add(jPanel13);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(130, 100));
        jPanel12.setLayout(new java.awt.GridLayout(3, 1, 5, 10));

        txtPhoneCustomer.setPreferredSize(new java.awt.Dimension(200, 22));
        txtPhoneCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneCustomerActionPerformed(evt);
            }
        });
        txtPhoneCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneCustomerKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneCustomerKeyTyped(evt);
            }
        });
        jPanel12.add(txtPhoneCustomer);

        txtCustomerName.setEnabled(false);
        jPanel12.add(txtCustomerName);

        txtLoyaltyPoints.setEnabled(false);
        txtLoyaltyPoints.setPreferredSize(new java.awt.Dimension(73, 25));
        jPanel12.add(txtLoyaltyPoints);

        jPanel6.add(jPanel12);

        jPanel2.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thanh toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel7.setMinimumSize(new java.awt.Dimension(200, 127));
        jPanel7.setPreferredSize(new java.awt.Dimension(330, 300));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setMinimumSize(new java.awt.Dimension(100, 230));
        jPanel14.setPreferredSize(new java.awt.Dimension(160, 250));
        jPanel14.setLayout(new java.awt.GridLayout(7, 1, 0, 15));

        lblSoLuongSP.setBackground(new java.awt.Color(255, 255, 255));
        lblSoLuongSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSoLuongSP.setText("Tổng số sản phẩm");
        lblSoLuongSP.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel14.add(lblSoLuongSP);

        lblTotalProductPrice1.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalProductPrice1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotalProductPrice1.setText("Tổng tiền sản phẩm");
        lblTotalProductPrice1.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel14.add(lblTotalProductPrice1);

        lblProductDiscount.setBackground(new java.awt.Color(255, 255, 255));
        lblProductDiscount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblProductDiscount.setText("Tiền giảm trên sản phẩm");
        jPanel14.add(lblProductDiscount);

        lblVoucher.setBackground(new java.awt.Color(255, 255, 255));
        lblVoucher.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblVoucher.setText("Voucher");
        jPanel14.add(lblVoucher);

        lblVoucherDiscount.setBackground(new java.awt.Color(255, 255, 255));
        lblVoucherDiscount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblVoucherDiscount.setText("Tiền giảm từ voucher");
        jPanel14.add(lblVoucherDiscount);

        lblSubtotal.setBackground(new java.awt.Color(255, 255, 255));
        lblSubtotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubtotal.setText("Tạm tính");
        jPanel14.add(lblSubtotal);
        jPanel14.add(jLabel14);

        jPanel7.add(jPanel14);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(130, 250));
        jPanel15.setLayout(new java.awt.GridLayout(7, 1, 5, 10));

        txtTotalProduct.setEnabled(false);
        txtTotalProduct.setPreferredSize(new java.awt.Dimension(200, 22));
        jPanel15.add(txtTotalProduct);

        txtTotalProductPrice.setEnabled(false);
        jPanel15.add(txtTotalProductPrice);

        txtProductDiscount.setEnabled(false);
        txtProductDiscount.setPreferredSize(new java.awt.Dimension(73, 25));
        jPanel15.add(txtProductDiscount);

        txtPromoId.setEnabled(false);
        jPanel15.add(txtPromoId);

        txtDiscount.setEnabled(false);
        jPanel15.add(txtDiscount);

        txtSubtotal.setEnabled(false);
        jPanel15.add(txtSubtotal);

        btnPayment.setBackground(new java.awt.Color(0, 204, 51));
        btnPayment.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPayment.setForeground(new java.awt.Color(255, 255, 255));
        btnPayment.setText("Thanh toán");
        btnPayment.setMargin(new java.awt.Insets(2, 10, 3, 10));
        btnPayment.setMaximumSize(new java.awt.Dimension(325689, 326589));
        btnPayment.setMinimumSize(new java.awt.Dimension(0, 0));
        btnPayment.setPreferredSize(new java.awt.Dimension(100, 40));
        btnPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPaymentMouseClicked(evt);
            }
        });
        btnPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentActionPerformed(evt);
            }
        });
        jPanel15.add(btnPayment);

        jPanel7.add(jPanel15);

        jPanel2.add(jPanel7);

        jPanel19.add(jPanel2, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel19);

        jTabbedPane1.addTab("Bán hàng", jScrollPane1);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setMaximumSize(new java.awt.Dimension(326589, 326589));
        jPanel9.setMinimumSize(new java.awt.Dimension(800, 80));
        jPanel9.setPreferredSize(new java.awt.Dimension(1250, 770));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setMinimumSize(new java.awt.Dimension(800, 70));
        jPanel16.setPreferredSize(new java.awt.Dimension(1250, 110));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("QUẢN LÝ HÓA ĐƠN BÁN HÀNG");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setPreferredSize(new java.awt.Dimension(1200, 30));
        jPanel16.add(jLabel11);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel10.setText("Tìm hóa đơn");
        jPanel10.add(jLabel10);

        txtSearchInvoice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearchInvoice.setMinimumSize(new java.awt.Dimension(80, 30));
        txtSearchInvoice.setPreferredSize(new java.awt.Dimension(400, 35));
        jPanel10.add(txtSearchInvoice);

        btnRefreshInvoice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefreshInvoice.setText("Làm mới");
        btnRefreshInvoice.setMaximumSize(new java.awt.Dimension(325689, 326589));
        btnRefreshInvoice.setMinimumSize(new java.awt.Dimension(0, 0));
        btnRefreshInvoice.setPreferredSize(new java.awt.Dimension(95, 35));
        btnRefreshInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshInvoiceActionPerformed(evt);
            }
        });
        jPanel10.add(btnRefreshInvoice);

        btnExportPDF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExportPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pdf.png"))); // NOI18N
        btnExportPDF.setMaximumSize(new java.awt.Dimension(325689, 326589));
        btnExportPDF.setMinimumSize(new java.awt.Dimension(0, 0));
        btnExportPDF.setPreferredSize(new java.awt.Dimension(40, 35));
        btnExportPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPDFActionPerformed(evt);
            }
        });
        jPanel10.add(btnExportPDF);

        jPanel16.add(jPanel10);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Từ ngày");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel20.add(jLabel7);

        date_start.setPreferredSize(new java.awt.Dimension(130, 30));
        date_start.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date_startPropertyChange(evt);
            }
        });
        jPanel20.add(date_start);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Đến ngày");
        jPanel20.add(jLabel8);

        date_end.setPreferredSize(new java.awt.Dimension(130, 30));
        jPanel20.add(date_end);

        jPanel16.add(jPanel20);

        jPanel9.add(jPanel16, java.awt.BorderLayout.NORTH);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(1250, 650));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(1200, 650));

        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"HD01", "Nguyen Van B", null, "101000"},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Họ tên khách hàng", "Ngày mua", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblInvoice.setFillsViewportHeight(true);
        tblInvoice.setMaximumSize(new java.awt.Dimension(32767, 326589));
        tblInvoice.setRowHeight(30);
        tblInvoice.setShowGrid(true);
        tblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInvoiceMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblInvoiceMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblInvoice);
        tblInvoice.getAccessibleContext().setAccessibleDescription("");

        jPanel18.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel18, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Hóa đơn", jPanel9);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tblProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProductMousePressed

    private void btnPaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentMouseClicked

    }//GEN-LAST:event_btnPaymentMouseClicked

    private void btnPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentActionPerformed
        BigDecimal totalAmount = calculateTotalAmount();
        if (totalAmount.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal productDiscount = calculateDiscountAmount();
            String discountText = txtDiscount.getText().trim();
            BigDecimal promoDiscount = new BigDecimal(discountText);

            BigDecimal subTotal = totalAmount
                    .subtract(productDiscount)
                    .subtract(promoDiscount);
            BigDecimal totalDiscount = productDiscount.add(promoDiscount);

            String userId = user_id;
            String customerId = currentCustomerId;


            String promoId = txtPromoId.getText().trim();
            if (promoId.equals("Không có!") && promoId.equals("Không có khuyến mãi!")){
                promoId = null;
            }

            Window parenWindow = SwingUtilities.getWindowAncestor(this);
            PaymentDialog dialog = new PaymentDialog(
                (Frame) parenWindow,
                true,
                this,
                totalAmount,
                totalDiscount,
                subTotal,
                cartItemsMap,
                userId,
                customerId,
                promoId
            );
            dialog.setLocationRelativeTo(parenWindow);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa có sản phẩm trong giỏ hàng", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_btnPaymentActionPerformed

    private void tblInvoiceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblInvoiceMousePressed

    private void btnAddMedicineToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMedicineToCartActionPerformed
        int[] selectedRows = tblProduct.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một thuốc để thêm vào giỏ.");
            return;
        }
        for (int rowIndex : selectedRows) {
            // Lấy batch_id từ table (giả sử là cột 0)
            String batchId = tblProduct.getValueAt(rowIndex, 0).toString();

            // Tìm SaleItem tương ứng từ danh sách
            for (SaleItemDTO item : saleItemList) {
                if (item.getBatchId().equals(batchId)) {
                    addToCart(item);
                    break;
                }
            }
        }
    }//GEN-LAST:event_btnAddMedicineToCartActionPerformed

    private void txtPhoneCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneCustomerActionPerformed
    
    }//GEN-LAST:event_txtPhoneCustomerActionPerformed

    private void txtPhoneCustomerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneCustomerKeyPressed
        
    }//GEN-LAST:event_txtPhoneCustomerKeyPressed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        tblProduct.clearSelection();  // Bỏ mọi dòng đang được chọn
        loadAllData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnClearCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCartActionPerformed
        // Hiện hộp thoại xác nhận trước khi xóa
        int confirmed = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng không?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        if (confirmed == JOptionPane.YES_OPTION){
            cartItemsMap.clear();
            cartPanel.removeAll(); // Xóa giao diện các item
            cartPanel.revalidate(); // Cập nhật lại layout
            cartPanel.repaint();
            updatePayment(); // Cập nhật lại khu vực thanh toán
        }
    }//GEN-LAST:event_btnClearCartActionPerformed

    private void txtPhoneCustomerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneCustomerKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // chặn ký tự không phải số
        }
    }//GEN-LAST:event_txtPhoneCustomerKeyTyped

    private void tblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMouseClicked
        int selectedRow = tblInvoice.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy mã hóa đơn từ dòng được chọn
            String invoiceId = tblInvoice.getValueAt(selectedRow, 0).toString();

            // Lấy hóa đơn từ BUS
            SalesInvoiceDTO invoice = invoiceBUS.getInvoiceById(invoiceId); // cần cài đặt phương thức này trong BUS
            if (invoice == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn.");
                return;
            }

            // Lấy danh sách chi tiết hóa đơn
            ArrayList<SalesInvoiceDetailDTO> detailList = invoicedetailsBUS.getByInvoiceId(invoiceId);
            if (detailList == null || detailList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hóa đơn này không có chi tiết.");
                return;
            }

            // Hiển thị dialog
            InvoiceDetailDialog dialog = new InvoiceDetailDialog((JFrame) SwingUtilities.getWindowAncestor(this), invoice, detailList);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_tblInvoiceMouseClicked

    private void btnExportPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportPDFActionPerformed
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tblInvoice.getModel();

            com.pharmacy.app.Utils.PDFExporter.exportSalesInvoiceToPDF(this, model);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất PDF: " + e.getMessage(),
                "Lỗi",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExportPDFActionPerformed

    private void btnRefreshInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshInvoiceActionPerformed
        txtSearchInvoice.setText("");
        tblInvoice.clearSelection();
        date_start.setDate(null);
        date_end.setDate(null);
        loadAllData();
    }//GEN-LAST:event_btnRefreshInvoiceActionPerformed

    private void date_startPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_startPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_date_startPropertyChange

    private void jLabel1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1PropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMedicineToCart;
    private javax.swing.JButton btnClearCart;
    private javax.swing.JButton btnExportPDF;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRefreshInvoice;
    private javax.swing.JPanel cartPanel;
    private com.toedter.calendar.JDateChooser date_end;
    private com.toedter.calendar.JDateChooser date_start;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblNameCustomer;
    private javax.swing.JLabel lblPhoneCustomer;
    private javax.swing.JLabel lblPointCustomer;
    private javax.swing.JLabel lblProductDiscount;
    private javax.swing.JLabel lblSoLuongSP;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTotalProductPrice1;
    private javax.swing.JLabel lblVoucher;
    private javax.swing.JLabel lblVoucherDiscount;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtLoyaltyPoints;
    private javax.swing.JTextField txtPhoneCustomer;
    private javax.swing.JTextField txtProductDiscount;
    private javax.swing.JTextField txtPromoId;
    private javax.swing.JTextField txtSearchInvoice;
    private javax.swing.JTextField txtSearchProduct;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotalProduct;
    private javax.swing.JTextField txtTotalProductPrice;
    // End of variables declaration//GEN-END:variables

    private void addToCart(SaleItemDTO item) {
        String key = item.getBatchId();

        if (cartItemsMap.containsKey(key)) {
            // Nếu đã tồn tại thì tăng số lượng
            CartItemDTO cartItem = cartItemsMap.get(key);
            JSpinner spinner = cartItem.getSpinner();
            int current = (int) spinner.getValue();
            spinner.setValue(current + 1);
            cartItem.setQuantity(current + 1);
            
        } else {
            // Đưa dữ liệu vào giỏ hàng
            // Tạo dòng mới
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 26, 5)); 
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // Cố định chiều cao

            // Tên thuốc
            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setPreferredSize(new Dimension(300, 30));
            nameLabel.setMinimumSize(new Dimension(150,30));

            // Giá x số lượng
            JLabel totalLabel = new JLabel(String.format("%.2f", item.getFinalPrice()));
            totalLabel.setPreferredSize(new Dimension(200, 30));
            totalLabel.setMinimumSize(new Dimension(100,30));
            
            // Số lượng
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
            quantitySpinner.setPreferredSize(new Dimension(100, 30));
            quantitySpinner.setMinimumSize(new Dimension(50,30));
            quantitySpinner.addChangeListener(e -> {
                int quantity = (int) quantitySpinner.getValue();
                double finalprice = quantity * item.getFinalPrice().doubleValue();                
                double originalprice = quantity * item.getSellPrice().doubleValue();
                double discountprice = quantity * item.getDiscountAmount().doubleValue();

                totalLabel.setText(String.format("%.2f", finalprice));
                
                // Lưu sp vào hashmap
                CartItemDTO cartItem = cartItemsMap.get(key);
                cartItem.setQuantity(quantity);
                cartItem.setFinalPrice(BigDecimal.valueOf(finalprice));                
                cartItem.setSellPrice(BigDecimal.valueOf(originalprice));
                cartItem.setDiscountAmount(BigDecimal.valueOf(discountprice));
                

                // Cập nhật khu vực thanh toán
                updatePayment();
            });

            // Nút xoá
            JButton deleteButton = new JButton("X");
            deleteButton.setPreferredSize(new Dimension(50, 30));
            deleteButton.setMinimumSize(new Dimension(25,30));
            deleteButton.addActionListener(e -> {
                cartPanel.remove(row);
                cartItemsMap.remove(key);
                cartPanel.revalidate();
                cartPanel.repaint();
                updatePayment();
            });

            // add thành phần vào giỏ hàng
            row.add(nameLabel);
            row.add(totalLabel);
            row.add(quantitySpinner);
            row.add(deleteButton);

            CartItemDTO cartItem = new CartItemDTO(
                item.getBatchId(),
                item.getProductId(),
                item.getName(),
                item.getPromoId(),
                item.getPercentDiscount(),
                item.getDiscountAmount(),
                item.getSellPrice(),
                item.getFinalPrice(),
                1 // Khởi tạo số lượng ban đầu là 1
            );

            cartItem.setPanel(row);
            cartItem.setSpinner(quantitySpinner);
            
            cartItemsMap.put(key, cartItem);
            cartPanel.add(row);
            cartPanel.revalidate();
            cartPanel.repaint();
            
            updatePayment();

            // Cuộn xuống cuối
            SwingUtilities.invokeLater(() -> {
                Rectangle bounds = row.getBounds();
                cartPanel.scrollRectToVisible(bounds);
            });
        }
    }
    
    
    // Hàm tính tổng tiền gốc
    public BigDecimal calculateTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemDTO cartItem : cartItemsMap.values()) {
            totalAmount = totalAmount.add(cartItem.getSellPrice());
        }

        return totalAmount;
    }
    
    // Hàm tính tổng tiền khuyến mãi 
    public BigDecimal calculateDiscountAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemDTO cartItem : cartItemsMap.values()) {
            totalAmount = totalAmount.add(cartItem.getDiscountAmount());
        }

        return totalAmount;
    }

    // Hàm đếm tổng số lượng sp
    public int calculateTotalProduct() {
        int totalProduct = 0;

        for (CartItemDTO cartItem : cartItemsMap.values()) {
            totalProduct += cartItem.getQuantityFromSpinner();
        }

        return totalProduct;
    }
        
    // Tính toán và gán dữ liệu vào khu vực thanh toán
    private void updatePayment() {
        BigDecimal totalAmount = calculateTotalAmount();        
        BigDecimal productDiscount = calculateDiscountAmount();        
        int totalProduct = calculateTotalProduct();
        
        String discountText = txtDiscount.getText().trim();
        BigDecimal promoDiscount = new BigDecimal(discountText);

        BigDecimal subTotal = totalAmount
                .subtract(productDiscount)
                .subtract(promoDiscount);

        txtTotalProductPrice.setText(totalAmount.toString());
        txtProductDiscount.setText(productDiscount.toString());        
        txtTotalProduct.setText(String.valueOf(totalProduct));
        txtSubtotal.setText(subTotal.toString());

        showHashMap();
    }
    
    // Hàm tìm KH theo SDT
    public CustomerDTO getCustomerByPhone(String phone) {
        return customerBUS.findCustomerByPhone(phone);
    }

    // Hàm xử lý nhập SDT
    private void handlePhoneInput() {
        String phone = txtPhoneCustomer.getText().trim();

        if (phone.length() >= 10) { // Kiểm tra độ dài hợp lệ
            CustomerDTO customer = getCustomerByPhone(phone);
            if (customer != null) {
                printCustomerInfo(customer);
                applyBestPromoForCustomer(customer);
//                System.out.println(currentCustomerId);
            } else {
                int choice = JOptionPane.showConfirmDialog(null,
                    "Khách hàng chưa tồn tại. Bạn có muốn tạo mới không?",
                    "Khách hàng mới",
                    JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    String name = JOptionPane.showInputDialog("Nhập tên khách hàng:");
                    String id = customerBUS.generateNextId(); // Tạo id mới
                    if (name != null && !name.trim().isEmpty()) {
                        CustomerDTO newCustomer = new CustomerDTO();
                        newCustomer.setName(name.trim());
                        newCustomer.setPhone(phone);
                        newCustomer.setPoint(0);

                        boolean newCustomerId = customerBUS.addCustomer(newCustomer);
                        if (newCustomerId) {
                            JOptionPane.showMessageDialog(null, "Tạo khách hàng thành công!");
                            newCustomer.setId(id);
                            printCustomerInfo(newCustomer);
//                            System.out.println(currentCustomerId);
                           
                        } else {
                            JOptionPane.showMessageDialog(null, "Lỗi khi tạo khách hàng.");
                        }
                    }
                }   else {
                        txtPhoneCustomer.setText("");
                        printCustomerInfo(null);
                        currentCustomerId = "";
                }
            }
        } else {
            printCustomerInfo(null);
            currentCustomerId = "";
            txtPromoId.setText("Không có!");
            txtDiscount.setText("0");
        }
    }
    
    private void applyBestPromoForCustomer(CustomerDTO customer) {        
        PromotionDTO bestPromo = promoBUS.findBestRewardPromo(customer.getPoint());
        if (bestPromo != null) {
            txtPromoId.setText(bestPromo.getPromotionId());
            txtDiscount.setText(bestPromo.getDiscountAmount().toString());
        } else {
            txtPromoId.setText("Không có khuyến mãi!");
            txtDiscount.setText("0");
        }
        updatePayment();
    }
    
    private void printCustomerInfo(CustomerDTO t){
        if (t != null){
            txtCustomerName.setText(t.getName());
            txtLoyaltyPoints.setText(String.valueOf(t.getPoint()));
            currentCustomerId = t.getId(); // lưu để dùng khi thanh toán

        } else {
            txtCustomerName.setText("");
            txtLoyaltyPoints.setText("");
        }
        
    }
    
    private void searchProduct() {
        String keyword = txtSearchProduct.getText();
        ArrayList<SaleItemDTO> result = saleItemBUS.searchProduct(keyword);
        showDataToTableProduct(result);
    }
    
    
    private String formatMoney(BigDecimal amount) {
        return String.format("%,.0f VNĐ", amount);
    }
    
    private String formatMoney_1(BigDecimal amount) {
        return String.format("%,.0f", amount);
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

}