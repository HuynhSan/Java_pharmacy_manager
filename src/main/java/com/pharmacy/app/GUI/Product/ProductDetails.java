/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.pharmacy.app.GUI.Product;

import com.pharmacy.app.BUS.CategoryBUS;
import com.pharmacy.app.BUS.MedicalProductsBUS;
import com.pharmacy.app.BUS.ProductBatchBUS;
import com.pharmacy.app.BUS.ProductPromoBUS;
import com.pharmacy.app.BUS.PromotionBUS;
import com.pharmacy.app.DTO.CategoryDTO;
import com.pharmacy.app.DTO.MedicalProductsDTO;
import com.pharmacy.app.DTO.ProductBatchDTO;
import com.pharmacy.app.DTO.ProductPromoDTO;
import com.pharmacy.app.DTO.PromotionDTO;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.CaretListener;

/**
 *
 * @author LENOVO
 */
public class ProductDetails extends javax.swing.JDialog {

    private MedicalProductsDTO productDTO;
    private MedicalProductsBUS productsBUS;
    private ProductPromoBUS ppBUS = new ProductPromoBUS();
    private ProductBatchBUS pbbus = new ProductBatchBUS();
    private String originalName;
    private String originalCate;
    private String originalDes;
    private String originalUnit;
    private String originalPacking;
    /**
     * Creates new form MedicineDetails
     */
    public ProductDetails(java.awt.Frame parent, boolean modal, MedicalProductsDTO productDTO) {
        super(parent, modal);
        initComponents();
        this.productDTO = productDTO;
        loadData(productDTO);
        productsBUS = new MedicalProductsBUS();
    }

    private void loadData(MedicalProductsDTO productDTO){
        txtName.setText(productDTO.getName());
        txtID.setText(productDTO.getMedicineID());
        cbbCate.setSelectedItem(productDTO.getCategory());
        txtDes.setText(productDTO.getDescription());
        txtUnit.setText(productDTO.getUnit());
        txtQuantity.setText(Integer.toString(productDTO.getQuantity()));
        txtPacking.setText(productDTO.getPackingSpecification());
        ProductBatchDTO batch = pbbus.getProductBatchByProductID(productDTO.getMedicineID());
        if (batch != null) {
            txtSup.setText(batch.getSupplierName());
        } else {
            txtSup.setText("Không xác định"); // hoặc "Không xác định"
        }
        ProductPromoDTO promo = ppBUS.getPromoByProductID(productDTO.getMedicineID());
        System.out.println(productDTO.getMedicineID());
        
        
        if(promo != null){
            System.out.println(promo.getPromoID());
            txtPromo.setText(promo.getPromoID());
        }else{
            txtPromo.setText("Không có.");
        }
        // Lưu giá trị gốc
        originalName = productDTO.getName();
        originalCate = productDTO.getCategory();
        originalDes = productDTO.getDescription();
        originalUnit = productDTO.getUnit();
        originalPacking = productDTO.getPackingSpecification();

        imgLbl.setPreferredSize(new Dimension(300, 200));
        System.out.println(productDTO.getImgPath());
        loadProductImage(productDTO.getImgPath());
        // Thêm listener kiểm tra thay đổi
        setupChangeListeners();

    }
    
    private void loadProductImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            imgLbl.setText("Không có hình ảnh");
            return;
        }
            System.out.println("Đang cố tải ảnh từ: " + imagePath);
            System.out.println("Absolute path: " + new File(imagePath).getAbsolutePath());
            System.out.println("Tồn tại? " + new File(imagePath).exists());
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                imgLbl.setIcon(new ImageIcon(img));
            } else {
                imgLbl.setText("Không có hình ảnh");
            }
            

        } catch (Exception e) {
            
        }
    }
    
    private void setupChangeListeners() {
        CaretListener fieldListener = e -> checkForChanges();
        txtName.addCaretListener(fieldListener);
        txtDes.addCaretListener(fieldListener);
        txtUnit.addCaretListener(fieldListener);
        txtPacking.addCaretListener(fieldListener);

        cbbCate.addItemListener(e -> checkForChanges());
    }

    // nếu có field nào thay đổi thì enable
    private void checkForChanges() {
        boolean changed =
            !txtName.getText().equals(originalName) ||
            !cbbCate.getSelectedItem().toString().equals(originalCate) ||
            !txtDes.getText().equals(originalDes) ||
            !txtUnit.getText().equals(originalUnit) ||
            !txtPacking.getText().equals(originalPacking);

        updateBtn.setEnabled(changed);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDes = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtUnit = new javax.swing.JTextField();
        txtSup = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        cbbCate = new javax.swing.JComboBox<>();
        txtPacking = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPromo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        imgLbl = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("THÔNG TIN SẢN PHẨM");
        jPanel1.add(jLabel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Mã sản phẩm:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        jLabel3.setText("Tên sản phẩm:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 140, -1));

        jLabel4.setText("Phân loại:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 71, -1));

        jLabel5.setText("Đơn vị tính:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 71, -1));

        txtDes.setColumns(20);
        txtDes.setRows(5);
        jScrollPane1.setViewportView(txtDes);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 184, 109));

        jLabel6.setText("Hình ảnh:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 70, -1));

        txtID.setEditable(false);
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        jPanel2.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 6, 155, -1));

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        jPanel2.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 155, -1));

        txtUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnitActionPerformed(evt);
            }
        });
        jPanel2.add(txtUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 155, -1));

        txtSup.setEditable(false);
        txtSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupActionPerformed(evt);
            }
        });
        jPanel2.add(txtSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 155, -1));

        jLabel9.setText("Nhà cung cấp:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 92, -1));

        jLabel10.setText("Số lượng:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 71, -1));

        txtQuantity.setEditable(false);
        txtQuantity.setEnabled(false);
        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });
        jPanel2.add(txtQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 155, -1));

        cbbCate.setEditable(true);
        cbbCate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thuốc kê đơn", "Thuốc không kê đơn", "Vitamin & Thực phẩm chức năng", "Dược mỹ phẩm", "Chăm sóc cá nhân", "Thuốc khác" }));
        cbbCate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCateActionPerformed(evt);
            }
        });
        jPanel2.add(cbbCate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 155, -1));

        txtPacking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPackingActionPerformed(evt);
            }
        });
        jPanel2.add(txtPacking, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 155, -1));

        jLabel8.setText("Quy cách:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jLabel7.setText("Mã khuyến mãi áp dụng:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 139, 22));

        txtPromo.setEditable(false);
        txtPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPromoActionPerformed(evt);
            }
        });
        jPanel2.add(txtPromo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 160, -1));

        jLabel11.setText("Mô tả:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 80, -1));
        jPanel2.add(imgLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 330, 170));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        updateBtn.setBackground(new java.awt.Color(0, 204, 51));
        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        updateBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateBtn.setText("CẬP NHẬT");
        updateBtn.setEnabled(false);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        jPanel3.add(updateBtn);

        deleteBtn.setBackground(new java.awt.Color(255, 0, 0));
        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setText("XÓA");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        jPanel3.add(deleteBtn);

        cancelBtn.setText("THOÁT");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        jPanel3.add(cancelBtn);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
//        Frame frame = JOptionPane.getFrameForComponent(jButton1);
//        UpdateProduct dialog = new UpdateProduct(frame, true);
//        dialog.setLocationRelativeTo(frame);
//        dialog.setVisible(true);
    int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

    if (option == JOptionPane.YES_OPTION) {
        String name = txtName.getText().trim();
        String description = txtDes.getText().trim();
        String unit = txtUnit.getText().trim();
        String packing = txtPacking.getText().trim();

        // Lấy tên danh mục từ ComboBox
        String selectedCategoryName = (String) cbbCate.getSelectedItem();

        // Lấy categoryID từ tên
        CategoryBUS categoryBUS = new CategoryBUS();
        String categoryID = categoryBUS.getCategoryIDByName(selectedCategoryName);
        if (categoryID == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã danh mục cho tên: " + selectedCategoryName);
            return;
        }

        // Cập nhật DTO
        productDTO.setName(name);
        productDTO.setCategory(categoryID);
        productDTO.setDescription(description);
        productDTO.setUnit(unit);
        productDTO.setPackingSpecification(packing);

        boolean isUpdated = productsBUS.updateProduct(productDTO);

        if (isUpdated) {
            originalName = name;
            originalCate = selectedCategoryName;
            originalDes = description;
            originalUnit = unit;
            originalPacking = packing;

            updateBtn.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Đã cập nhật thành công.");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng thử lại.");
        }
    }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
            // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa sản phẩm này?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);

        // Nếu người dùng chọn Yes
        if (confirm == JOptionPane.YES_OPTION) {
            // Giả sử bạn đã lấy được productId từ một trường nào đó trong GUI (ví dụ: bảng, textbox)
            String productId = txtID.getText();
            boolean isDeleted = productsBUS.deleteProduct(productId);

            // Thông báo kết quả
            if (isDeleted) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã được xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình xóa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void txtSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupActionPerformed

    private void txtUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnitActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void cbbCateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbCateActionPerformed

    private void txtPackingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPackingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPackingActionPerformed

    private void txtPromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPromoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPromoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProductDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            private MedicalProductsDTO productDTO;
            public void run() {
                ProductDetails dialog = new ProductDetails(new javax.swing.JFrame(), true, productDTO);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox<String> cbbCate;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel imgLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDes;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPacking;
    private javax.swing.JTextField txtPromo;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSup;
    private javax.swing.JTextField txtUnit;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
