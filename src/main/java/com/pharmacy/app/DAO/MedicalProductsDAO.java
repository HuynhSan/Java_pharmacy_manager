/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import com.pharmacy.app.DTO.MedicalProductsDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class MedicalProductsDAO implements DAOinterface<MedicalProductsDTO>{
    MyConnection myconnect = new MyConnection();
    @Override
    public boolean insert(MedicalProductsDTO product) {
        boolean result = false;

        if (myconnect.openConnection()) {
            try {
                String sql = "INSERT INTO medical_products(product_id, name, category_id, description, unit, quantity, packing_specification, img_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                // Sử dụng phương thức prepareUpdate để thực thi câu lệnh SQL
                int rowsAffected = myconnect.prepareUpdate(sql, 
                    product.getMedicineID(),
                    product.getName(),
                    product.getCategory(),
                    product.getDescription(),
                    product.getUnit(),
                    product.getQuantity(),
                    product.getPackingSpecification(),
                    product.getImgPath()
                );

                if (rowsAffected > 0) {
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }

        return result;
}


    @Override
    public boolean update(MedicalProductsDTO product) {
        boolean result = false;

        try {
            myconnect.openConnection();

            String sql = "UPDATE medical_products SET name = ?, category_id = ?, description = ?, unit = ?, packing_specification = ? WHERE product_id = ?";

            int rowsAffected = myconnect.prepareUpdate(sql, 
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getUnit(),
                product.getPackingSpecification(),
                product.getMedicineID()
            );
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myconnect.closeConnection();
        }

        return result;
}



    @Override
    public boolean delete(String ID) {
            boolean result = false;

            try {
                myconnect.openConnection();

                String sql = "UPDATE medical_products SET is_deleted = 1 WHERE product_id = ?";

                int rowsAffected = myconnect.prepareUpdate(sql, ID);

                if (rowsAffected > 0) {
                    result = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Đảm bảo đóng kết nối
                myconnect.closeConnection();
            }

            return result;
    }

    @Override
    public ArrayList<MedicalProductsDTO> selectAll() {
        ArrayList<MedicalProductsDTO> medicineList = new ArrayList<MedicalProductsDTO>();
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM medical_products m JOIN categories c ON m.category_id = c.category_id";
            ResultSet rs = myconnect.runQuery(sql);
            
            try {
                while(rs.next()){
                       MedicalProductsDTO product = new MedicalProductsDTO(
                       rs.getString(1),
                       rs.getString(2),
                       rs.getString(11),
                       rs.getString(4),
                       rs.getString(5),
                       rs.getInt(6),
                       rs.getString(7)
                    );
                       if(rs.getBoolean(8) == false){
                           product.setStatus("Còn kinh doanh");
                       }else {
                           product.setStatus("Ngừng kinh doanh");
                       }
                       product.setImgPath(rs.getString("img_path"));
                       medicineList.add(product);
                }
            } catch (SQLException ex) {
                System.out.println("SQLException occurred: " + ex.getMessage());
                ex.printStackTrace();
            }finally{
                myconnect.closeConnection();
                    }
        }
        return medicineList;
    }
    
    public ArrayList<MedicalProductsDTO> selectAll1() {
        ArrayList<MedicalProductsDTO> medicineList = new ArrayList<>();
        if (myconnect.openConnection()){
            String sql = "SELECT m.product_id, m.name, c.category_name, SUM(pb.inventory_quantity) AS total, m.is_deleted "
                    + "FROM medical_products m "
                    + "JOIN categories c ON m.category_id = c.category_id "
                    + "JOIN product_batches pb ON pb.product_id = m.product_id "
                    + "GROUP BY m.product_id, m.name, c.category_name, m.is_deleted";
            ResultSet rs = myconnect.runQuery(sql);
            
            try {
                while(rs.next()){
                    MedicalProductsDTO product = new MedicalProductsDTO();
                    product.setMedicineID(rs.getString("product_id"));
                    product.setName(rs.getString("name"));
                    product.setQuantity(rs.getInt("total"));
                    product.setCategory(rs.getString("category_name"));
                    if(rs.getBoolean("is_deleted")==false){
                        product.setStatus("Còn hoạt động");
                    }
                    else {
                        product.setStatus("Ngưng hoạt động");
                    }
                    medicineList.add(product);
                }
                System.out.println(medicineList);
            } catch (SQLException ex) {
                System.out.println("SQLException occurred: " + ex.getMessage());
                ex.printStackTrace();
            }finally{
                myconnect.closeConnection();
                    }
        }
        return medicineList;
    }
    @Override
    public MedicalProductsDTO selectByID(String ID) {
        if(myconnect.openConnection()){
            try {
                String sql = "SELECT p.*, c.category_name FROM medical_products p " + 
                             "JOIN categories c ON p.category_id = c.category_id " + 
                             "WHERE p.product_id = ?";

                PreparedStatement ps = myconnect.con.prepareStatement(sql);
                ps.setString(1, ID);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    MedicalProductsDTO product = new MedicalProductsDTO();

                    product.setMedicineID(rs.getString("product_id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category_name")); 
                    product.setDescription(rs.getString("description"));
                    product.setPackingSpecification(rs.getString("packing_specification"));
                    product.setUnit(rs.getString("unit"));
                    product.setQuantity(rs.getInt("quantity"));
                    if(rs.getBoolean("is_deleted") == false){
                           product.setStatus("Còn kinh doanh");
                       }else {
                           product.setStatus("Ngừng kinh doanh");
                       }

                    return product;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return null;
    }

    @Override
    public ArrayList<MedicalProductsDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
        

    public void updateSumQuantity(String productId, int quantity) {
        if (myconnect.openConnection()) {
            try {
                String sql = "UPDATE medical_products SET quantity = ? WHERE product_id = ?";
                myconnect.prepareUpdate(sql, quantity, productId);

            } finally {
                myconnect.closeConnection();
            }
        }
    }
    public String getLatestProductID() {
        String latestID = null;
        if (myconnect.openConnection()) {
            try {
                String sql = "SELECT MAX(product_id) AS max_id FROM medical_products";
                ResultSet rs = myconnect.runQuery(sql);
                if (rs.next()) {
                    latestID = rs.getString("max_id"); // Có thể là null nếu chưa có sản phẩm
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return latestID;
}

    
}
