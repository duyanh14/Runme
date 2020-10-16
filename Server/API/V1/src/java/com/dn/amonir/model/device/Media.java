/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.API;
import com.dn.amonir.model.Account;
import com.dn.amonir.model.Database;
import com.dn.amonir.model.Device;
import com.dn.amonir.model.Time;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Duy
 */
public class Media {
    public static JSONObject Insert(String path, long time, MultipartFile file, String token) {
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

                String Ftype = FilenameUtils.getExtension(path);

                Connection conn = Database.Connect();
                String sql = "INSERT INTO [device_media] (did,path,name,type,time_device_create,time_server_create) VALUES (  ? , ? , ?, ?,  ?, ?);";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, device.ID);
                ps.setString(2, path);
                ps.setString(3, FilenameUtils.getBaseName(path));
                ps.setString(4, Ftype);
                ps.setLong(5, time);
                ps.setLong(6, Time.Unix.Now());

                if (ps.executeUpdate() == 0) {
                    jobj.put("Status", 3);
                    jobj.put("Message", "Could not initialize file on database system.");
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
                    API.Spring.uploadFile(file, "Media\\" + device.ID, rs.getInt(1) + "." + Ftype);
                } catch (Exception ex) {
//                    ex.printStackTrace();

                    sql = "DELETE FROM [device_media] WHERE id = ?;";
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
                    + "    *\n"
                    + "FROM\n"
                    + "    device_media\n"
                    + "    WHERE\n"
                    + "        did = ?\n"
                    + "    ORDER BY\n"
                    + "       time_device_create  DESC\n"
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

                File file = new File(API.Config.DataPath + "\\Media\\" + device.ID + "\\" + rs.getInt("id") + "." + rs.getString("type"));
                if (!file.exists()) {
                    continue;
                }
                JSONObject record = new JSONObject();
                record.put("ID", rs.getInt("id"));
                record.put("Type", rs.getString("type"));
                record.put("Time", rs.getLong("time_device_create"));

                jobj_l.put(record);
                count++;
            }

            jobj.put("List", jobj_l);

            boolean IsNextPage = false;

            sql = "SELECT\n"
                    + "    *\n"
                    + "FROM\n"
                    + "    device_media\n"
                    + "    WHERE\n"
                    + "        did = ?\n"
                    + "    ORDER BY\n"
                    + "       time_device_create  DESC\n"
                    + "\n"
                    + "OFFSET ? ROWS \n"
                    + "FETCH NEXT ? ROWS ONLY \n"
                    + ";";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, device.ID);
            ps.setInt(2, page * limit);
            ps.setInt(3, limit);
            rs = ps.executeQuery();
            if (rs.next()) {
                IsNextPage = true;
            }

            jobj.put("IsNextPage", IsNextPage);

//            sql = "select (select count(*) from device_messages_sms INNER JOIN device_contacts_number ON device_contacts_number.id = device_messages_sms.cpid INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid  WHERE device_contacts.did = ?) as ts,\n"
//                    + "(select count(*) from device_messages_sms INNER JOIN device_contacts_number ON device_contacts_number.id = device_messages_sms.cpid INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid  WHERE device_messages_sms.state = 1 AND device_contacts.did = ?) as tss,\n"
//                    + "  (select count(*) from device_messages_sms INNER JOIN device_contacts_number ON device_contacts_number.id = device_messages_sms.cpid INNER JOIN device_contacts ON device_contacts.id = device_contacts_number.cid  WHERE device_messages_sms.state = 2 AND device_contacts.did = ?) as  tsr";
//            ps = conn.prepareStatement(sql);
//            ps.setInt(1, device.ID);
//            ps.setInt(2, device.ID);
//            ps.setInt(3, device.ID);
//            rs = ps.executeQuery();
//            rs.next();
//
//            jobj.put("TotalOfSMS", count);
//            jobj.put("TotalOfSMSContent", rs.getInt("ts"));
//            jobj.put("TotalOfSMSContentSent", rs.getInt("tss"));
//            jobj.put("TotalOfSMSContentReceived", rs.getInt("tsr"));
            jobj.put("Status", 0);
            jobj.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jobj;
    }

    public static void View(int id, String token, HttpServletResponse response) {

        JSONObject jobj = new JSONObject();
        try {
            jobj.put("Status", 1);
            jobj.put("Message", "An unknown error.");

            com.dn.amonir.dto.Account account = Account.Initialization(token);

            if (account == null) {
                jobj.put("Status", 2);
                jobj.put("Message", "Could not initialize account.");
                return;
//                return jobj;
            }

            Connection conn = Database.Connect();

            String sql = "SELECT did,type FROM [device_media] INNER JOIN device ON [device_media].did = device.id INNER JOIN account ON device.aid = account.id WHERE account.id = ? AND device_media.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.ID);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                jobj.put("Status", 3);
                jobj.put("Message", "File does not exist in the server.");
                return;
            }

            String type = rs.getString("type");
            String file_path = API.Config.DataPath + "\\Media\\" + rs.getInt("did") + "\\" + id + "." + type;
            java.io.File file = new java.io.File(file_path);

            if (!file.exists()) {
                jobj.put("Status", 4);
                jobj.put("Message", "File does not exist in the server.");
                System.out.println("2343");
                return;
            }

            if (("jpeg".equals(type)) || ("jpg".equals(type)) || ("png".equals(type))) {
                response.setContentType("image/jpeg");
                BufferedImage image = ImageIO.read(file);

                ImageIO.write(resize(image, 500), "JPG", response.getOutputStream());

            } else if ("mp4".equals(type)) {
                response.setContentType("video/mp4");
                ServletOutputStream out = response.getOutputStream();
                FileInputStream fin = new FileInputStream(file);

                byte[] buf = new byte[4096];
                int read;
                while ((read = fin.read(buf)) != -1) {
                    out.write(buf, 0, read);
                }

                fin.close();
                out.flush();
                out.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static BufferedImage resize(BufferedImage src, int targetSize) {
        if (targetSize <= 0) {
            return src; //this can't be resized
        }
        int targetWidth = targetSize;
        int targetHeight = targetSize;
        float ratio = ((float) src.getHeight() / (float) src.getWidth());
        if (ratio <= 1) { //square or landscape-oriented image
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else { //portrait image
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bi;
    }
}
