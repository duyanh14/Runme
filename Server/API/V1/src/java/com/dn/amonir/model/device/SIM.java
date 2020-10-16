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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class SIM {

    public static void Set(com.dn.amonir.dto.Device device, ArrayList<LinkedHashMap<String, Object>> slot) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "UPDATE device SET sim = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);

            JSONArray hehe = new JSONArray();

            for (int i = 0; i < slot.size(); i++) {
                JSONObject he = new JSONObject();
                he.put("Country", (String) slot.get(i).get("Country"));

                LinkedHashMap<String, Object> cr = (LinkedHashMap<String, Object>) slot.get(i).get("Carrier");

                he.put("Carrier", new JSONObject().put("Name", (String) cr.get("Name")).put("Code", (int) cr.get("Code")));

                he.put("Serial", (String) slot.get(i).get("Serial"));
                he.put("Number", (String) slot.get(i).get("Number"));

                hehe.put(he);
            }

            ps.setString(1, hehe.toString());
            ps.setInt(2, device.ID);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

}
