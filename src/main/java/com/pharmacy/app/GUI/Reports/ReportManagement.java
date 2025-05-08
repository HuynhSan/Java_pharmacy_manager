/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pharmacy.app.GUI.Reports;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author phong
 */
public class ReportManagement extends javax.swing.JPanel {
    private ButtonGroup revenueFilterGroup, productFilterGroup, invoiceFilterGroup;
    // Date format for display
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    // Period types for statistics
    private enum PeriodType {
        DAY, MONTH, YEAR, CUSTOM
    }

    /**
     * Creates new form ReportManagement
     */
    public ReportManagement() {
        initComponents();
        setupRadioButtons();
        setupListeners();
        initCharts();
        loadInitialData();
    }
    
    private void setupRadioButtons() {
        revenueFilterGroup = new ButtonGroup();
        revenueFilterGroup.add(revenueRadioDay);
        revenueFilterGroup.add(revenueRadioMonth);
        revenueFilterGroup.add(revenueRadioYear);
        revenueFilterGroup.add(revenueRadioCustom);

        productFilterGroup = new ButtonGroup();
        productFilterGroup.add(productRadioMonth);
        productFilterGroup.add(productRadioYear);

        invoiceFilterGroup = new ButtonGroup();
        invoiceFilterGroup.add(invoiceRadioDay);
        invoiceFilterGroup.add(invoiceRadioMonth);
        invoiceFilterGroup.add(invoiceRadioYear);
        invoiceFilterGroup.add(invoiceRadioCustom);
    }
    
    /**
     * Set up event listeners for components
     */
    private void setupListeners() {
        // Revenue period selection listeners
        revenueRadioDay.addActionListener(e -> updateRevenueView(PeriodType.DAY));
        revenueRadioMonth.addActionListener(e -> updateRevenueView(PeriodType.MONTH));
        revenueRadioYear.addActionListener(e -> updateRevenueView(PeriodType.YEAR));
        revenueRadioCustom.addActionListener(e -> updateRevenueView(PeriodType.CUSTOM));
        
        // Product period selection listeners
        productRadioMonth.addActionListener(e -> updateProductView(PeriodType.MONTH));
        productRadioYear.addActionListener(e -> updateProductView(PeriodType.YEAR));
        
        // Invoice period selection listeners
        invoiceRadioDay.addActionListener(e -> updateInvoiceView(PeriodType.DAY));
        invoiceRadioMonth.addActionListener(e -> updateInvoiceView(PeriodType.MONTH));
        invoiceRadioYear.addActionListener(e -> updateInvoiceView(PeriodType.YEAR));
        invoiceRadioCustom.addActionListener(e -> updateInvoiceView(PeriodType.CUSTOM));
        
        // Apply button listeners
        btnRevenueApply.addActionListener(e -> applyRevenueFilter());
        btnProductApply.addActionListener(e -> applyProductFilter());
        btnInvoiceApply.addActionListener(e -> applyInvoiceFilter());
        
        // Export PDF button listeners
        btnRevenueExport.addActionListener(e -> exportRevenueToPDF());
        btnProductExport.addActionListener(e -> exportProductToPDF());
        btnInvoiceExport.addActionListener(e -> exportInvoiceToPDF());
        
        // Month and Year combobox listeners
        cbRevenueMonth.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateRevenueData();
            }
        });
        
        cbRevenueYear.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateRevenueData();
            }
        });
        
        cbProductMonth.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateBestSellingProducts();
            }
        });
        
        cbProductYear.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateBestSellingProducts();
            }
        });
        
        cbInvoiceMonth.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateInvoiceStatistics();
            }
        });
        
        cbInvoiceYear.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateInvoiceStatistics();
            }
        });
    }
    
    /**
     * Initialize charts for statistics display
     */
    private void initCharts() {
        // Revenue chart initialization
        DefaultCategoryDataset revenueDataset = new DefaultCategoryDataset();
        JFreeChart revenueChart = ChartFactory.createLineChart(
                "Doanh thu theo thời gian",
                "Thời gian",
                "Doanh thu (VNĐ)",
                revenueDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel revenueChartPanel = new ChartPanel(revenueChart);
        revenueChartPanel.setPreferredSize(new Dimension(600, 300));
        pnlRevenueChart.setLayout(new BorderLayout());
        pnlRevenueChart.add(revenueChartPanel, BorderLayout.CENTER);
        
        // Product sales chart initialization
        DefaultPieDataset productDataset = new DefaultPieDataset();
        JFreeChart productChart = ChartFactory.createPieChart(
                "Sản phẩm bán chạy",
                productDataset,
                true,
                true,
                false
        );
        ChartPanel productChartPanel = new ChartPanel(productChart);
        productChartPanel.setPreferredSize(new Dimension(600, 300));
        pnlProductChart.setLayout(new BorderLayout());
        pnlProductChart.add(productChartPanel, BorderLayout.CENTER);
        
        // Invoice chart initialization
        DefaultCategoryDataset invoiceDataset = new DefaultCategoryDataset();
        JFreeChart invoiceChart = ChartFactory.createBarChart(
                "Số hóa đơn theo thời gian",
                "Thời gian",
                "Số lượng hóa đơn",
                invoiceDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel invoiceChartPanel = new ChartPanel(invoiceChart);
        invoiceChartPanel.setPreferredSize(new Dimension(600, 300));
        pnlInvoiceChart.setLayout(new BorderLayout());
        pnlInvoiceChart.add(invoiceChartPanel, BorderLayout.CENTER);
    }
    
    /**
     * Load initial data for all reports
     */
    private void loadInitialData() {
        // Default to current day for day view
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        
        // Set default dates for date choosers
        revenueStartDate.setDate(today);
        revenueEndDate.setDate(today);
        invoiceStartDate.setDate(today);
        invoiceEndDate.setDate(today);
        
        // Populate year combo boxes (current year and 4 years back)
        int currentYear = cal.get(Calendar.YEAR);
        for (int i = 0; i < 5; i++) {
            cbRevenueYear.addItem(String.valueOf(currentYear - i));
            cbProductYear.addItem(String.valueOf(currentYear - i));
            cbInvoiceYear.addItem(String.valueOf(currentYear - i));
        }
        
        // Set initial view to daily
        revenueRadioDay.setSelected(true);
        updateRevenueView(PeriodType.DAY);
        
        productRadioMonth.setSelected(true);
        updateProductView(PeriodType.MONTH);
        
        invoiceRadioDay.setSelected(true);
        updateInvoiceView(PeriodType.DAY);
        
        // Load mock data for demo
        loadMockData();
    }
    
    /**
     * Load mock data for demonstration
     */
    private void loadMockData() {
        // Revenue table mock data
        DefaultTableModel revenueModel = (DefaultTableModel) tblRevenue.getModel();
        revenueModel.setRowCount(0);
        
        Object[] row1 = {"08/05/2025", "15,000,000"};
        Object[] row2 = {"07/05/2025", "12,500,000"};
        Object[] row3 = {"06/05/2025", "18,200,000"};
        Object[] row4 = {"05/05/2025", "14,700,000"};
        Object[] row5 = {"04/05/2025", "21,300,000"};
        
        revenueModel.addRow(row1);
        revenueModel.addRow(row2);
        revenueModel.addRow(row3);
        revenueModel.addRow(row4);
        revenueModel.addRow(row5);
        
        // Products table mock data
        DefaultTableModel productModel = (DefaultTableModel) tblProducts.getModel();
        productModel.setRowCount(0);
        
        Object[] prod1 = {"P001", "Paracetamol 500mg", "250", "12,500,000"};
        Object[] prod2 = {"P002", "Amoxicillin 250mg", "200", "10,000,000"};
        Object[] prod3 = {"P003", "Omeprazole 20mg", "180", "9,000,000"};
        Object[] prod4 = {"P004", "Vitamin C 1000mg", "150", "7,500,000"};
        Object[] prod5 = {"P005", "Ibuprofen 400mg", "120", "6,000,000"};
        
        productModel.addRow(prod1);
        productModel.addRow(prod2);
        productModel.addRow(prod3);
        productModel.addRow(prod4);
        productModel.addRow(prod5);
        
        // Invoice table mock data
        DefaultTableModel invoiceModel = (DefaultTableModel) tblInvoices.getModel();
        invoiceModel.setRowCount(0);
        
        Object[] inv1 = {"08/05/2025", "10", "15,000,000"};
        Object[] inv2 = {"07/05/2025", "8", "12,500,000"};
        Object[] inv3 = {"06/05/2025", "12", "18,200,000"};
        Object[] inv4 = {"05/05/2025", "9", "14,700,000"};
        Object[] inv5 = {"04/05/2025", "15", "21,300,000"};
        
        invoiceModel.addRow(inv1);
        invoiceModel.addRow(inv2);
        invoiceModel.addRow(inv3);
        invoiceModel.addRow(inv4);
        invoiceModel.addRow(inv5);
        
        // Update summary labels
        lblRevenueTotalValue.setText("81,700,000 VNĐ");
        lblProductTotalValue.setText("45,000,000 VNĐ");
        lblInvoiceTotalValue.setText("54 hóa đơn");
        
        // Update charts with mock data
        updateCharts();
    }
    
    /**
     * Update charts with current data
     */
    private void updateCharts() {
        // Mock data for charts
        // Revenue chart
        DefaultCategoryDataset revenueDataset = new DefaultCategoryDataset();
        revenueDataset.addValue(15000000, "Doanh thu", "08/05");
        revenueDataset.addValue(12500000, "Doanh thu", "07/05");
        revenueDataset.addValue(18200000, "Doanh thu", "06/05");
        revenueDataset.addValue(14700000, "Doanh thu", "05/05");
        revenueDataset.addValue(21300000, "Doanh thu", "04/05");
        
        JFreeChart revenueChart = ChartFactory.createLineChart(
                "Doanh thu theo thời gian",
                "Thời gian",
                "Doanh thu (VNĐ)",
                revenueDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel revenueChartPanel = new ChartPanel(revenueChart);
        pnlRevenueChart.removeAll();
        pnlRevenueChart.add(revenueChartPanel, BorderLayout.CENTER);
        pnlRevenueChart.revalidate();
        pnlRevenueChart.repaint();
        
        // Product sales chart
        DefaultPieDataset productDataset = new DefaultPieDataset();
        productDataset.setValue("Paracetamol 500mg", 250);
        productDataset.setValue("Amoxicillin 250mg", 200);
        productDataset.setValue("Omeprazole 20mg", 180);
        productDataset.setValue("Vitamin C 1000mg", 150);
        productDataset.setValue("Ibuprofen 400mg", 120);
        
        JFreeChart productChart = ChartFactory.createPieChart(
                "Sản phẩm bán chạy",
                productDataset,
                true,
                true,
                false
        );
        ChartPanel productChartPanel = new ChartPanel(productChart);
        pnlProductChart.removeAll();
        pnlProductChart.add(productChartPanel, BorderLayout.CENTER);
        pnlProductChart.revalidate();
        pnlProductChart.repaint();
        
        // Invoice chart
        DefaultCategoryDataset invoiceDataset = new DefaultCategoryDataset();
        invoiceDataset.addValue(10, "Số lượng", "08/05");
        invoiceDataset.addValue(8, "Số lượng", "07/05");
        invoiceDataset.addValue(12, "Số lượng", "06/05");
        invoiceDataset.addValue(9, "Số lượng", "05/05");
        invoiceDataset.addValue(15, "Số lượng", "04/05");
        
        JFreeChart invoiceChart = ChartFactory.createBarChart(
                "Số hóa đơn theo thời gian",
                "Thời gian",
                "Số lượng hóa đơn",
                invoiceDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel invoiceChartPanel = new ChartPanel(invoiceChart);
        pnlInvoiceChart.removeAll();
        pnlInvoiceChart.add(invoiceChartPanel, BorderLayout.CENTER);
        pnlInvoiceChart.revalidate();
        pnlInvoiceChart.repaint();
    }
    
    /**
     * Update revenue view based on selected period type
     */
    private void updateRevenueView(PeriodType type) {
        // Show/hide appropriate controls based on period type
        switch (type) {
            case DAY:
                pnlRevenueDay.setVisible(true);
                pnlRevenueMonth.setVisible(false);
                pnlRevenueYear.setVisible(false);
                pnlRevenueCustom.setVisible(false);
                break;
            case MONTH:
                pnlRevenueDay.setVisible(false);
                pnlRevenueMonth.setVisible(true);
                pnlRevenueYear.setVisible(false);
                pnlRevenueCustom.setVisible(false);
                break;
            case YEAR:
                pnlRevenueDay.setVisible(false);
                pnlRevenueMonth.setVisible(false);
                pnlRevenueYear.setVisible(true);
                pnlRevenueCustom.setVisible(false);
                break;
            case CUSTOM:
                pnlRevenueDay.setVisible(false);
                pnlRevenueMonth.setVisible(false);
                pnlRevenueYear.setVisible(false);
                pnlRevenueCustom.setVisible(true);
                break;
        }
        
        // Update data based on selected period
        updateRevenueData();
    }
    
    /**
     * Update product view based on selected period type
     */
    private void updateProductView(PeriodType type) {
        // Show/hide appropriate controls based on period type
        switch (type) {
            case MONTH:
                pnlProductMonth.setVisible(true);
                pnlProductYear.setVisible(false);
                break;
            case YEAR:
                pnlProductMonth.setVisible(false);
                pnlProductYear.setVisible(true);
                break;
            case CUSTOM:
                pnlProductMonth.setVisible(false);
                pnlProductYear.setVisible(false);
                break;
            default:
                break;
        }
        
        // Update data based on selected period
        updateBestSellingProducts();
    }
    
    /**
     * Update invoice view based on selected period type
     */
    private void updateInvoiceView(PeriodType type) {
        // Show/hide appropriate controls based on period type
        switch (type) {
            case DAY:
                pnlInvoiceDay.setVisible(true);
                pnlInvoiceMonth.setVisible(false);
                pnlInvoiceYear.setVisible(false);
                pnlInvoiceCustom.setVisible(false);
                break;
            case MONTH:
                pnlInvoiceDay.setVisible(false);
                pnlInvoiceMonth.setVisible(true);
                pnlInvoiceYear.setVisible(false);
                pnlInvoiceCustom.setVisible(false);
                break;
            case YEAR:
                pnlInvoiceDay.setVisible(false);
                pnlInvoiceMonth.setVisible(false);
                pnlInvoiceYear.setVisible(true);
                pnlInvoiceCustom.setVisible(false);
                break;
            case CUSTOM:
                pnlInvoiceDay.setVisible(false);
                pnlInvoiceMonth.setVisible(false);
                pnlInvoiceYear.setVisible(false);
                pnlInvoiceCustom.setVisible(true);
                break;
        }
        
        // Update data based on selected period
        updateInvoiceStatistics();
    }
    
    /**
     * Update revenue data based on selected period
     * In a real application, this would fetch data from database
     */
    private void updateRevenueData() {
        // This is a placeholder - in a real app, you would fetch data from your database
        // For now, we'll just use the mock data already loaded
    }
    
    /**
     * Update best selling products data
     * In a real application, this would fetch data from database
     */
    private void updateBestSellingProducts() {
        // This is a placeholder - in a real app, you would fetch data from your database
        // For now, we'll just use the mock data already loaded
    }
    
    /**
     * Update invoice statistics data
     * In a real application, this would fetch data from database
     */
    private void updateInvoiceStatistics() {
        // This is a placeholder - in a real app, you would fetch data from your database
        // For now, we'll just use the mock data already loaded
    }
    
    /**
     * Apply filter for revenue statistics
     */
    private void applyRevenueFilter() {
        if (revenueRadioDay.isSelected()) {
            // Apply day filter
            Date selectedDate = revenueCurrentDate.getDate();
            // In real app, query database with this date
        } else if (revenueRadioMonth.isSelected()) {
            // Apply month filter
            String month = cbRevenueMonth.getSelectedItem().toString();
            String year = cbRevenueYear.getSelectedItem().toString();
            // In real app, query database with month and year
        } else if (revenueRadioYear.isSelected()) {
            // Apply year filter
            String year = cbRevenueYearOnly.getSelectedItem().toString();
            // In real app, query database with year
        } else if (revenueRadioCustom.isSelected()) {
            // Apply custom date range filter
            Date startDate = revenueStartDate.getDate();
            Date endDate = revenueEndDate.getDate();
            // In real app, query database with date range
        }
        
        // For demo, just refresh the existing mock data
        updateRevenueData();
        updateCharts();
    }
    
    /**
     * Apply filter for product statistics
     */
    private void applyProductFilter() {
        if (productRadioMonth.isSelected()) {
            // Apply month filter
            String month = cbProductMonth.getSelectedItem().toString();
            String year = cbProductYear.getSelectedItem().toString();
            // In real app, query database with month and year
        } else if (productRadioYear.isSelected()) {
            // Apply year filter
            String year = cbProductYearOnly.getSelectedItem().toString();
            // In real app, query database with year
        }
        // For demo, just refresh the existing mock data
        updateBestSellingProducts();
        updateCharts();
    }
    
    /**
     * Apply filter for invoice statistics
     */
    private void applyInvoiceFilter() {
        if (invoiceRadioDay.isSelected()) {
            // Apply day filter
            Date selectedDate = invoiceCurrentDate.getDate();
            // In real app, query database with this date
        } else if (invoiceRadioMonth.isSelected()) {
            // Apply month filter
            String month = cbInvoiceMonth.getSelectedItem().toString();
            String year = cbInvoiceYear.getSelectedItem().toString();
            // In real app, query database with month and year
        } else if (invoiceRadioYear.isSelected()) {
            // Apply year filter
            String year = cbInvoiceYearOnly.getSelectedItem().toString();
            // In real app, query database with year
        } else if (invoiceRadioCustom.isSelected()) {
            // Apply custom date range filter
            Date startDate = invoiceStartDate.getDate();
            Date endDate = invoiceEndDate.getDate();
            // In real app, query database with date range
        }
        
        // For demo, just refresh the existing mock data
        updateInvoiceStatistics();
        updateCharts();
    }
    
    /**
     * Export revenue statistics to PDF
     */
    private void exportRevenueToPDF() {
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tblRevenue.getModel();
            
            // Use the PDFExporter utility class to export revenue data
            com.pharmacy.app.Utils.PDFExporter.exportRevenueToPDF(this, model);
            
            javax.swing.JOptionPane.showMessageDialog(this,
                "Xuất báo cáo PDF thành công!",
                "Thông báo",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất PDF: " + e.getMessage(),
                "Lỗi",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Export product statistics to PDF
     */
    private void exportProductToPDF() {
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
            
            // Use the PDFExporter utility class to export product data
            com.pharmacy.app.Utils.PDFExporter.exportProductStatsToPDF(this, model);
            
            javax.swing.JOptionPane.showMessageDialog(this,
                "Xuất báo cáo PDF thành công!",
                "Thông báo",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất PDF: " + e.getMessage(),
                "Lỗi",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Export invoice statistics to PDF
     */
    private void exportInvoiceToPDF() {
        try {
            // Get table model
            DefaultTableModel model = (DefaultTableModel) tblInvoices.getModel();
            
            // Use the PDFExporter utility class to export invoice data
            com.pharmacy.app.Utils.PDFExporter.exportInvoiceStatsToPDF(this, model);
            
            javax.swing.JOptionPane.showMessageDialog(this,
                "Xuất báo cáo PDF thành công!",
                "Thông báo",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất PDF: " + e.getMessage(),
                "Lỗi",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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

        tpReports = new javax.swing.JTabbedPane();
        pnlRevenue = new javax.swing.JPanel();
        pnlRevenueFilter = new javax.swing.JPanel();
        pnlRevenueRadio = new javax.swing.JPanel();
        revenueRadioDay = new javax.swing.JRadioButton();
        revenueRadioMonth = new javax.swing.JRadioButton();
        revenueRadioYear = new javax.swing.JRadioButton();
        revenueRadioCustom = new javax.swing.JRadioButton();
        pnlRevenueControls = new javax.swing.JPanel();
        pnlRevenueDay = new javax.swing.JPanel();
        lblRevenueCurrentDate = new javax.swing.JLabel();
        revenueCurrentDate = new com.toedter.calendar.JDateChooser();
        pnlRevenueMonth = new javax.swing.JPanel();
        lblRevenueMonth = new javax.swing.JLabel();
        cbRevenueMonth = new javax.swing.JComboBox<>();
        lblRevenueMonthYear = new javax.swing.JLabel();
        cbRevenueYear = new javax.swing.JComboBox<>();
        pnlRevenueYear = new javax.swing.JPanel();
        lblRevenueYear = new javax.swing.JLabel();
        cbRevenueYearOnly = new javax.swing.JComboBox<>();
        pnlRevenueCustom = new javax.swing.JPanel();
        lblRevenueStartDate = new javax.swing.JLabel();
        revenueStartDate = new com.toedter.calendar.JDateChooser();
        lblRevenueEndDate = new javax.swing.JLabel();
        revenueEndDate = new com.toedter.calendar.JDateChooser();
        pnlRevenueButtons = new javax.swing.JPanel();
        btnRevenueApply = new javax.swing.JButton();
        btnRevenueExport = new javax.swing.JButton();
        pnlRevenueData = new javax.swing.JPanel();
        pnlRevenueTableAndSummary = new javax.swing.JPanel();
        pnlRevenueTable = new javax.swing.JPanel();
        scrollRevenue = new javax.swing.JScrollPane();
        tblRevenue = new javax.swing.JTable();
        pnlRevenueSummary = new javax.swing.JPanel();
        lblRevenueTotal = new javax.swing.JLabel();
        lblRevenueTotalValue = new javax.swing.JLabel();
        pnlRevenueChart = new javax.swing.JPanel();
        pnlProducts = new javax.swing.JPanel();
        pnlProductFilter = new javax.swing.JPanel();
        pnlProductRadio = new javax.swing.JPanel();
        productRadioMonth = new javax.swing.JRadioButton();
        productRadioYear = new javax.swing.JRadioButton();
        pnlPoductControls = new javax.swing.JPanel();
        pnlProductMonth = new javax.swing.JPanel();
        lblProductMonth = new javax.swing.JLabel();
        cbProductMonth = new javax.swing.JComboBox<>();
        lblProductMonthYear = new javax.swing.JLabel();
        cbProductYear = new javax.swing.JComboBox<>();
        pnlProductYear = new javax.swing.JPanel();
        lblProductYear = new javax.swing.JLabel();
        cbProductYearOnly = new javax.swing.JComboBox<>();
        pnlProductButtons = new javax.swing.JPanel();
        btnProductApply = new javax.swing.JButton();
        btnProductExport = new javax.swing.JButton();
        pnlProductData = new javax.swing.JPanel();
        pnlProductTableAndSummary = new javax.swing.JPanel();
        pnlProductTable = new javax.swing.JPanel();
        scrollProduct = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        pnlProductSummary = new javax.swing.JPanel();
        lblProductTotal = new javax.swing.JLabel();
        lblProductTotalValue = new javax.swing.JLabel();
        pnlProductChart = new javax.swing.JPanel();
        pnlInvoices = new javax.swing.JPanel();
        pnlInvoiceFilter = new javax.swing.JPanel();
        pnlInvoiceRadio = new javax.swing.JPanel();
        invoiceRadioDay = new javax.swing.JRadioButton();
        invoiceRadioMonth = new javax.swing.JRadioButton();
        invoiceRadioYear = new javax.swing.JRadioButton();
        invoiceRadioCustom = new javax.swing.JRadioButton();
        pnlInvoiceControls = new javax.swing.JPanel();
        pnlInvoiceDay = new javax.swing.JPanel();
        lblInvoiceCurrentDate = new javax.swing.JLabel();
        invoiceCurrentDate = new com.toedter.calendar.JDateChooser();
        pnlInvoiceMonth = new javax.swing.JPanel();
        lblInvoiceMonth = new javax.swing.JLabel();
        cbInvoiceMonth = new javax.swing.JComboBox<>();
        lblInvoiceMonthYear = new javax.swing.JLabel();
        cbInvoiceYear = new javax.swing.JComboBox<>();
        pnlInvoiceYear = new javax.swing.JPanel();
        lblInvoiceYear = new javax.swing.JLabel();
        cbInvoiceYearOnly = new javax.swing.JComboBox<>();
        pnlInvoiceCustom = new javax.swing.JPanel();
        lblInvoiceStartDate = new javax.swing.JLabel();
        invoiceStartDate = new com.toedter.calendar.JDateChooser();
        lblInvoiceEndDate = new javax.swing.JLabel();
        invoiceEndDate = new com.toedter.calendar.JDateChooser();
        pnlInvoiceButtons = new javax.swing.JPanel();
        btnInvoiceApply = new javax.swing.JButton();
        btnInvoiceExport = new javax.swing.JButton();
        pnlInvoiceData = new javax.swing.JPanel();
        pnlInvoiceTableAndSummary = new javax.swing.JPanel();
        pnlInvoiceTable = new javax.swing.JPanel();
        scrollInvoices = new javax.swing.JScrollPane();
        tblInvoices = new javax.swing.JTable();
        pnlInvoiceSummary = new javax.swing.JPanel();
        lblInvoiceTotal = new javax.swing.JLabel();
        lblInvoiceTotalValue = new javax.swing.JLabel();
        pnlInvoiceChart = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        tpReports.setBackground(new java.awt.Color(255, 255, 255));

        pnlRevenue.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenue.setLayout(new javax.swing.BoxLayout(pnlRevenue, javax.swing.BoxLayout.Y_AXIS));

        pnlRevenueFilter.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc theo thời gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlRevenueFilter.setLayout(new java.awt.BorderLayout());

        pnlRevenueRadio.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueRadio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 5));

        revenueRadioDay.setText("Theo ngày");
        pnlRevenueRadio.add(revenueRadioDay);

        revenueRadioMonth.setText("Theo tháng");
        pnlRevenueRadio.add(revenueRadioMonth);

        revenueRadioYear.setText("Theo năm");
        pnlRevenueRadio.add(revenueRadioYear);

        revenueRadioCustom.setText("Tùy chỉnh");
        pnlRevenueRadio.add(revenueRadioCustom);

        pnlRevenueFilter.add(pnlRevenueRadio, java.awt.BorderLayout.NORTH);

        pnlRevenueControls.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueControls.setLayout(new java.awt.CardLayout());

        pnlRevenueDay.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueDay.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblRevenueCurrentDate.setText("Ngày hiện tại:");
        pnlRevenueDay.add(lblRevenueCurrentDate);

        revenueCurrentDate.setMinimumSize(new java.awt.Dimension(130, 22));
        revenueCurrentDate.setPreferredSize(new java.awt.Dimension(130, 22));
        pnlRevenueDay.add(revenueCurrentDate);

        pnlRevenueControls.add(pnlRevenueDay, "day");

        pnlRevenueMonth.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueMonth.setPreferredSize(new java.awt.Dimension(597, 40));
        pnlRevenueMonth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblRevenueMonth.setText("Tháng:");
        pnlRevenueMonth.add(lblRevenueMonth);

        cbRevenueMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        pnlRevenueMonth.add(cbRevenueMonth);

        lblRevenueMonthYear.setText("Năm:");
        pnlRevenueMonth.add(lblRevenueMonthYear);

        cbRevenueYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlRevenueMonth.add(cbRevenueYear);

        pnlRevenueControls.add(pnlRevenueMonth, "month");

        pnlRevenueYear.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueYear.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblRevenueYear.setText("Năm:");
        pnlRevenueYear.add(lblRevenueYear);

        cbRevenueYearOnly.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlRevenueYear.add(cbRevenueYearOnly);

        pnlRevenueControls.add(pnlRevenueYear, "year");

        pnlRevenueCustom.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueCustom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblRevenueStartDate.setText("Từ ngày:");
        pnlRevenueCustom.add(lblRevenueStartDate);

        revenueStartDate.setMinimumSize(new java.awt.Dimension(130, 22));
        revenueStartDate.setPreferredSize(new java.awt.Dimension(130, 22));
        pnlRevenueCustom.add(revenueStartDate);

        lblRevenueEndDate.setText("Đến ngày:");
        pnlRevenueCustom.add(lblRevenueEndDate);

        revenueEndDate.setMinimumSize(new java.awt.Dimension(130, 22));
        revenueEndDate.setPreferredSize(new java.awt.Dimension(130, 22));
        pnlRevenueCustom.add(revenueEndDate);

        pnlRevenueControls.add(pnlRevenueCustom, "custom");

        pnlRevenueFilter.add(pnlRevenueControls, java.awt.BorderLayout.CENTER);

        pnlRevenueButtons.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueButtons.setPreferredSize(new java.awt.Dimension(303, 33));
        pnlRevenueButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

        btnRevenueApply.setBackground(new java.awt.Color(0, 204, 51));
        btnRevenueApply.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRevenueApply.setForeground(new java.awt.Color(255, 255, 255));
        btnRevenueApply.setText("Áp dụng");
        btnRevenueApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevenueApplyActionPerformed(evt);
            }
        });
        pnlRevenueButtons.add(btnRevenueApply);

        btnRevenueExport.setText("Xuất PDF");
        pnlRevenueButtons.add(btnRevenueExport);

        pnlRevenueFilter.add(pnlRevenueButtons, java.awt.BorderLayout.SOUTH);

        pnlRevenue.add(pnlRevenueFilter);

        pnlRevenueData.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueData.setLayout(new java.awt.BorderLayout());

        pnlRevenueTableAndSummary.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueTableAndSummary.setPreferredSize(new java.awt.Dimension(500, 305));
        pnlRevenueTableAndSummary.setLayout(new java.awt.BorderLayout());

        pnlRevenueTable.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bảng doanh thu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlRevenueTable.setLayout(new java.awt.BorderLayout());

        scrollRevenue.setPreferredSize(new java.awt.Dimension(100, 100));

        tblRevenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ngày", "Doanh thu (VNĐ)"
            }
        ));
        scrollRevenue.setViewportView(tblRevenue);

        pnlRevenueTable.add(scrollRevenue, java.awt.BorderLayout.CENTER);

        pnlRevenueTableAndSummary.add(pnlRevenueTable, java.awt.BorderLayout.CENTER);

        pnlRevenueSummary.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueSummary.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblRevenueTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRevenueTotal.setText("Tổng doanh thu:");
        pnlRevenueSummary.add(lblRevenueTotal);

        lblRevenueTotalValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRevenueTotalValue.setForeground(new java.awt.Color(0, 102, 204));
        lblRevenueTotalValue.setText("jLabel2");
        pnlRevenueSummary.add(lblRevenueTotalValue);

        pnlRevenueTableAndSummary.add(pnlRevenueSummary, java.awt.BorderLayout.SOUTH);

        pnlRevenueData.add(pnlRevenueTableAndSummary, java.awt.BorderLayout.WEST);

        pnlRevenueChart.setBackground(new java.awt.Color(255, 255, 255));
        pnlRevenueChart.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Biểu đồ doanh thu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout pnlRevenueChartLayout = new javax.swing.GroupLayout(pnlRevenueChart);
        pnlRevenueChart.setLayout(pnlRevenueChartLayout);
        pnlRevenueChartLayout.setHorizontalGroup(
            pnlRevenueChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlRevenueChartLayout.setVerticalGroup(
            pnlRevenueChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlRevenueData.add(pnlRevenueChart, java.awt.BorderLayout.CENTER);

        pnlRevenue.add(pnlRevenueData);

        tpReports.addTab("Thống kê doanh thu", pnlRevenue);

        pnlProducts.setBackground(new java.awt.Color(255, 255, 255));
        pnlProducts.setLayout(new javax.swing.BoxLayout(pnlProducts, javax.swing.BoxLayout.Y_AXIS));

        pnlProductFilter.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc theo thời gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlProductFilter.setLayout(new java.awt.BorderLayout());

        pnlProductRadio.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductRadio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 5));

        productRadioMonth.setText("Theo tháng");
        pnlProductRadio.add(productRadioMonth);

        productRadioYear.setText("Theo năm");
        pnlProductRadio.add(productRadioYear);

        pnlProductFilter.add(pnlProductRadio, java.awt.BorderLayout.NORTH);

        pnlPoductControls.setBackground(new java.awt.Color(255, 255, 255));
        pnlPoductControls.setLayout(new java.awt.CardLayout());

        pnlProductMonth.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductMonth.setPreferredSize(new java.awt.Dimension(597, 40));
        pnlProductMonth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblProductMonth.setText("Tháng:");
        pnlProductMonth.add(lblProductMonth);

        cbProductMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        pnlProductMonth.add(cbProductMonth);

        lblProductMonthYear.setText("Năm:");
        pnlProductMonth.add(lblProductMonthYear);

        cbProductYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlProductMonth.add(cbProductYear);

        pnlPoductControls.add(pnlProductMonth, "month");

        pnlProductYear.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductYear.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblProductYear.setText("Năm:");
        pnlProductYear.add(lblProductYear);

        cbProductYearOnly.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlProductYear.add(cbProductYearOnly);

        pnlPoductControls.add(pnlProductYear, "year");

        pnlProductFilter.add(pnlPoductControls, java.awt.BorderLayout.CENTER);

        pnlProductButtons.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductButtons.setPreferredSize(new java.awt.Dimension(303, 33));
        pnlProductButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

        btnProductApply.setBackground(new java.awt.Color(0, 204, 51));
        btnProductApply.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnProductApply.setForeground(new java.awt.Color(255, 255, 255));
        btnProductApply.setText("Áp dụng");
        btnProductApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductApplyActionPerformed(evt);
            }
        });
        pnlProductButtons.add(btnProductApply);

        btnProductExport.setText("Xuất PDF");
        pnlProductButtons.add(btnProductExport);

        pnlProductFilter.add(pnlProductButtons, java.awt.BorderLayout.SOUTH);

        pnlProducts.add(pnlProductFilter);

        pnlProductData.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductData.setLayout(new java.awt.BorderLayout());

        pnlProductTableAndSummary.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductTableAndSummary.setPreferredSize(new java.awt.Dimension(500, 305));
        pnlProductTableAndSummary.setLayout(new java.awt.BorderLayout());

        pnlProductTable.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm bán chạy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlProductTable.setLayout(new java.awt.BorderLayout());

        scrollProduct.setPreferredSize(new java.awt.Dimension(100, 100));

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu (VNĐ)"
            }
        ));
        scrollProduct.setViewportView(tblProducts);

        pnlProductTable.add(scrollProduct, java.awt.BorderLayout.CENTER);

        pnlProductTableAndSummary.add(pnlProductTable, java.awt.BorderLayout.CENTER);

        pnlProductSummary.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductSummary.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblProductTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductTotal.setText("Tổng doanh thu SP:");
        pnlProductSummary.add(lblProductTotal);

        lblProductTotalValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductTotalValue.setForeground(new java.awt.Color(0, 102, 204));
        lblProductTotalValue.setText("jLabel2");
        pnlProductSummary.add(lblProductTotalValue);

        pnlProductTableAndSummary.add(pnlProductSummary, java.awt.BorderLayout.SOUTH);

        pnlProductData.add(pnlProductTableAndSummary, java.awt.BorderLayout.WEST);

        pnlProductChart.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductChart.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Biểu đồ sản phẩm bán chạy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout pnlProductChartLayout = new javax.swing.GroupLayout(pnlProductChart);
        pnlProductChart.setLayout(pnlProductChartLayout);
        pnlProductChartLayout.setHorizontalGroup(
            pnlProductChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlProductChartLayout.setVerticalGroup(
            pnlProductChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlProductData.add(pnlProductChart, java.awt.BorderLayout.CENTER);

        pnlProducts.add(pnlProductData);

        tpReports.addTab("Thống kê sản phẩm", pnlProducts);

        pnlInvoices.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoices.setLayout(new javax.swing.BoxLayout(pnlInvoices, javax.swing.BoxLayout.Y_AXIS));

        pnlInvoiceFilter.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc theo thời gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlInvoiceFilter.setLayout(new java.awt.BorderLayout());

        pnlInvoiceRadio.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceRadio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 5));

        invoiceRadioDay.setText("Theo ngày");
        pnlInvoiceRadio.add(invoiceRadioDay);

        invoiceRadioMonth.setText("Theo tháng");
        pnlInvoiceRadio.add(invoiceRadioMonth);

        invoiceRadioYear.setText("Theo năm");
        pnlInvoiceRadio.add(invoiceRadioYear);

        invoiceRadioCustom.setText("Tùy chỉnh");
        pnlInvoiceRadio.add(invoiceRadioCustom);

        pnlInvoiceFilter.add(pnlInvoiceRadio, java.awt.BorderLayout.NORTH);

        pnlInvoiceControls.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceControls.setLayout(new java.awt.CardLayout());

        pnlInvoiceDay.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceDay.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblInvoiceCurrentDate.setText("Ngày hiện tại:");
        pnlInvoiceDay.add(lblInvoiceCurrentDate);

        invoiceCurrentDate.setMinimumSize(new java.awt.Dimension(130, 22));
        invoiceCurrentDate.setPreferredSize(new java.awt.Dimension(130, 22));
        pnlInvoiceDay.add(invoiceCurrentDate);

        pnlInvoiceControls.add(pnlInvoiceDay, "day");

        pnlInvoiceMonth.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceMonth.setPreferredSize(new java.awt.Dimension(597, 40));
        pnlInvoiceMonth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblInvoiceMonth.setText("Tháng:");
        pnlInvoiceMonth.add(lblInvoiceMonth);

        cbInvoiceMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        pnlInvoiceMonth.add(cbInvoiceMonth);

        lblInvoiceMonthYear.setText("Năm:");
        pnlInvoiceMonth.add(lblInvoiceMonthYear);

        cbInvoiceYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlInvoiceMonth.add(cbInvoiceYear);

        pnlInvoiceControls.add(pnlInvoiceMonth, "month");

        pnlInvoiceYear.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceYear.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblInvoiceYear.setText("Năm:");
        pnlInvoiceYear.add(lblInvoiceYear);

        cbInvoiceYearOnly.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlInvoiceYear.add(cbInvoiceYearOnly);

        pnlInvoiceControls.add(pnlInvoiceYear, "year");

        pnlInvoiceCustom.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceCustom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 5));

        lblInvoiceStartDate.setText("Từ ngày:");
        pnlInvoiceCustom.add(lblInvoiceStartDate);

        invoiceStartDate.setMinimumSize(new java.awt.Dimension(130, 22));
        invoiceStartDate.setPreferredSize(new java.awt.Dimension(130, 22));
        pnlInvoiceCustom.add(invoiceStartDate);

        lblInvoiceEndDate.setText("Đến ngày:");
        pnlInvoiceCustom.add(lblInvoiceEndDate);

        invoiceEndDate.setMinimumSize(new java.awt.Dimension(130, 22));
        invoiceEndDate.setPreferredSize(new java.awt.Dimension(130, 22));
        pnlInvoiceCustom.add(invoiceEndDate);

        pnlInvoiceControls.add(pnlInvoiceCustom, "custom");

        pnlInvoiceFilter.add(pnlInvoiceControls, java.awt.BorderLayout.CENTER);

        pnlInvoiceButtons.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceButtons.setPreferredSize(new java.awt.Dimension(303, 33));
        pnlInvoiceButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

        btnInvoiceApply.setBackground(new java.awt.Color(0, 204, 51));
        btnInvoiceApply.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInvoiceApply.setForeground(new java.awt.Color(255, 255, 255));
        btnInvoiceApply.setText("Áp dụng");
        btnInvoiceApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceApplyActionPerformed(evt);
            }
        });
        pnlInvoiceButtons.add(btnInvoiceApply);

        btnInvoiceExport.setText("Xuất PDF");
        pnlInvoiceButtons.add(btnInvoiceExport);

        pnlInvoiceFilter.add(pnlInvoiceButtons, java.awt.BorderLayout.SOUTH);

        pnlInvoices.add(pnlInvoiceFilter);

        pnlInvoiceData.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceData.setLayout(new java.awt.BorderLayout());

        pnlInvoiceTableAndSummary.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceTableAndSummary.setPreferredSize(new java.awt.Dimension(500, 305));
        pnlInvoiceTableAndSummary.setLayout(new java.awt.BorderLayout());

        pnlInvoiceTable.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thống kê hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnlInvoiceTable.setLayout(new java.awt.BorderLayout());

        scrollInvoices.setPreferredSize(new java.awt.Dimension(100, 100));

        tblInvoices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Ngày", "Số hóa đơn", "Tổng tiền (VNĐ)"
            }
        ));
        scrollInvoices.setViewportView(tblInvoices);

        pnlInvoiceTable.add(scrollInvoices, java.awt.BorderLayout.CENTER);

        pnlInvoiceTableAndSummary.add(pnlInvoiceTable, java.awt.BorderLayout.CENTER);

        pnlInvoiceSummary.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceSummary.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        lblInvoiceTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInvoiceTotal.setText("Tổng doanh thu:");
        pnlInvoiceSummary.add(lblInvoiceTotal);

        lblInvoiceTotalValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInvoiceTotalValue.setForeground(new java.awt.Color(0, 102, 204));
        lblInvoiceTotalValue.setText("jLabel2");
        pnlInvoiceSummary.add(lblInvoiceTotalValue);

        pnlInvoiceTableAndSummary.add(pnlInvoiceSummary, java.awt.BorderLayout.SOUTH);

        pnlInvoiceData.add(pnlInvoiceTableAndSummary, java.awt.BorderLayout.WEST);

        pnlInvoiceChart.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoiceChart.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Biểu đồ doanh thu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout pnlInvoiceChartLayout = new javax.swing.GroupLayout(pnlInvoiceChart);
        pnlInvoiceChart.setLayout(pnlInvoiceChartLayout);
        pnlInvoiceChartLayout.setHorizontalGroup(
            pnlInvoiceChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlInvoiceChartLayout.setVerticalGroup(
            pnlInvoiceChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlInvoiceData.add(pnlInvoiceChart, java.awt.BorderLayout.CENTER);

        pnlInvoices.add(pnlInvoiceData);

        tpReports.addTab("Thống kê hóa đơn", pnlInvoices);

        add(tpReports, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRevenueApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevenueApplyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRevenueApplyActionPerformed

    private void btnProductApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductApplyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProductApplyActionPerformed

    private void btnInvoiceApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoiceApplyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInvoiceApplyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvoiceApply;
    private javax.swing.JButton btnInvoiceExport;
    private javax.swing.JButton btnProductApply;
    private javax.swing.JButton btnProductExport;
    private javax.swing.JButton btnRevenueApply;
    private javax.swing.JButton btnRevenueExport;
    private javax.swing.JComboBox<String> cbInvoiceMonth;
    private javax.swing.JComboBox<String> cbInvoiceYear;
    private javax.swing.JComboBox<String> cbInvoiceYearOnly;
    private javax.swing.JComboBox<String> cbProductMonth;
    private javax.swing.JComboBox<String> cbProductYear;
    private javax.swing.JComboBox<String> cbProductYearOnly;
    private javax.swing.JComboBox<String> cbRevenueMonth;
    private javax.swing.JComboBox<String> cbRevenueYear;
    private javax.swing.JComboBox<String> cbRevenueYearOnly;
    private com.toedter.calendar.JDateChooser invoiceCurrentDate;
    private com.toedter.calendar.JDateChooser invoiceEndDate;
    private javax.swing.JRadioButton invoiceRadioCustom;
    private javax.swing.JRadioButton invoiceRadioDay;
    private javax.swing.JRadioButton invoiceRadioMonth;
    private javax.swing.JRadioButton invoiceRadioYear;
    private com.toedter.calendar.JDateChooser invoiceStartDate;
    private javax.swing.JLabel lblInvoiceCurrentDate;
    private javax.swing.JLabel lblInvoiceEndDate;
    private javax.swing.JLabel lblInvoiceMonth;
    private javax.swing.JLabel lblInvoiceMonthYear;
    private javax.swing.JLabel lblInvoiceStartDate;
    private javax.swing.JLabel lblInvoiceTotal;
    private javax.swing.JLabel lblInvoiceTotalValue;
    private javax.swing.JLabel lblInvoiceYear;
    private javax.swing.JLabel lblProductMonth;
    private javax.swing.JLabel lblProductMonthYear;
    private javax.swing.JLabel lblProductTotal;
    private javax.swing.JLabel lblProductTotalValue;
    private javax.swing.JLabel lblProductYear;
    private javax.swing.JLabel lblRevenueCurrentDate;
    private javax.swing.JLabel lblRevenueEndDate;
    private javax.swing.JLabel lblRevenueMonth;
    private javax.swing.JLabel lblRevenueMonthYear;
    private javax.swing.JLabel lblRevenueStartDate;
    private javax.swing.JLabel lblRevenueTotal;
    private javax.swing.JLabel lblRevenueTotalValue;
    private javax.swing.JLabel lblRevenueYear;
    private javax.swing.JPanel pnlInvoiceButtons;
    private javax.swing.JPanel pnlInvoiceChart;
    private javax.swing.JPanel pnlInvoiceControls;
    private javax.swing.JPanel pnlInvoiceCustom;
    private javax.swing.JPanel pnlInvoiceData;
    private javax.swing.JPanel pnlInvoiceDay;
    private javax.swing.JPanel pnlInvoiceFilter;
    private javax.swing.JPanel pnlInvoiceMonth;
    private javax.swing.JPanel pnlInvoiceRadio;
    private javax.swing.JPanel pnlInvoiceSummary;
    private javax.swing.JPanel pnlInvoiceTable;
    private javax.swing.JPanel pnlInvoiceTableAndSummary;
    private javax.swing.JPanel pnlInvoiceYear;
    private javax.swing.JPanel pnlInvoices;
    private javax.swing.JPanel pnlPoductControls;
    private javax.swing.JPanel pnlProductButtons;
    private javax.swing.JPanel pnlProductChart;
    private javax.swing.JPanel pnlProductData;
    private javax.swing.JPanel pnlProductFilter;
    private javax.swing.JPanel pnlProductMonth;
    private javax.swing.JPanel pnlProductRadio;
    private javax.swing.JPanel pnlProductSummary;
    private javax.swing.JPanel pnlProductTable;
    private javax.swing.JPanel pnlProductTableAndSummary;
    private javax.swing.JPanel pnlProductYear;
    private javax.swing.JPanel pnlProducts;
    private javax.swing.JPanel pnlRevenue;
    private javax.swing.JPanel pnlRevenueButtons;
    private javax.swing.JPanel pnlRevenueChart;
    private javax.swing.JPanel pnlRevenueControls;
    private javax.swing.JPanel pnlRevenueCustom;
    private javax.swing.JPanel pnlRevenueData;
    private javax.swing.JPanel pnlRevenueDay;
    private javax.swing.JPanel pnlRevenueFilter;
    private javax.swing.JPanel pnlRevenueMonth;
    private javax.swing.JPanel pnlRevenueRadio;
    private javax.swing.JPanel pnlRevenueSummary;
    private javax.swing.JPanel pnlRevenueTable;
    private javax.swing.JPanel pnlRevenueTableAndSummary;
    private javax.swing.JPanel pnlRevenueYear;
    private javax.swing.JRadioButton productRadioMonth;
    private javax.swing.JRadioButton productRadioYear;
    private com.toedter.calendar.JDateChooser revenueCurrentDate;
    private com.toedter.calendar.JDateChooser revenueEndDate;
    private javax.swing.JRadioButton revenueRadioCustom;
    private javax.swing.JRadioButton revenueRadioDay;
    private javax.swing.JRadioButton revenueRadioMonth;
    private javax.swing.JRadioButton revenueRadioYear;
    private com.toedter.calendar.JDateChooser revenueStartDate;
    private javax.swing.JScrollPane scrollInvoices;
    private javax.swing.JScrollPane scrollProduct;
    private javax.swing.JScrollPane scrollRevenue;
    private javax.swing.JTable tblInvoices;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTable tblRevenue;
    private javax.swing.JTabbedPane tpReports;
    // End of variables declaration//GEN-END:variables
}
