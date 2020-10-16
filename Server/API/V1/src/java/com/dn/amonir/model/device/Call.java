/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.API;
import com.dn.amonir.model.Database;
import com.dn.amonir.model.Device;
import com.dn.amonir.model.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Duy
 */
public class Call {
    public static JSONObject Insert(int state, com.dn.amonir.dto.device.Contacts contacts, long stime, long atime, long etime, MultipartFile file, String token) {
            JSONObject jobj = new JSONObject();
            try {
                jobj.put("Status", 1);
                jobj.put("Message", "An unknown error.");

                com.dn.amonir.dto.Device device = Device.Initialization(token);

                if (device == null) {
                    jobj.put("Status", 2);
                    jobj.put("Message", "Could not initialize device.");
                    return jobj;
                }

                Connection conn = Database.Connect();
                String sql = "INSERT INTO [device_call] (cpid,state,start_time,answered_time,end_time,[read],time_device_create,time_server_create) VALUES (  ? , ? , ?, ? , ?, ? , ?, ?);";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, Contacts.Insert(device, contacts));
                ps.setInt(2, state);
                ps.setLong(3, 0);
                ps.setLong(4, 0);
                ps.setLong(5, 0);
                ps.setInt(6, 0);
                ps.setLong(7, stime);
                ps.setLong(8, Time.Unix.Now());

                if ((state == 1) || (state == 2)) {
                    ps.setLong(3, stime);
                    if (state == 2) {
                        ps.setLong(4, atime);
                    }
                    ps.setLong(5, etime);
                } else if (state == 3) {
                    ps.setLong(3, stime);
                }

                if (ps.executeUpdate() == 0) {
                    jobj.put("Status", 3);
                    jobj.put("Message", "Could not initialize call on database system.");
                    return jobj;
                }

                ResultSet rs = ps.getGeneratedKeys();
                int generatedKey = 0;
                if (!rs.next()) {
                    jobj.put("Status", 4);
                    jobj.put("Message", "Could not initialize file on database system.");
                    return jobj;
                }

                try {
                    API.Spring.uploadFile(file, "Call\\" + device.ID, rs.getInt(1) + ".mp3");
                } catch (Exception ex) {
//                    ex.printStackTrace();

                    sql = "DELETE FROM [device_call] WHERE id = ?;";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, rs.getInt(1));
                    ps.executeUpdate();

                    jobj.put("Status", 5);
                    jobj.put("Message", "Could not initialize file on storage.");
                    return jobj;
                }

                jobj.put("Status", 0);
                jobj.remove("Message");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jobj;
        }
}
