/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.Database;
import com.dn.amonir.model.Device;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

/**
 *
 * @author Duy
 */
public class Network {

    public static void Set(com.dn.amonir.dto.Device device, LinkedHashMap<String, Object> Wifi) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "UPDATE device SET wifi_connected = ?,wifi_name=? WHERE id = ?";
            ps = conn.prepareStatement(sql);

            ps.setBoolean(1, (Boolean) Wifi.get("Connected"));
            ps.setString(2, (String) Wifi.get("Name"));
            ps.setInt(3, device.ID);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

}
