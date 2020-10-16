/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.Account;
import com.dn.amonir.model.Database;
import com.dn.amonir.model.Device;
import com.dn.amonir.model.Time;
import com.dn.amonir.model.is;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Contacts {

    public static JSONObject Get(int did, String token) {
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
            String sql = "SELECT id, name FROM [device_contacts]  WHERE did = ? AND dcid !=0 ORDER BY name ASC;;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, device.ID);

            ResultSet rs = ps.executeQuery();

            JSONArray jobj_l = new JSONArray();
            int count = 0;
            while (rs.next()) {
                jobj_l.put(new JSONObject().put("ID", rs.getInt("id")).put("Name", rs.getString("name")));
                count++;
            }

            jobj.put("List", jobj_l);

            sql = "select (select count(*) from device_contacts WHERE device_contacts.did = ?) as dc,\n"
                    + "(select count(*) from device_contacts_number INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid WHERE device_contacts.did = ?) as dcn,\n"
                    + "  (select count(*) from device_contacts_email INNER JOIN device_contacts ON device_contacts.id = device_contacts_email.cid WHERE device_contacts.did = ?) as dce ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, device.ID);
            ps.setInt(2, device.ID);
            ps.setInt(3, device.ID);
            rs = ps.executeQuery();
            rs.next();

            jobj.put("TotalOfContacts", rs.getInt("dc"));
            jobj.put("TotalOfContactsNumber", rs.getInt("dcn"));
            jobj.put("TotalOfContactsEmail", rs.getInt("dce"));

            jobj.put("Status", 0);
            jobj.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jobj;
    }

    public static int Insert(com.dn.amonir.dto.Device device, com.dn.amonir.dto.device.Contacts contacts) {
        Connection conn = Database.Connect();
        PreparedStatement ps;
        ResultSet rs;
        String sql;

        int cid = 0;
        int cpid = 0;
        int ceid = 0;

        try {

            if ((contacts.By.Type == 1) && (contacts.By.Value != null)) {
                sql = "SELECT device_contacts_number.id as id FROM device_contacts_number INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid where ([device_contacts_number].[value] = ? OR [device_contacts_number].[value] = ?) and device_contacts.did = ?;";
                ps = conn.prepareStatement(sql);
                if (contacts.By.Value == null) {
                    ps.setString(1, contacts.Number.get(0).Value);
                    ps.setString(2, (contacts.Number.get(0).Value.substring(0, 3).equals("+84")) ? "0" + contacts.Number.get(0).Value.substring(3) : "+84" + contacts.Number.get(0).Value.substring(1));
                } else {
                    ps.setString(1, contacts.By.Value);
                    ps.setString(2, (contacts.By.Value.substring(0, 3).equals("+84")) ? "0" + contacts.By.Value.substring(3) : "+84" + contacts.By.Value.substring(1));
                }
                ps.setInt(3, device.ID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    cpid = rs.getInt("id");
                }
            }

            if ((contacts.ID == 0) && (cpid == 0) && ((contacts.By.Type == 1) && (contacts.By.Value != null))) {
//                System.out.println("2222");

                sql = "INSERT INTO device_contacts (did,dcid,name,time_server_create) VALUES ( ? , 0 , '',?);";
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, device.ID);
                ps.setLong(2, Time.Unix.Now());
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    sql = "INSERT INTO device_contacts_number (cid,value,type) VALUES ( ? , ? , 0);";
                    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, rs.getInt(1));
                    ps.setString(2, contacts.By.Value);
                    ps.executeUpdate();
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        cpid = rs.getInt(1);
                    }
                }
            } else if (contacts.ID != 0) {

//                System.out.println(cpid);
//                System.out.println(contacts.By.Value);
//                                System.out.println(contacts.Number.get(0).Value);
//
//                System.out.println(contacts.ID);
//                System.out.println("1111");
//                
                sql = "SELECT device_contacts.id as id FROM [dbo].[device_contacts_number] INNER JOIN [dbo].[device_contacts] ON [dbo].[device_contacts_number].cid = device_contacts.id WHERE device_contacts.dcid = 0 AND device_contacts_number.id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, cpid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    sql = "UPDATE device_contacts SET dcid=? WHERE id=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, contacts.ID);
                    ps.setInt(2, rs.getInt("id"));
                    ps.executeUpdate();
                }

                sql = "SELECT id FROM device_contacts WHERE did= ? AND dcid= ?;";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, device.ID);
                ps.setInt(2, contacts.ID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    cid = rs.getInt("id");
                    sql = "UPDATE device_contacts SET name=? WHERE id=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, contacts.Name);
                    ps.setInt(2, cid);
                    ps.executeUpdate();

                } else {
                    sql = "INSERT INTO device_contacts (did,dcid,name,time_server_create) VALUES ( ? , ? , ?, ?);";
                    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, device.ID);
                    ps.setInt(2, contacts.ID);
                    ps.setString(3, contacts.Name);
                    ps.setLong(4, Time.Unix.Now());

                    ps.executeUpdate();

                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        cid = rs.getInt(1);
                    }
                }

                if (cid != 0) {
                    try {

                        if (contacts.Number != null) {
                            for (int i = 0; i < contacts.Number.size(); i++) {

                                com.dn.amonir.dto.device.Contacts.Number OPhoneNumber = contacts.Number.get(i);

                                sql = "SELECT device_contacts_number.id as id, device_contacts_number.cid as cid, device_contacts.dcid as dcid, device_contacts_number.type as type FROM device_contacts_number INNER JOIN device_contacts ON device_contacts_number.cid = device_contacts.id WHERE device_contacts_number.value= ? AND device_contacts.dcid = ? AND device_contacts.did= ?;";
                                ps = conn.prepareStatement(sql);
                                ps.setString(1, OPhoneNumber.Value);
                                ps.setInt(2, contacts.ID);
                                ps.setInt(3, device.ID);
                                rs = ps.executeQuery();
                                if (rs.next()) {

                                    sql = "UPDATE device_contacts_number SET type = ? WHERE id=?";
                                    ps = conn.prepareStatement(sql);
                                    ps.setInt(1, OPhoneNumber.Type);
                                    ps.setInt(2, rs.getInt("id"));
                                    ps.executeUpdate();

                                    if (contacts.ID == rs.getInt("dcid")) {
                                        continue;
                                    } else {

                                        if (((contacts.By.Type == 1) && (contacts.By.Value != null)) && (OPhoneNumber.Value.equals(contacts.By.Value))) {
                                            cpid = rs.getInt("id");
                                        }

                                        sql = "UPDATE device_contacts_number SET cid=? WHERE id=?";
                                        ps = conn.prepareStatement(sql);
                                        ps.setInt(1, cid);
                                        ps.setInt(2, rs.getInt("id"));
                                        ps.executeUpdate();

                                        sql = "DELETE FROM device_contacts WHERE contacts.id = ? AND dcid = 0";
                                        ps = conn.prepareStatement(sql);
                                        ps.setInt(1, rs.getInt("cid"));
                                        ps.executeUpdate();

                                    }
                                } else {
                                    sql = "INSERT INTO device_contacts_number (cid,value, type) VALUES ( ? , ?, ?);";
                                    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                    ps.setInt(1, cid);
                                    ps.setString(2, OPhoneNumber.Value);
                                    ps.setInt(3, OPhoneNumber.Type);
                                    ps.executeUpdate();
                                    rs = ps.getGeneratedKeys();
                                    if (rs.next()) {
                                        if (((contacts.By.Type == 1) && (contacts.By.Value != null)) && (OPhoneNumber.Value.equals(contacts.By.Value))) {
                                            cpid = rs.getInt(1);
                                        }
                                    }
                                }

                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        if (contacts.Email != null) {
                            for (int i = 0; i < contacts.Email.size(); i++) {
                                com.dn.amonir.dto.device.Contacts.Email OEmail = contacts.Email.get(i);

                                if (!is.Email(OEmail.Value)) {
                                    continue;
                                }

                                sql = "SELECT device_contacts.id FROM device_contacts_email INNER JOIN device_contacts ON device_contacts_email.cid = device_contacts.id WHERE device_contacts_email.value= ?  AND device_contacts.dcid = ? AND device_contacts.did= ?;";
                                ps = conn.prepareStatement(sql);
                                ps.setString(1, OEmail.Value);
                                ps.setInt(2, contacts.ID);
                                ps.setInt(3, device.ID);
                                rs = ps.executeQuery();
                                if (rs.next()) {

                                    sql = "UPDATE device_contacts_email SET type = ? WHERE id=?";
                                    ps = conn.prepareStatement(sql);
                                    ps.setInt(1, OEmail.Type);
                                    ps.setInt(2, rs.getInt("id"));
                                    ps.executeUpdate();

                                    continue;
                                }

                                sql = "INSERT INTO device_contacts_email (cid,value,type) VALUES ( ? , ?, ?);";
                                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                ps.setInt(1, cid);
                                ps.setString(2, OEmail.Value);
                                ps.setInt(3, OEmail.Type);
                                ps.executeUpdate();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            return cpid;
        }
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

            jobj.put("Name", rs.getString("name"));

            sql = "SELECT device_contacts_number.* FROM device_contacts_number INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid INNER JOIN device ON device.id = device_contacts.did WHERE device_contacts.id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            JSONArray list = new JSONArray();
            while (rs.next()) {
                JSONObject phone = new JSONObject();
                phone.put("Value", rs.getString("value"));
                phone.put("Type", rs.getInt("type"));
                list.put(phone);
            }
            if (list.length() > 0) {
                jobj.put("Number", list);
            }

            sql = "SELECT device_contacts_email.* FROM device_contacts_email INNER JOIN device_contacts ON device_contacts.id = device_contacts_email.cid INNER JOIN device ON device.id = device_contacts.did WHERE device_contacts.id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            list = new JSONArray();
            while (rs.next()) {
                JSONObject email = new JSONObject();
                email.put("Value", rs.getString("value"));
                email.put("Type", rs.getInt("type"));
                list.put(email);
            }
            if (list.length() > 0) {
                jobj.put("Email", list);
            }

            jobj.put("Status", 0);
            jobj.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jobj;
    }

}
