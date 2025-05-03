/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phong
 */
public class PDFExporter {
    /**
     * Export table data to PDF
     * 
     * @param parentComponent Parent component for dialogs
     * @param tableModel TableModel containing data
     * @param title Title of the PDF document
     * @param filename Default filename for the PDF
     * @param columnWidths Custom column widths (null for automatic)
     * @param landscape True for landscape orientation, false for portrait
     * @return True if export was successful, false otherwise
     */
    public static boolean exportTableToPDF(
            Component parentComponent,
            TableModel tableModel,
            String title,
            String filename,
            float[] columnWidths,
            boolean landscape) {
        
        try {
            // Check if data exists
            int rowCount = tableModel.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Không có dữ liệu để xuất PDF.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            
            // Get columns for the table
            int columnCount = tableModel.getColumnCount();
            List<String> columns = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                columns.add(tableModel.getColumnName(i));
            }
            
            // Select file location
            File fileToSave = selectSaveLocation(parentComponent, filename);
            if (fileToSave == null) {
                return false; // User canceled operation
            }
            
            // Create and export PDF
            createPDF(fileToSave, title, columns, tableModel, columnWidths, landscape);
            
            // Ask to open the file
            boolean opened = askToOpenFile(parentComponent, fileToSave);
            
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentComponent,
                    "Lỗi khi xuất PDF: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Open file chooser dialog to select save location
     */
    private static File selectSaveLocation(Component parentComponent, String defaultFilename) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file PDF");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File(defaultFilename));
        
        // Add a file filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(filter);
        
        int userSelection = fileChooser.showSaveDialog(parentComponent);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Ensure the file has .pdf extension
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
                fileToSave = new File(filePath);
            }
            
            return fileToSave;
        }
        
        return null;
    }
    
    /**
     * Create the PDF document with table data
     */
    private static void createPDF(
            File file,
            String title,
            List<String> columns,
            TableModel tableModel,
            float[] columnWidths,
            boolean landscape) throws DocumentException, IOException {
        
        // Set page orientation
        Rectangle pageSize = landscape ? PageSize.A4.rotate() : PageSize.A4;
        Document document = new Document(pageSize, 36, 36, 54, 36); // left, right, top, bottom margins
        
        // Create writer
        PdfWriter.getInstance(document, new FileOutputStream(file));
        
        // Open document
        document.open();
        
        // Set fonts
        BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", 
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                
        Font titleFont = new Font(baseFont, 18, Font.BOLD);
        Font headerFont = new Font(baseFont, 12, Font.BOLD);
        Font contentFont = new Font(baseFont, 11, Font.NORMAL);
        
        // Add title
        Paragraph titlePara = new Paragraph(title, titleFont);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        titlePara.setSpacingAfter(10);
        document.add(titlePara);
        
        // Add date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formatDateTime = now.format(formatter);
        Paragraph dateTime = new Paragraph("Ngày xuất: " + formatDateTime, contentFont);
        dateTime.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateTime);
        
        // Add space
        document.add(new Paragraph(" "));
        
        // Create table
        PdfPTable pdfTable = new PdfPTable(columns.size());
        pdfTable.setWidthPercentage(100);
        
        // Set column widths if provided
        if (columnWidths != null && columnWidths.length == columns.size()) {
            pdfTable.setWidths(columnWidths);
        }
        
        // Add headers
        for (String column : columns) {
            PdfPCell cell = new PdfPCell(new Phrase(column, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(new BaseColor(220, 220, 220));
            cell.setPadding(5);
            pdfTable.addCell(cell);
        }
        
        // Add data
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                String value = tableModel.getValueAt(i, j) != null ? 
                        tableModel.getValueAt(i, j).toString() : "";
                
                PdfPCell cell = new PdfPCell(new Phrase(value, contentFont));
                
                // Adjust cell alignment based on content type if needed
                if (tableModel.getColumnClass(j) == Integer.class || 
                    tableModel.getColumnClass(j) == Long.class ||
                    tableModel.getColumnClass(j) == Double.class) {
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                } else if (tableModel.getColumnClass(j) == Boolean.class) {
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                } else {
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                }
                
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                pdfTable.addCell(cell);
            }
        }
        
        // Add table to document
        document.add(pdfTable);
        
        // Add footer with page number
        document.add(new Paragraph(" "));
        Paragraph footer = new Paragraph("Trang 1", contentFont);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);
        
        // Close document
        document.close();
    }
    
    /**
     * Ask user if they want to open the generated PDF
     */
    private static boolean askToOpenFile(Component parentComponent, File file) {
        int option = JOptionPane.showConfirmDialog(parentComponent,
                "PDF đã được tạo thành công! Bạn có muốn mở file không?",
                "Xuất PDF thành công",
                JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            try {
                // Open the PDF file with default system application
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(parentComponent,
                            "Không thể mở file. Vui lòng mở thủ công tại: " + file.getAbsolutePath(),
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Không thể mở file: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return false;
    }
    
    /**
     * Alternative method with more customization options
     */
    public static boolean exportToPDF(
            Component parentComponent,
            String title,
            String subtitle,
            String filename,
            List<String> headers,
            List<List<String>> data,
            float[] columnWidths,
            boolean landscape) {
        
        try {
            // Check if data exists
            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Không có dữ liệu để xuất PDF.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            
            // Select file location
            File fileToSave = selectSaveLocation(parentComponent, filename);
            if (fileToSave == null) {
                return false; // User canceled operation
            }
            
            // Create PDF document
            Rectangle pageSize = landscape ? PageSize.A4.rotate() : PageSize.A4;
            Document document = new Document(pageSize, 36, 36, 54, 36);
            
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();
            
            // Set fonts
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", 
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font subtitleFont = new Font(baseFont, 14, Font.NORMAL);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font contentFont = new Font(baseFont, 11, Font.NORMAL);
            
            // Add title and subtitle
            Paragraph titlePara = new Paragraph(title, titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            titlePara.setSpacingAfter(5);
            document.add(titlePara);
            
            if (subtitle != null && !subtitle.isEmpty()) {
                Paragraph subtitlePara = new Paragraph(subtitle, subtitleFont);
                subtitlePara.setAlignment(Element.ALIGN_CENTER);
                subtitlePara.setSpacingAfter(10);
                document.add(subtitlePara);
            }
            
            // Add date
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            Paragraph dateTime = new Paragraph("Ngày xuất: " + now.format(formatter), contentFont);
            dateTime.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateTime);
            
            // Add space
            document.add(new Paragraph(" "));
            
            // Create table
            PdfPTable pdfTable = new PdfPTable(headers.size());
            pdfTable.setWidthPercentage(100);
            
            // Set column widths if provided
            if (columnWidths != null && columnWidths.length == headers.size()) {
                pdfTable.setWidths(columnWidths);
            }
            
            // Add headers
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(new BaseColor(220, 220, 220));
                cell.setPadding(5);
                pdfTable.addCell(cell);
            }
            
            // Add data rows
            for (List<String> row : data) {
                for (int i = 0; i < row.size(); i++) {
                    String value = row.get(i) != null ? row.get(i) : "";
                    PdfPCell cell = new PdfPCell(new Phrase(value, contentFont));
                    
                    // Default alignment is left, but you can customize based on column index
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPadding(5);
                    pdfTable.addCell(cell);
                }
            }
            
            // Add table to document
            document.add(pdfTable);
            
            // Add footer
            document.add(new Paragraph(" "));
            Paragraph footer = new Paragraph("Trang 1", contentFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);
            
            document.close();
            
            // Ask to open file
            askToOpenFile(parentComponent, fileToSave);
            
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentComponent,
                    "Lỗi khi xuất PDF: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean exportEmployeesToPDF(Component parentComponent, TableModel employeeTableModel) {
        // Define custom column widths for employee table
        float[] columnWidths = {0.8f, 2f, 1.2f, 0.8f, 2f, 1.2f, 2.5f};
        
        return exportTableToPDF(
                parentComponent,
                employeeTableModel,
                "DANH SÁCH NHÂN VIÊN",
                "DanhSachNhanVien.pdf",
                columnWidths,
                true // landscape orientation
        );
    }
    
    public static boolean exportContractsToPDF(Component parentComponent, TableModel contractTableModel) {
        // Define custom column widths for contract table
        float[] columnWidths = {0.8f, 2f, 1.2f, 0.8f, 2f, 1.2f, 2.5f};
        
        return exportTableToPDF(
                parentComponent,
                contractTableModel,
                "DANH SÁCH HỢP ĐỒNG",
                "DanhSachHopDong.pdf",
                columnWidths,
                true // landscape orientation
        );
    }
    
    public static boolean exportSuppliersToPDF(Component parentComponent, TableModel supplierTableModel) {
        // Define custom column widths for supplier table
        float[] columnWidths = {0.8f, 2f, 1.2f, 0.8f, 2f, 1.2f, 2.5f};
        
        return exportTableToPDF(
                parentComponent,
                supplierTableModel,
                "DANH SÁCH NHÀ CUNG CẤP",
                "DanhSachNhaCungCap.pdf",
                columnWidths,
                true // landscape orientation
        );
    }
    public static boolean exportCustomersToPDF(Component parentComponent, TableModel supplierTableModel) {
        // Define custom column widths for supplier table
        float[] columnWidths = {0.8f, 2f, 1.2f, 0.8f, 2f, 1.2f, 2.5f};
        
        return exportTableToPDF(
                parentComponent,
                supplierTableModel,
                "DANH SÁCH KHÁCH HÀNG",
                "DanhSachKhachHang.pdf",
                columnWidths,
                true // landscape orientation
        );
    }
    public static boolean exportInvoicesToPDF(Component parentComponent, TableModel invoiceTableModel) {
        // Define custom column widths for supplier table
        float[] columnWidths = {0.8f, 2f, 1.2f, 0.8f, 2f, 1.2f, 2.5f};
        
        return exportTableToPDF(
                parentComponent,
                invoiceTableModel,
                "DANH SÁCH PHIẾU NHẬP",
                "DanhSachPhieuNhap.pdf",
                columnWidths,
                true // landscape orientation
        );
    }
}
