/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Giai Cuu Li San
 */
public class testconnect {
    
    public static void main(String[] args) {
        try {
            // Load driver SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Chuỗi kết nối
            String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=pharmacy;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "123456";

            // Kết nối với SQL Server
            Connection con = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Kết nối thành công!");

            // Thực thi truy vấn
            Statement stmt1 = con.createStatement();
            ResultSet rs = stmt1.executeQuery("SELECT * FROM category");
            
            while (rs.next()) {
                System.out.println( "ID: " + rs.getString(1) + ", Tên thuốc: " + rs.getString(2) + ", Chú thích: " +rs.getString(3) +" Trạng thái: "+rs.getString(4));
            }
            
//            // Tạo một statement khác để insert
//            Statement stmt2 = con.createStatement();
//            int i = stmt2.executeUpdate("INSERT INTO SINH VALUES ('LAN', 'DCT1215','HOA')");
//
//            // Duyệt dữ liệu
//            while (rs.next()) {
//                System.out.println("ID: " + rs.getString(1) + ", Họ tên: " + rs.getString(2));
//            }
//            System.out.println("Thêm " + i + " dòng vào csdl thành công");

            // Đóng
            rs.close();
            stmt1.close();
//            stmt2.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace(); // Hiển thị lỗi chi tiết
        }
    }
}
