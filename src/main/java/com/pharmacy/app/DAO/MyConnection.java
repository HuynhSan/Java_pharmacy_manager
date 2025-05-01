/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Giai Cuu Li San
 */
public class MyConnection {
    Connection con;
    Statement stm;
    PreparedStatement prestm;

    public boolean openConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pharmacy;trustServerCertificate=true";
            String user = "sa";
            String password = "123456";
            con = DriverManager.getConnection(url, user, password);

            stm = con.createStatement();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet runQuery(String st) {
        try {
            return stm.executeQuery(st);
        } catch (Exception e) {
            return null;
        }
    }
    
    public int RunUpdate(String st){
        try {
            return stm.executeUpdate(st);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public ResultSet runPreparedQuery(String sql, Object... params) {
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]); // Gán từng tham số vào vị trí ?
            }

            return pstmt.executeQuery(); // Chỉ dùng cho SELECT
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet prepareQuery(String sql, Object... params) {
        try {
            PreparedStatement prestm = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                prestm.setObject(i + 1, params[i]);
            }
            return prestm.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int prepareUpdate(String sql, Object...params) {
        try {
            PreparedStatement prestm= con.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                prestm.setObject(i+1, params[i]);
            }
            return prestm.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
