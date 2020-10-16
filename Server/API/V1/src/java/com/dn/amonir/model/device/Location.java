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
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Location {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                PreparedStatement ps;
                String sql;
                try {
                    String title = null;
                    try {
                        String url = "https://www.google.com/maps/place/" + URLEncoder.encode("21.019117, 105.775844", "UTF-8");
                        URL obj = new URL(url);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");
                        con.connect();
                        int responseCode = con.getResponseCode();
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream(), "UTF-8"));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        System.out.println(response.toString());
                        String line = StringUtils.substringBetween(response.toString(), "<head>", "</head>");
                        String pattern = "(?<= content=\\\")[^\\\"]*(?=\\\" property=\\\"og:description\\\")";
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(line);
                        if ((m.find()) && (m.group(0).indexOf("Find local businesses, view maps and get driving directions in Google Maps.") == -1)) {
                            title = m.group(0);
                        } else {
                            try {
                                line = StringUtils.substringBetween(response.toString(), "\"E\",\"", "\",\"https://lh5.googleusercontent.");
                                if (line == null) {
                                    try {
                                        line = StringUtils.substringBetween(response.toString(), "window.APP_INITIALIZATION_STATE", "];");
                                        line = StringUtils.substringBetween(line + "hjeheheheheh2354324324", "Starred places", "hjeheheheheh2354324324");
                                        String[] xxx = StringUtils.split(line, "\\\"");
                                        title = xxx[9];
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    title = line;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (title == null) {
                        title = null;
                    } else if ("Asia".equals(title)) {
                        title = null;
                    } else if ("Find Local Businesses, View Maps And Get Driving Directions In Google Maps.".equals(title)) {
                    } else {
                        title = WordUtils.capitalizeFully(title);
                        title = title.replace("Unnamed Road, ", "");
                        title = title.replace("Hanoi", "Hà Nội");
                        title = title.replace("Hà Nội 100000", "Hà Nội");
                        title = title.replace("Bac Ninh Province", "Bắc Ninh");
                        title = title.replace(" District", "");
                        title = title.replace("Bac Giang", "Bắc Giang");
                    }

                    System.out.println(title);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public static void Insert(com.dn.amonir.dto.Device device, String coordinates, double altitude, float accuracy, float speed, long time) {

        Connection conn = Database.Connect();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        try {

            sql = "SELECT TOP 1 * FROM device_location WHERE did = ? ORDER BY time_server_create DESC;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, device.ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                if ((coordinates.equals(rs.getString("coordinates"))) && (Double.compare(altitude, rs.getFloat("altitude")) == 0)) {
                    return;
                }
            }

            sql = "INSERT INTO device_location (did, coordinates,altitude,accuracy,speed, title , [read], time_device_create, time_server_create) VALUES (  ?,? ,?,?,?, ? , ?,  ? , ?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, device.ID);
            ps.setString(2, coordinates);
            ps.setDouble(3, altitude);
            ps.setFloat(4, accuracy);
            ps.setFloat(5, speed);
            ps.setString(6, null);
            ps.setBoolean(7, false);
            ps.setLong(8, time);
            ps.setLong(9, Time.Unix.Now());

            if (ps.executeUpdate() <= 0) {
                return;
            }

            rs = ps.getGeneratedKeys();

            rs.next();
            final int id = rs.getInt(1);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    PreparedStatement ps;
                    String sql;
                    try {
                        String title = null;
                        try {
                            String url = "https://www.google.com/maps/place/" + URLEncoder.encode(coordinates, "UTF-8");
                            URL obj = new URL(url);
                            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                            con.setRequestMethod("GET");
                            con.connect();
                            int responseCode = con.getResponseCode();
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(con.getInputStream(), "UTF-8"));
                            String inputLine;
                            StringBuffer response = new StringBuffer();
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();

                            String line = StringUtils.substringBetween(response.toString(), "<head>", "</head>");
                            String pattern = "(?<= content=\\\")[^\\\"]*(?=\\\" property=\\\"og:description\\\")";
                            Pattern r = Pattern.compile(pattern);
                            Matcher m = r.matcher(line);
                            if ((m.find()) && (m.group(0).indexOf("Find local businesses, view maps and get driving directions in Google Maps.") == -1)) {
                                title = m.group(0);
                            } else {
                                try {
                                    line = StringUtils.substringBetween(response.toString(), "\"E\",\"", "\",\"https://lh5.googleusercontent.");
                                    if (line == null) {
                                        try {
                                            line = StringUtils.substringBetween(response.toString(), "window.APP_INITIALIZATION_STATE", "];");
                                            line = StringUtils.substringBetween(line + "hjeheheheheh2354324324", "Starred places", "hjeheheheheh2354324324");
                                            String[] xxx = StringUtils.split(line, "\\\"");
                                            title = xxx[9];
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    } else {
                                        title = line;
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        if (title == null) {
                            title = null;
                        } else if ("Asia".equals(title)) {
                            title = null;
                        } else if ("Find Local Businesses, View Maps And Get Driving Directions In Google Maps.".equals(title)) {
                        } else {
                            title = WordUtils.capitalizeFully(title);
                            title = title.replace("Unnamed Road, ", "");
                            title = title.replace("Hanoi", "Hà Nội");
                            title = title.replace("Hà Nội 100000", "Hà Nội");
                            title = title.replace("Bac Ninh Province", "Bắc Ninh");
                            title = title.replace(" District", "");
                            title = title.replace("Bac Giang", "Bắc Giang");
                        }

                        sql = "UPDATE device_location SET title=? WHERE id=?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, title);
                        ps.setInt(2, id);
                        ps.executeUpdate();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

            String sql = "SELECT coordinates FROM [device_location] INNER JOIN device ON [device_location].did = device.id INNER JOIN account ON device.aid = account.id WHERE account.id = ? AND device_location.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.ID);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                jobj.put("Status", 3);
                jobj.put("Message", "The location does not exist in the server.");
                return;
            }

            sql = "UPDATE device_location SET device_location.[read] = 1 FROM \n"
                    + "    device_location \n"
                    + "    WHERE device_location.id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<body style='margin: 0px !important;'><iframe id=\"if\" width=\"100%\" height=\"100%\" frameborder=\"0\" style=\"border:0\" allowfullscreen src='http://maps.google.com/maps?q=" + rs.getString("coordinates") + "&z=18&output=embed'></iframe></body>");
                out.flush();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static class History {

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
                String sql = "SELECT [dbo].device_location.id as id, [dbo].device_location.coordinates as coordinates, [dbo].device_location.title as title, [dbo].device_location.time_device_create as [time], [dbo].device_location.[read] as [read] FROM [dbo].device_location   WHERE [dbo].device_location.did = ? ORDER BY [dbo].device_location.time_device_create DESC \n"
                        + "OFFSET ? ROWS\n"
                        + "FETCH NEXT ? ROWS ONLY ";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, device.ID);
                ps.setInt(2, (page - 1) * limit);
                ps.setInt(3, limit);

                ResultSet rs = ps.executeQuery();

                JSONArray jobj_l = new JSONArray();
                int count = 0;
                while (rs.next()) {

                    JSONObject record = new JSONObject();
                    record.put("ID", rs.getInt("id"));
                    record.put("Title", (rs.getString("title") == null) ? "Unknown" : rs.getString("title"));
                    record.put("Coordinates", rs.getString("coordinates"));
                    record.put("Read", rs.getBoolean("read"));
                    record.put("Time", rs.getLong("time"));

                    jobj_l.put(record);
                    count++;
                }

                jobj.put("List", jobj_l);

                sql = "SELECT COUNT(id) as count FROM [dbo].device_location  WHERE device_location.did = ? ;";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, device.ID);
                rs = ps.executeQuery();
                rs.next();

                jobj.put("TotalOfLocation", rs.getInt("count"));

                jobj.put("Status", 0);
                jobj.remove("Message");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jobj;
        }
    }

}
