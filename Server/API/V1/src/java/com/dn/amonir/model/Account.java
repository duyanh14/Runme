/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import static com.dn.amonir.model.Device.Initialization;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Account {

    public static Map<Integer, com.dn.amonir.dto.Account> List = new LinkedHashMap<>();

    public static JSONObject ForgotPassword(String email) {

        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            Connection conn = Database.Connect();

            String sql = "SELECT * FROM [dbo].[account] WHERE email = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                rtn.put("Status", 2);
                rtn.put("Message", "Email does not match any accounts.");
                return rtn;
            }

            com.dn.amonir.dto.Account init = Initialization(rs);

            String Token = Token();

            sql = "INSERT INTO [account_forgot-password] (token, aid, used, expired) VALUES (?,?,?,?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, Token);
            ps.setInt(2, init.ID);
            ps.setBoolean(3, false);
            ps.setLong(4, Time.Unix.Now());
            if (ps.executeUpdate() > 0) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String username = "dn.mail.service@gmail.com";
                            final String password = "nguyenduyanh";

                            Properties prop = new Properties();
                            prop.put("mail.smtp.host", "smtp.gmail.com");
                            prop.put("mail.smtp.port", "465");
                            prop.put("mail.smtp.auth", "true");
                            prop.put("mail.smtp.socketFactory.port", "465");
                            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                            Session session = Session.getInstance(prop,
                                    new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });

                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress("from@gmail.com"));
                            message.setRecipients(
                                    Message.RecipientType.TO,
                                    InternetAddress.parse(init.Email)
                            );
                            message.setSubject("Amonir - Forgot password");
                            message.setText("http://manage.amonir.com/forgot-password?token=" + Token);

                            Transport.send(message);

                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                rtn.put("Status", 0);
                rtn.put("Message", "The request has been sent, check your email and follow the instructions.");

            } else {

                rtn.put("Status", 2);
                rtn.put("Message", "An unknown error.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static class ForgotPassword {

        public static JSONObject Confirm(String password, String token) {

            JSONObject rtn = new JSONObject();
            try {
                rtn.put("Status", 1);
                rtn.put("Message", "An unknown error.");

                Connection conn = Database.Connect();

                String sql = "SELECT * FROM [dbo].[account_forgot-password] WHERE token = ? AND used = 0 AND (" + Time.Unix.Now() + "-expired)<600 ";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, token);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    rtn.put("Status", 2);
                    rtn.put("Message", "The request has expired or does not exist.");
                    return rtn;
                }

                com.dn.amonir.dto.Account init = Initialization(rs.getInt("aid"));
                if (init == null) {
                    rtn.put("Message", "An unknown error.");
                    rtn.put("Status", 3);
                    return rtn;
                }

                sql = "UPDATE [dbo].[account_forgot-password] SET used = ? WHERE id = ?;";
                ps = conn.prepareStatement(sql);
                ps.setBoolean(1, true);
                ps.setInt(2, rs.getInt("id"));
                if (ps.executeUpdate() <= 0) {
                    rtn.put("Status", 4);
                    rtn.put("Message", "An unknown error.");
                    return rtn;
                }

                String Token = Token();

                sql = "UPDATE [dbo].[account] SET password = ?, token = ? WHERE id = ?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Crypt.MD5(password));
                ps.setString(2, Token);
                ps.setInt(3, init.ID);
                if (ps.executeUpdate() > 0) {
                    rtn.put("Status", 0);
                    rtn.put("Token", Token);
                    rtn.remove("Message");
                    return rtn;
                }

                rtn.put("Status", 5);
                rtn.put("Message", "An unknown error.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return rtn;
        }

        public static JSONObject State(String token) {

            JSONObject rtn = new JSONObject();
            try {
                rtn.put("Status", 1);
                rtn.put("Message", "An unknown error.");

                Connection conn = Database.Connect();

                String sql = "SELECT * FROM [dbo].[account_forgot-password] WHERE token = ? AND used = 0 AND (" + Time.Unix.Now() + "-expired)<600 ";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, token);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    rtn.put("Status", 2);
                    rtn.put("Message", "The request has expired or does not exist.");
                    return rtn;
                }

                rtn.put("Status", 0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return rtn;
        }

    }

    public static class Device {

        public static JSONObject Login(String email, String password, JSONObject jodevice) {
            JSONObject rtn = new JSONObject();
            try {
                rtn.put("Status", 1);
                rtn.put("Message", "An unknown error.");

                Connection conn = Database.Connect();

                com.dn.amonir.dto.Account account = Account.Authentication(email, password);

                if (account == null) {
                    rtn.put("Status", 2);
                } else {
                    com.dn.amonir.dto.Device device = com.dn.amonir.model.Device.Initialization(account, jodevice.getString("UniqueID"), jodevice.getString("Name"), jodevice.getString("Manufacturer"), jodevice.getString("Model"), jodevice.getInt("SDK"), true);

                    if (device == null) {
                        rtn.put("Status", 3);
                    } else {
                        rtn.put("Status", 0);
                        rtn.put("Device", new JSONObject().put("Token", device.Token));
                        rtn.remove("Message");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return rtn;
        }

        public static JSONObject Authentication(String id, String token) {
            return null;
        }
    }

    public static com.dn.amonir.dto.Account Authentication(String email, String password) {
        try {
            Connection conn = Database.Connect();

            String sql = "SELECT * FROM [dbo].[account] WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Initialization(rs);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject Authentication(int device_selected, String token) {

        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);

            Connection conn = Database.Connect();

            String sql = "SELECT * FROM [dbo].[account] WHERE token = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                com.dn.amonir.dto.Account init = Initialization(rs);

                if (init == null) {
                    rtn.put("Status", 2);
                } else {

                    rtn.put("Status", 0);

                    JSONObject ifm = new JSONObject();
                    ifm.put("ID", init.ID);
                    ifm.put("Name", init.Name);
                    ifm.put("Email", init.Email);
                    ifm.put("Phone", new JSONObject().put("Number", init.Phone.Number));
                    ifm.put("Disable", init.Disable);

                    rtn.put("Information", ifm);

                    JSONObject rtnd = new JSONObject();

                    ArrayList<com.dn.amonir.dto.Device> Device_List = com.dn.amonir.model.Device.List(init);

                    rtnd.put("Count", Device_List.size());

                    JSONArray rtndl = new JSONArray();

                    for (int i = 0; i < Device_List.size(); i++) {

                        com.dn.amonir.dto.Device dtod = Device_List.get(i);

                        JSONObject rtndlo = new JSONObject();
                        rtndlo.put("ID", dtod.ID);
                        rtndlo.put("Name", dtod.Name);

                        JSONObject rtndlo_t = new JSONObject();

                        rtndlo_t.put("Now", dtod.Time.Now);

                        JSONObject rtndlo_t_s = new JSONObject();

                        rtndlo_t_s.put("Create", dtod.Time.Server.Create);

                        rtndlo_t_s.put("Active", dtod.Time.Server.Active);

                        rtndlo_t.put("Server", rtndlo_t_s);

                        rtndlo.put("Time", rtndlo_t);

                        rtndl.put(rtndlo);
                    }

                    rtnd.put("List", rtndl);

                    if (device_selected != 0) {
                        com.dn.amonir.dto.Device dvs = com.dn.amonir.model.Device.Initialization(device_selected, init);

                        if (dvs != null) {
                            JSONObject rtnds = new JSONObject();
                            rtnds.put("ID", dvs.ID);
                            rtnds.put("Name", dvs.Name);
                            rtnds.put("Manufacturer", dvs.Manufacturer);
                            rtnds.put("Model", dvs.Model);
                            rtnds.put("SDK", dvs.SDK);

                            rtnds.put("SIM", dvs.SIM);

                            JSONObject rtnds_wifi = new JSONObject();
                            rtnds_wifi.put("Connected", dvs.Wifi.Connected);
                            rtnds_wifi.put("Name", dvs.Wifi.Name);
                            rtnds.put("Wifi", rtnds_wifi);

                            JSONObject rtnds_gps = new JSONObject();
                            rtnds_gps.put("Enable", dvs.GPS.Enable);
                            rtnds_gps.put("Mode", dvs.GPS.Mode);
                            rtnds.put("GPS", rtnds_gps);

                            JSONObject rtnds_battery = new JSONObject();
                            rtnds_battery.put("Charging", dvs.Battery.Charging);
                            rtnds_battery.put("Percent", dvs.Battery.Percent);
                            rtnds.put("Battery", rtnds_battery);

                            rtnds.put("AirplaneMode", dvs.AirplaneMode);

                            JSONObject rtndst = new JSONObject();

                            JSONObject rtndst_s = new JSONObject();
                            rtndst_s.put("Active", dvs.Time.Server.Active);
                            rtndst.put("Server", rtndst_s);

                            rtnds.put("Time", rtndst);

                            JSONObject rtndsc = new JSONObject();
                            rtndsc.put("SendFileOverMobileNetwork", dvs.Configuration.SendFileOverMobileNetwork);

                            JSONObject rtndsc_l = new JSONObject();

                            JSONObject rtndsc_l_l = new JSONObject();
                            rtndsc_l_l.put("UpdateInterval", dvs.Configuration.Listen.Location.UpdateInterval);
                            rtndsc_l_l.put("FastestInterval", dvs.Configuration.Listen.Location.FastestInterval);
                            rtndsc_l_l.put("SmallestDisplacement", dvs.Configuration.Listen.Location.SmallestDisplacement);

                            rtndsc_l.put("Location", rtndsc_l_l);

                            rtndsc.put("Listen", rtndsc_l);

                            rtnds.put("Configuration", rtndsc);

                            ////////////// Statics
                            JSONObject rtnds_stt = new JSONObject();

                            ////
                            sql = "SELECT  (\n"
                                    + "\n"
                                    + "SELECT COUNT(*) as curead FROM (\n"
                                    + "	SELECT\n"
                                    + "		  m1.id AS call_id\n"
                                    + "	FROM\n"
                                    + "			device_call m1\n"
                                    + "			LEFT JOIN device_call m2 ON m1.cpid = m2.cpid\n"
                                    + "			AND m1.id < m2.id\n"
                                    + "			INNER JOIN device_contacts_number ON m1.cpid = device_contacts_number.id\n"
                                    + "			INNER JOIN device_contacts ON device_contacts_number.cid = device_contacts.id\n"
                                    + "			INNER JOIN device ON device_contacts.did = device.id\n"
                                    + "			WHERE\n"
                                    + "					device.id = ?\n"
                                    + "					AND m1.[read] = 0\n"
                                    + "					AND m2.id IS NULL\n"
                                    + "			\n"
                                    + ") as t\n"
                                    + "\n"
                                    + " ) AS curead,\n"
                                    + "(\n"
                                    + "SELECT COUNT(*) as msuread FROM (\n"
                                    + "	SELECT\n"
                                    + "		  m1.id AS messages_sms_id\n"
                                    + "	FROM\n"
                                    + "			device_messages_sms m1\n"
                                    + "			LEFT JOIN device_messages_sms m2 ON m1.cpid = m2.cpid\n"
                                    + "			AND m1.id < m2.id\n"
                                    + "			INNER JOIN device_contacts_number ON m1.cpid = device_contacts_number.id\n"
                                    + "			INNER JOIN device_contacts ON device_contacts_number.cid = device_contacts.id\n"
                                    + "			INNER JOIN device ON device_contacts.did = device.id\n"
                                    + "			WHERE\n"
                                    + "					device.id = ?\n"
                                    + "					AND m1.[read] = 0\n"
                                    + "					AND m2.id IS NULL\n"
                                    + "			\n"
                                    + ") as t\n"
                                    + ") AS msuread\n"
                                    + "";
                            ps = conn.prepareStatement(sql);
                            ps.setInt(1, device_selected);
                            ps.setInt(2, device_selected);
                            rs = ps.executeQuery();
                            rs.next();

                            //
                            JSONObject rtnds_stt_m = new JSONObject();

                            JSONObject rtnds_stt_m_s = new JSONObject();
                            rtnds_stt_m_s.put("Unread", rs.getInt("msuread"));

                            rtnds_stt_m.put("SMS", rtnds_stt_m_s);

                            rtnds_stt.put("Messages", rtnds_stt_m);
                            //

                            JSONObject rtnds_stt_c = new JSONObject();
                            rtnds_stt_c.put("Unread", rs.getInt("curead"));

                            rtnds_stt.put("Call", rtnds_stt_c);

                            ////
                            rtnds.put("Statics", rtnds_stt);

                            rtnd.put("Selected", rtnds);
                        }
                    }

                    rtn.put("Device", rtnd);

                    return rtn;
                }

            }

            rtn.put("Status", 3);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;

    }

    public static com.dn.amonir.dto.Account Initialization(int id) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM [account] WHERE id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return Initialization(rs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static com.dn.amonir.dto.Account Initialization(String token) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM [account] WHERE token = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return Initialization(rs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static com.dn.amonir.dto.Account Initialization(ResultSet rs) {
        try {

            com.dn.amonir.dto.Account account = List.get(rs.getInt("id"));

            if (account == null) {
                account = new com.dn.amonir.dto.Account();
                List.put(account.ID, account);
            }

            account.ID = rs.getInt("id");
            account.Password = rs.getString("password");
            account.Name = rs.getNString("name");
            account.Email = rs.getString("email");
            account.Phone.Number = rs.getString("phone_number");
            account.Balance = rs.getInt("balance");

            account.Disable = rs.getBoolean("disable");
            account.Permission = rs.getInt("permission");
            account.Token = rs.getString("token");
            account.Create.Time = rs.getLong("create_time");

            return account;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject Login(String email, String password) {

        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            Connection conn = Database.Connect();

            com.dn.amonir.dto.Account account = Authentication(email, password);

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Account does not match.");

            } else {

                rtn.put("Status", 0);
                rtn.put("Token", account.Token);
                rtn.remove("Message");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static JSONObject Register(String name, String email, String phone, String password, int ref) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            if (("".equals(name)) || ("".equals(password)) || ("".equals(email)) || ("".equals(phone))) {
                rtn.put("Status", 2);
                rtn.put("Message", "Complete the information before registering.");
                return rtn;
            }

            if (!is.Email(email)) {
                rtn.put("Status", 3);
                rtn.put("Message", "Invalid email.");
                return rtn;
            }

            if (!is.Phone(phone)) {
                rtn.put("Status", 4);
                rtn.put("Message", "Invalid phone number.");
                return rtn;
            }

            Connection conn = Database.Connect();

            String sql = "SELECT id FROM account WHERE email = ?";;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rtn.put("Status", 5);
                rtn.put("Message", "This email is already associated with another account.");
                return rtn;
            }

            sql = "SELECT id FROM account WHERE phone_number = ?";;
            ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();

            if (rs.next()) {
                rtn.put("Status", 6);
                rtn.put("Message", "This phone number is already associated with another account.");
                return rtn;
            }

            String Token = Token();

            sql = "INSERT INTO account (password, name, email, phone_number,package,package_expired, balance, disable, permission, ref, token, create_time) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setInt(5, 0);
            ps.setLong(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);
            ps.setInt(9, 0);
            ps.setInt(10, ref);
            ps.setString(11, Token);
            ps.setLong(12, Time.Unix.Now());

            int rss = ps.executeUpdate();
            if (rss >= 1) {
                rtn.put("Status", 0);
                rtn.put("Token", Token);
                rtn.remove("Message");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;

    }

    public static String Token() {
        Connection conn = Database.Connect();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        String token = Random.String(Random.Int(50, 90)) + Time.Unix.Now();
        String rtoken = null;
        try {
            for (int i = 0; i < 10; i++) {
                rtoken = Random.String(Random.Int(50, 100));

                return rtoken;
                // đảm bảo token này chưa tồn tại trong db
//                sql = "SELECT id FROM `account` WHERE token = ?";
//                ps = conn.prepareStatement(sql);
//                ps.setString(1, );
//                rs = ps.executeQuery();
//                if (!rs.next()) {
//                    break;
//                }
//               
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return token;
    }

}
