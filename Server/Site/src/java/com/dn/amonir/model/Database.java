/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

/**
 *
 * @author Revolt02
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duy
 */
public class Database {

    public static String user = "sa";
    public static String password = "nguyenduyanh";
    public static String url = "jdbc:sqlserver://127.0.0.1;databaseName=KKK;useUnicode=true;characterEncoding=UTF-8;CharacterSet=UTF-8";
    public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static Connection conn = null;

    public static Connection connect() {
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,password);
            return conn;
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet rs, PreparedStatement ps) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (conn != null) {
            try {
                conn.close();
                Database.conn = null;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static int getRows(ResultSet res) {
        int totalRows = 0;
        try {
            res.last();
            totalRows = res.getRow();
            res.beforeFirst();
        } catch (Exception ex) {
            return 0;
        }
        return totalRows;
    }

}
