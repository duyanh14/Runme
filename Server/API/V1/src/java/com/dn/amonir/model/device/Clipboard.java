/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.Database;
import com.dn.amonir.model.Device;
import com.dn.amonir.model.Time;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Duy
 */
public class Clipboard {

    public static void Insert(com.dn.amonir.dto.Device device, String content, long time) {

        Connection conn = Database.Connect();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        try {

            sql = "SELECT TOP 1 * FROM device_clipboard WHERE did = ? ORDER BY time_server_create DESC;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, device.ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (content.equals(rs.getString("content"))) {
                    return;
                }
            }

            sql = "INSERT INTO device_clipboard (did, content, time_device_create, time_server_create) VALUES ( ?,?,?,?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, device.ID);
            ps.setString(2, content);

            ps.setLong(3, time);
            ps.setLong(4, Time.Unix.Now());

            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
