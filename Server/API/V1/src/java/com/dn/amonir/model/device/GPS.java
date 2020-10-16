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

/**
 *
 * @author Duy
 */
public class GPS {

    public static void Set(com.dn.amonir.dto.Device device, boolean enable, int mode) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "UPDATE device SET gps_enable=?,gps_mode=? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, (enable) ? 1 : 0);
            ps.setInt(2, mode);
            ps.setInt(3, device.ID);
            ps.executeUpdate();
        } catch (Exception ex) {

        }
    }
    
}
