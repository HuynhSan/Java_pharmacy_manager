/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;
import com.pharmacy.app.DTO.PromotionDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Giai Cuu Li San
 */
public class PromotionDAO implements DAOinterface<PromotionDTO>{
    MyConnection myconnect = new MyConnection();

    public String generateNewPromoId() {
        String newId = "P001"; // mặc định nếu chưa có khuyến mãi nào
        if (myconnect.openConnection()) {
            String sql = "SELECT TOP 1 promo_id FROM promotions ORDER BY promo_id DESC";
            try {
                ResultSet rs = myconnect.runQuery(sql);
                if (rs != null && rs.next()) {
                    String lastId = rs.getString("promo_id"); // ví dụ: "KM015"
                    int num = Integer.parseInt(lastId.substring(1)) + 1;
                    newId = String.format("P%03d", num);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return newId;
    }

    
    @Override
    public boolean insert(PromotionDTO t) {
        boolean isSuccess = false;
        if (myconnect.openConnection()) {
            String sql = "INSERT INTO promotions (promo_id, promo_name, promo_type, percent_discount, points_required, points_to_money, start_date, end_date) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Sử dụng prepareUpdate để thực hiện câu lệnh INSERT
            int result = myconnect.prepareUpdate(
                sql,
                t.getPromotionId(),
                t.getProgramName(),
                t.getPromotionType(),
                t.getDiscountPercent() != null ? t.getDiscountPercent() : null, // Kiểm tra nếu là null thì truyền null
                t.getMinAccumulatedPoints() != null ? t.getMinAccumulatedPoints() : null, // Kiểm tra nếu là null thì truyền null
                t.getDiscountAmount() != null ? t.getDiscountAmount() : null, // Kiểm tra nếu là null thì truyền null
                Date.valueOf(t.getStartDate()), // Convert LocalDate sang java.sql.Date
                Date.valueOf(t.getEndDate())   // Convert LocalDate sang java.sql.Date
                
            );

            // Kiểm tra kết quả và trả về true nếu thành công, false nếu thất bại
            if (result > 0) {
                isSuccess = true; // Insert thành công
            }

            myconnect.closeConnection(); // Đảm bảo đóng kết nối sau khi xong
        }

        return isSuccess;
    }

    @Override
    public boolean update(PromotionDTO t) {
        boolean isSuccess = false;
        if (myconnect.openConnection()) {
            String sql = "UPDATE promotions SET promo_name = ?, promo_type = ?, percent_discount = ?, points_required = ?, points_to_money = ?, start_date = ?, end_date = ? "
                       + "WHERE promo_id = ? AND is_deleted = 0";
            
            // Sử dụng prepareUpdate để thực hiện câu lệnh UPDATE
            int result = myconnect.prepareUpdate(
                sql,
                t.getProgramName(),
                t.getPromotionType(),
                t.getDiscountPercent() != null ? t.getDiscountPercent() : null, // Kiểm tra nếu là null thì truyền null
                t.getMinAccumulatedPoints() != null ? t.getMinAccumulatedPoints() : null, // Kiểm tra nếu là null thì truyền null
                t.getDiscountAmount() != null ? t.getDiscountAmount() : null, // Kiểm tra nếu là null thì truyền null
                Date.valueOf(t.getStartDate()), // Convert LocalDate sang java.sql.Date
                Date.valueOf(t.getEndDate()),   // Convert LocalDate sang java.sql.Date
                t.getPromotionId()

            );

            // Kiểm tra kết quả và trả về true nếu thành công, false nếu thất bại
            if (result > 0) {
                isSuccess = true; // thành công
            }
            myconnect.closeConnection(); // Đảm bảo đóng kết nối sau khi xong
        }
        return isSuccess;
    }

    @Override
    public boolean delete(String t) {
        boolean isSuccess = false;
        if (myconnect.openConnection()) {
            String sql = "UPDATE promotions SET is_deleted = 1 WHERE promo_id = ?";
            
            // Sử dụng prepareUpdate để thực hiện câu lệnh UPDATE
            int result = myconnect.prepareUpdate(sql, t);
            
            // Kiểm tra kết quả và trả về true nếu thành công, false nếu thất bại
            if (result > 0) {
                isSuccess = true; // thành công
            }
            myconnect.closeConnection(); // Đảm bảo đóng kết nối sau khi xong
        }
        return isSuccess;
    }

    /**
     *
     * @return
     */
    @Override    
    public ArrayList<PromotionDTO> selectAll(){
        ArrayList<PromotionDTO> promos = new ArrayList<>();
        
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM promotions WHERE is_deleted=0";
            
            ResultSet rs = myconnect.runQuery(sql);
            
            try {
                while (rs != null && rs.next()){
                    PromotionDTO promo = new PromotionDTO(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getFloat(4),
                            rs.getFloat(5),
                            rs.getDouble(6),
                            rs.getDate(7).toLocalDate(),
                            rs.getDate(8).toLocalDate()
                    );
                    promos.add(promo);
                }
            } catch(Exception e){
                    e.printStackTrace();
            } finally{
                myconnect.closeConnection();
            }
        }
        return promos;
    }
    
    

    @Override
    public PromotionDTO selectByID(String t) {
        PromotionDTO promo = null;

        if (myconnect.openConnection()) {
            String sql = "SELECT * FROM promotions WHERE promo_id = ? AND is_deleted = 0";
            try {

                ResultSet rs = myconnect.runPreparedQuery(sql, t);
                if (rs.next()) {
                    promo = new PromotionDTO(
                        rs.getString("promo_id"),
                        rs.getString("promo_name"),
                        rs.getString("promo_type"),
                        rs.getFloat("percent_discount"),
                        rs.getFloat("points_required"),
                        rs.getDouble("points_to_money"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myconnect.closeConnection();
            }
        }
        return promo;
    }

    @Override
    public ArrayList<PromotionDTO> search(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // Hàm lọc khuyến mãi theo cbx
    public ArrayList<PromotionDTO> getPromotionsByType(String type) {
        ArrayList<PromotionDTO> promos = new ArrayList<>();
        
        if (myconnect.openConnection()){
            String sql = "SELECT * FROM promotions WHERE promo_type = ? AND is_deleted = 0";
            
            ResultSet rs = myconnect.runPreparedQuery(sql, type);
            
            try {
                while (rs != null && rs.next()){
                    PromotionDTO promo = new PromotionDTO(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getFloat(4),
                            rs.getFloat(5),
                            rs.getDouble(6),
                            rs.getDate(7).toLocalDate(),
                            rs.getDate(8).toLocalDate()
                    );
                    promos.add(promo);
                }
            } catch(Exception e){
                    e.printStackTrace();
            } finally{
                myconnect.closeConnection();
            }
        }
        return promos;
    }

    // Hàm lấy khuyến mãi ưu đãi nhất
    public PromotionDTO getBestRewardPromotion(float customerPoint) {
        PromotionDTO promo = null;
        
        if (myconnect.openConnection()){
            String sql = "SELECT TOP 1 * " +
                        "FROM promotions " +
                        "WHERE promo_type = N'Đổi điểm' " +
                        "AND points_required <= ? " +
                        "AND CAST(GETDATE() AS DATE) BETWEEN start_date AND end_date " +
                        "AND is_deleted = 0 " +
                        "ORDER BY points_to_money DESC, points_required ASC";

            
            ResultSet rs = myconnect.runPreparedQuery(sql, customerPoint);
            
            try {
                while (rs != null && rs.next()){
                    promo = new PromotionDTO(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getFloat(4),
                            rs.getFloat(5),
                            rs.getDouble(6),
                            rs.getDate(7).toLocalDate(),
                            rs.getDate(8).toLocalDate()
                    );
                }
            } catch(Exception e){
                    e.printStackTrace();
            } finally{
                myconnect.closeConnection();
            }
        }
        return promo;
    }

    
    
}
