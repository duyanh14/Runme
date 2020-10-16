/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Duy
 */
public class Time {

    public static void Set(com.dn.amonir.dto.Device device, long now) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "UPDATE device SET [time_now]=? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, now);
            ps.setInt(2, device.ID);
            ps.executeUpdate();
        } catch (Exception ex) {

        }
    }
}
