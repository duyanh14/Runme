/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device.messages;

import com.dn.amonir.model.Account;
import com.dn.amonir.model.Database;
import com.dn.amonir.model.Device;
import com.dn.amonir.model.Time;
import com.dn.amonir.model.device.Contacts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class SMS {

    public static void main(String[] args) {
        long aa = 1582088161879l;

        System.out.println(aa/1000);
    }

    public static void Insert(com.dn.amonir.dto.Device device, int state, String content, long time, com.dn.amonir.dto.device.Contacts contacts) {
        Connection conn = Database.Connect();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        try {
            sql = "INSERT INTO device_messages_sms (cpid, state, content, [read], time_device_create, time_server_create) VALUES (  ? , ? , ?, 0 , ? , ?);";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Contacts.Insert(device, contacts));
            ps.setInt(2, state);
            ps.setString(3, content);
            ps.setLong(4, time);
            ps.setLong(5, Time.Unix.Now());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static JSONObject Get(int did, int page, int limit, String token) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("Status", 1);
            jobj.put("Message", "An unknown error.");

            com.dn.amonir.dto.Device device = Device.Initialization(did, Account.Initialization(token));

            if (device == null) {
                jobj.put("Status", 2);
                jobj.put("Message", "Could not initialize device.");
                return jobj;
            }

            Connection conn = Database.Connect();
            String sql = "SELECT\n"
                    + "    m1.id AS messages_sms_id,\n"
                    + "    m1.content AS messages_sms_content,\n"
                    + "    m1.state AS messages_sms_state,\n"
                    + "    m1.time_device_create AS messages_sms_time_device_create,\n"
                    + "    m1.[read] AS messages_sms_read,\n"
                    + "    device_contacts.id AS cid,\n"
                    + "    device_contacts_number.value AS cp_phone,\n"
                    + "    device_contacts.name AS c_name\n"
                    + "FROM\n"
                    + "    device_messages_sms m1\n"
                    + "    LEFT JOIN device_messages_sms m2 ON m1.cpid = m2.cpid\n"
                    + "    AND m1.id < m2.id\n"
                    + "    INNER JOIN device_contacts_number ON m1.cpid = device_contacts_number.id\n"
                    + "    INNER JOIN device_contacts ON device_contacts_number.cid = device_contacts.id\n"
                    + "    INNER JOIN device ON device_contacts.did = device.id\n"
                    + "    WHERE\n"
                    + "        device.id = ?\n"
                    + "        AND m2.id IS NULL\n"
                    + " \n"
                    + "    ORDER BY\n"
                    + "        m1.time_device_create DESC\n"
                    + "\n"
                    + "OFFSET ? ROWS \n"
                    + "FETCH NEXT ? ROWS ONLY \n"
                    + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, device.ID);
            ps.setInt(2, (page - 1) * limit);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();

            JSONArray jobj_l = new JSONArray();
            int count = 0;
            while (rs.next()) {

                JSONObject record = new JSONObject();
                record.put("ID", rs.getInt("messages_sms_id"));
                record.put("State", rs.getString("messages_sms_state"));
                record.put("Content", rs.getString("messages_sms_content"));
                record.put("Read", rs.getBoolean("messages_sms_read"));
                record.put("Time", rs.getLong("messages_sms_time_device_create"));

                JSONObject contacts = new JSONObject();
                contacts.put("ID", rs.getInt("cid"));
                contacts.put("Name", rs.getString("c_name"));

                JSONObject contacts_by = new JSONObject();
                contacts_by.put("Value", rs.getString("cp_phone"));
                contacts.put("By", contacts_by);
                record.put("Contacts", contacts);

                jobj_l.put(record);
                count++;
            }

            jobj.put("List", jobj_l);

            sql = "select (select count(*) from device_messages_sms INNER JOIN device_contacts_number ON device_contacts_number.id = device_messages_sms.cpid INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid  WHERE device_contacts.did = ?) as ts,\n"
                    + "(select count(*) from device_messages_sms INNER JOIN device_contacts_number ON device_contacts_number.id = device_messages_sms.cpid INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid  WHERE device_messages_sms.state = 1 AND device_contacts.did = ?) as tss,\n"
                    + "  (select count(*) from device_messages_sms INNER JOIN device_contacts_number ON device_contacts_number.id = device_messages_sms.cpid INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid  WHERE device_messages_sms.state = 2 AND device_contacts.did = ?) as  tsr";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, device.ID);
            ps.setInt(2, device.ID);
            ps.setInt(3, device.ID);
            rs = ps.executeQuery();
            rs.next();

            jobj.put("TotalOfSMS", count);
            jobj.put("TotalOfSMSContent", rs.getInt("ts"));
            jobj.put("TotalOfSMSContentSent", rs.getInt("tss"));
            jobj.put("TotalOfSMSContentReceived", rs.getInt("tsr"));

            jobj.put("Status", 0);
            jobj.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jobj;
    }

    public static JSONObject Details(int id, String token) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("Status", 1);
            jobj.put("Message", "An unknown error.");

            com.dn.amonir.dto.Account account = Account.Initialization(token);

            if (account == null) {
                jobj.put("Status", 2);
                jobj.put("Message", "Could not initialize account.");
                return jobj;
            }

            Connection conn = Database.Connect();

            String sql = "SELECT device_contacts.* FROM device_contacts INNER JOIN device ON device.id = device_contacts.did INNER JOIN account ON account.id = device.aid WHERE device_contacts.id = ? AND account.id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, account.ID);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                jobj.put("Status", 3);
                jobj.put("Message", "Could not validate contacts.");
                return jobj;
            }

            sql = "SELECT device_messages_sms.id as messages_id, device_messages_sms.state as messages_state, device_messages_sms.content as messages_content, device_messages_sms.time_device_create as  messages_deviceTime FROM device_messages_sms \n"
                    + "INNER JOIN device_contacts_number ON device_messages_sms.cpid = device_contacts_number.id \n"
                    + "INNER JOIN device_contacts ON device_contacts_number.cid = device_contacts.id\n"
                    + "INNER JOIN device ON device_contacts.did = device.id\n"
                    + "INNER JOIN account ON device.aid = account.id\n"
                    + "WHERE device_contacts.id = ? \n"
                    + "ORDER BY device_messages_sms.time_device_create DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            //////
            sql = "UPDATE device_messages_sms SET device_messages_sms.[read] = 1 FROM \n"
                    + "    device_messages_sms c\n"
                    + "    INNER JOIN device_contacts_number t\n"
                    + "        ON c.cpid = t.id INNER JOIN device_contacts ON device_contacts.id = t.cid WHERE device_contacts.id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            //////

            JSONArray list = new JSONArray();
            while (rs.next()) {

                JSONObject record = new JSONObject();

                record.put("ID", rs.getInt("messages_id"));
                record.put("State", rs.getString("messages_state"));
                record.put("Content", rs.getString("messages_content"));
                record.put("Time", rs.getLong("messages_deviceTime"));
                list.put(record);

            }

            jobj.put("List", list);

//            sql = "SELECT device_contacts_email.* FROM device_contacts_email INNER JOIN device_contacts ON device_contacts.id = device_contacts_email.cid INNER JOIN device ON device.id = device_contacts.did WHERE device_contacts.id = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setInt(1, id);
//
//            rs = ps.executeQuery();
//
//            list = new JSONArray();
//            while (rs.next()) {
//                JSONObject email = new JSONObject();
//                email.put("Value", rs.getString("value"));
//                email.put("Type", rs.getInt("type"));
//                list.put(email);
//            }
//            if (list.length() > 0) {
//                jobj.put("Email", list);
//            }
            jobj.put("Status", 0);
            jobj.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jobj;
    }
}
